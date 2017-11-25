<style lang="less">
  .wallet-wallet-set {
    .input-width {
      width: 250px;
    }
    .public-content {
      padding-top: 30px;
    }
    .public-c999 {
      font-size: 13px;
    }
  }

</style>
<template>
  <section class="wallet-wallet-set">
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item :to="{ path: '/wallet/index' }">多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>多粉钱包设置</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-content">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="170px" class="demo-ruleForm">
        <el-form-item label="设置多粉钱包密码：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入您的交易密码" class="input-width"></el-input>
          <p class="public-c999">密码由8-20位数字和字母组成，区分字母大小写</p>
        </el-form-item>
        <el-form-item label="确认多粉钱包密码：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请再次输入您的交易密码" class="input-width"></el-input>
          <p class="public-c999">密码由8-20位数字和字母组成，区分字母大小写</p>
        </el-form-item>
        <el-form-item label="安全手机号：">
          6666
        </el-form-item>
        <el-form-item label="短信验证码：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入6位验证码" class="input-width"></el-input>
          <el-button type="primary">获取验证码</el-button>
        </el-form-item>
        <el-form-item label="绑定手机号：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入手机号" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="短信验证码：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入验证码" class="input-width"></el-input>
          <el-button type="primary">获取验证码</el-button>
        </el-form-item>
        <el-form-item style="padding-top:60px;">
          <el-button type="primary" @click="submitForm('ruleForm')">确认</el-button>
        </el-form-item>
      </el-form>
    </div>
  </section>
</template>
<script>
  export default {
    data() {
      return {
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
    }
  }

</script>
