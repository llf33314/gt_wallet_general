<style lang="stylus">
  .wallet-success {
    text-align: center;
    font-size: 14px;
    .el-icon-circle-check {
      font-size: 40px;
      color: #34d063;
      margin-bottom: 20px;
    }
    .public-c666 {
      padding-top: 65px;
    }
    p {
      line-height: 2;
    }
  }

</style>
<template>
  <section class="wallet-success">
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item :to="{ path: '/wallet/index' }">多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>个人开通</el-breadcrumb-item>
      <el-breadcrumb-item>注册成功</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-c666">
      <i class="el-icon-circle-check"></i>
      <p>恭喜您完成多粉钱包的注册</p>
      <p>身份信息与银行卡信息需要X个工作日进行审核验证，请您耐心等候。</p>
    </div>
  </section>
</template>
<script>
  export default {

  }

</script>
