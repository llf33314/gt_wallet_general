### ### 公共功能组件===表格无数据显示状态
#### 调用方法：
```aidl
   // vue
   <div class="row">
     <gt-null-data>您还没有授权人信息，<span @click="add()">点击这里</span>添加授权人吧</gt-null-data>
   </div>

   <div class="row">
     <gt-null-data>还没有相关数据</gt-null-data>
   </div>
  
   <script>
       export default {
         methods:{
           add(){
               
           }
         }
       }
   </script>

```
