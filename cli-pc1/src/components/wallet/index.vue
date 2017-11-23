<template>
  <div>666
    <router-view></router-view>
  </div>
</template>
<script>
  import {
    Individual
  } from './api'
  export default {
    mounted() {
      Individual.isPassWallet().then(res => {
        console.log(res)
      })
    }
  }

</script>
