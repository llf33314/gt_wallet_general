<template>
  <section>
    <script id="editor" type="text/plain" style="width:1024px;height:300px;"></script>
    <gt-material ref="gtuueditor" prop="gt-ueditor" :url="url" v-on:getChangeUrl="getChangeUrl" width="0" height="0"></gt-material>
  </section>
</template>
<script>
  import './../../../../static/js/lib/ueditor/ueditor.config.js'
  import './../../../../static/js/lib/ueditor/ueditor.all.js'
  import './../../../../static/js/lib/ueditor/lang/zh-cn/zh-cn.js'
  export default {
    props: {
      html: {
        type: String,
        default: ''
      }
    },
    mounted() {
      this.destroy()
    },
    created() {
      setTimeout(() => {
        this.created()
      }, 300)
    },
    methods: {
      created() {
        const self = this
        try {
          UE.getEditor('editor');
          setTimeout(() => {
            UE.getEditor('editor').execCommand('insertHtml', self.html);
            UE.getEditor('editor').addListener('contentChange', () => {
              self.getContent()
            })
            UE.getEditor('editor').commands['gtsimpleupload'] = {
              execCommand: function () {
                self.openMaterial()
                return true;
              },
              queryCommandState: function (e) {

              }
            };
          }, 100)
        } catch (error) {

        }
      },
      destroy() {
        try {
          UE.getEditor('editor').destroy();
        } catch (error) {

        }
      },
      openMaterial() {
        this.$refs.gtuueditor.visibleMaterial()
      },
      // 获取更新的图片url
      getChangeUrl(e) {
        const img = '<img src="' + e.url + '" />'
        UE.getEditor('editor').execCommand('insertHtml', img);
      },
      getContent() {
        this.$emit('getContent', UE.getEditor('editor').getContent())
      }
    }
  }

</script>
<style scoped>
  @import './../../../../static/js/lib/ueditor/themes/default/css/ueditor.css';

</style>
