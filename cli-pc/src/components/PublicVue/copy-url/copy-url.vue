<template>
  <section>
    <el-dialog title="预览" :visible.sync="copeData.copyUrlVisible" @close="$util.closeParentMask" @open="$util.openParentMask"
      size="cope-link">
      <el-form label-width="120px">
        <el-form-item label="页面二维码:">
          <img class="erwema" :src=" $baseURL + '/app/link/buildQrcode?url='+ copeData.url" />
          <el-button type="primary" class="down" @click="down">下载二维码</el-button>
        </el-form-item>
        <el-form-item label="页面链接:" v-if="copeData.url != ''">
          <input type="text" class="el-input__inner" id="gtLongUrl" style="width: 320px;
            margin-right: 17px;" :value="copeData.url">
          <el-button type="primary" data-clipboard-target="#gtLongUrl" aria-label="复制成功！" @click="copy(0)" id="gtLongUrlCopy">复制</el-button>
        </el-form-item>
        <el-form-item label="短信链接:" v-if="copeData.shortUrl != ''">
          <input type="text" class="el-input__inner" id="gtShortUrl" style="width: 320px;
            margin-right: 17px;" :value="copeData.shortUrl">
          <el-button type="primary" data-clipboard-target="#gtShortUrl" aria-label="复制成功！" @click="copy(1)" id="gtShortUrlCopy">复制</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </section>
</template>

<script>
  // 加载复制js
  import Clipboard from 'clipboard'
  export default {
    props: {
      copeData: {
        url: {
          type: String,
          default: ''
        },
        shortUrl: {
          type: String,
          default: ''
        },
        copyUrlVisible: {
          type: Boolean,
          default: false
        },
      }
    },
    methods: {
      handleClick(evt) {
        this.$emit('click', evt);
      },
      down() {
        window.location.href = this.$baseURL + '/app/link/downQrcode?url=' + this.copeData.url
      },
      /*
       * 复制链接
       * */
      copy(type) {
        var self = this
        if (type === 0) {
          var clipboard = new Clipboard("#gtLongUrlCopy");
          clipboard.on('success', function (e) {
            self.$message({
              message: e.trigger.getAttribute('aria-label'),
              type: 'success'
            });
            clipboard.destroy();
          });

        } else if (type === 1) {
          var clipboard = new Clipboard("#gtShortUrlCopy");
          clipboard.on('success', function (e) {
            self.$message({
              message: e.trigger.getAttribute('aria-label'),
              type: 'success'
            });
            clipboard.destroy();
          });
        }
      },
      // 关闭回调
      closeDialog() {
        console.log('close', 999999999)
      }
    }
  }

</script>

<style lang="scss" type="text/css" scoped>


</style>
