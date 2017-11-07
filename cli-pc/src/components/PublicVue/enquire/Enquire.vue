<!-- null-data.vue组件 -->
<template>
  <div class="gt-success-enquire">
    <i v-if="type == 'success' " class="icont el-icon-circle-check"></i>
    <i v-if="type == 'warning' " class="icont el-icon-warning" style="color: #f7ba2a;"></i>
    <p class="title" v-text="title" :style="dps == ''? 'lineHeight:37px':''"></p>
    <p class="dps" v-text="dps"></p>
  </div>
</template>

<script>
  export default {
    data(){
      return {
        title:'',
        dps:'',
        type:'success'
      }
    },
    props: {
      title:{
        type:String,
        default:''
      },
      dps:{
        type:String,
        default:''
      },
      type:{
        type:String,
        default:'success'
      }
    },

  }
</script>

<style lang="scss" type="text/css" scoped>

</style>
