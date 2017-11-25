<template>
  <div>
    <router-view></router-view>
  </div>
</template>
<script>
  import {
    wallet
  } from './api'
  export default {
    mounted() {
      this.isPass()
      this.getVipMsg()
    },
    methods: {
      // 查询会员信息
      getVipMsg(){
        this.store.commit('getVipMsg', {
          title: 1111,
          path:2222
        })

        wallet.findMember().then(res=>{
          console.log(res,'查询会员信息')
        })
      },
      // 判断商家是否开通多粉钱包
      isPass() {
        wallet.isPassWallet().then(res => {
          console.log(res,'判断商家是否开通多粉钱包')
          if (res.code == 0) {
            //0 :已开通, 1:未开通
            if (res.data == 0) {
              this.$router.push({
                //path: '/wallet/noOpen'
                path: '/wallet/individual/index'
              })
            }
            
          }
        })
      }
    }
  }

</script>
<style lang="less">
  @import './css/index.less';

</style>
