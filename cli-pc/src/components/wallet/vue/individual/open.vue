<style lang="less">
  .wallet-personal-open {
    .input-width {
      width: 250px;
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
    .public-c333 {
      line-height: 1
    }
    .public-c666 {
      line-height: 1
    }
  }

</style>
<template>
  <section class="wallet-personal-open">
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item>多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>个人开通</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-top20" style="margin-left: 30px;">
      <!-- <div class="public-table-title" style="margin-top: 35px;">
        个人信息
      </div> -->
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="170px" class="demo-ruleForm">
        <el-form-item label="姓名：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入姓名" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="身份证号：" prop="identityNo">
          <el-input v-model="ruleForm.identityNo" placeholder="请输入身份证号" class="input-width"></el-input>
        </el-form-item>

        <el-form-item label="银行卡预留手机号：" prop="phone">
          <el-input v-model="ruleForm.phone" placeholder="请输入银行卡预留手机号" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="银行卡开户人姓名：" prop="bankName">
          <el-input v-model="ruleForm.bankName" placeholder="请输入银行卡开户人姓名" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="银行卡号：" prop="cardNo">
          <el-input v-model="ruleForm.cardNo" placeholder="请输入银行卡号" class="input-width"></el-input>
          <p class="public-c666">此为多粉钱包安全卡，绑定后不能直接修改。如需修改请联系：***-***</p>
        </el-form-item>
        <el-form-item label="身份证正面：" prop="identitycardUrl1File">
          <el-upload class="avatar-uploader" ref="identitycardUrl1File" action="" :multiple="true" :show-file-list="false" :http-request="uploadImg"
            :before-upload="beforeAvatarUpload">
            <img v-if="ruleForm.identitycardUrl1File" :src="ruleForm.identitycardUrl1File" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="dome-img public-c333">
            <span>示例：</span>
            <img src="./../../img/c1.jpg" alt="">
          </div>
        </el-form-item>
        <el-form-item label="身份证背面：" prop="identitycardUrl2File">
          <el-upload class="avatar-uploader" ref="identitycardUrl2File" action="" :show-file-list="false" :http-request="uploadImg2"
            :before-upload="beforeAvatarUpload">
            <img v-if="ruleForm.identitycardUrl2File" :src="ruleForm.identitycardUrl2File" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="dome-img public-c333">
            <span>示例：</span>
            <img src="./../../img/c2.jpg" alt="">
          </div>
        </el-form-item>
        <!-- <el-form-item label="短信验证：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入验证码" class="input-width"></el-input>
          <el-button type="primary" @click="sendVerificationCode">获取验证码</el-button>
        </el-form-item> -->
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">确认开通</el-button>
        </el-form-item>
      </el-form>

    </div>
  </section>
</template>
<script>
  import {
    wallet
  } from './../../api/index'
  export default {
    data() {
      return {
        ruleForm: {
          identitycardUrl1File: 'http://113.106.202.53:14884/upload/image/2/wallet/null/1511871076687.png',
          identitycardUrl2File: 'http://113.106.202.53:14884/upload/image/2/wallet/null/1511871076687.png',
          memberId: 9,
          name: '李小强', //注册人姓名
          identityNo: '441621199011164013', //身份证号码
          cardNo: '6217212008009165086', //银行卡号
          phone: '15902042654', //银行预留手机
          bankName: '李小强', //银行卡开户人姓名(必须与注册人姓名一致)
          unionBank: '' //支付行号
        },
        rules: {
          name: [{
              required: true,
              message: '请输入姓名',
              trigger: 'blur'
            },
            {
              min: 2,
              message: '长度在2个字符以上',
              trigger: 'blur'
            }
          ],
          identitycardUrl1File: [{
            required: true,
            message: '请上传身份证正面',
            trigger: 'change'
          }],
          identitycardUrl2File: [{
            required: true,
            message: '请上传身份证背面',
            trigger: 'change'
          }],
          identityNo: [{
            required: true,
            message: '请输入身份证号',
            trigger: 'change'
          }],
          cardNo: [{
            required: true,
            message: '请输入银行卡号',
            trigger: 'change'
          }],
          phone: [{
            required: true,
            message: '请输入银行预留手机',
            trigger: 'change'
          }],
          bankName: [{
            required: true,
            message: '请输入银行卡开户人姓名',
            trigger: 'change'
          }],
        },
        wmemberId: '',
      }
    },
    mounted() {

    },
    methods: {
      // 选择正面
      uploadImg(e) {
        wallet.upload(e.file).then(res => {
          this.ruleForm.identitycardUrl1File = res.data
        })
      },
      //选择反面
      uploadImg2(e) {
        wallet.upload(e.file).then(res => {
          this.ruleForm.identitycardUrl2File = res.data
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
      //发送短信验证码
      sendVerificationCode() {
        $.ajax({
          url: this.DFPAYDOMAIN + '/sendVerificationCode',
          type: 'POST',
          dataType: 'json',
          data: {
            phone: this.ruleForm.phone,
            wmemberId: this.wmemberId
          },
          success: (res) => {
            console.log(res, 'res')
            if (res.code != 0) {
              this.$message.error(res.msg);
            } else {
              this.$message({
                message: '提交成功',
                type: 'success'
              });
            }
          }
        })
      },
      //确认开通
      submitForm(formName) {
        this.$router.push({
          path: '/wallet/individual/index'
        })

        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.ruleForm.memberId = this.$route.params.memberId
            console.log(this.ruleForm, 'this.ruleForm')
            $.ajax({
              url: this.DFPAYDOMAIN + '/walletIndividual/saveIndividual',
              type: 'POST',
              dataType: 'json',
              data: this.ruleForm,
              success: (res) => {
                console.log(res, 'res')
                if (res.code != 0) {
                  this.$message.error(res.msg);
                } else {
                  this.$message({
                    message: res.msg,
                    type: 'success',
                    duration: 2000,
                    onClose: () => {
                      this.$router.push({
                        path: '/wallet/individual/index'
                      })
                    }
                  });
                }
              }
            })
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }

</script>
