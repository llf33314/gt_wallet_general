<template>
    <div>
        <el-form-item :label="Original.label">
            <gt-region-choose :selectedOptions="Original.Charactron"  @change="regionChange"></gt-region-choose>
            <el-input v-model="Original.detailedAddress" placeholder="请点击选择地址" @focus="openDialog"></el-input>
        </el-form-item>
        <el-dialog title="选择地址" :visible.sync="dialogVisible" top="20%" :close-on-click-modal="false" class="largeDialog">
            <div class="map">
                <el-input v-model="input" placeholder="搜索地点" icon="search" size="small"  :on-icon-click="searchKeyword" @keydown.enter.native="searchKeyword"></el-input>
                <div id="container"></div>
                <el-radio-group v-model="radio" @change="changefn()">
                    <el-radio :label="index" class="clearfix" :key="index" v-for="(item,index) in MapData.pois">
                        <strong>{{item.name}}</strong>
                        <p>{{item.address}}</p>
                    </el-radio>
                </el-radio-group>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="Determine">确 定</el-button>
                <el-button @click="dialogVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script type='text/ecmascript-6'>
import { TMap } from "./TMap";
export default {
  data() {
    return {
      input: "", //弹框的输入框
      radio: 0, //弹框的单选框
      dialogVisible: false, //弹框
      MapData: {
        map: "", //地图实例
        LatLngx: 23.08828, //经纬度
        LatLngy: 114.43721, //经纬度
        searchService: "", //检索方法
        pois: [], //搜索到的数据
        markers: "" //地图上蓝色的点
      }, //地图要用到的数据
      Original: {}, //父组件传过来的数据
      required: {
        MapData: "", //地图的经纬度和其他信息
        ProvinceCharactron: "", //省份的代码
        Provinceaddress: "" //省份的文字
      } //子组件传出去的数据
    };
  },
  created() {
    this.Original = this.mapInformation;
    this.required.ProvinceCharactron = this.mapInformation.Charactron;
    this.required.Provinceaddress = this.mapInformation.address;
  },
  methods: {
    //    选择省份地区
    regionChange(e) {
      let _this = this;
      //改变地区的显示
      _this.Original.address = "";
      _this.Original.Charactron = e.value;
      e.name.forEach(function(item) {
        _this.Original.address += item;
      }, this);
      //将数据放到返回给父组件的数据中
      _this.required.ProvinceCharactron = e.value;
      _this.required.Provinceaddress = _this.Original.address;
    },
    //    打开弹窗加根据省份初始化地图
    openDialog() {
      if (this.Original.showProvince && this.Original.Charactron.length == 0) {
        this.$message.error("请选择省市区");
        return false;
      }
      this.dialogVisible = true;
      var _this = this;
      TMap("M3KBZ-QUMKU-YJQVE-2OETC-N7TJ5-VLBJW").then(qq => {
        var init = function() {
          var center = new qq.maps.LatLng(
            _this.MapData.LatLngx,
            _this.MapData.LatLngy
          );
          _this.MapData.map = new qq.maps.Map(
            document.getElementById("container"),
            {
              center: center,
              zoom: 13
            }
          );
          //设置Poi检索服务，用于本地检索、周边检索
          _this.MapData.searchService = new qq.maps.SearchService({
            //检索成功的回调函数
            complete: function(results) {
              //将单选框数据变空
              _this.MapData.pois = [];
              _this.cleanMarker();
              //去除掉公交站
              let originalPois = results.detail.pois;
              originalPois.forEach(function(item) {
                if (item.name) {
                  if (item.name.slice(item.name.length - 5) != "[公交站]") {
                    _this.MapData.pois.push(item);
                  }
                } else {
                  _this.MapData.pois.push(item);
                }
              }, this);
              if (!_this.MapData.pois) {
                return false;
              }
              var latlngBounds = new qq.maps.LatLngBounds();
              //扩展边界范围，用来包含搜索到的Poi点
              latlngBounds.extend(_this.MapData.pois[_this.radio].latLng);
              //将数据放到返回给父组件的数据中
              _this.required.MapData = _this.MapData.pois[_this.radio];
              //添加第一个点
              _this.MapData.markers = new qq.maps.Marker({
                position: _this.MapData.pois[_this.radio].latLng,
                map: _this.MapData.map
              });
              //调整地图视野
              _this.MapData.map.fitBounds(latlngBounds);
            },
            //若服务请求失败，则运行以下函数
            error: function(err) {
              _this.$message.error("地址有误 请重新打开弹窗");
            }
          });
          // //清除覆盖层
          _this.cleanMarker();
          //根据输入的城市设置搜索范围
          _this.MapData.searchService.setLocation(_this.Original.address);
          //设置搜索页码
          // _this.searchService.setPageIndex(5);
          //设置每页的结果数
          // _this.MapData.searchService.setPageCapacity(10);
          //根据输入的关键字在搜索范围内检索 //定位到当前的那个地区
          if (_this.Original.detailedAddress) {
            _this.MapData.searchService.search(_this.Original.detailedAddress);
          } else {
            _this.MapData.searchService.search(_this.Original.address);
          }
        };
        init();
        // 添加监听事件  获取鼠标点击事件
        qq.maps.event.addListener(_this.MapData.map, "click", function(event) {
          _this.MapData.pois = [];
          //添加蓝色的点
          _this.cleanMarker();
          _this.MapData.markers = new qq.maps.Marker({
            position: event.latLng,
            map: _this.MapData.map
          });
          //获取点击位置方法
          var geocoder = new qq.maps.Geocoder({
            complete: function(result) {
              _this.MapData.map.setCenter(result.detail.location);
              _this.MapData.pois = [
                { name: "", address: result.detail.address }
              ];
              _this.radio = 0;
              //将数据放到返回给父组件的数据中
              result.detail.address = result.detail.address.replace("中国", "");
              _this.required.MapData = result.detail;
            }
          });

          geocoder.getAddress(event.latLng);
        });
      });
    },
    //清除地图上的marker
    cleanMarker() {
      if (this.MapData.markers) {
        this.MapData.markers.setMap(null);
      }
    },
    //根据输入框值搜索地图
    searchKeyword() {
      var _this = this;
      //清除地图上的marker
      //清除覆盖层
      _this.radio = 0;
      this.cleanMarker();
      _this.MapData.searchService.setLocation(_this.Original.address);
      _this.MapData.searchService.search(_this.input);
    },
    //根据单选框值改变 地图蓝点也改变
    changefn() {
      var _this = this;
      //清除覆盖层
      this.cleanMarker();
      var latlngBounds = new qq.maps.LatLngBounds();
      //扩展边界范围，用来包含搜索到的Poi点
      latlngBounds.extend(_this.MapData.pois[_this.radio].latLng);
      _this.MapData.markers = new qq.maps.Marker({
        position: _this.MapData.pois[_this.radio].latLng,
        // animation: qq.maps.MarkerAnimation.DROP,
        map: _this.MapData.map
      });
      //调整地图视野
      _this.MapData.map.fitBounds(latlngBounds);
      //将数据放到返回给父组件的数据中

      _this.required.MapData = _this.MapData.pois[_this.radio];
    },
    Determine() {
      var _this = this;
      if (!_this.required.MapData) {
        _this.$message.error("请输入详细地址");
        return false;
      }
      if (this.required.MapData.address) {
        this.Original.detailedAddress = this.required.MapData.address;
      } else {
        this.Original.detailedAddress = this.required.MapData.name;
      }
      this.dialogVisible = false;
      this.$emit("update:mapInformation", _this.required);
    }
  },
  props: ["mapInformation"] //省份名字和地址,
};
</script>

<style lang='less' rel='stylesheet/less'>
#container {
  width: 100%;
  height: 280px;
}

.largeDialog {
  .el-dialog {
    width: 900px;
  }
}
.map {
  .el-input {
    width: 280px;
    margin-bottom: 20px;
  }
  .el-radio-group {
    display: block;
    margin-top: 10px;
    height: 146px;
    overflow-x: hidden;
    .el-radio {
      display: block;
      border-bottom: 1px solid #dfe6ec;
      margin: 0;
      padding: 10px 30px 10px 0;
    }
    .el-radio__input {
      float: right;
      margin-top: 17px;
    }
    .el-radio__label {
      float: left;
    }
    strong {
      font-size: 14px;
      color: #333333;
      font-weight: normal;
      line-height: 26px;
    }
    p {
      font-size: 14px;
      color: #999999;
      line-height: 26px;
    }
  }
}

//设置弹窗框的滚动条
.largeDialog .el-dialog ::-webkit-scrollbar {
  width: 4px;
  height: 4px;
  background-color: #eef1f6;
}

.largeDialog .el-dialog ::-webkit-scrollbar-track-piece {
  background-color: #fff;
}

.largeDialog .el-dialog ::-webkit-scrollbar-thumb:vertical {
  border: 1px solid #eef1f6;
  background-color: #eef1f6; // border-radius: 5px;
}

.largeDialog .el-dialog ::-webkit-scrollbar-thumb:horizontal {
  border: 1px solid #eef1f6;
  background-color: #eef1f6; // border-radius: 4px;
}

.largeDialog .el-dialog ::-webkit-scrollbar-thumb:hover {
  background-color: #eef1f6; // border-radius: 4px;
}
</style>
