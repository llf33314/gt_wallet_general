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
      <el-breadcrumb-item>审核成功</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-c666">
      <i class="el-icon-circle-check"></i>
      <p>您的多粉钱包审核成功，请点击下一步进入多粉钱包设置</p>
    </div>
    <div style="padding-top:100px;">
      <el-button type="primary">下一步</el-button>
    </div>
  </section>
</template>
<script>
  export default {

  }

</script>
