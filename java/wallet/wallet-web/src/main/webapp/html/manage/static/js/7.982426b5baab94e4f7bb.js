webpackJsonp([7],{Kmoo:function(a,t){},ZjVH:function(a,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n={mounted:function(){this.isPass()},methods:{getVipMsg:function(){var a=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMember/findMember",type:"GET",dataType:"json",success:function(t){0==t.code?3==t.data.memberType?0==t.data.status||-1==t.data.status?(t.data.WalletIndividual&&""!=t.data.WalletIndividual.name||a.$router.push({path:"/wallet/individual/open/"+t.data.id}),0==t.data.isBindingPhone&&a.$router.push({path:"/wallet/individual/open/bindPhone/"+t.data.id}),1==t.data.isBindingPhone&&-1==t.data.status&&(window.sessionStorage.walletId=t.data.id,a.$router.push({path:"/wallet/individual/index"}))):3==t.data.status&&(window.sessionStorage.walletId=t.data.id,a.$router.push({path:"/wallet/individual/index"})):(0!=t.data.status&&-1!=t.data.status||(t.data.walletCompany&&""!=t.data.walletCompany.companyName||a.$router.push({path:"/wallet/company/open/base/"+t.data.id}),""==t.data.walletCompany.doBusinessUrl&&a.$router.push({path:"/wallet/company/open/uploadFil/"+t.data.id}),0==t.data.isBindingPhone&&a.$router.push({path:"/wallet/company/open/bindPhone/"+t.data.id}),1==t.data.isBindingPhone&&-1==t.data.status&&(window.sessionStorage.walletId=t.data.id,a.$router.push({path:"/wallet/company/index"}))),3==t.data.status&&(window.sessionStorage.walletId=t.data.id,a.$router.push({path:"/wallet/company/index"}))):1009==t.code?a.$router.push({path:"/wallet/noOpen"}):a.$message.error(t.msg)}})},goToWhere:function(a,t){var e=!1;[a.doBusinessUrl,a.identitycardUrl1,a.identitycardUrl2,a.licenseUrl].forEach(function(a){null!=a&&""!=a||(e=!0)}),e?this.$router.push({path:"/wallet/company/open/uploadFile/"+t}):this.$router.push({path:"/wallet/company/open/base/"+t})},isPass:function(){var a=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMember/isPassWallet",type:"GET",dataType:"json",success:function(t){0==t.code?0==t.data?a.getVipMsg():a.$router.push({path:"/wallet/noOpen"}):a.$message.error(t.msg)}})}}},s={render:function(){var a=this.$createElement,t=this._self._c||a;return t("div",[t("router-view")],1)},staticRenderFns:[]},i=e("8AGX")(n,s,!1,function(a){e("Kmoo")},null,null);t.default=i.exports}});