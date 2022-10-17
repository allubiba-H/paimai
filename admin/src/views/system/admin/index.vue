<template>
  <div class="app-container">
    <!--顶部按钮-->
    <div class="top__header">
      <el-row :gutter="20">
        <el-col push="1" :span="4">
          <el-button @click="add" type="primary" icon="el-icon-plus" size="small">添加</el-button>
        </el-col>
        <el-col :span="10">
          <el-button @click="batchDelmethod" type="danger" size="small" icon="el-icon-delete">批量删除</el-button>
        </el-col>
      </el-row>
    </div>
    <!--  表格  -->
    <el-table
      v-loading="listLoading"
      :data="list"
      ref="tableList"
      row-key="aid"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      empty-text="暂无数据"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column align="center" label="ID" width="50" prop="aid">
        <template slot-scope="scope">{{ scope.row.aid }}</template>
      </el-table-column>
      <el-table-column label="用户名" prop="aname">
      </el-table-column>
      <el-table-column label="密码" prop="apwd">
      </el-table-column>
      <el-table-column label="权限" prop="rid" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.rid == 1" effect="plain">
            超级管理员
          </el-tag>
          <el-tag color="Transparent" v-else effect="light">
            采购员
          </el-tag>
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
            @click="handleDelete(scope.row.aid)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 编辑时候的弹出层 -->
    <el-dialog :title="fromTitle" :visible.sync="dialogFormVisible" width="400px" top="50px">
      <el-form ref="modal__info" :model="form" size="small" :rules="rules" status-icon>
        <el-form-item label="序号" prop="aid">
          <el-input v-model="form.aid"
                    auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="aname">
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
                   @click="handleSet('modal__info')">确 定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {del, getAdminByName, getAdminList, insertAdmin} from "@/api/admin";
import {batchDel} from "@/api/common";
import Qs from "qs";

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
    let check_name = (rules, value, callback) => {
      if (!value) {
        return callback(new Error("名字不能为空!"));
      } else if (value.length > 10) {
        return callback(new Error("名字太长了，必须小于10个"))
      }
    }
    return {
      dialogFormVisible: false,
      list: null,
      fromTitle: '',
      listLoading: true,
      search: '',
      tableList: [],
      batch: [],
      form: {
        aid: '',
        aname: '',
        apwd: '',
        rid: '',
      },
      rules: {
        aname: [{validator: check_name, trigger: 'blur'}]
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
      getAdminList().then(response => {
        this.list = response.data;
        this.listLoading = false
      })
    },
    //查询
    OnSearch() {
      this.listLoading = true;
      getAdminByName(this.search).then(response => {
        this.list = (response.data);
        this.listLoading = false;
      })
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
      })
    },
    handleSelectionChange(val) {
      this.tableList = val;
      this.tableList.forEach((v) => {
        this.batch.push(Number.parseInt(v.aid));
      })

    },
    handleSet() {
      this.$refs.modal__info.validate((valid) => {
        if (!valid) {
          this.$msgbox.alert("请检查表单", "温馨提示", {type: "warning", iconClass: 'el-icon-error'})
          console.log("ssssssssss")
        } else {
          insertAdmin(this.form).then(res => {
            this.$message.success(res.data);
            this.fetchData();
            this.dialogFormVisible = false;
          })
        }
      })

    },
    batchDelmethod() {
      if (this.batch.includes(1)) {
        this.$message.error("超级管理员不能删除！")
        return
      }
      batchDel('admin/batchDel', Qs.stringify(this.batch, {indices: false})).then((res) => {
        console.log(res)
      })
    }
    ,
    add() {
      this.form = {};
      this.fromTitle = "添加用户";
      this.dialogFormVisible = true;
    }
  }
}
</script>
<style>
.top__header {
  margin: 15px;
}
</style>
