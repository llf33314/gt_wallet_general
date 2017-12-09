<style lang="less">
  .wallet-company-open {
    .public-f13 {
      font-weight: 500;
      padding-left: 15px;
    }
    .public-table-title {
      margin-top: 50px;
      margin-bottom: 30px;
    }
    .input-width {
      width: 220px;
    }
    .public-content {
      padding-bottom: 150px;
    }
    .dome-img {
      padding-left: 50px;
      float: left;
      span {
        float: left;
      }
      img {
        vertical-align: text-top;
        height: 140px;
        margin-left: 20px;
        float: left;
      }
    }
    .el-icon-circle-check {
      font-size: 40px;
      color: #34d063;
      margin-bottom: 20px;
    }
    .active-last {
      text-align: center;
      color: #666666;
      font-size: 14px;
      padding-top: 60px;
    }
    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      float: left;
      width: 220px;
      height: 137px;
      background-color: #fbfdff;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #20a0ff;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 220px;
      height: 137px;
      line-height: 137px;
      text-align: center;
    }
    .avatar {
      width: 220px;
      height: 137px;
      display: block;
    }
    .public-bottom-btns {
      background-color: #dfdfdf;
      padding: 10px 0;
      position: fixed;
      width: 100%;
      left: 0;
      bottom: 0;
      text-align: center;
    }
  }

</style>
<template>
  <section class="wallet-company-open">
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item :to="{ path: '/wallet/index' }">多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>企业开通</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-content" style="margin-bottom:0;padding-bottom:0">
      <el-steps :active="active" center="true" style="border-bottom: 1px solid #ddd;padding:20px 0;">
        <el-step title="填写基本信息"></el-step>
        <el-step title="上传证件照片"></el-step>
        <el-step title="提交成功"></el-step>
      </el-steps>
    </div>
    <div v-show="active==1">
      <open-base></open-base>
    </div>
    <div v-show="active==2">
      <div class="public-content">
        <div class="public-table-title">
          企业法人营业执照
        </div>
        <el-form :model="ruleForm4" :rules="rules4" ref="ruleForm4" label-width="170px" class="demo-ruleForm">
          <el-form-item label="营业执照：" prop="doBusinessUrl">
            <el-upload class="avatar-uploader" ref="identitycardUrl1" action="" style="float:left" :multiple="true" :show-file-list="false"
              :http-request="uploadImgDoBusinessUrl" :before-upload="beforeAvatarUpload">
              <img v-if="ruleForm4.doBusinessUrl!=''" :src="ruleForm4.doBusinessUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../../img/c1.jpg" alt="">
            </div>
          </el-form-item>
          <el-form-item label="开户许可证：" prop="licenseUrl">
            <el-upload class="avatar-uploader" ref="licenseUrl" action="" style="float:left" :multiple="true" :show-file-list="false"
              :http-request="uploadImgLicenseUrl" :before-upload="beforeAvatarUpload">
              <img v-if="ruleForm4.licenseUrl!=''" :src="ruleForm4.licenseUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../../img/c1.jpg" alt="">
            </div>
          </el-form-item>
          <el-form-item label="身份证正面：" prop="identitycardUrl1">
            <el-upload class="avatar-uploader" ref="identitycardUrl1" action="" style="float:left" :multiple="true" :show-file-list="false"
              :http-request="uploadImgIdentitycardUrl1" :before-upload="beforeAvatarUpload">
              <img v-if="ruleForm4.identitycardUrl1!=''" :src="ruleForm4.identitycardUrl1" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../../img/c1.jpg" alt="">
            </div>
          </el-form-item>
          <el-form-item label="身份证背面：" prop="identitycardUrl2">
            <el-upload class="avatar-uploader" ref="identitycardUrl2" action="" style="float:left" :show-file-list="false" :http-request="uploadImgIdentitycardUrl2"
              :before-upload="beforeAvatarUpload">
              <img v-if="ruleForm4.identitycardUrl2!=''" :src="ruleForm4.identitycardUrl2" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../../img/c2.jpg" alt="">
            </div>
          </el-form-item>
        </el-form>
      </div>
      <div class="public-bottom-btns">
        <el-button @click="active=1">上一步</el-button>
        <el-button type="primary" @click="avtive2('ruleForm4')">下一步</el-button>
      </div>
    </div>
    <div v-show="active == 3">
      <div class="public-c666 active-last">
        <i class="el-icon-circle-check"></i>
        <p>恭喜您完成多粉钱包的注册</p>
        <p>身份信息与银行卡信息需要X个工作日进行审核验证，请您耐心等候。</p>
      </div>
    </div>


  </section>
</template>
<script>
  import {
    wallet
  } from './../../api/index'
  import OpenBase from './template/base.vue'
  export default {
    components: {
      OpenBase
    },
    data() {
      return {
        ruleForm4: {
          doBusinessUrl: '',
          licenseUrl: '',
          identitycardUrl1: '',
          identitycardUrl2: '',
        },
        rules4: {
          doBusinessUrl: [{
            required: true,
            message: '请上传营业执照',
            trigger: 'change'
          }],
          licenseUrl: [{
            required: true,
            message: '请上传开户许可证',
            trigger: 'change'
          }],
          identitycardUrl1: [{
            required: true,
            message: '请上传身份证正面',
            trigger: 'change'
          }],
          identitycardUrl2: [{
            required: true,
            message: '请上传身份证背面',
            trigger: 'change'
          }],
        },
        
        active: 1,
        // *************
        value1: true,
        radio2: 3,
        checked: true,
        value3: '',
        options: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
        value: '',
        ruleForm: {
          name: '',
        },
        rules: {
          name: [{
              required: true,
              message: '请输入活动名称',
              trigger: 'blur'
            },
            {
              min: 3,
              max: 5,
              message: '长度在 3 到 5 个字符',
              trigger: 'blur'
            }
          ],
        },

      }
    },

    methods: {
      avtive2(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(66)
          } else {
            return false
          }
        });
      },
      uploadImgDoBusinessUrl(e) {
        wallet.upload(e.file).then(res => {
          this.ruleForm4.doBusinessUrl = res.data
        })
      },
      uploadImgLicenseUrl(e) {
        wallet.upload(e.file).then(res => {
          this.ruleForm4.licenseUrl = res.data
        })
      },
      uploadImgIdentitycardUrl1(e) {
        wallet.upload(e.file).then(res => {
          this.ruleForm4.identitycardUrl1 = res.data
        })
      },
      uploadImgIdentitycardUrl2(e) {
        wallet.upload(e.file).then(res => {
          this.ruleForm4.identitycardUrl2 = res.data
        })
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
        const isLt5M = file.size / 1024 / 1024 < 5;
        if (!isJPG) {
          this.$message.error('上传图片只能是 JPG/PNG 格式!');
        }
        if (!isLt5M) {
          this.$message.error('上传图片大小不能超过 5MB!');
        }
        return isJPG && isLt5M;
      },
    }
  }

</script>
