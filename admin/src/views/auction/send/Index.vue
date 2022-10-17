<template>
  <div>
    <el-row>
      <el-col :span="12">
        <el-form :model="auctionForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="拍品名称" required>
            <el-input v-model="auctionForm.gname"></el-input>
          </el-form-item>
          <el-form-item label="商品类型" required>
            <el-select v-model="auctionForm.tid" placeholder="请选择商品类型">
              <el-option v-for="item in typesList" :label="item.tname" :value="item.tid"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="起拍价" required>
            <el-input-number v-model="auctionForm.bprice" controls-position="right" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="增长价" required>
            <el-input-number v-model="auctionForm.increase" controls-position="right" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="保证金" required>
            <el-input-number v-model="auctionForm.abmoney" controls-position="right" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="开始时间" required>
            <div class="block">
              <el-date-picker
                v-model="auctionForm.time"
                type="daterange"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="pickerOptions">
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item label="备注">
            <el-input type="textarea" v-model="auctionForm.cbackup"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('auctionForm')">立即创建</el-button>
            <el-button @click="resetForm('auctionForm')">重置</el-button>
          </el-form-item>

        </el-form>
      </el-col>
      <el-col :span="12">
        <el-upload
          class="upload-demo"
          ref="upload"
          action="http://localhost:8888/paipai/auction/addAuction"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :file-list="fileList"
          :auto-upload="false">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-col>
    </el-row>
  </div>
</template>
<script>

import {getPtypes} from "@/api/ptype";

export default {

  data() {
    return {
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      fileList: '',
      typesList: [],
      auctionForm: {
        gname: '',
        tid: '',
        gpic: '',
        bprice: '',
        increase: '',
        abmoney: '',
        cflag: 2,
        time: '',
        cbackup: ''
      },
      dialogImageUrl: '',
      dialogVisible: false,

    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      getPtypes().then((res) => {
        this.typesList = res.data;
      });
    },
    submitForm(formName) {
      console.log(this.auctionForm);
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleRemove(file) {
      this.dialogImageUrl = file;
    },
    handlePreview(file) {
      console.log(file);
    },
    submitUpload(file) {
      this.$refs.upload.submit();
    }
  }
}
</script>
