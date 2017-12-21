webpackJsonp([7],{BVTR:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a={data:function(){console.log(window);return{dialogApply:!1,ruleForm1:{companyName:"",businessLicense:"",companyAddress:"",area:"",province:""},rules1:{area:[{required:!0,message:"请选择市县区",trigger:"change"}],province:[{required:!0,message:"请选择省份",trigger:"change"}],companyName:[{required:!0,message:"请输入企业名称",trigger:"blur"},{min:3,max:30,message:"长度在 3 到 30 个字符",trigger:"blur"}],companyAddress:[{required:!0,message:"请输入企业地址",trigger:"blur"},{min:3,max:30,message:"长度在 3 到 30 个字符",trigger:"blur"}],businessLicense:[{required:!0,message:"请输入营业执照号",trigger:"blur"}]},provinceOptins:[],areaOptins:[],walletIndividual:{},phone:"",memberType:"",walletCompany:{accountNo:"string",area:"130100",bankCtiyNo:"开户行地区代码 ",bankName:"开户行支行名",businessLicense:"营业执照号",companyAddress:"地址",companyName:"企业名称",country:"string",doBusinessUrl:"string",id:0,identityType:0,identitycardUrl1:"string",identitycardUrl2:"string",legalIds:"法人证件号码",legalName:"法人姓名",legalPhone:"string",licenseUrl:"string",memberNum:"string",organizationCode:"string",parentBankName:"string",province:"省份",telephone:"联系电话",unionBank:"支付行号",wmemberId:0},ruleForm2:{phone:"",code:""},rules2:{phone:[{required:!0,validator:function(e,t,r){if(!t)return r(new Error("请输入新手机号码"));setTimeout(function(){11!=t.length?r(new Error("请输入正确新手机号码")):r()},1e3)},trigger:"blur"}],code:[{required:!0,message:"请输入短信验证",trigger:"blur"}]},loading2:!1,getCodeText:"获取验证码",loading22:!1}},mounted:function(){this.findMember()},methods:{findMember:function(){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMember/findMember",type:"GET",dataType:"JSON",success:function(t){console.log(t,"查询多粉会员信息"),0==t.code?(e.walletIndividual=t.data.walletIndividual,e.phone=t.data.phone,e.memberType=t.data.memberType):e.$message.error(t.msg)}})},getVerificationCode:function(){var e=this;this.$refs.ruleForm2.validateField("phone",function(t){if(t)return!1;e.loading2=!0,$.ajax({url:e.DFPAYDOMAIN+"/walletMember/sendVerificationCode",type:"POST",dataType:"JSON",data:{phone:e.ruleForm2.phone,wmemberId:e.walletIndividual.wmemberId},success:function(t){console.log(t,"获取短信验证码"),0==t.code?function(){var t=60;e.getCodeText=t+"s";var r=setInterval(function(){e.getCodeText=t--+"s",t<0&&(e.loading2=!1,e.getCodeText="获取验证码",clearInterval(r))},1e3)}():e.$message({showClose:!0,message:t.msg,type:"error",duration:1500,onClose:function(){e.loading2=!1,e.findMember()}})}})})},cancle:function(e){this.$refs[e].resetFields()},submitRuleForm2:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;t.loading22=!0,$.ajax({url:t.DFPAYDOMAIN+"/walletMember/reset",type:"POST",dataType:"JSON",data:{newPhone:t.ruleForm2.phone,verificationCode:t.ruleForm2.code,wmemberId:t.walletCompany.wmemberId},success:function(e){console.log(e,"确认重置手机"),0==e.code?(t.dialogApply=!1,t.findMember()):t.$message.error(e.msg),t.loading22=!1}})})}}},n={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"wallet-company-messages"},[r("el-breadcrumb",{staticClass:"public-crumbs",attrs:{separator:"/"}},[r("el-breadcrumb-item",{attrs:{onclick:"window.history.go(-1)"}},[e._v("多粉钱包")]),e._v(" "),r("el-breadcrumb-item",[e._v("个人信息")])],1),e._v(" "),r("div",{staticClass:"public-content public-top20"},[r("el-form",{ref:"walletCompany",staticStyle:{width:"600px"},attrs:{model:e.walletCompany,rules:e.rules1,"label-width":"150px"}},[r("el-form-item",{attrs:{label:"姓名："}},[r("span",{domProps:{textContent:e._s(e.walletIndividual.name)}})]),e._v(" "),r("el-form-item",{attrs:{label:"身份证号："}},[r("span",{domProps:{textContent:e._s(e.walletIndividual.identityCardNo)}})]),e._v(" "),r("el-form-item",{attrs:{label:"帐号类型："}},[3==e.memberType?r("span",[e._v("个人会员")]):e._e(),e._v(" "),2==e.memberType?r("span",[e._v("企业会员")]):e._e()]),e._v(" "),r("el-form-item",{attrs:{label:"绑定通联手机号码："}},[r("span",{domProps:{textContent:e._s(e.phone)}}),e._v(" "),r("el-button",{staticStyle:{"margin-left":"20px"},attrs:{type:"primary",size:"small"},on:{click:function(t){e.dialogApply=!0}}},[e._v("修改")])],1),e._v(" "),r("el-form-item",[r("el-button",{attrs:{onclick:"window.history.go(-1)"}},[e._v("返回")])],1)],1)],1),e._v(" "),r("el-dialog",{attrs:{title:"绑定通联手机号码",visible:e.dialogApply,"custom-class":"wallet-drawcash-dialog"},on:{"update:visible":function(t){e.dialogApply=t}}},[r("el-form",{ref:"ruleForm2",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm2,rules:e.rules2,"label-width":"155px"}},[r("el-form-item",{attrs:{label:"新手机号码：",prop:"phone"}},[r("el-input",{attrs:{placeholder:"请输入新手机号码"},model:{value:e.ruleForm2.phone,callback:function(t){e.$set(e.ruleForm2,"phone",t)},expression:"ruleForm2.phone"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"短信验证：",prop:"code"}},[r("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"请输入短信验证"},model:{value:e.ruleForm2.code,callback:function(t){e.$set(e.ruleForm2,"code",t)},expression:"ruleForm2.code"}}),e._v(" "),r("el-button",{attrs:{type:"primary",loading:e.loading2},on:{click:e.getVerificationCode}},[e._v(e._s(e.getCodeText))])],1),e._v(" "),r("el-form-item",{staticStyle:{"text-align":"right","margin-top":"50px"}},[r("el-button",{attrs:{type:"primary",loading:e.loading22},on:{click:function(t){e.submitRuleForm2("ruleForm2")}}},[e._v("确定")]),e._v(" "),r("el-button",{on:{click:function(t){e.dialogApply=!1}}},[e._v("取消")])],1)],1)],1)],1)},staticRenderFns:[]},o=r("oO+S")(a,n,!1,function(e){r("NO47")},null,null);t.default=o.exports},NO47:function(e,t){}});