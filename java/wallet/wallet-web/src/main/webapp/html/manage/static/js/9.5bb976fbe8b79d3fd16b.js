webpackJsonp([9],{"75i/":function(e,r){},GiAJ:function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0});var o={data:function(){var e=this;return{ruleForm2:{phone:"",code:""},rules2:{phone:[{required:!0,validator:function(r,t,o){""==t?o(new Error("请输入手机号码")):e.isPhone(t)?o():o(new Error("请输入正确手机号码"))},trigger:"blur"}],code:[{required:!0,message:"请输入短信验证",trigger:"blur"}]},loading2:!1,getCodeText:"获取验证码",loading22:!1}},methods:{isPhone:function(e){var r=!0;return/^((\+?86)|(\(\+86\)))?(13[0123456789][0-9]{8}|15[0123456789][0-9]{8}|17[0123456789][0-9]{8}|18[0123456789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/.test(e)||(r=!1),r},getVerificationCode:function(){var e=this;this.$refs.ruleForm2.validateField("phone",function(r){if(r)return!1;e.loading2=!0,$.ajax({url:e.DFPAYDOMAIN+"/walletMember/sendVerificationCode",type:"POST",dataType:"JSON",data:{phone:e.ruleForm2.phone,wmemberId:e.$route.params.memberId,verificationCodeType:9},success:function(r){0==r.code?function(){var r=60;e.getCodeText=r+"s";var t=setInterval(function(){e.getCodeText=r--+"s",r<0&&(e.loading2=!1,e.getCodeText="获取验证码",clearInterval(t))},1e3)}():e.$message.error(r.msg)}})})},cancle:function(e){this.$refs[e].resetFields()},submitRuleForm2:function(e){var r=this;this.$refs[e].validate(function(e){if(!e)return!1;r.loading22=!0,$.ajax({url:r.DFPAYDOMAIN+"/walletMember/bindingPhone",type:"POST",dataType:"JSON",data:{phone:r.ruleForm2.phone,code:r.ruleForm2.code,wmemberId:r.$route.params.memberId},success:function(e){0==e.code?r.$message({message:e.msg,type:"success",duration:1500,onClose:function(){r.$router.replace({path:"/wallet/individual/index"})}}):r.$message.error(e.msg),r.loading22=!1}})})}}},n={render:function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",[t("el-breadcrumb",{staticClass:"public-crumbs",attrs:{separator:"/"}},[t("el-breadcrumb-item",[e._v("多粉钱包")]),e._v(" "),t("el-breadcrumb-item",[e._v("绑定手机")])],1),e._v(" "),t("el-form",{ref:"ruleForm2",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm2,rules:e.rules2,"label-width":"155px"}},[t("el-form-item",{attrs:{label:"手机号码：",prop:"phone"}},[t("el-input",{staticStyle:{width:"367px"},attrs:{type:"number",placeholder:"请输入绑定的手机号码"},model:{value:e.ruleForm2.phone,callback:function(r){e.$set(e.ruleForm2,"phone",r)},expression:"ruleForm2.phone"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"短信验证：",prop:"code"}},[t("el-input",{staticStyle:{width:"250px"},attrs:{type:"number",placeholder:"请输入短信验证"},model:{value:e.ruleForm2.code,callback:function(r){e.$set(e.ruleForm2,"code",r)},expression:"ruleForm2.code"}}),e._v(" "),t("el-button",{attrs:{type:"primary",loading:e.loading2},on:{click:e.getVerificationCode}},[e._v(e._s(e.getCodeText))])],1),e._v(" "),t("el-form-item",[t("el-button",{attrs:{type:"primary",loading:e.loading22},on:{click:function(r){e.submitRuleForm2("ruleForm2")}}},[e._v("确定")])],1)],1)],1)},staticRenderFns:[]},a=t("8AGX")(o,n,!1,function(e){t("75i/")},"data-v-72217f8e",null);r.default=a.exports}});