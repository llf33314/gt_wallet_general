<style lang="stylus">
  .wallet-audit-failure {
    text-align: center;
    font-size: 14px;
    .el-icon-warning {
      font-size: 40px;
      color: #f7ba2a;
      margin-bottom: 20px;
    }
    .top {
      padding-top: 65px;
      padding-bottom: 30px;
      margin-bottom:30px;
      border-bottom: 1px solid #eeeeee;
    }
    p {
      line-height: 2;
    }
    .bottom{
      width: 360x;
      display: inline-block;
      text-align: left;
      margin-bottom:65px;
    }
  }

</style>
<template>
  <section class="wallet-audit-failure">
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item :to="{ path: '/wallet/index' }">多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>个人开通</el-breadcrumb-item>
      <el-breadcrumb-item>审核失败</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-content public-c666">
      <div class="top">
        <i class="el-icon-warning"></i>
        <p>您的多粉钱包业务审核失败</p>
      </div>
      <div class="bottom">
        <p>提交的资料未通过审核，具体原因如下：</p>
        <p>1.个人信息填写有误，请认真填写</p>
        <p>2.证件上传失败，证件不符合要求，请认真确认后再填写</p>
        <p>3.填写信息与上传照片不符合，请认真确认后再填写</p>
      </div>
      <div>
          <el-button type="primary">重新填写</el-button>
      </div>
    </div>
  </section>
</template>
<script>
  export default {

  }

</script>
