<!-- video-button.vue组件 -->
<template>
  <el-button type="warning"  videoId="" @click="linkToVideo">
    <i class="iconfont gt-play"></i>视频教程
  </el-button>
</template>

<script>
  export default {
    props: {
      videoId: {
        type: Number,
        default: ''
      }
    },
    methods: {
      linkToVideo() {
        const url = this.$baseURL + '/app/link/' + this.videoId + '/getVideourl'
        window.open(url)
      }
    }
  }

</script>

<style lang="scss" type="text/css" scoped>


</style>
