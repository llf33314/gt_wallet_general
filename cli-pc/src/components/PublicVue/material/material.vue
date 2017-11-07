<template>
  <section style="display:inline-block">
    <div class="material-box" :title="url" :style="style[prop]">
      <div v-if="type == 'img'">
        <!-- 显示图片 -->
        <div class="show-img-box" v-if="url" @mouseover.stop="showCover(true)" @mouseout.stop="showCover(false)">
          <div class="show-img-cover plus-box no-border" v-show="coverVisible==prop&&showProp==true">
            <i class="el-icon-view" @click.stop="doLargeImg"></i>
            <i class="el-icon-delete2" @click.stop="doDeleteImg"></i>
          </div>
          <img class="show-img-m" :src="url" :alt="url">
        </div>
        <!-- 打开素材库 -->
        <div class="plus-box" v-if="!showImg[prop] || url==''" @click.stop="visibleMaterial">
          <i class="el-icon-plus" v-if="btnContent == 'icon'"></i>
          <span v-else>{{btnContent}}</span>
        </div>
      </div>
      <div v-if="type == 'text'" @click.stop="visibleMaterial">
        <slot></slot>
      </div>
    </div>
    <!-- 图片放大 -->
    <div class="material-dialog" v-if="largeImgVisible">
      <div class="content">
        <div class="title">图片
          <i @click.stop="closeLargeImg" class="el-dialog__close2 el-icon el-icon-close"></i>
        </div>
        <div class="">
          <img class="show-img-large" :src="url" :alt="url">
        </div>
      </div>
    </div>
    <!-- 素材库 -->
    <div class="material-dialog" v-if="materialVisible==prop">
      <div class="content">
        <div class="title">素材库
          <i class="el-icon-close icon" @click="close"></i>
        </div>
        <div class="">
          <iframe :src="materialUrl" class="material"></iframe>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
  export default {
    data() {
      return {
        largeImgVisible: '',
        materialVisible: null,
        materialUrl: '',
        showImg: {},
        cur: '',
        coverVisible: null,
        style: {},
        showProp: false
      }
    },
    props: {
      prop: {
        type: String,
        default: ''
      },
      url: {
        type: String,
        default: ''
      },
      width: {
        type: String,
        default: '75'
      },
      height: {
        type: String,
        default: '75'
      },
      type: {
        type: String,
        default: 'img'
      },
      selectType: {
        type: String,
        default: 'radio'
      },
      btnContent: {
         type: String,
         default: 'icon'
      }
    },
    mounted: function () {
      let _this = this;
      window.addEventListener("message", function (e) {
        const num = e.data.length - 1
        if (!num) return false;
        var newList = []
        if (_this.selectType == 'radio') {
          e.data.substring(6, num).split(',').forEach((item) => {
            newList.push(item.substring(1, (item.length - 1)))
          })
          if (_this.materialVisible == _this.prop) {
            _this.$set(_this.showImg, _this.prop, newList[1])
            _this.postToParent()
          }
        } else if (_this.selectType == 'select') {
          newList = e.data.substring(7, num - 13)
          console.log(newList)
          if (_this.materialVisible == _this.prop) {
            _this.$set(_this.showImg, _this.prop, newList)
            _this.postToParent()
          }
        }
        _this.materialVisible = null
        _this.showProp = false
      });
    },
    created() {
      this.showImg[this.prop] = this.url
      this.style[this.prop] = {
        width: this.width + 'px',
        height: this.height + 'px'
      }
    },
    methods: {
      //打开素材库
      visibleMaterial() {
        this.materialVisible = this.prop
        if (this.selectType == 'radio') {
          this.materialUrl = window.MATERIALUrl + window.location.href;
        } else if (this.selectType == 'select') {
          this.materialUrl = window.MATERIALUrlMULTI + window.location.href;
        }

      },
      // 放大图片
      doLargeImg() {
        this.largeImgVisible = this.prop
      },
      //删除图片
      doDeleteImg() {
        this.$emit('getChangeUrl', {
          prop: this.prop,
          url: ''
        })
      },
      // 关闭放大图片
      closeLargeImg() {
        this.largeImgVisible = null
      },
      //显示隐藏
      showCover(state) {
        if (state) {
          this.coverVisible = this.prop
          this.showProp = true
        } else {
          this.coverVisible = null
          this.showProp = false
        }
      },
      // 传值给父组件
      postToParent() {
        this.$emit('getChangeUrl', {
          prop: this.prop,
          url: this.showImg[this.prop] || ''
        })
      },
      // 关闭素材库
      close() {
        this.materialVisible = null
      }
    },
  }

</script>
<style lang="scss" type="text/css" scoped>
  /* new */

  .show-img-large {
    max-width: 100%;
  }

  .material-box {
    border-radius: 3px;
    overflow: hidden;
  }
   .material-box >div:first-child {
     height: 100%;
   }

  .plus-box {
    width: 100%;
    height: 100%;
    border: 1px dashed #c0ccda;
    background-color: #fbfdff;
    border-radius: 3px;
    text-align: center;
    box-sizing: border-box;
    -moz-box-align: center;
    /* Firefox */
    display: -moz-box;
    -moz-box-pack: center;
    -moz-box-align: center;
    /* Safari、Opera 以及 Chrome */
    display: -webkit-box;
    -webkit-box-pack: center;
    -webkit-box-align: center;
    /* W3C */
    display: box;
    box-pack: center;
    box-align: center;
    cursor: pointer;
    &.no-border {
      border: 0;
    }
  }

  .show-img-m {
    height: 100%;
    width: 100%;
  }

  .show-img-box {
    background: #cccccc;
    height: 100%;
    position: relative;
  }

  .show-img-cover {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.5);
    color: #fff;
    &.no-border {
      border: 0;
    }
  }

  .show-img-cover i {
    cursor: pointer;
    font-size: 18px;
  }
  /* new */

  .m-content {
    position: relative;
    box-sizing: border-box;
    overflow: hidden;
    display: inline-block;
  }

  .m-content .cover {
    position: absolute;
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
    box-sizing: border-box;
  }

  .material-square {
    text-align: center;
    line-height: 79px;
    background-color: #fbfdff;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    box-sizing: border-box;
    .el-icon-plus {
      color: #c0ccda;
    }
  }

  .delete {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    border: 2px dashed rgba(0, 0, 0, 0);
    left: 0;
    background-color: rgba(0, 0, 0, .3);
    z-index: 1;
    color: #fff;
    font-size: 18px;
    line-height: 79px;
    text-align: center;
    display: none;
    &.show {
      display: block
    }
    i {
      cursor: pointer;
    }
  }

  .el-dialog__close2 {
    float: right;
    cursor: pointer;
    font-size: 16px;
    color: rgb(191, 203, 217);
    margin: 20px 10px;
  }

  .el-dialog__close2:hover {
    color: rgb(32, 160, 255);
  }

  .show-img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }

  .material-dialog {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .3);
    z-index: 9999;
    text-align: center;
    .content {
      background-color: #fff;
      padding: 0 10px 10px;
      overflow: hidden;
      border-radius: 5px;
      min-width: 700px;
      max-width: 1000px;
      display: inline-block;
      margin-top: 10%;
      .title {
        line-height: 50px;
        height: 50px;
        font-weight: 700;
        text-align: left;
      }
    }
  }

  .material {
    width: 100%;
    min-height: 500px;
  }

  .icon {
    float: right;
    margin-top: 15px;
    margin-right: 10px;
    cursor: pointer;
  }

  .icon:hover {
    color: #20a0ff;
  }

</style>
