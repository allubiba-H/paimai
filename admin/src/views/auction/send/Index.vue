<template>
  <div>
    <el-form :model="auctionForm" ref="auctionForm" label-width="100px" class="demo-ruleForm" style="margin: 20px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="拍品名称" required prop="gname">
            <el-input v-model="auctionForm.gname"></el-input>
          </el-form-item>
          <el-form-item label="商品类型" required prop="tid">
            <el-select v-model="auctionForm.tid" placeholder="请选择商品类型">
              <el-option v-for="item in typesList" :label="item.tname" :value="item.tid"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="起拍价" required prop="bprice">
            <el-input-number v-model="auctionForm.bprice" controls-position="right" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="增长价" required prop="increase">
            <el-input-number v-model="auctionForm.increase" controls-position="right" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="保证金" required prop="abmoney">
            <el-input-number v-model="auctionForm.abmoney" controls-position="right" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="开始时间" prop="time">
            <div class="block">
              <el-date-picker
                value-format="yyyy-MM-dd"
                v-model="time"
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
          <el-form-item label="备注" required prop="cbackup">
            <el-input type="textarea" v-model="auctionForm.cbackup"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <div class="right-uploader">
            <el-col>图片上传</el-col>
            <el-upload
              class="avatar-uploader "
              action="http://localhost:8888/paipai/auction/upload"
              auto-upload
              multiple
              :show-file-list="true"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>

        </el-col>
      </el-row>
      <el-form-item style="text-align: center">
        <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>

import {getPtypes} from "@/api/ptype";
import {save} from "@/api/auction";

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
      typesList: [],
      auctionForm: {
        gname: '',
        tid: '',
        gpic: '',
        bprice: '',
        increase: '',
        abmoney: '',
        cflag: 2,
        gzan: 0,
        anum: 0,
        createrid: 0,
        cbackup: ''
      },
      time: '',
      imageUrl: '',
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
    submitForm() {
      let [start, end] = this.time;
      this.auctionForm['stime'] = start;
      this.auctionForm['etime'] = end;
      save(this.auctionForm).then((res) => {
        if (res.code == 0) {
          this.$message.success(res.data);
          this.resetForm();
        }
      })

    },
    resetForm() {
      this.$refs['auctionForm'].resetFields();
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.auctionForm.gpic = res.data;
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M;
    },
  }
}
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.right-uploader {
  text-align: center;
  gap: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 90px;
}
</style>
