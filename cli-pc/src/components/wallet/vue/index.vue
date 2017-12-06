<template>
  <div>
    <router-view></router-view>
  </div>
</template>
<script>
  import {
    wallet
  } from './../api/index'
  export default {
    mounted() {
      console.log('///////////////////')
      this.isPass()
      wallet.findMember().then(res => {
        console.log(res, 'res*********')
      })
    },
    methods: {
      // 查询会员信息
      getVipMsg() {
        console.log(6666)
        $.ajax({
          url: this.DFPAYDOMAIN + '/findMember',
          type: 'GET',
          dataType: 'json',
          success: (res) => {
            console.log(res, '查询会员信息')
            //-2:删除,-1:锁定用户,0:创建,1:审核中,3:正常使用)
            // res.data.status = 3
            if (res.data.status == 0) {
              this.$router.push({
                path: '/wallet/individual/open'
              })
            } else if (res.data.status == 1) {
              this.$router.push({
                path: '/wallet/individual/auditing'
              })
            } else if (res.data.status == 3) {
              if (res.data.memberType == 2) {
                this.$router.push({
                  path: '/wallet/individual/index'
                })
              }
            }
          }
        })
      },
      // 判断商家是否开通多粉钱包
      isPass() {
        $.ajax({
          url: this.DFPAYDOMAIN + '/isPassWallet',
          type: 'GET',
          dataType: 'json',
          success: (res) => {
            console.log(res, '判断商家是否开通多粉钱包')
            //0 :已开通, 1:未开通
            // res.code = 1
            if (res.code == 0) {
              this.getVipMsg()
            } else {
              this.$router.push({
                path: '/wallet/noOpen'
              })
            }
          }
        })
      }
    }
  }

</script>
<style lang="less">
  

</style>
