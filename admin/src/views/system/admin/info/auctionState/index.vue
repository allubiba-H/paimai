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
      max-height="500"
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
      <el-table-column label="拍品图片" prop="gpic">
        <template slot-scope="scope">
          <el-image style="width: 50px; height: 50px"
                    :src="'http://localhost:9999/upload/img/'+scope.row.gpic">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="起拍价" prop="bprice">
        <template slot-scope="scope">
          <el-col>{{ scope.row.bprice | currencyFilter }}</el-col>
        </template>
      </el-table-column>

      <el-table-column label="竞拍开始时间" min-width="100" align="center" prop="stime">
      </el-table-column>
      <el-table-column label="竞拍结束时间" min-width="100" align="center" prop="etime">
      </el-table-column>
      <el-table-column label="当前状态" prop="state">
        <template slot-scope="scope">
          <div v-for="i in stateList">
          <el-tag v-if="scope.row.state === i.state" type="info">{{ i.name }}</el-tag>
          </div>
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
<!--    <el-dialog title="編輯"-->
<!--               :visible.sync="dialogFormVisible"-->
<!--               width="400px"-->
<!--               top="50px">-->
<!--      <el-form :model="form" size="small">-->
<!--        <el-form-item label="序号">-->
<!--          <el-input v-model="form.aid"-->
<!--                    auto-complete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="用户名">-->
<!--          <el-input v-model="form.aname"-->
<!--                    auto-complete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="密码">-->
<!--          <el-input v-model="form.apwd"-->
<!--                    auto-complete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="权限">-->
<!--          <el-input v-model.number="form.rid"-->
<!--                    auto-complete="off"></el-input>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      <div slot="footer"-->
<!--           class="dialog-footer">-->
<!--        <el-button @click="dialogFormVisible = false">取 消</el-button>-->
<!--        <el-button type="primary"-->
<!--                   @click="handleSet()">确 定-->
<!--        </el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
  </div>
</template>
<script>
import {getAllAdmin} from "@/api/auction";

export default {
  data() {
    return {
      listLoading: false,
      list: null,
      search: '',
      page_count: 0,
      page_current: 1,
      page_size: 10,
      info_total: 0,
      stateList: [
        {
          name: '审核中', state: 1
        }, {
          name: '审核通过', state: 2
        }, {
          name: '审核失败', state: 3
        }, {
          name: '竞拍中', state: 4
        }, {
          name: '竞拍结束', state: 5
        }, {
          name: '交易结束', state: 6
        }
      ]
    }
  },
  created() {
    this.fetchData(this.page_current,this.page_size);
  },
  methods: {
    fetchData(no,size) {
      getAllAdmin(no,size).then((res)=>{
        let p = res.data;
        this.page_current = p.current;
        this.page_size = p.size;
        this.page_count = p.pages;
        this.info_total = p.total;
        this.list = p.records;
      })

    },
    onSubmit() {
      console.log('submit!');
    },
    onSearch() {

    },
    handleSizeChange(val) {
      this.page_size = val;
      this.fetchData(this.page_current, this.page_size);
    },
    handleCurrentChange(val) {
      this.page_current = val;
      this.fetchData(this.page_current, this.page_size);

    },
    handleEdit(){

    },
    handleDelete() {

    }
  },
  filters: {
    currencyFilter(money) {
      return '￥' + money;
    }
  }
}
</script>
