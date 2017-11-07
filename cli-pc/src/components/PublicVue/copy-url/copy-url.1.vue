<template>
  <section style="display:inline-block">
    
    <el-button v-if="type == 'big'" type="primary" @click="showCopeUrl()" >文件链接</el-button>
    <el-button v-if="type == 'small'" @click="showCopeUrl()" class="gt-button-normal blue">文件链接</el-button>

    <el-dialog title="预览" :visible.sync="copyUrlVisible" @close="$util.closeParentMask" @open="$util.openParentMask" size="cope-link">
      <el-form label-width="120px">
        <el-form-item label="页面二维码:">
          <img class="erwema" :src="'http://192.168.3.98:7073/app/link/buildQrcode?url='+longUrl" />
          <el-button type="primary" class="down" @click="down">下载二维码</el-button>
        </el-form-item>
        <el-form-item label="页面链接:" v-if="longUrl != ''">
          <el-input class="el-input" v-model="longUrl" id="gtLongUrl" :value="longUrl"></el-input>
          <el-button type="primary" data-clipboard-target="#gtLongUrl" aria-label="复制成功！" @click="copy(0)" id="gtLongUrlCopy">复制</el-button>
        </el-form-item>
        <el-form-item label="短信链接:" v-if="shortUrl != ''">
          <el-input class="el-input" v-model="shortUrl" id="gtShortUrl" :value="shortUrl"></el-input>
          <el-button type="primary" data-clipboard-target="#gtShortUrl" aria-label="复制成功！" @click="copy(1)" id="gtShortUrlCopy">复制</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <solt></solt>
  </section>
</template>

<script>
  // 加载复制js
  import Clipboard from './../../../assets/js/copy-vendor.js'
  export default {
    data(){
      return {
        copyUrlVisible: false,
        longUrl:'',
        shortUrl:''
      }
    },
    props: {
      data: {
        url: '',
        shortUrl: ''
      },
      type:{
        type:String,
        default:'big'
      }
    },
    methods: {
      handleClick(evt) {
        this.$emit('click', evt);
      },
      down() {
        window.location.href = 'http://192.168.3.98:7073/app/link/downQrcode?url=' + this.longUrl
      },
      // 获取链接信息
      showCopeUrl() {
        console.log(this.data)
        let vm = this
        let url = BASEDOMAIN + this.data.url 
        axios.post(url, {
          "mainId": this.data.mainId
        }).then((res)=>{
            console.log(res)
            if(res.data.code == 100){
              vm.copyUrlVisible = true
              vm.longUrl = res.data.data.mobileUrl
            }
        })
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
    }
  }

</script>

<style lang="scss" type="text/css" scoped>


</style>
