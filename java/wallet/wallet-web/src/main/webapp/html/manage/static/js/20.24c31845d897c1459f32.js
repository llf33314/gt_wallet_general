webpackJsonp([20],{"5mWy":function(t,e){},"8J4s":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s={data:function(){return{tableData3:[],multipleSelection:[],filters:[{value:"",text:""}],form:{current:1,wmemberId:7,size:10,msgType:""},page:{total:1},readstate:0,selectIds:[]}},mounted:function(){this.form.wmemberId=window.sessionStorage.walletId,this.getPage(),this.getMsgTypeResult(),this.getReadState()},watch:{"form.msgType":function(){var t=this;this.form.current=1,setTimeout(function(){t.getPage()},100)}},methods:{getReadState:function(){var t=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMessage/79B4DE7C/getReadState",type:"POST",dataType:"JSON",data:{wMemberId:window.sessionStorage.walletId},success:function(e){0==e.code||1009==e.code?t.readstate=e.data:t.$message.error(e.msg)}})},filterChange:function(){this.form.current=1,this.getPage()},getPage:function(){var t=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMessage/79B4DE7C/getPage",type:"POST",dataType:"JSON",data:this.form,success:function(e){0==e.code||1009==e.code?e.data?(t.tableData3=e.data.records||[],t.page.total=e.data.total||0):(t.tableData3=[],t.page.total=0):(t.$message.error(e.msg),t.tableData3=[])}})},getMsgTypeResult:function(){var t=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMessage/79B4DE7C/getMsgTypeResult",type:"POST",dataType:"JSON",success:function(e){if(0==e.code||1009==e.code){var a=e.data||[],s=[];a.forEach(function(t){s.push({text:t.msgTypeDesc,value:t.msgType})}),t.filters=a}else t.$message.error(e.msg)}})},handleCurrentChange:function(t){this.form.current=t,this.getPage()},filterTag:function(t,e){this.form.msgType=t},toggleSelection:function(t){var e=this;t?t.forEach(function(t){e.$refs.multipleTable.toggleRowSelection(t)}):this.$refs.multipleTable.clearSelection()},handleSelectionChange:function(t){this.selectIds=t},upstate:function(){var t=this,e=[];this.selectIds.forEach(function(t){e.push(t.id)}),0==e.length?this.$message.error("请选择消息"):$.ajax({url:this.DFPAYDOMAIN+"/walletMessage/79B4DE7C/upstate",type:"POST",dataType:"JSON",data:{listStr:e.join(",")},success:function(e){0==e.code||1009==e.code?(t.form.current=1,t.$message({message:e.msg,type:"success"}),t.getPage(),t.getReadState()):t.$message.error(e.msg)}})}}},n={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"wallet-company-news"},[a("span",{staticClass:"tips",domProps:{textContent:t._s(t.readstate)}},[t._v("12")]),t._v(" "),a("div",{staticStyle:{"padding-bottom":"20px"}},[a("el-select",{attrs:{clearable:"",placeholder:"请选择消息类型"},model:{value:t.form.msgType,callback:function(e){t.$set(t.form,"msgType",e)},expression:"form.msgType"}},t._l(t.filters,function(t){return a("el-option",{key:t.msgType,attrs:{label:t.msgTypeDesc,value:t.msgType}})}))],1),t._v(" "),a("el-table",{ref:"multipleTable",staticStyle:{width:"100%"},attrs:{data:t.tableData3},on:{"selection-change":t.handleSelectionChange,"filter-change":t.filterChange}},[a("el-table-column",{attrs:{type:"selection",width:"55"}}),t._v(" "),a("el-table-column",{attrs:{prop:"ctime",label:"日期",width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[0==e.row.state?a("span",{staticStyle:{color:"#000"},domProps:{textContent:t._s(t.DateFormat(e.row.ctime,"yyyy-MM-dd hh:mm"))}}):t._e(),t._v(" "),1==e.row.state?a("span",{domProps:{textContent:t._s(t.DateFormat(e.row.ctime,"yyyy-MM-dd hh:mm"))}}):t._e()]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"descContent",label:"内容"},scopedSlots:t._u([{key:"default",fn:function(e){return[0==e.row.state?a("span",{staticStyle:{color:"#000"},domProps:{textContent:t._s(e.row.descContent)}}):t._e(),t._v(" "),1==e.row.state?a("span",{domProps:{textContent:t._s(e.row.descContent)}}):t._e()]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"state",label:"状态",width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[0==e.row.state?a("span",{staticStyle:{color:"#000"}},[t._v("未读")]):t._e(),t._v(" "),1==e.row.state?a("span",[t._v("已读")]):t._e()]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"state ",label:"消息类型",width:"180"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-tag",{attrs:{type:"primary"}},[t._v(t._s(e.row.msgTypeDesc))])]}}])})],1),t._v(" "),0!=t.tableData3.length?a("div",{staticStyle:{"margin-top":"20px"}},[a("el-button",{attrs:{size:"small"},on:{click:function(e){t.toggleSelection(t.tableData3)}}},[t._v("全选")]),t._v(" "),a("el-button",{attrs:{size:"small"},on:{click:t.upstate}},[t._v("标记已读")]),t._v(" "),a("el-pagination",{staticStyle:{float:"right"},attrs:{"current-page":t.form.current,"page-size":10,layout:"prev, pager, next, jumper",total:t.page.total},on:{"current-change":t.handleCurrentChange,"update:currentPage":function(e){t.$set(t.form,"current",e)}}})],1):t._e()],1)},staticRenderFns:[]};var o=a("r7/j")(s,n,!1,function(t){a("5mWy")},null,null);e.default=o.exports}});