<template>

  <div style="margin: 20px">
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="商品类型名称">
        <el-input v-model="form.tname"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input type="textarea" v-model="form.tbackup"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">立即添加</el-button>
      </el-form-item>
    </el-form>
    <el-table
      size="small"
      ref="tableList"
      show-header
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      max-height="500"
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="tid"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="tname"
        label="商品类型">
      </el-table-column>
      <el-table-column
        prop="tbackup"
        label="备注">
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
  </div>
</template>
<script>
import {getPtypesOfPages, insert} from "@/api/ptype";

export default {
  data() {
    return {
      page_current: 1,
      page_size: 10,
      page_count: 0,
      tableData: [],
      info_total: 0,
      form: {
        tname: '',
        tbackup: '',
      }
    }
  },
  created() {
    this.initFetchData(this.page_current, this.page_size);
  },
  methods: {
    onSubmit() {
      insert(this.form).then((res) => {
        this.$message.success(res.data)
        this.form = {};
        this.initFetchData(this.page_current, this.page_size);
      })

    },
    initFetchData(no, size) {
      getPtypesOfPages(no, size).then((res) => {
        let p = res.data;
        this.page_current = p.current;
        this.page_size = p.size;
        this.page_count = p.pages;
        this.info_total = p.total;
        this.tableData = p.records;
      })
    },
    handleSizeChange(val) {
      this.page_size = val;
      this.initFetchData(this.page_current, this.page_size);
    },
    handleCurrentChange(val) {
      this.page_current = val;
      this.initFetchData(this.page_current, this.page_size);

    }
  }
}
</script>
