webpackJsonp([13],{"3gDx":function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0});t("w7j4");var l={data:function(){return{number:0,dialogApply:!1,ruleForm:{wMemberId:9,quotaValue:"2222",quotaDesc:"34534534534"},rules:{quotaValue:[{required:!0,message:"请输入申请额度",trigger:"blur"}],quotaDesc:[{required:!0,message:"请输入申请描述",trigger:"blur"},{min:3,max:75,message:"长度在 3 到 100 个字符",trigger:"blur"}]}}},mounted:function(){this.number=this.store.state.wallet.vipMsg.title},methods:{save:function(){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/walletQuota/add",type:"POST",dataType:"json",data:this.ruleForm,success:function(r){0==r.code?(e.$message({message:"提交成功",type:"success"}),e.dialogApply=!1):e.$message.error(r.msg)}})},submitForm:function(e){var r=this;this.$refs[e].validate(function(e){if(!e)return!1;r.save()})},resetForm:function(e){this.$refs[e].resetFields(),this.dialogApply=!1}}},o={render:function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("section",{staticClass:"wallet-drawcash"},[t("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-width":"155px"}},[t("el-form-item",{attrs:{label:"姓名：",prop:"bankName"}},[t("el-input",{attrs:{placeholder:"必须与注册人姓名一致"},model:{value:e.ruleForm.bankName,callback:function(r){e.$set(e.ruleForm,"bankName",r)},expression:"ruleForm.bankName"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"手机号：",prop:"phone "}},[t("el-input",{attrs:{placeholder:"银行预留手机号"},model:{value:e.ruleForm.phone,callback:function(r){e.$set(e.ruleForm,"phone",r)},expression:"ruleForm.phone"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"银行卡号：",prop:"cardNo"}},[t("el-input",{model:{value:e.ruleForm.cardNo,callback:function(r){e.$set(e.ruleForm,"cardNo",r)},expression:"ruleForm.cardNo"}})],1),e._v(" "),t("el-form-item",[t("el-button",{attrs:{type:"primary"},on:{click:function(r){e.submitForm("ruleForm")}}},[e._v("申请")]),e._v(" "),t("el-button",{on:{click:function(r){e.resetForm("ruleForm")}}},[e._v("取消")])],1)],1)],1)},staticRenderFns:[]},a=t("UF+H")(l,o,!1,function(e){t("EIbD")},null,null);r.default=a.exports},EIbD:function(e,r){}});