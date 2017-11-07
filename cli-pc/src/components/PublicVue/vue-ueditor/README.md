### 公共功能组件=== 编辑器
#### 调用方法：
```aidl
  // vue
  <vqEditor v-on:getContent="getContent" html="<span style='color:red'>编辑器</span>"></vqEditor>
  
  
  // js
  
  <script>
    import VqEditor from './../../../../PublicVue/vue-ueditor/index.vue'
    export default {
      components: {
        VqEditor
      },
      methods:{
        // 获取编辑器html
        getChangeUrl(e) {
          console.log(e)
        },
      }
    }
  </script>
```
