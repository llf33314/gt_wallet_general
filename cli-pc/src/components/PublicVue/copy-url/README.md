### 公共功能组件===提取链接
#### 调用方法：
```aidl
  // vue
  <el-button type="info" @click="showCopeUrl">提取链接</el-button>
  <gt-copy-url :copeData="copeData"></gt-copy-url>
  
  
  // js
  <script>
    export default {
      data(){
        copeData:{
          url:'',
          shortUrl: '', 
          copyUrlVisible:false,
        }
      },
      methods:{
        showCopeUrl(){
          this.copeData.copyUrlVisible = true
        }
      }
    }
  </script>
```
