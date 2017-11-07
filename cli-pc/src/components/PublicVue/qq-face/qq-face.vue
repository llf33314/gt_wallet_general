<template>
  <div>
    <div class="emoji-content">
      <div class="emoji-title">
        <img @click="showEmojiCentont" :src="'./static/js/lib/qq-face/img/icon.png'" />
      </div>
      <textarea v-model="textareaEmoji" class="emoji-textarea " @focus="showEmojiCentont(1)" placeholder="最多可输入100个字符"></textarea>
      <div class="show-emoji-content" v-if="showEomji">
        <ul>
          <li v-for="(item,index) in  emojiAlias" class="emoji-item" @click="selectEmoji(item)">
            <img :src="'./static/js/lib/qq-face/img/qq/'+(index+1)+'.gif'">
          </li>
        </ul>
      </div>
    </div>
    <div class="community-post-picture" v-if="showUploadPicture" style="margin-left:70px;">
      <el-upload class="upload-demo" ref="upload" action="https://jsonplaceholder.typicode.com/posts/" :on-preview="handlePreview"
        :on-remove="handleRemove" :on-change="handleChange" :before-upload="beforeUpload" :file-list="fileListItem" list-type="picture-card"
        multiple="true" :auto-upload="false" >
        <i class="el-icon-plus"></i>
        <!--<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
      </el-upload>
      <el-dialog v-model="dialogVisible" title="图片" size="small" :modal="false">
        <img width="100%" :src="dialogImageUrl" alt="">
      </el-dialog>
    </div>
  </div>
</template>

<script>
  // 在assets设置依赖加载公共js的jquery
  const _emojiAlias = [
    '微笑', '撇嘴', '流口水', '发呆', '得意', '流泪', '害羞', '闭嘴', '睡', '大哭', '尴尬', '发怒', '调皮', '呲牙笑', '惊讶',
    '难过', '酷', '冷汗', '抓狂', '吐', '偷笑', '可爱', '白眼', '傲慢', '饥饿', '困的', '惊恐', '流汗', '憨笑', '大兵', '奋斗',
    '咒骂', '疑问', '嘘', '晕', '折磨', '衰', '骷髅', '敲打', '再见', '擦汗', '抠鼻', '鼓掌', '糗大了', '坏笑', '左哼', '右哼',
    '打哈欠', '鄙视', '委屈', '快哭了', '阴险', '亲嘴', '惊讶', '撒娇', '菜刀', '啤酒', '咖啡', '米饭', '猪头', '玫瑰花', '枯萎的玫瑰花',
    '红唇', '爱心', '爱心', '蛋糕', '闪电', '炸弹', '沾血的刀', '足球', '甲壳虫', '大便', '拥抱', '大拇指上', '大拇指下', '握手',
    '剪刀手', '抱拳', '勾引', '五指握拳', '小拇指', '我爱你', 'no', 'ok', '跳跳', '发抖', '怄火', '磕头', '回头', '跳绳', '街舞',
  ]
  export default {
    data() {
      return {
        emojiAlias: _emojiAlias,
        textareaEmoji: '',
        showEomji: false,
        fileListItem: [],
        dialogImageUrl: '',
        dialogVisible: false,
        showUploadPicture: true
      }
    },
    props: {
      showUploadPicture: {
        type: Boolean,
        default: true
      }
    },
    methods: {
      /*
      * 显示表情图片
      * */
      showEmojiCentont(type) {
        this.showEomji = !this.showEomji
        if (type == 1) {
          this.showEomji = false
        }
      },
      /*
      * 选择表情
      * */
      selectEmoji(type) {
        var type = ':' + type + ':'
        var textareaEmoji = this.textareaEmoji + type
        this.textareaEmoji = textareaEmoji
        console.log(textareaEmoji)
      },
      /*
      * 图片上传到服务器
      * */
      submitUpload() {
        console.log(this.$refs.upload.uploadFiles)
        const list = this.$refs.upload.uploadFiles
        var flag = false
        list.forEach(function (item) {
          if (item.size > 20000) {
            flag = true
          }
        })
        if (flag) {
          this.$message.error('只能上传jpg/png文件，且不超过2Mkb');
        }
        this.$refs.upload.submit();
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      handleChange(file, fileList) {
        this.fileListItem = fileList.slice(0, 9);
        if (this.fileListItem.length >= 9) {
          this.$message.error('最多可上传9张图片!');
        }

      },
      beforeUpload(file) {

      }
    }
  }
</script>

<style lang="scss" type="text/css" scoped>
  .emoji-content {
    border: 1px solid #dfe6ec;
    height: 200px;
    width: 500px;
    position: relative;
    float: left;
    margin-bottom: 20px;
    .emoji-textarea {
      border: 0;
      height: 157px;
      width: 498px;
      outline: none;
      box-sizing: border-box;
      resize: none;
      text-indent: 5px;
      color: #1f2d3d;
      font-size: 14px;
      line-height: 1.5;
    }
  }

  .emoji-title {
    background-color: #eef1f6;
    line-height: 41px;
    height: 41px;

    img {
      float: left;
      margin-left: 25px;
      margin-top: 13px;
      cursor: pointer;
    }

  }

  .show-emoji-content {
    width: 498px;
    padding: 5px;
    overflow: hidden;
    border: 1px solid #8c939d;
    box-sizing: border-box;
    position: absolute;
    top: 198px;
    left: 0;
    background-color: #fff;
    z-index: 1;


  }

  .emoji-item {
    float: left;
  }

  .community-post-picture {
    
  }
</style>