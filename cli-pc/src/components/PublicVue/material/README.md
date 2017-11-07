### 公共功能组件=== 获取素材库单张图片url
#### 调用方法：
- 先登录： goodtom账号（密码gt123456）http://deeptel.com.cn/user/toLogin.do

```aidl
  // vue
  <gt-material prop="url" :url="url" v-on:getChangeUrl="getChangeUrl" width="220" height="220"></gt-material>
  
  
  // js
  
  <script>
    export default {
      data(){
        url:''
      },
      methods:{
        // 获取更新的图片url
        getChangeUrl(e) {
          console.log(e)
        },
      }
    }
  </script>
```
