<style lang="stylus">
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
  }

</style>
<template>
  <section class="wallet-personal-open">
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item :to="{ path: '/wallet/index' }">多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>个人开通</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-content">
      <div class="public-table-title" style="margin-top: 35px;">
        个人信息
      </div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="170px" class="demo-ruleForm">
        <el-form-item label="姓名：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入姓名" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="身份证号：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入身份证号" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="身份证正面：" prop="name">
          <gt-material class="public-fl" prop="url" :url="url" v-on:getChangeUrl="getChangeUrl" width="220" height="140"></gt-material>
          <div class="dome-img public-c333">
            <span>示例：</span>
            <img src="./../img/c1.jpg" alt="">
          </div>
        </el-form-item>
        <el-form-item label="身份证背面：" prop="name">
          <gt-material class="public-fl" prop="url" :url="url" v-on:getChangeUrl="getChangeUrl" width="220" height="140"></gt-material>
          <div class="dome-img public-c333">
            <span>示例：</span>
            <img src="./../img/c2.jpg" alt="">
          </div>
        </el-form-item>
        <el-form-item label="个人账户：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入个人账户" class="input-width"></el-input>
          <p class="public-c666">此为多粉钱包安全卡，绑定后不能直接修改。如需修改请联系：***-***</p>
        </el-form-item>
        <el-form-item label="开户人姓名：" prop="name">
          6666
        </el-form-item>
        <el-form-item label="银行卡预留手机号：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入银行卡预留手机号" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="短信验证：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入验证码" class="input-width"></el-input>
          <el-button type="primary">获取验证码</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">确认开通</el-button>
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
