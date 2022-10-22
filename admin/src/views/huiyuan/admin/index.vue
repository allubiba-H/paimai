<template>
  <div class="app-container">
    <el-button @click="add" type="primary" size="small">添加</el-button>
    <el-table
      size="small"
      v-loading="listLoading"
      :data="list"
      style="width: 100%"
      max-height="400"
      ref="tableList"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column align="center" label="ID" prop="hid" width="50">
      </el-table-column>
      <el-table-column label="用户名" prop="hname" width="80">
      </el-table-column>
      <el-table-column label="密码" prop="hpwd" width="60">
      </el-table-column>
      <el-table-column label="手机号" prop="htel">
      </el-table-column>
      <el-table-column label="地址" prop="haddress">
      </el-table-column>
      <el-table-column label="金额" prop="hyue">
      </el-table-column>
      <el-table-column label="冻住金额" prop="hicemoney">
      </el-table-column>
      <el-table-column label="邮箱" prop="hemail">
      </el-table-column>
      <el-table-column label="备注" prop="hbackup">
      </el-table-column>
      <el-table-column label="权限" prop="rid">
      </el-table-column>
      <el-table-column
        align="right">
        <template slot="header" slot-scope="scope">
          <el-input
            @blur="OnSearch"
            v-model="search"
            size="mini"
            placeholder="输入用户名搜索"/>
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.row)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.aid)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page_current"
        :page-size="page_size"
        :page-count="page_count"
        layout="total, sizes, prev, pager, next, jumper"
        :total="info_total">
      </el-pagination>
    </div>
    <!-- 编辑时候的弹出层 -->
    <el-dialog :title="fromTitle"
               :visible.sync="dialogFormVisible"
               width="400px"
               top="50px">
      <el-form :model="form" size="small">
        <el-form-item label="序号">
          <el-input v-model="form.aid"
                    auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.aname"
                    auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.apwd"
                    auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="权限">
          <el-input v-model.number="form.rid"
                    auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer"
           class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="handleSet()">确 定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getAll} from "@/api/huiyuan";
import {search} from "@/api/common";

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      dialogFormVisible: false,
      list: null,
      page_current: 1,
      info_total: 0,
      page_size: 10,
      page_count:0,
      fromTitle: '',
      listLoading: true,
      search: '',
      tableList: [],
      form: {
        aid: '',
        aname: '',
        apwd: '',
        rid: '',
      }
    }
  },
  created() {
    this.fetchData(this.page_current,this.page_size);
  },
  methods: {
    //初始化
    fetchData(page,size) {
      this.listLoading = true
      getAll(page, size).then(response => {
        let res = response.data;
        this.page_current = res.current;
        this.page_size = res.size;
        this.page_count = res.pages;
        this.info_total = res.total;
        this.list = res.records;
        this.listLoading = false;

      })
    },
    //查询
    OnSearch() {
      if (this.search != '') {
        this.listLoading = true;
        search("huiyuan",this.search).then(response => {
          this.list = response.data;
          this.listLoading = false;
        })
      }
      else {
        this.fetchData(this.page_current,this.page_size);
      }

    },
    handleEdit(row) {
      this.fromTitle = "编辑用户";
      this.dialogFormVisible = true;
      this.form.aid = row.aid;
      this.form.aname = row.aname;
      this.form.apwd = row.apwd;
      this.form.rid = row.rid;
    },
    handleDelete(id) {
      this.$msgbox("确认要删除？", "温馨提示", "warning").then((res) => {
        del(id).then((response) => {
          this.$message.success(response.data);
          this.fetchData();
        })
      }).catch((err)=>{
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      })
    },
    handleSelectionChange(val) {
      let arr = [];
      this.tableList = val;
      this.tableList.forEach((v) => {
        arr.push(Number.parseInt(v.aid));
      })
      console.log(arr);
    },
    handleSet() {
      console.log(this.form)
      insertAdmin(this.form).then(res => {
        this.$message.success(res.data);
        this.fetchData();
        this.dialogFormVisible = false;
      })
    },
    add() {
      this.form = {};
      this.fromTitle = "添加用户";
      this.dialogFormVisible = true;
    },
    handleSizeChange(val) {
      this.page_total = val;
    },
    handleCurrentChange(val) {
      this.page_current = val;
    }
  }
}
</script>
