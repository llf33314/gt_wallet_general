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
      <el-breadcrumb-item :to="{ path: '/wallet/index' }">多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>个人开通</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-content public-top20">
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
        <el-form-item label="身份证正面：" prop="identitycardUrl1File">
          <el-upload :action="DFPAYDOMAIN+'/wcommon/upload'" list-type="picture-card" class="public-fl" :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible" width="50%">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
          <!-- <input class="public-fl identitycardUrl1File" type="file"> -->
          <div class="dome-img public-c333">
            <span>示例：</span>
            <img src="./../img/c1.jpg" alt="">
          </div>
        </el-form-item>
        <el-form-item label="身份证背面：" prop="identitycardUrl2File">
            <el-upload :action="DFPAYDOMAIN+'/wcommon/upload'" list-type="picture-card" class="public-fl" :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible" width="50%">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
          <div class="dome-img public-c333">
            <span>示例：</span>
            <img src="./../img/c2.jpg" alt="">
          </div>
        </el-form-item>
        <el-form-item label="个人账户：" prop="cardNo">
          <el-input v-model="ruleForm.cardNo" placeholder="请输入个人账户" class="input-width"></el-input>
          <p class="public-c666">此为多粉钱包安全卡，绑定后不能直接修改。如需修改请联系：***-***</p>
        </el-form-item>
        <el-form-item label="开户人姓名：" prop="bankName">
          <el-input v-model="ruleForm.bankName" placeholder="请输入个人账户" class="input-width"></el-input>
          <p class="public-c666">必须与注册人姓名一致</p>
        </el-form-item>
        <el-form-item label="银行卡预留手机号：" prop="phone">
          <el-input v-model="ruleForm.phone" placeholder="请输入银行卡预留手机号" class="input-width"></el-input>
        </el-form-item>
        <!-- <el-form-item label="短信验证：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入验证码" class="input-width"></el-input>
          <el-button type="primary">获取验证码</el-button>
        </el-form-item> -->
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">确认开通</el-button>
        </el-form-item>
      </el-form>
      <draw-cash></draw-cash>
    </div>
  </section>
</template>
<script>
  import {
    wallet
  } from './../api'
  import DrawCash from "./../template/drawcash.vue";
  export default {
    components: {
      DrawCash
    },
    data() {
      return {
        dialogImageUrl: '',
        dialogVisible: false,
        imageUrl: '',
        ruleForm: {
          identitycardUrl1File: '',
          identitycardUrl2File: '',
          // identitycardUrl1File: function(){return document.querySelectorAll('.identitycardUrl1File')[0].value}, //身份证正面
          // identitycardUrl2File: function(){return document.querySelectorAll('.identitycardUrl2File')[0].value}, //身份证反面
          memberId: '',
          name: '李小强', //注册人姓名
          identityNo: '441621199011164018', //身份证号码
          cardNo: '99967484641657486', //银行卡号
          phone: '15902042654', //银行预留手机
          bankName: '李小强', //银行卡开户人姓名(必须与注册人姓名一致)
          unionBank: '99967484641657486' //支付行号
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
          // identitycardUrl1File: [{
          //   message: '请上传身份证正面',
          //   trigger: 'change'
          // }],
          // identitycardUrl2File: [{
          //   message: '请上传身份证背面',
          //   trigger: 'change'
          // }],
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
        },
      }
    },
    mounted() {
      this.getVipNum()
    },
    methods: {
      //获取会员id
      getVipNum() {
        wallet.register({
          memberType: 3
        }).then(res => {
          console.log(res.data, '会员id')
          if (res.code != 0) {
            this.$message.error(res.msg);
          } else {
            this.ruleForm.memberId = res.data
          }
        })
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      submitForm(formName) {
        console.log(document.querySelectorAll('.identitycardUrl1File')[0].value)
        this.$refs[formName].validate((valid) => {
          if (valid) {
            wallet.saveIndividual(this.ruleForm).then(res => {
              console.log(res, 'res')
              console.log(this.ruleForm)
            })
          } else {
            console.log('error submit!!');
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
