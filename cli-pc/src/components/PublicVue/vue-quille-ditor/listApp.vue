<template>
  <el-row>
    <div class="infoList" :class="listStatus">
      <el-col :span="24">
        <el-input placeholder="搜索关键词" icon="search" v-model="input" :on-icon-click="searchList"></el-input>
        <div class="grid-content">
          <el-button type="primary" @click="addInfo()">新增信息</el-button>
        </div>
      </el-col>

      <!--设备列表-->
      <div v-if="!tableData">
        <div class="listNone">
          <span>!</span>
          <p>您还没有创建相关信息，<label>点击这里</label>创建信息吧</p>
        </div>
      </div>
      <div v-else>
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" @select="chooseSelct" @select-all="chooseSelct" @selection-change="chooseSelct" style="width: 100%">
          <el-table-column type="selection" width="140"></el-table-column>
          <el-table-column label="标题"  style="width: 20%">
            <template scope="scope">{{ scope.row.title }}</template>
          </el-table-column>
          <el-table-column label="信息图片" style="width: 25%">
            <template scope="scope">
              <img width="60" height="60" style="margin-top: 10px;" :src="scope.row.imgurl" />
            </template>
          </el-table-column>
          <el-table-column prop="createTimeString" style="width: 25%" label="创建时间" show-overflow-tooltip></el-table-column>
          <el-table-column label="操作" style="width: 20%" show-overflow-tooltip>
            <template scope="scope">
              <el-button class="newBtncss" style="border: 1px solid #20a0ff;color: #20a0ff" size="small" @click="handleEdit(scope.row.id)">编辑</el-button>
              <el-button size="small" @click="handleDelete(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="chooseBtn">
          <el-button @click="toggleSelection(tableData)">全选</el-button>
          <el-button @click="toggleSelection()">取消选择</el-button>
          <el-button @click="batchDel()">批量删除</el-button>
          <el-pagination class="newPagination" @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="curPage"
                         :page-size="pageSize" layout="prev, pager, next, jumper" :total="rowCount"></el-pagination>
        </div>
      </div>
    </div>

    <div class="infoList" :class="addStatus">
      <div class="infoHead">信息发布<span>/信息编辑</span></div>
      <div class="infoCommon">
        <div class="dl">信息标题：</div>
        <div class="dr"><el-input placeholder="请输入内容" v-model="infoTitle"></el-input></div>
      </div>
      <div class="infoCommon">
        <div class="dl">信息图片：</div>
        <div class="dr newFrom">
          <img :class="chooseImg ? 'chooseImgCss' : 'hidden'" :src="chooseImg"  @click="materiallayer('1')"/>
          <el-button class="uploadBtn" @click="materiallayer('1')"><i style="font-size: 28px;color: #8c939d;" class="el-icon-plus"></i></el-button>
          <label class="chooseImgText">建议尺寸：480 * 480 px</label>
          <el-dialog title="素材库" :visible.sync="materialVisible">
            <iframe class="uploadIframe" :src="materialUrl"></iframe>
          </el-dialog>
        </div>
      </div>
      <div class="infoCommon">
        <div class="dl">信息内容：</div>
        <div class="dr" style="height: 520px;">
          <vqEditor @getValue = "getContent"></vqEditor>
        </div>
      </div>
      <div class="infoCommon">
        <div class="dl"></div>
        <div class="dr">
          <el-button type="primary" @click="comfirmAddInfo">保存</el-button>
          <el-button @click="addInfoCancel">取消</el-button>
        </div>
      </div>
    </div>

  </el-row>
</template>

<script>
    import Vue from 'vue'
    import axios from 'axios'
    import Lib from  '../../../assets/js/common.js'
    import  vqEditor from '../../../components/common/vuequilleditor.vue'
    export default {
        components: {
            vqEditor
        },
        data () {
            return {
                input: '',
                chooseImg: 'http://pic.90sjimg.com/back_pic/qk/back_origin_pic/00/02/29/b9b9122765f04f7c87cf0b4516481b98.jpg',
                editor: {
                    list: ''
                },
                infoTitle: '',
                materialUrl: '',
                chooseId: '',
                editorOption: {
                    modules: {
                        toolbar: '#toolbar'
                    }
                },
                materialVisible: false,
                pageSize: '',
                curPage: '',
                rowCount: '',
                listStatus: '',
                addStatus: 'hidden',
                dialogImageUrl: '',
                dialogVisible: false,
                tableData: '',
                multipleSelection: [],
                getChooseList: []
            }
        },
        methods: {
            materiallayer(status) {                                                  // 调用素材库
                this.editor.list.chooseStatus = status
                this.materialVisible = true
                this.materialUrl = 'https://suc.deeptel.com.cn/common/material.do?retUrl=' + window.location.href
            },
            getContent(list){                                                     //获取文本编辑器的内容
                this.editor.list = list
            },
            searchList() {                                                     //搜索列表
                var _this = this
                if(!_this.input){
                    _this.$message({message: '请输入内容', type: 'info'})
                    return
                }else{
                    _this.loadList('1',_this.input)
                }
            },
            loadList(num,content) {                                             //加载列表
                var _this = this
                if(content){
                    var _content = content
                }else{
                    var _content = ''
                }
                axios.get(Lib.A + '/look/lookAboutus/getList.do',{
                    params: {keyWord: _content,pageNum: num}
                }).then(function (res) {
                    var data = res.data.data
                    if (res.data.code === 0) {
                        _this.tableData = data.page.records
                        _this.pageSize = data.page.size
                        _this.curPage = data.page.current
                        _this.rowCount = data.page.total
                    } else {
                        console.log('获取数据失败----' + err)
                    }
                }).catch(function (err) {
                    console.log('调用失败----' + err)
                })
            },
            toggleSelection(rows) {                                            //选择列表
                if (rows) {
                    rows.forEach(row => {
                        this.$refs.multipleTable.toggleRowSelection(row,true)
                    })
                } else {
                    this.$refs.multipleTable.clearSelection()
                }
            },
            addInfo(){                                                          //切换到添加信息
                var _this = this
                _this.listStatus = 'hidden'
                _this.addStatus = ''
                _this.chooseId = ''
                _this.chooseImg = ''
                _this.infoTitle = ''
                document.querySelector(".ql-editor").innerHTML = ''
            },
            comfirmAddInfo() {                                                  //确定添加信息事件
                var _this = this
                var listImg = new Array()
                listImg = _this.chooseImg.split('/image/')
                axios.post(Lib.A + '/look/lookAboutus/saveOrUpdate.do',{
                    id: _this.chooseId,
                    content: _this.editor.list.content,
                    imgurl: '/image/'+listImg[1],
                    title: _this.infoTitle
                }).then(function (res) {
                    if (res.data.code === 0) {
                        _this.loadList('1','')
                        _this.addInfoCancel()
                        _this.$message({message: '添加成功', type: 'success'})
                    } else {
                        _this.$message({message: '添加失败', type: 'warning'})
                    }
                }).catch(function (err) {
                    console.log('调用失败----' + err)
                })
            },
            addInfoCancel(){                                                    //返回到信息列表
                this.listStatus = ''
                this.addStatus = 'hidden'
            },
            handleEdit(id) {                                                     //编辑信息窗口
                var _this = this
                _this.chooseId = id
                axios.get(Lib.A + '/look/lookAboutus/getOne.do',{
                    params: {
                        id: _this.chooseId
                    }
                }).then(function (res) {
                    _this.listStatus = 'hidden'
                    _this.addStatus = ''
                    if (res.data.code === 0) {
                        _this.chooseImg = res.data.data.imgurl
                        _this.infoTitle = res.data.data.title
                        document.querySelector(".ql-editor").innerHTML = res.data.data.content
                    } else {
                        console.log('请求数据失败----' + res)
                    }
                }).catch(function (err) {
                    console.log('调用失败----' + err)
                })
            },
            handleDelete(id) {                                                   //删除信息事件
                var _this = this
                _this.$confirm('确定删除该项目吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then(() => {
                    axios.get(Lib.A + '/look/lookAboutus/deleteByIds.do',{
                        params: {
                            ids: id
                        }
                    }).then(function (res) {
                        if (res.data.code === 0) {
                            _this.loadList()
                            _this.$message({message: '删除成功', type: 'success'})
                        } else {
                            _this.$message({message: '删除失败', type: 'warning'})
                        }
                    })
                }).catch(() => {
                    _this.$message({type: 'info', message: '已取消删除'})
                })
            },
            batchDel() {                                                             //批量删除
                var _this = this
                if(_this.getChooseList.length > 0){
                    var ids = []
                    for (var i = 0;_this.getChooseList.length > i;i++) {
                        ids.push(_this.getChooseList[i].id)
                    }
                    _this.$confirm('确定删除该项目吗？', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消'
                    }).then(() => {
                        axios.get(Lib.A + '/look/lookAboutus/deleteByIds.do',{
                            params: { ids: ids.toString() }
                        }).then(function (res) {
                            if (res.data.code === 0) {
                                _this.loadList()
                                _this.$message({message: '删除成功', type: 'success'})
                            } else {
                                _this.$message({message: '删除失败', type: 'warning'})
                            }
                        })
                    }).catch(() => {
                        _this.$message({type: 'info', message: '已取消删除'})
                    })
                }else{
                    _this.$message({type: 'info', message: '请选择需要删除的信息'})
                }
            },
            chooseSelct(selection, row){
                this.getChooseList = selection
            },
            handleSizeChange(val) {
                this.loadList(val,this.input)
            },
            handleCurrentChange(val) {
                this.loadList(val,this.input)
            }
        },
//  初始化列表数据
        created () {
            this.loadList('1','')
        },
        mounted () {
            var _this = this
            var strs = new Array()
            window.addEventListener("message", function (e) {
                strs = e.data.replace(/[()'']/g,'').split(',')
                if(_this.editor.list.chooseStatus === '1'){
                    _this.materialVisible = false
                    _this.chooseImg = strs[1]
                }else{
                    _this.editor.list.dialogVisible = false
                    document.querySelector(".ql-editor").innerHTML = _this.editor.list.content + '<img class="ql_image" src="'+strs[1]+'" />'
                }
            })
        }
    }
</script>

<style>
  .hidden{display: none;opacity: 0;}
  .el-input, .el-input__inner{width: 220px !important;}
  .el-table{width: auto !important;margin: 0 65px !important;position: relative !important;top: 30px;}
  .el-col {padding: 20px 65px 15px 65px;background: #F5F5F5;border-radius: 4px;  }
  .el-button--small {padding: 6px 12px;  }
  .grid-content {margin-top: 20px;border-radius: 4px;min-height: 36px;}
  .newBtncss:hover{border: 1px solid #20a0ff;color: #fff !important;background-color: #20a0ff;}
  .el-table th>.cell {font-weight: 500;}
  .listNone{box-sizing: border-box;width: auto;padding: 15px 0 30px 0;margin: 0 65px;position: relative;top: 30px;
    overflow: hidden;max-width: 100%;background-color: #fff;border: 1px solid #dfe6ec;}
  .listNone span{display: block;text-align: center;width: 30px;height: 30px;line-height: 30px;font-size: 25px;
    color: #cecece;border: 2px solid #cecece;margin: 10px auto;border-radius: 50%;}
  .listNone p{font-size: 16px;color: #d0d0d0;text-align: center;}
  .listNone p label{color: #20a0ff;}
  .el-button--small {padding: 7px 12px !important;}
  .chooseBtn{margin: 50px 0 0 60px;}
  .newPagination{float: right;margin: 0 60px 0 0;}
  .el-table th:first-child .cell{display: none;}

  body{margin: 0;}
  .infoList{width: 100%;margin-bottom: 30px;}
  .infoList .infoHead{height: 50px;line-height: 50px;padding: 0 60px;font-size: 14px;color: #20a0ff;border-bottom: 1px solid #eee;margin-bottom: 40px;}
  .infoList .infoHead span{color: #8391a5;}
  .infoList .infoCommon{padding: 0 60px;margin-bottom: 30px;clear: both;}
  .infoList .infoCommon .dl{width: 95px;color: #333;font-size: 14px;height: 30px;line-height: 30px;float: left;}
  .infoList .infoCommon .dr{}
  .infoList .infoCommon .dr .chooseImgText{font-size: 12px;color: #bbb;position: relative;top: 22px;margin-left: 10px;}
  .uploadBtn{width: 70px;height: 70px;border: 1px dashed #c0ccda !important;background-color: #fbfdff !important;border-radius: 6px;box-sizing: border-box;}
  .chooseImgCss{width: 70px;height: 70px;position: absolute;}

  /*重新定义vue样式iframe*/
  .newFrom .el-dialog--small{width: 820px !important;}
  .newFrom .el-message-box__content{padding: 0 !important;}
  .newFrom .el-dialog__header{padding: 20px !important;line-height: 0 !important;}
  .newFrom .el-dialog__body{padding: 0 !important;height: 460px !important;border-top: 1px solid #eee;}
  .newFrom .el-dialog__body iframe{height: 460px !important;padding: 0 !important;margin: 0 !important;border: 0 !important;width: 820px !important;}
  .newFrom .infoList .infoCommon .dr .el-dialog__headerbtn{padding: 4px 8px !important;}

  /*重新定义vue样式*/
  .el-message-box__header {padding: 20px !important;}
  .el-message-box__message{margin-left: 20px !important;}
  .el-dialog--small{width: 820px !important;}
  .el-message-box__content{padding: 0 !important;}
  .el-dialog__header{padding: 20px !important;}
  .el-dialog__body{padding: 0 !important;height: 460px !important;border-top: 1px solid #eee;}
  .el-dialog__body iframe{height: 460px !important;padding: 0 !important;margin: 0 !important;border: 0 !important;}
  .infoList .infoCommon .dr .el-dialog__headerbtn{padding: 4px 8px !important;}

</style>
