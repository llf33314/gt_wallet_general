webpackJsonp([16],{"1wj2":function(e,t){},ZjVH:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s={mounted:function(){this.isPass()},methods:{getVipMsg:function(){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMember/findMember",type:"GET",dataType:"json",success:function(t){0==t.code?3==t.data.memberType?0==t.data.status?e.$router.push({path:"/wallet/individual/open/"+t.data.id}):(window.sessionStorage.walletId=t.data.id,e.$router.push({path:"/wallet/individual/index"})):0==t.data.status?t.data.walletCompany.doBusinessUrl&&t.data.walletCompany.identitycardUrl1&&t.data.walletCompany.identitycardUrl2&&t.data.walletCompany.licenseUrl?e.$router.push({path:"/wallet/company/open/base/"+t.data.id}):e.$router.push({path:"/wallet/company/open/uploadFile/"+t.data.id}):(window.sessionStorage.walletId=t.data.id,e.$router.push({path:"/wallet/company/index"})):1009==t.code?e.$router.push({path:"/wallet/noOpen"}):e.$message.error(t.msg)}})},isPass:function(){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMember/isPassWallet",type:"GET",dataType:"json",success:function(t){0==t.code?0==t.data?e.getVipMsg():e.$router.push({path:"/wallet/noOpen"}):e.$message.error(t.msg)}})}}},n={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",[t("router-view")],1)},staticRenderFns:[]},l=a("DoIX")(s,n,!1,function(e){a("1wj2")},null,null);t.default=l.exports}});