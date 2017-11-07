### 公共功能组件=== 地图
#### 调用方法：

```aidl
  // vue
  <gtmap :gtmapInformation.sync="mapBean" v-on:getMapData="getMapData" style="width: 251px!important;" ></gtmap>
  
  
  // js
  
  <script>
    import gtmap from '@/components/PublicVue/map/gtMap.vue';
    export default {
        components: {
            gtmap
        },
      data(){
        mapBean: {
            label: '',
            detailedAddress: ''
        },
      },
      methods:{
        //自动获取地图信息
        getMapData(e){
             console.log(e,'mapdata')
        },
      }
    }
  </script>
```
