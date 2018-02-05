<style lang="less">
.wallet-company-open-base {
  .demo-ruleForm {
    width: 500px;
  }
  .search-bank {
  }
}
</style>
<template>
  <div>
    <section class="wallet-company-open">
      <el-breadcrumb separator="/" class="public-crumbs">
        <el-breadcrumb-item>多粉钱包</el-breadcrumb-item>
        <el-breadcrumb-item>企业开通</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="public-content" style="margin-bottom:0;padding-bottom:0">
        <el-steps :active="1" center="true" style="border-bottom: 1px solid #ddd;padding:20px 0;">
          <el-step title="填写基本信息"></el-step>
          <el-step title="上传证件照片"></el-step>
          <el-step title="提交成功"></el-step>
        </el-steps>
      </div>
    </section>
    <div class="public-content" style="margin-top: 40px;">
      <div class="public-table-title">
        企业信息
        <span class="public-c999 public-f13">按照营业执照上的内容逐字填写</span>
      </div>
      <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="150px" style="width:600px;">
        <el-form-item label="企业名称：" prop="companyName">
          <el-input v-model="ruleForm1.companyName" placeholder="请输入企业名称" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="营业执照号：" prop="businessLicense">
          <el-input v-model="ruleForm1.businessLicense" placeholder="请输入营业执照号" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="企业地址：" prop="addressNull">
          <el-form-item prop="province" style="display:inline-block;width:218px;">
            <el-select v-model="ruleForm1.province" placeholder="请选择" @change="getareas">
              <el-option v-for="item in provinceOptins" :key="item.id" :label="item.city_name" :value="item.id+''">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="area" style="display:inline-block;width:218px;">
            <el-select v-model="ruleForm1.area" placeholder="请选择">
              <el-option v-for="item in areaOptins" :key="item.id" :label="item.city_name" :value="item.id+''">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form-item>
        <el-form-item prop="companyAddress">
          <el-input v-model="ruleForm1.companyAddress" placeholder="请输入详细地址" class="input-width"></el-input>
        </el-form-item>
      </el-form>
      <div class="public-table-title">
        法定代表人信息
      </div>
      <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="150px" style="width:600px;">
        <el-form-item label="法定代表人姓名：" prop="legalName">
          <el-input v-model="ruleForm2.legalName" placeholder="请输入法定代表人姓名" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="法人证件号码：" prop="legalIds">
          <el-input v-model="ruleForm2.legalIds" placeholder="请输入法人证件号码" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="法人手机号码：" prop="legalPhone">
          <el-input v-model="ruleForm2.legalPhone" placeholder="请输入法人手机号码" type="number" class="input-width"></el-input>
        </el-form-item>
      </el-form>
      <div class="public-table-title">
        银行卡信息
      </div>
      <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="150px" style="width:600px;">
        <el-form-item label="开户银行名称：" prop="parentBankName">
          <el-input v-model="ruleForm3.parentBankName" placeholder="请输入开户银行名称" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="企业对公账户：" prop="accountNo">
          <el-input v-model="ruleForm3.accountNo" placeholder="请输入企业对公账户" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="支付行号：" prop="unionBank">
          <el-input v-model="ruleForm3.unionBank" placeholder="请输入支付行号" class="input-width"></el-input>

        </el-form-item>
        <el-form-item label="开户行地区代码：" prop="bankCityNo">
          <el-input v-model="ruleForm3.bankCityNo" placeholder="请输入开户行地区代码" class="input-width"></el-input>
          <el-tooltip placement="right" effect="light">
            <div slot="content" @click="linkBlank" style="cursor:pointer">点击查询开户行地区代码
            </div>
            <i class="el-icon-question" style="position: absolute; color: #666; right: -20px;top: 13px;"></i>
          </el-tooltip>
          <!-- <a class="search-bank" target="_blank" href="http://www.lianhanghao.com/index.php/Index/index/bank/4/province/19/city/243/p/5.html" style="position: absolute; right: -140px; top: 0;color:#409EFF">查询开户行地区代码</a> -->
        </el-form-item>
        <el-form-item label="开户行支行名称：" prop="backName">
          <el-input v-model="ruleForm3.backName" placeholder="请输入开户行支行名称" class="input-width"></el-input>
        </el-form-item>
      </el-form>
      <div class="public-bottom-btns">
        <el-button type="primary" style="margin-left: 210px;" @click="avtive1">下一步</el-button>
      </div>
    </div>
    <el-alert v-if="failReason" :title="failReason" type="error">
    </el-alert>
  </div>
</template>
<script>
export default {
  data() {
    var validatorAccountNo = (rule, value, callback) => {
      if (value == '') {
        callback(new Error('请输入对公帐号'))
      } else if (!this.CheckBankNo(value)) {
        callback(new Error('请输入对公帐号'))
      } else {
        callback()
        // $.ajax({
        //   url: this.DFPAYDOMAIN + '/getBankCardBin',
        //   type: 'POST',
        //   dataType: 'json',
        //   data: {
        //     bankCardNo: this.ruleForm3.accountNo
        //   },
        //   success: (res) => {
        //     console.log(res, '****')
        //     if (res.code == 0) {
        //       if (res.data.iscreditcard == 2) {
        //         callback(new Error('对公帐号不能为信用卡'));
        //       } else {
        //         this.isUnionBank(res.data.bankname)
        //         callback();
        //       }
        //     } else {
        //       callback(new Error(res.msg));
        //     }
        //   }
        // })
      }
    }
    var validatorIsPhone = (rule, value, callback) => {
      if (value == '') {
        callback(new Error('请输入法人手机号码'))
      } else {
        if (this.isPhone(value)) {
          callback();
        } else {
          callback(new Error('请输入正确法人手机号码'))
        }
      }
    }
    return {
      ruleForm1: {
        companyName: '',
        businessLicense: '',
        companyAddress: '',
        area: '',
        province: '',
        addressNull: ''
      },
      rules1: {
        area: [{
          required: true,
          message: '请选择市县区',
          trigger: 'change'
        }],
        addressNull: [{
          required: true,
          message: ' ',
          trigger: 'blur'
        }],
        province: [{
          required: true,
          message: '请选择省份',
          trigger: 'change'
        }],
        companyName: [{
          required: true,
          message: '请输入企业名称',
          trigger: 'blur'
        },
        {
          min: 3,
          max: 30,
          message: '长度在 3 到 30 个字符',
          trigger: 'blur'
        }
        ],
        companyAddress: [{
          required: true,
          message: '请输入企业地址',
          trigger: 'blur'
        },
        {
          min: 3,
          max: 30,
          message: '长度在 3 到 30 个字符',
          trigger: 'blur'
        }
        ],
        businessLicense: [{
          required: true,
          message: '请输入营业执照号',
          trigger: 'blur'
        }],
      },
      provinceOptins: [],
      areaOptins: [],
      ruleForm2: {
        legalName: '',
        legalIds: '',
        legalPhone: '',
      },
      rules2: {
        legalName: [{
          required: true,
          message: '请输入法定代表人姓名',
          trigger: 'blur'
        },
        {
          min: 2,
          max: 10,
          message: '长度在 2 到 10 个字符',
          trigger: 'blur'
        }
        ],
        legalIds: [{
          required: true,
          message: '请输入法人证件号码',
          trigger: 'blur'
        },
        {
          min: 18,
          max: 18,
          message: '请输入正确法人证件号码',
          trigger: 'blur'
        }],
        legalPhone: [{
          required: true,
          // message: '请输入法人手机号码',
          validator: validatorIsPhone,
          trigger: 'blur'
        }],
      },
      ruleForm3: {
        accountNo: '',
        unionBank: '',
        parentBankName: '',
        bankCityNo: '', //开户行地区代码
        backName: '', //开户行支行名称
      },
      rules3: {
        accountNo: [{
          required: true,
          // message: '请输入对公账号',
          validator: validatorAccountNo,
          trigger: 'blur'
        }],
        unionBank: [{
          required: true,
          message: '请输入支付行号',
          trigger: 'blur'
        }],
        bankCityNo: [{
          required: true,
          message: '请输入开户行地区代码',
          trigger: 'blur'
        }],
        backName: [{
          required: true,
          message: '请输入开户行支行名称',
          trigger: 'blur'
        }],
      },
      activeFlag: {},
      isUnionBankFlag: false,
      failReason: '13131'
    }
  },
  watch: {
    'ruleForm1.companyAddress'() {
      this.ruleForm1.addressNull = this.ruleForm1.companyAddress
    }
  },
  mounted() {
    this.getProvince()
    $.ajax({
      url: this.DFPAYDOMAIN + "/walletMember/findMember",
      type: "GET",
      dataType: "json",
      success: res => {
        if (res.code === 0) {
          if (res.data.failReason) {
            this.$message({
              showClose: true,
              message: res.data.failReason,
              type: 'error',
              duration: 0
            });
          }
        }
      }
    })
  },
  methods: {
    //打开查询页面
    linkBlank() {
      window.open('http://www.lianhanghao.com/index.php/Index/index/bank/4/province/19/city/243/p/5.html')
    },
    CheckBankNo(bankno) {
      var bankno = bankno.replace(/\s/g, '');
      if (bankno == "") {
        return false;
      }
      if (bankno.length < 16 || bankno.length > 20) {
        return false;
      }
      var num = /^\d*$/;//全数字
      if (!num.exec(bankno)) {
        return false;
      }
      //开头6位
      var strBin = "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
      if (strBin.indexOf(bankno.substring(0, 2)) == -1) {
        return false;
      }
      return true;
    },
    isPhone(obj) {
      var result = true;
      var isPhone = /^((\+?86)|(\(\+86\)))?(13[0123456789][0-9]{8}|15[0123456789][0-9]{8}|17[0123456789][0-9]{8}|18[0123456789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
      if (!isPhone.test(obj)) {
        result = false;
      }
      return result;
    },
    isChina(s) {  //判断字符是否是中文字符
      var patrn = /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
      if (!patrn.exec(s)) {
        return false;
      } else {
        return true;
      }
    },
    //判断是否要填支付行号
    isUnionBank(name) {
      console.log(name, 'name')
      const bankList = [
        '中国工商银行', '中国农业银行', '中国建设银行', '中信银行', '平安银行', '招商银行', '兴业银行',
        '南京银行', '农信银行'
      ]
      this.isUnionBankFlag = false
      var m = bankList.join('')
      var t = m.indexOf(name)
      if (t > 0) {
        this.isUnionBankFlag = false
      } else {
        this.isUnionBankFlag = true
      }
    },
    //获取省份数据
    getProvince() {
      $.ajax({
        url: this.DFPAYDOMAIN + '/wcommon/getProvince',
        type: 'GET',
        dataType: 'json',
        success: (res) => {
          console.log(res, '6')
          if (res.code == 0) {
            this.provinceOptins = res.data
          } else {
            this.$message.error(res.msg)
          }
        }
      })
    },
    //获取市县区
    getareas(e) {
      $.ajax({
        url: this.DFPAYDOMAIN + '/wcommon/queryCityByParentId',
        type: 'POST',
        dataType: 'json',
        data: {
          parentId: e
        },
        success: (res) => {
          console.log(res, '6')
          if (res.code == 0) {
            this.ruleForm1.area = ''
            this.areaOptins = res.data
          } else {
            this.$message.error(res.msg)
          }
        }
      })
    },
    //验证
    submitForm(formName, flagType) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.activeFlag[flagType] = true
        } else {
          this.activeFlag[flagType] = false
        }
      });
    },
    //下一步
    avtive1() {
      this.submitForm("ruleForm1", 'type1');
      this.submitForm("ruleForm2", 'type2');
      this.submitForm("ruleForm3", 'type3');
      if (this.activeFlag.type1 && this.activeFlag.type2 && this.activeFlag.type3) {
        const form1 = JSON.parse(JSON.stringify(this.ruleForm1))
        this.provinceOptins.forEach((item) => {
          if (this.ruleForm1.province == item.id) {
            form1.province = item.city_code
          }
        })
        this.areaOptins.forEach((item) => {
          if (this.ruleForm1.area == item.id) {
            form1.area = item.city_code
          }
        })
        var obj = Object.assign(form1, this.ruleForm2, this.ruleForm3);
        obj.memberId = this.$route.params.memberId
        this.submit(obj)
      }
    },
    //提交信息
    submit(obj) {
      this.$confirm('提交后不可更改信息，确认提交么?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        $.ajax({
          url: this.DFPAYDOMAIN + '/walletCompany/save',
          type: 'POST',
          dataType: 'json',
          data: obj,
          success: (res) => {
            console.log(res, 'res')
            if (res.code == 0) {
              this.$router.push({
                path: '/wallet/company/open/uploadFile/' + this.$route.params.memberId
              })
            } else {
              this.$message.error(res.msg);
            }
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    }
  }
}

</script>
