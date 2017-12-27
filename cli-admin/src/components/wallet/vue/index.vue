<template>
  <div>
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  mounted() {
    this.isPass()
  },
  methods: {
    // 查询会员信息
    getVipMsg() {
      console.log(6666)
      $.ajax({
        url: this.DFPAYDOMAIN + '/walletMember/findMember',
        type: 'GET',
        dataType: 'json',
        success: (res) => {
          console.log(res, '查询会员信息')
          // 开通
          if (res.code == 0) {

            if (res.data.memberType == 3) { //个人会员
              if (res.data.status == 0) { //创建
                this.$router.push({
                  path: '/wallet/individual/open/' + res.data.id
                })
              } else { //正常使用
                window.sessionStorage.walletId = res.data.id
                this.$router.push({
                  path: '/wallet/individual/index'
                })
              }
            } else { //企业会员
              if (res.data.status == 0) {  //创建
                this.$router.push({
                  path: '/wallet/company/open/base/' + res.data.id
                })
              } else { //正常使用
                window.sessionStorage.walletId = res.data.id
                this.$router.push({
                  path: '/wallet/company/index'
                })
              }
            }
          } else if (res.code == 1009) { // 未开通
            this.$router.push({
              path: '/wallet/noOpen'
            })
          } else { //异常的错误
            this.$message.error(res.msg)
          }
        }
      })
    },
    // 判断商家是否开通多粉钱包
    isPass() {
      $.ajax({
        url: this.DFPAYDOMAIN + '/walletMember/isPassWallet',
        type: 'GET',
        dataType: 'json',
        success: (res) => {
          console.log(res, '判断商家是否开通多粉钱包')
          //0 :已开通, 1:未开通
          if (res.code == 0) {
            if (res.data == 0) {
              this.getVipMsg()
            } else {
              this.$router.push({
                path: '/wallet/noOpen'
              })
            }
          } else {
            this.$message.error(res.msg)
          }
        }
      })
    }
  }
}

</script>
<style lang="less">

</style>
