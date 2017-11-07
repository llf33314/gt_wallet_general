<!-- null-data.vue组件 -->
<template>
  <div class="gt-nomsg">
    <div class="icon"><i class="el-icon-warning"></i></div>
    <p class="text">
      <slot></slot>
    </p>
  </div>
</template>

<script>
  export default {
    props: {},
    methods: {
      handleClick(evt) {
        this.$emit('click', evt)
      },
    }
  }
</script>

<style lang="scss" type="text/css" scoped>
  .gt-nomsg {
    text-align: center;
    border: 1px solid #dfe6ec;
    font-size: 14px;
    color: #999;

  .text {
    padding-bottom: 15px;
  }

  .icon {
    color: #cecece;
    font-size: 30px;
    padding: 27px 0 10px;
  }

  span {
    color: #20a0ff;
    cursor: pointer;
  }

  }
</style>
