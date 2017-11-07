<template>
  <el-dialog title="提示" :visible.sync="showTip" class="gt-del-tip" @close="hide">
    <div class="el-message-box__status el-icon-warning"></div>
    <div class="mg-l50">
      <slot>
        <p class="c333">确定要删除？</p>
        <p class="c666">点击确定后，就不可以恢复哦~</p>
      </slot>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="$emit('update:showTip', false)">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  props: {
    showTip: {
      type: Boolean,
      default: false
    },
    // 点击确定后需要执行的函数 可有可无 
    confirmFuc: {
      type: Function
    }
  },
  methods: {
    confirm() {
      console.log(typeof this.confirmFuc === "function");
      if (typeof this.confirmFuc === "function") {
        this.confirmFuc();
      } else {
        this.hide()
      }
    },
    hide() {
      this.$emit("update:showTip", false);
    }
  }
};
</script>

<style lang="stylus">
.gt-del-tip .el-dialog {
  width: 560px;
}
</style>