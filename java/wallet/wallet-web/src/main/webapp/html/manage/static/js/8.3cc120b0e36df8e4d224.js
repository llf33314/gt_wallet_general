webpackJsonp([8],{"6xzj":function(e,t){},ML6T:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l={data:function(){var e=this;return{name:"",phone:"",total:0,id:"",walletBanks:[],walletBanksIndex:"",ruleForm:{money:"",bankId:""},rules:{bankId:[{required:!0,message:"请选择提现银行卡",trigger:"change"}],money:[{required:!0,validator:function(t,a,l){""===a?l(new Error("请输入提现金额")):a<100?l(new Error("提现金额最低100元!")):a>e.withdrawQuota?l(new Error("提现额度为"+e.withdrawQuota+"元")):a>e.total?l(new Error("可用余额"+e.total+"元")):l()},trigger:"blur"}]},loading:!1,withdrawQuota:"0",loading2:!1,ruleForm2:{id:"",verificationCode:""},rules2:{verificationCode:[{required:!0,message:"请输入手机短信验证码",trigger:"change"}]},dialogApply2:!1,dialogApply:!1,loading3:!1,ruleForm3:{wMemberId:"",quotaValue:"",quotaDesc:""},rules3:{quotaValue:[{validator:function(t,a,l){""==a?l(new Error("请输入需求额度")):a<=e.withdrawQuota?l(new Error("最低需求额度为"+e.withdrawQuota)):l()},trigger:"blur"}],quotaDesc:[{required:!0,message:"请输入申请描述",trigger:"blur"}]},legalName:"法人",dialogApply4:!1,ruleForm4:{bankName:"",cardNo:"",isSafeCard:"",memberId:"",phone:"",unionBank:""},rules4:{cardNo:[{required:!0,message:"请输入法人个人账户",trigger:"blur"}],phone:[{required:!0,message:"请输入银行卡预留手机号码",trigger:"blur"},{min:11,max:11,message:"请输入正确银行卡预留手机号码",trigger:"blur"}]},loading4:!1,bindBankCardDialog:!1,bindBankCardParams:{id:"",verificationCode:""},ruleForm5:{bankName:""},rules5:{verificationCode:[{required:!0,message:"请输入手机验证码",trigger:"blur"}]},loading5:!1}},watch:{walletBanksIndex:function(){this.ruleForm.bankId=this.walletBanks[this.walletBanksIndex].id}},mounted:function(){this.findMember()},methods:{walletQuotaAdd:function(){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/walletQuota/add",type:"POST",dataType:"JSON",data:this.ruleForm3,success:function(t){0==t.code?e.$message({message:t.msg,type:"success",duration:2e3,onClose:function(){e.dialogApply=!1}}):e.$message.error(t.msg),e.loading3=!1}})},submitForm3:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;t.loading3=!0,t.walletQuotaAdd()})},confirm:function(){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMoney/confirm",type:"POST",dataType:"JSON",data:this.ruleForm2,success:function(t){0==t.code?e.$message({message:t.msg,type:"success",duration:2e3,onClose:function(){e.getTotal(),e.findMember(),e.dialogApply2=!1}}):e.$message.error(t.msg),e.loading2=!1}})},submitForm2:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;t.loading2=!0,t.confirm()})},findMember:function(){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMember/findMember",type:"GET",dataType:"JSON",success:function(t){if(0==t.code){var a=t.data,l=a.walletIndividual.name;e.name=l,e.phone=t.data.phone,e.ruleForm3.wMemberId=a.id,e.ruleForm4.memberId=a.id,e.ruleForm4.bankName=l,e.withdrawQuota=a.withdrawQuota,e.legalName=l,e.getWalletBanksByMemberId(a.id),e.getTotal(a.id)}else e.$message.error(t.msg)}})},submitForm:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;t.withdrawApply()})},withdrawApply:function(){var e=this;this.loading=!0,$.ajax({url:this.DFPAYDOMAIN+"/walletMoney/withdrawApply",type:"POST",dataType:"JSON",data:this.ruleForm,success:function(t){0==t.code?(e.confirm.id=t.data,e.dialogApply2=!0):e.$message.error(t.msg),e.loading=!1}})},getTotal:function(){var e=this;$.ajax({url:this.DFPAYDOMAIN+"/walletMoney/getTotal",type:"POST",dataType:"JSON",data:{wMemberId:7},success:function(t){0==t.code?e.total=t.data:e.$message.error(t.msg)}})},getWalletBanksByMemberId:function(e){var t=this;$.ajax({url:this.DFPAYDOMAIN+"/getWalletBanksByMemberId",type:"GET",dataType:"JSON",data:{wmemberId:e},success:function(e){0==e.code?t.walletBanks=e.data:t.$message.error(e.msg)}})},substring:function(e){return e.substring(e.length-4)},resetForm:function(e){this.$refs[e].resetFields()},submitRuleForm4:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;t.loading4=!0,$.ajax({url:t.DFPAYDOMAIN+"/addBank",type:"POST",data:t.ruleForm4,dataType:"json",success:function(e){0==e.code?(t.bindBankCardDialog=!0,t.bindBankCardParams.id=e.data||0):t.$message.error(e.msg),t.loading4=!1}})})},submitRuleForm5:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;t.loading5=!0,$.ajax({url:t.DFPAYDOMAIN+"/bindBankCard",type:"POST",data:t.ruleForm5,dataType:"json",success:function(e){0==e.code?t.$message({message:e.msg,type:"success",duration:2e3,onClose:function(){t.getTotal(),t.getWalletBanksByMemberId(),t.findMember(),t.bindBankCardDialog=!1}}):(t.$message.error(e.msg),t.loading5=!0),t.loading5=!1}})})}}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("section",{staticClass:"wallet-drawcash"},[a("el-breadcrumb",{staticClass:"public-crumbs",attrs:{separator:"/"}},[a("el-breadcrumb-item",{attrs:{onclick:"window.history.go(-1)"}},[e._v("多粉钱包")]),e._v(" "),a("el-breadcrumb-item",[e._v("提现")])],1),e._v(" "),a("div",{staticClass:"public-content"},[a("div",{staticClass:"top-msg"},[a("div",{staticClass:"gray"},[a("div",{staticClass:"public-fl"},[a("div",{staticClass:"row1 public-c333"},[a("span",{staticClass:"title title-i"},[e._v("个人名称：")]),e._v(" "),a("span",{domProps:{textContent:e._s(e.name)}},[e._v("撒个")])]),e._v(" "),e._m(0)])]),e._v(" "),a("div",{staticClass:"data-msg"},[a("ul",{staticClass:"public-fl public-c333 list"},[a("li",[a("p",{staticClass:"name"},[e._v("可用余额")]),e._v(" "),a("p",[a("span",{staticClass:"num",domProps:{textContent:e._s(e.total)}}),e._v("元")])])])])]),e._v(" "),a("div",{staticClass:"public-table-title public-c666",staticStyle:{"font-weight":"500",margin:"40px 0"}},[e._v("\n      进行提现\n    ")]),e._v(" "),a("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-width":"115px"}},[a("el-form-item",{attrs:{label:"手机号码："}},[a("span",{domProps:{textContent:e._s(e.phone)}}),e._v(" "),a("span",{staticClass:"public-c666"},[e._v("(已绑定通联)")])]),e._v(" "),a("el-form-item",{attrs:{label:"提现银行卡：",prop:"bankId"}},[a("div",[a("el-radio-group",{staticStyle:{display:"block"},model:{value:e.walletBanksIndex,callback:function(t){e.walletBanksIndex=t},expression:"walletBanksIndex"}},e._l(e.walletBanks,function(t,l){return a("ul",{staticStyle:{border:"1px solid #dfe6ec",padding:"15px 30px 0","margin-bottom":"20px"}},[a("li",{staticClass:"card-item"},[a("p",[a("span",[a("el-radio",{attrs:{label:l}},[e._v(" ")])],1),e._v(" "),a("img",{attrs:{src:t.iconUrl,alt:"logo"}}),e._v(" "),a("span",{staticClass:"font-size name",domProps:{textContent:e._s(t.bankName)}},[e._v("建设银行")])]),e._v(" "),a("p",{staticClass:"font-size"},[e._v("尾号\n                  "),a("span",{domProps:{textContent:e._s(e.substring(t.cardNo))}}),e._v(" "),a("span",{staticClass:"card-type-tips",domProps:{textContent:e._s(1==t.cardClass?"个人银行卡":"对公账号")}},[e._v("银行卡")])]),e._v(" "),a("p",{staticClass:"font-size"},[e._v("1-3个工作日到账")])])])}))],1),e._v(" "),a("p",{staticClass:"public-c999",staticStyle:{"margin-top":"-10px"}},[a("span",[e._v("此账户提现额度为\n            "),a("span",{staticStyle:{color:"#ff4949"},domProps:{textContent:e._s(e.withdrawQuota)}},[e._v("100,000")]),e._v("元,")]),e._v("\n          如需提高提现金额，请点击\n          "),a("el-button",{attrs:{type:"primary",size:"small"},on:{click:function(t){e.dialogApply=!0}}},[e._v("申请")])],1)]),e._v(" "),a("el-form-item",{attrs:{label:"提现金额：",prop:"money"}},[a("el-input",{staticStyle:{width:"250px"},attrs:{type:"number",placeholder:"请输入提现金额"},model:{value:e.ruleForm.money,callback:function(t){e.$set(e.ruleForm,"money",t)},expression:"ruleForm.money"}}),e._v(" "),a("p",{staticClass:"public-c999"},[e._v("\n          提现金额最低100元，提现手续费2元/笔\n        ")])],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary",loading:e.loading},on:{click:function(t){e.submitForm("ruleForm")}}},[e._v("提现")]),e._v(" "),a("el-button",{attrs:{onclick:"window.history.go(-1)"}},[e._v("返回")])],1)],1),e._v(" "),e._m(1)],1),e._v(" "),a("el-dialog",{attrs:{title:"提现短信验证",visible:e.dialogApply2,"close-on-click-modal":!1,"close-on-press-escape":!1,"show-close":!1,"custom-class":"wallet-drawcash-dialog"},on:{"update:visible":function(t){e.dialogApply2=t},close:function(t){e.loading2=!1}}},[a("el-form",{ref:"ruleForm2",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm2,rules:e.rules2,"label-width":"155px"}},[a("el-form-item",{attrs:{label:"短信验证码：",prop:"verificationCode"}},[a("el-input",{attrs:{placeholder:"请输入手机短信验证码"},model:{value:e.ruleForm2.verificationCode,callback:function(t){e.$set(e.ruleForm2,"verificationCode",t)},expression:"ruleForm2.verificationCode"}})],1),e._v(" "),a("el-form-item",{staticStyle:{"text-align":"right"}},[a("el-button",{attrs:{type:"primary",loading:e.loading2},on:{click:function(t){e.submitForm2("ruleForm2")}}},[e._v("确认")])],1)],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"申请提额",visible:e.dialogApply,"custom-class":"wallet-drawcash-dialog"},on:{"update:visible":function(t){e.dialogApply=t},close:function(t){e.loading3=!1}}},[a("el-form",{ref:"ruleForm3",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm3,rules:e.rules3,"label-width":"155px"}},[a("el-form-item",{attrs:{label:"银行卡额度："}},[a("span",{staticStyle:{color:"#ff4949"},domProps:{textContent:e._s(e.withdrawQuota)}},[e._v("100,000")]),e._v("元\n      ")]),e._v(" "),a("el-form-item",{attrs:{label:"需求额度：",prop:"quotaValue"}},[a("el-input",{staticClass:"RMBinput",staticStyle:{width:"150px"},attrs:{placeholder:"请输入金额"},model:{value:e.ruleForm3.quotaValue,callback:function(t){e.$set(e.ruleForm3,"quotaValue",t)},expression:"ruleForm3.quotaValue"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"提额原因：",prop:"quotaDesc"}},[a("el-input",{staticStyle:{width:"350px"},attrs:{type:"textarea",placeholder:"请输入原因"},model:{value:e.ruleForm3.quotaDesc,callback:function(t){e.$set(e.ruleForm3,"quotaDesc",t)},expression:"ruleForm3.quotaDesc"}})],1),e._v(" "),a("el-form-item",{staticStyle:{"text-align":"right"}},[a("el-button",{attrs:{type:"primary",loading:e.loading3},on:{click:function(t){e.submitForm3("ruleForm3")}}},[e._v("申请")]),e._v(" "),a("el-button",{on:{click:function(t){e.dialogApply=!1}}},[e._v("取消")])],1)],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"添加个人账户",visible:e.dialogApply4,"custom-class":"wallet-drawcash-dialog"},on:{"update:visible":function(t){e.dialogApply4=t},close:function(t){e.loading4=!1}}},[a("el-form",{ref:"ruleForm4",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm4,rules:e.rules4,"label-width":"125px"}},[a("el-form-item",{attrs:{label:"法人姓名："}},[a("span",{domProps:{textContent:e._s(e.legalName)}},[e._v("法人姓名")])]),e._v(" "),a("el-form-item",{attrs:{label:"法人账户：",prop:"cardNo"}},[a("el-input",{attrs:{placeholder:"请输入法人个人账户"},model:{value:e.ruleForm4.cardNo,callback:function(t){e.$set(e.ruleForm4,"cardNo",t)},expression:"ruleForm4.cardNo"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"手机号码：",prop:"phone"}},[a("el-input",{attrs:{placeholder:"请输入银行卡预留手机号码"},model:{value:e.ruleForm4.phone,callback:function(t){e.$set(e.ruleForm4,"phone",t)},expression:"ruleForm4.phone"}})],1),e._v(" "),a("el-form-item",{staticStyle:{"text-align":"right"}},[a("el-button",{attrs:{type:"primary",loading:e.loading4},on:{click:function(t){e.submitRuleForm4("ruleForm4")}}},[e._v("确认")]),e._v(" "),a("el-button",{on:{click:function(t){e.dialogApply4=!1}}},[e._v("取消")])],1)],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"确认绑定银行卡","close-on-click-modal":!1,"close-on-press-escape":!1,"show-close":!1,visible:e.bindBankCardDialog,"custom-class":"wallet-drawcash-dialog"},on:{close:function(t){e.loading5=!1},"update:visible":function(t){e.bindBankCardDialog=t}}},[a("el-form",{ref:"ruleForm5",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm5,rules:e.rules5,"label-width":"125px"}},[a("el-form-item",{attrs:{label:"短信验证：",prop:"verificationCode"}},[a("el-input",{staticStyle:{width:"318px"},attrs:{placeholder:"请输入手机验证码"},model:{value:e.ruleForm5.verificationCode,callback:function(t){e.$set(e.ruleForm5,"verificationCode",t)},expression:"ruleForm5.verificationCode"}})],1),e._v(" "),a("el-form-item",{staticStyle:{"text-align":"right"}},[a("el-button",{attrs:{type:"primary",loading:e.loading5},on:{click:function(t){e.submitRuleForm5("ruleForm5")}}},[e._v("确认")])],1)],1)],1)],1)},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"row1 public-c333"},[t("span",{staticClass:"title title-i"},[this._v("认证类型：")]),this._v(" "),t("span",[this._v("个人认证")])])},function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"public-c333 bottom-dps"},[t("p",[this._v("提现手续费怎么收取？")]),this._v(" "),t("p",[this._v("答：每次提现算做一笔，提现手续费按每笔2元收取。")]),this._v(" "),t("p",[this._v("提现限额为多少？")]),this._v(" "),t("p",[this._v("答：单笔最低100元，最高50000元，单日无限制。")]),this._v(" "),t("p",[this._v("提现到账时间？")]),this._v(" "),t("p",[this._v("答：1-3个工作日。")])])}]},i=a("UF+H")(l,r,!1,function(e){a("6xzj")},null,null);t.default=i.exports}});