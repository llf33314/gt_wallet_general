<style lang="stylus">
  .wallet-audit {
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
  <section class="wallet-audit">
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item :to="{ path: '/wallet/index' }">多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>审核中</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-c666">
      <i class="el-icon-circle-check"></i>
      <p>您的多粉钱包业务审核中，请您耐心等候</p>
    </div>
  </section>
</template>
<script>
  export default {

  }

</script>
