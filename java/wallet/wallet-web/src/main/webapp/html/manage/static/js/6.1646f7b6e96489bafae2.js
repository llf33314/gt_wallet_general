webpackJsonp([6],{XKJf:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});n("w7j4");var a={methods:{individualOpen:function(t){this.$router.push({path:"/wallet/individual/open/"+t})},companyOpen:function(t){this.$router.push({path:"/wallet/company/open/base/"+t})},getVipNum:function(t){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMember/register",type:"GET",dataType:"json",data:{memberType:t},success:function(n){0==n.code?2==t?e.companyOpen(n.data):e.individualOpen(n.data):e.$message.error(n.msg)}})}}},i={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"no-open"},[n("i",{staticClass:"el-icon-warning"}),t._v(" "),n("p",[t._v("您尚未开通多粉钱包，点击下方按钮开通吧！")]),t._v(" "),n("div",{staticClass:"bts"},[n("el-button",{attrs:{type:"primary"},on:{click:function(e){t.getVipNum(2)}}},[t._v("企业开通")]),t._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:function(e){t.getVipNum(3)}}},[t._v("个人开通")])],1)])},staticRenderFns:[]};var s=n("r7/j")(a,i,!1,function(t){n("dRXA")},null,null);e.default=s.exports},dRXA:function(t,e){}});