<template>
  <div class="app-container">
    <el-button @click="add" type="primary" size="small">添加</el-button>
    <el-table
      size="small"
      v-loading="listLoading"
      :data="list"
      style="width: 100%"
      max-height="390"
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
      <el-table-column align="center" label="ID" prop="mid" width="50">
      </el-table-column>
      <el-table-column align="center" label="操作员" prop="hname" width="80">
      </el-table-column>
      <el-table-column align="center" label="操作金额" prop="cjmoney" width="80">
      </el-table-column>
      <el-table-column align="center" label="操作时间" prop="cjtime">
      </el-table-column>
      <el-table-column label="操作类型" prop="mtype" align="center">
        <template slot-scope="scope">
          <el-tag effect="dark" type="success" v-if="scope.row.mtype == 0">充值</el-tag>
          <el-tag type="info" v-if="scope.row.mtype == 2">体现</el-tag>
          <el-tag type="danger" v-if="scope.row.mtype == 1">消费</el-tag>
        </template>
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
            @click="handleDelete(scope.row.mid)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-count="page_size"
        :current-page="page_current"
        :page-sizes="[10,20]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <!-- 编辑时候的弹出层 -->
    <el-dialog :title="fromTitle"
               :visible.sync="dialogFormVisible"
               width="400px"
               top="50px">
      <el-form :model="form" size="small">
        <el-form-item label="序号">
          <el-input v-model.number="form.mid"
                    auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.hname"
                    auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="金额">
          <el-input v-model.number="form.cjmoney"
                    auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="操作类型">
          <el-input v-model.number="form.mtype"
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
import {del, getAllMoneyRocred, getByName, insert} from "@/api/moneyrecord";

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
      page_current: 1,
      page_size: 10,
      total: 0,
      list: null,
      fromTitle: '',
      listLoading: false,
      search: '',
      tableList: [],
      form: {
        mid: '',
        hname: '',
        cjmoney: '',
        cjtime: '',
        mtype: '',
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    //初始化
    fetchData() {
      this.listLoading = true
      getAllMoneyRocred(this.page_current, this.page_size).then(response => {
        this.list = response.data.records;
        this.page_current = response.data.current;
        this.total = response.data.total;
        this.listLoading = false
      })
    },
    //查询
    OnSearch() {
      this.listLoading = true;
      getByName(this.search).then(response => {
        this.list = response.data.records;
        this.listLoading = false;
      })
    },
    handleEdit(row) {
      this.fromTitle = "编辑用户";
      this.dialogFormVisible = true;
      this.form.mid = row.mid;
      this.form.hname = row.hname;
      this.form.cjmoney = row.cjmoney;
      this.form.mtype = row.mtype;
      this.form.cjtime = row.cjtime;
    },
    handleDelete(id) {
      this.$msgbox("确认要删除？", "温馨提示", "warning").then((res) => {
        del(id).then((response) => {
          this.$message.success(response.data);
          this.fetchData();
        })
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
    handleCurrentChange(val) {
      this.fetchData()
    },
    handleSet() {
      insert(this.form).then(res => {
        this.$message.success(res.data);
        this.fetchData();
        this.dialogFormVisible = false;
      })
    },
    handleSizeChange(val) {
      this.page_size = val
      this.fetchData()
    },
    add() {
      this.form = {};
      this.fromTitle = "添加用户";
      this.dialogFormVisible = true;
    }
  }
}
</script>
