### 公共功能组件=== 选择省份市区
#### 调用方法：

```aidl
  // vue
  <div class="row">
    <gt-region-choose :selectedOptions="selectedOptions"   @change="regionChange"></gt-region-choose>
  </div>
  
  // js
  
  <script>
    export default {
      data(){
        selectedOptions:[
          "130000",
          "130200",
          "130203"
        ],
      },
      methods:{
        regionChange(e){
            this.selectedOptions = e.value
        },
      }
    }
  </script>
```
