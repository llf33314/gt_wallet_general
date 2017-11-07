<template>
  <section>
    <el-breadcrumb separator="/" class="gt-crumbs">
      <el-breadcrumb-item :to="{ path: '/' }">特色应用</el-breadcrumb-item>
      <el-breadcrumb-item>欢迎页</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="gt-content">
      <div class="gt-gray-region">
        <el-button type="primary" @click="getData">请求数据</el-button>
      </div>

      <div class="row">
        <gt-null-data>您还没有授权人信息，
          <span @click="test()">点击这里</span>添加授权人吧</gt-null-data>
      </div>
      <div class="row">
        <el-button :plain="true" type="info" :autofocus="true" size="mini">管理社区</el-button>

        <el-button :plain="true" type="danger" size="mini">删除</el-button>
      </div>
      <div class="row">
        <gt-null-data>还没有相关数据</gt-null-data>
      </div>

      <div class="row">
        <el-button type="primary" @click="askDelete">删除询问</el-button>

        <el-dialog title="提示" :visible.sync="askDeleteVisible" size="askDeleteVisible" top="20%" :close-on-click-modal="false">
          <gt-enquire type="warning" title="999是的大幅杀跌" dps="3333大幅杀跌"></gt-enquire>

          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="askDeleteVisible = false">确 定</el-button>
            <el-button @click="askDeleteVisible = false">取 消</el-button>
          </div>
        </el-dialog>
      </div>

      <div class="row">
        <el-button type="success" @click="askDelete2">成功询问</el-button>

        <el-dialog title="提示" :visible.sync="askDeleteVisible2" size="askDeleteVisible" top="20%" :close-on-click-modal="false">

          <gt-enquire type="success" title="999是的大幅杀跌" dps="3333大幅杀跌"></gt-enquire>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="askDeleteVisible2 = false">确 定</el-button>
            <el-button @click="askDeleteVisible2 = false">取 消</el-button>
          </div>
        </el-dialog>
      </div>

      <div class="row">
        <el-button type="info" @click="askDelete3">提取链接</el-button>
        <gt-copy-url :copeData="copeData"></gt-copy-url>
      </div>
      <!-- <div class="row">
        <gt-material :materialVisible="materialVisible" :imgList="imgList"></gt-material>
      </div> -->
      <div>
        <el-button type="info" @click="askDelete5">查看图片链接</el-button>
      </div>
      <div class="row">
        <gt-region-choose :selectedOptions="selectedOptions" @change="regionChange"></gt-region-choose>
      </div>
      <div class="row">
        <gt-audio :src="audio"></gt-audio>
      </div>
      <div class="row">
        <gt-material prop="url" :url="url"  v-on:getChangeUrl="getChangeUrl" width="220" height="220"></gt-material>
      </div>
      <div class="row">
        <gt-show-phone style="float: right">
          <h1>5555</h1>
        </gt-show-phone>
      </div>
    </div>
  </section>
</template>

<script>
  // 加载当前项目下的js文件
  import {
    Dome
  } from './../js/index.js'

  // 在assets设置依赖加载公共js的jquery
  // import $ from './../../../assets/js/jquery-vendor.js'

  // 加载公共模块vue
  import GtCopyUrl from './../../PublicVue/copy-url/copy-url'
  import GtShowPhone from './../../PublicVue/show-phone/show-phone'

  // 加载请求方法
  import {
    requestLogin
  } from './../Api/api'

  export default {
    name: 'welCome',
    data() {
      return {
        audio: 'http://so1.111ttt.com:8282/2017/4/05m/10/298101104389.m4a?tflag=1505446006&pin=61cfd15d266627120c6575f3019774d8&ip=113.106.199.74#.mp3',
        activeName: 'chil1',
        askDeleteVisible: false,
        askDeleteVisible2: false,
        askDeleteVisible3: true,
        formLabelAlign: {
          name: '',
          region: '',
          type: ''
        },
        url: '',
        copeData: {
          // 二维码图片url
          erweima: 'http://duofriend.com/common/getWxQRcode.do?url=http://r25.cn/0/iQVbe25n',
          // 下载图片url
          downUrl: 'http://duofriend.com/common/downQr.do?url=http://r25.cn/0/iQVbe25n',
          // 长链接
          longUrl: '000',
          // 短链接
          shortUrl: '11111',
          copyUrlVisible: false,
          title: '我是标题'
        },
        materialVisible: false,
        imgList: [{
          url: ""
        }],
        selectedOptions: [
          "130000",
          "130200",
          "130203"
        ],
      };
    },
    components: {
      GtCopyUrl,
      GtShowPhone
    },
    mounted() {
      /*
       * 根据url参数判断显示第几个tab标题
       * 需要在router.js设置传参type
       * */
      var type = this.$route.params.type
      if (type == "0") {
        this.activeName = 'chil1'
      } else if (type == "1") {
        this.activeName = 'chil2'
      }

    },
    methods: {
      // 获取更新的图片url
      getChangeUrl(e) {
        console.log(e)
        this.url = e.url
      },
      /*
       * 选择省份地区
       * */
      regionChange(e) {
        this.selectedOptions = e.value
        console.log(this.selectedOptions)
      },
      /*
       * tab切换跳转路由
       * */
      handleClick(tab, event) {
        if (tab.index === "0" || tab === "0") {
          this.$router.push({
            path: '/dome/child1/0'
          }) // child1"0" 表示type = 0
        } else if (tab.index === "1" || tab === "1") {
          this.$router.push({
            path: '/dome/child2/1'
          }) // child1"0" 表示type = 1
        }
      },
      /*
       * 执行js文件里的方法
       * */
      test() {
        Dome.fn('text') //当前项目下的js方法
        Public.fn('Public') // 公共资源的js方法
        console.log($('#app')) //
      },
      /*
       * 请求数据
       * */
      getData() {
        const data = {}
        requestLogin(data).then(data => {
          console.log(data)
        })
      },

      askDelete() {
        this.askDeleteVisible = true
      },
      askDelete2() {
        this.askDeleteVisible2 = true
      },
      askDelete3() {
        this.copeData.copyUrlVisible = true
      },
      askDelete4() {
        this.materialVisible = true
      },
      askDelete5() {
        console.log(this.imgList)
      }
    }
  }

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" type="text/css" scoped>
  /*加载static公共的css*/

  .row {
    margin: 30px 0;
    overflow: hidden
  }
  /*@import "./../../../../static/css/lib/devices.css";*/

</style>
