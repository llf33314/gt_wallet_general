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
      <el-button @click="getVipNum(2)" type="primary">企业开通</el-button>
      <el-button @click="getVipNum(3)" type="primary">个人开通</el-button>
    </div>
  </div>
</template>
<script>
  import {
    wallet
  } from './../../api/index'
  export default {
    methods: {
      individualOpen(memberId) {
        this.$router.push({
          path: '/wallet/individual/open/' + memberId
        })
      },
      companyOpen(memberId) {
        this.$router.push({
          path: '/wallet/company/open/' + memberId
        })
      },
      //获取会员id
      getVipNum(type) {
        $.ajax({
          url: this.DFPAYDOMAIN + '/register',
          type: 'GET',
          dataType: 'json',
          data: {
            memberType: type
          },
          success: (res) => {
            console.log(res, 'res')
            if (res.code != 0) {
              this.$message.error(res.msg);
            } else {
              if (type == 2) {
                this.companyOpen(res.data)
              } else {
                this.individualOpen(res.data)
              }
            }
          }
        })
      },
    }
  }

</script>
