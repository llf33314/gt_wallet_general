webpackJsonp([4],{NdKJ:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=a("i/rw"),o=a.n(r),n={data:function(){var e=this;return{disabledAddress:!1,dialogApply:!1,ruleForm1:{companyName:"",businessLicense:"",companyAddress:"",area:"",province:""},rules1:{area:[{required:!0,message:"请选择市县区",trigger:"change"}],province:[{required:!0,message:"请选择省份",trigger:"change"}],companyName:[{required:!0,message:"请输入企业名称",trigger:"blur"},{min:3,max:30,message:"长度在 3 到 30 个字符",trigger:"blur"}],companyAddress:[{required:!0,message:"请输入企业地址",trigger:"blur"}],businessLicense:[{required:!0,message:"请输入营业执照号",trigger:"blur"}]},provinceOptins:[],areaOptins:[],walletCompany:{accountNo:"",area:"",bankCtiyNo:" ",bankName:"",businessLicense:"",companyAddress:"",companyName:"",country:"",doBusinessUrl:"",id:0,identityType:0,identitycardUrl1:"",identitycardUrl2:"",legalIds:"",legalName:"",legalPhone:"",licenseUrl:"",memberNum:"",organizationCode:"",parentBankName:"",province:"",telephone:"",unionBank:"",wmemberId:0},ruleForm2:{phone:"",code:""},rules2:{phone:[{required:!0,validator:function(t,a,r){""==a?r(new Error("请输入新手机号码")):e.isPhone(a)?r():r(new Error("请输入正确新手机号码"))},trigger:"blur"}],code:[{required:!0,message:"请输入短信验证",trigger:"blur"}]},loading2:!1,getCodeText:"获取验证码",loading22:!1,memberType:""}},mounted:function(){this.findMember()},methods:{isPhone:function(e){var t=!0;return/^((\+?86)|(\(\+86\)))?(13[0123456789][0-9]{8}|15[0123456789][0-9]{8}|17[0123456789][0-9]{8}|18[0123456789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/.test(e)||(t=!1),t},getCityCode:function(e){var t=this;$.ajax({url:this.DFPAYDOMAIN+"/wcommon/getProvince",type:"GET",dataType:"json",success:function(a){0==a.code?(t.provinceOptins=a.data,a.data.forEach(function(a){e==a.city_code&&(t.ruleForm1.province=a.id+"")}),t.getCityCode2()):t.$message.error(a.msg)}})},getCityCode2:function(){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/wcommon/queryCityByParentId",type:"POST",dataType:"json",data:{parentId:this.ruleForm1.province},success:function(t){0==t.code?(e.ruleForm1.area="",e.areaOptins=t.data,t.data.forEach(function(t){e.walletCompany.area==t.city_code&&(e.ruleForm1.area=t.id+"")})):e.$message.error(t.msg)}})},getProvince:function(){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/wcommon/getProvince",type:"GET",dataType:"json",success:function(t){0==t.code?e.provinceOptins=t.data:e.$message.error(t.msg)}})},getareas:function(e){var t=this;$.ajax({url:this.DFPAYDOMAIN+"/wcommon/queryCityByParentId",type:"POST",dataType:"json",data:{parentId:e},success:function(e){0==e.code?(t.ruleForm1.area="",t.areaOptins=e.data):t.$message.error(e.msg)}})},findMember:function(){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMember/findMember",type:"GET",dataType:"JSON",success:function(t){0==t.code?(e.walletCompany=t.data.walletCompany,e.walletCompany.legalPhone=t.data.phone,e.memberType=t.data.memberType,e.getCityCode(t.data.walletCompany.province)):e.$message.error(t.msg)}})},getVerificationCode:function(){var e=this;this.$refs.ruleForm2.validateField("phone",function(t){if(t)return!1;e.loading2=!0,$.ajax({url:e.DFPAYDOMAIN+"/walletMember/sendVerificationCode",type:"POST",dataType:"JSON",data:{phone:e.ruleForm2.phone,wmemberId:e.walletCompany.wmemberId,verificationCodeType:9},success:function(t){0==t.code?function(){var t=60;e.getCodeText=t+"s";var a=setInterval(function(){e.getCodeText=t--+"s",t<0&&(e.loading2=!1,e.getCodeText="获取验证码",clearInterval(a))},1e3)}():e.$message({showClose:!0,message:t.msg,type:"error",duration:1500,onClose:function(){e.loading2=!1,e.findMember()}})}})})},cancle:function(e){this.$refs[e].resetFields()},submitRuleForm2:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;t.loading22=!0,$.ajax({url:t.DFPAYDOMAIN+"/walletMember/reset",type:"POST",dataType:"JSON",data:{newPhone:t.ruleForm2.phone,verificationCode:t.ruleForm2.code,wmemberId:t.walletCompany.wmemberId},success:function(e){0==e.code?(t.dialogApply=!1,t.findMember()):t.$message.error(e.msg),t.loading22=!1}})})},submitRuleForm1:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;var a=JSON.parse(o()(t.ruleForm1));t.provinceOptins.forEach(function(e){t.ruleForm1.province==e.id&&(a.province=e.city_code)}),t.areaOptins.forEach(function(e){t.ruleForm1.area==e.id&&(a.area=e.city_code)}),a.area&&$.ajax({url:t.DFPAYDOMAIN+"/walletCompany/updateAddress",type:"POST",dataType:"JSON",data:{companyAddress:t.walletCompany.companyAddress,memberId:t.walletCompany.wmemberId,province:a.province,area:a.area},success:function(e){0==e.code?(t.$message({message:e.msg,type:"success"}),t.findMember()):t.$message.error(e.msg+e.code)}})})}}},l={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"wallet-company-messages"},[a("el-breadcrumb",{staticClass:"public-crumbs",attrs:{separator:"/"}},[a("el-breadcrumb-item",{attrs:{onclick:"window.history.go(-1)"}},[e._v("多粉钱包")]),e._v(" "),a("el-breadcrumb-item",[e._v("企业信息")])],1),e._v(" "),a("div",{staticClass:"public-content public-top20"},[a("el-form",{ref:"walletCompany",staticStyle:{width:"600px"},attrs:{model:e.walletCompany,rules:e.rules1,"label-width":"150px"}},[a("el-form-item",{attrs:{label:"企业名称："}},[a("span",{domProps:{textContent:e._s(e.walletCompany.companyName)}})]),e._v(" "),a("el-form-item",{attrs:{label:"法人姓名："}},[a("span",{domProps:{textContent:e._s(e.walletCompany.legalName)}})]),e._v(" "),a("el-form-item",{attrs:{label:"身份证号："}},[a("span",{domProps:{textContent:e._s(e.walletCompany.legalIds)}})]),e._v(" "),a("el-form-item",{attrs:{label:"营业执照号："}},[a("span",{domProps:{textContent:e._s(e.walletCompany.businessLicense)}})]),e._v(" "),a("el-form-item",{attrs:{label:"企业地址："}},[a("el-form-item",{staticStyle:{display:"inline-block",width:"218px"},attrs:{prop:"province"}},[a("el-select",{attrs:{placeholder:"请选择",disabled:e.disabledAddress},on:{change:e.getareas},model:{value:e.ruleForm1.province,callback:function(t){e.$set(e.ruleForm1,"province",t)},expression:"ruleForm1.province"}},e._l(e.provinceOptins,function(e){return a("el-option",{key:e.id,attrs:{label:e.city_name,value:e.id+""}})}))],1),e._v(" "),a("el-form-item",{staticStyle:{display:"inline-block",width:"218px"},attrs:{prop:"area"}},[a("el-select",{attrs:{placeholder:"请选择",disabled:e.disabledAddress},model:{value:e.ruleForm1.area,callback:function(t){e.$set(e.ruleForm1,"area",t)},expression:"ruleForm1.area"}},e._l(e.areaOptins,function(e){return a("el-option",{key:e.id,attrs:{label:e.city_name,value:e.id+""}})}))],1)],1),e._v(" "),a("el-form-item",{attrs:{prop:"companyAddress"}},[a("el-input",{staticClass:"input-width",attrs:{disabled:e.disabledAddress,placeholder:"请输入详细地址"},model:{value:e.walletCompany.companyAddress,callback:function(t){e.$set(e.walletCompany,"companyAddress",t)},expression:"walletCompany.companyAddress"}})],1)],1),e._v(" "),a("div",{staticClass:"public-table-title public-c666",staticStyle:{margin:"40px 0"}},[e._v("\n      银行卡信息\n    ")]),e._v(" "),a("el-form",{ref:"ruleForm1",staticStyle:{width:"600px"},attrs:{model:e.ruleForm1,rules:e.rules1,"label-width":"150px"}},[a("el-form-item",{attrs:{label:"账户类型："}},[2==e.memberType?a("span",[e._v("企业会员")]):e._e(),e._v(" "),3==e.memberType?a("span",[e._v("个人会员")]):e._e()]),e._v(" "),a("el-form-item",{attrs:{label:"手机号码："}},[a("span",{domProps:{textContent:e._s(e.walletCompany.legalPhone)}}),e._v(" "),a("span",{staticStyle:{color:"#ccc"}},[e._v("(已绑定通联)")]),e._v(" "),a("i",{staticClass:"el-icon-edit-outline",attrs:{title:"修改手机号码"},on:{click:function(t){e.dialogApply=!0}}})]),e._v(" "),e.walletCompany.bankCtiyNo?a("el-form-item",{attrs:{label:"开户行地区代码："}},[a("span",{domProps:{textContent:e._s(e.walletCompany.bankCtiyNo||"——")}})]):e._e(),e._v(" "),e.walletCompany.bankName?a("el-form-item",{attrs:{label:"开户行支行名称："}},[a("span",{domProps:{textContent:e._s(e.walletCompany.bankName||"——")}})]):e._e(),e._v(" "),e.walletCompany.unionBank?a("el-form-item",{attrs:{label:"支付行号："}},[a("span",{domProps:{textContent:e._s(e.walletCompany.unionBank||"——")}})]):e._e(),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.submitRuleForm1("walletCompany")}}},[e._v("保存")]),e._v(" "),a("el-button",{attrs:{onclick:"window.history.go(-1)"}},[e._v("返回")])],1)],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"绑定通联手机号码",visible:e.dialogApply,"custom-class":"wallet-drawcash-dialog"},on:{"update:visible":function(t){e.dialogApply=t},close:function(t){e.loading22=!1,e.loading2=!1}}},[a("el-form",{ref:"ruleForm2",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm2,rules:e.rules2,"label-width":"155px"}},[a("el-form-item",{attrs:{label:"手机号码：",prop:"phone"}},[a("el-input",{attrs:{type:"number",placeholder:"请输入新手机号码"},model:{value:e.ruleForm2.phone,callback:function(t){e.$set(e.ruleForm2,"phone",t)},expression:"ruleForm2.phone"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"短信验证：",prop:"code"}},[a("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"请输入短信验证",type:"number"},model:{value:e.ruleForm2.code,callback:function(t){e.$set(e.ruleForm2,"code",t)},expression:"ruleForm2.code"}}),e._v(" "),a("el-button",{attrs:{type:"primary",loading:e.loading2},on:{click:e.getVerificationCode}},[e._v(e._s(e.getCodeText))])],1),e._v(" "),a("el-form-item",{staticStyle:{"text-align":"right","margin-top":"50px"}},[a("el-button",{attrs:{type:"primary",loading:e.loading22},on:{click:function(t){e.submitRuleForm2("ruleForm2")}}},[e._v("确定")]),e._v(" "),a("el-button",{on:{click:function(t){e.dialogApply=!1}}},[e._v("取消")])],1)],1)],1)],1)},staticRenderFns:[]},i=a("UF+H")(n,l,!1,function(e){a("m0OG")},null,null);t.default=i.exports},"i/rw":function(e,t,a){e.exports={default:a("s9uK"),__esModule:!0}},m0OG:function(e,t){},s9uK:function(e,t,a){var r=a("zJrI"),o=r.JSON||(r.JSON={stringify:JSON.stringify});e.exports=function(e){return o.stringify.apply(o,arguments)}}});