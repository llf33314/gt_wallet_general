<style lang="less">
  .no-open {
    padding-top: 70px;
    text-align: center;
    p {
      font-size: 14px;
      color: #333333;
      padding: 27px 0 70px;
    }
    i {
      color: #f7ba2a;
      font-size: 35px;
    }
  }

</style>
<template>
  <div class="no-open">
    <i class="el-icon-warning"></i>
    <p>您尚未开通多粉钱包，点击下方按钮开通吧！</p>
    <div class="bts">
      <el-button @click="companyOpen" type="primary">企业开通</el-button>
      <el-button @click="individualOpen" type="primary">个人开通</el-button>
    </div>
  </div>
</template>
<script>
  export default {
    methods: {
      individualOpen() {
        this.$router.push({
          path: '/wallet/individual/open'
        })
      },
      companyOpen() {
        this.$router.push({
          path: '/wallet/company/open'
        })
      }
    }
  }

</script>
