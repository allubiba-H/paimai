<template>
  <div class="app-container">
    <div class="header__container-top">
      <el-input
        @blur="onSearch"
        v-model="search"
        size="mini"
        placeholder="输入拍品名搜索"/>
    </div>
    <el-table
      size="small"
      v-loading="listLoading"
      :data="list"
      style="width: 100%"
      max-height="400"
      ref="tableList"
      show-header
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="ID" width="40" prop="aid">
      </el-table-column>
      <el-table-column label="拍品名" show-overflow-tooltip prop="gname">
      </el-table-column>
      <el-table-column label="拍品类型" prop="ptypes.tname">
      </el-table-column>
      <el-table-column label="拍品图片" prop="gpic">
        <template slot-scope="scope">
          <el-image style="width: 50px; height: 50px"
                    :src="'http://localhost:9999/upload/img/'+scope.row.gpic"
                    :preview-src-list="'http://localhost:9999/upload/img/'+scope.row.gpic">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="拍卖次数" width="80" align="center" prop="anum">
      </el-table-column>
      <el-table-column label="起拍价" prop="bprice">
        <template slot-scope="scope">
          <el-col>{{ scope.row.bprice | currencyFilter }}</el-col>
        </template>
      </el-table-column>
      <el-table-column label="增长价" prop="increase">
        <template slot-scope="scope">
          <el-col>{{ scope.row.increase | currencyFilter }} 📈</el-col>
        </template>
      </el-table-column>
      <el-table-column label="保证金" prop="abmoney">
        <template slot-scope="scope">
          <el-col>{{ scope.row.abmoney | currencyFilter }} 💰</el-col>
        </template>
      </el-table-column>
      <el-table-column label="竞拍开始时间" min-width="100" align="center" prop="stime">
      </el-table-column>
      <el-table-column label="竞拍结束时间" min-width="100" align="center" prop="etime">
      </el-table-column>
      <el-table-column label="当前状态" prop="state">
        <template slot-scope="scope">
          <el-tag type="warning">未审核</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发布者" align="center" prop="huiyuan.hname">
      </el-table-column>

      <el-table-column label="发布者类型" prop="cflag">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.cflag == 1 ? '会员' : '管理员' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="119">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-s-check"
            @click="handleEdit(scope.row.aid)">
          </el-button>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete-solid"
            @click="handleDelete(scope.row.aid)">
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
import {check, del, getAllActions} from "@/api/auction";
import {search} from "@/api/common";

const audit = {
  ok: 1,
  no: 0
}
export default {
  data() {
    return {
      dialogFormVisible: false,
      list: null,
      fromTitle: '',
      listLoading: true,
      search: '',
      page_count: 0,
      page_current: 1,
      page_size: 10,
      page_total: 0,
      info_total: 0,
      form: {}
    }
  },
  created() {
    this.fetchData(this.page_current, this.page_size)
  },
  methods: {
    fetchData(page, size) {
      getAllActions(page, size).then((res) => {
        let p = res.data;
        this.page_current = p.current;
        this.page_size = p.size;
        this.page_count = p.pages;
        this.info_total = p.total;
        this.list = p.records;
        this.listLoading = false;
      })
    },
    add() {

    },
    handleSizeChange(val) {
      this.fetchData(this.page_current, val);
    },
    handleCurrentChange(val) {
      this.fetchData(val, this.page_size);
    },
    handleEdit(row) {
      this.$confirm("确认审核通过吗？", "温馨提示", {
        center: true,
        roundButton: true,
        cancelButtonClass: 'no__btn',
        confirmButtonText: '确定',
        cancelButtonText: '拒绝',
        type: 'info'
      }).then(() => {
        check(row, audit.ok).then(() => {
          this.$message({
            type: 'success',
            message: '审核成功通过!'
          });
          this.fetchData(this.page_current, this.page_size);
        })
      }).catch(() => {
        check(row, audit.no).then(() => {
          this.$message({
            type: 'success',
            message: '审核成功拒绝!'
          });
          this.fetchData(this.page_current, this.page_size);
        })
      })

    },
    handleDelete(row) {
      this.$confirm("确认删除这条数据吗吗？", "温馨提示", {
        center: true,
        roundButton: true,
        cancelButtonClass: 'no__btn',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        del(row).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.fetchData(this.page_current, this.page_size);
        }).catch(() => {
          this.$message({
            type: 'error',
            message: '删除失败，请联系管理员!'
          });
        })
      })
    },
    onSearch() {
      if (this.search != '') {
        search("auction", this.search).then((res) => {
          this.list = res.data
        })
      } else {
        this.fetchData(this.page_current, this.page_size);
      }
    }
  },
  filters: {
    currencyFilter(money) {
      // console.log(this.$currency.format(1000, {code: 'CNY'}))
      return '￥' + money;

    },
  }
}
</script>

<style lang="scss" scoped>
.header__container-top {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;

  .el-input {
    width: 200px;
  }
}
</style>
