<style lang="less">
.wallet-company-messages {
  .edit {
    position: absolute;
    left: -107px;
    top: 13px;
    cursor: pointer;
  }
}
</style>
<template>
  <div class="wallet-company-messages">
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item onclick="window.history.go(-1)">多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>企业信息</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-content public-top20">
      <el-form :model="walletCompany" :rules="rules1" ref="walletCompany" label-width="150px" style="width:600px;">
        <el-form-item label="企业名称：">
          <span v-text="walletCompany.companyName"></span>
        </el-form-item>
        <el-form-item label="法人姓名：">
          <span v-text="walletCompany.legalName"></span>
        </el-form-item>
        <el-form-item label="身份证号：">
          <span v-text="walletCompany.legalIds"></span>
        </el-form-item>
        <el-form-item label="营业执照号：">
          <span v-text="walletCompany.businessLicense"></span>
        </el-form-item>
        <el-form-item label="企业地址：">
          <!-- <i :class="disabledAddress==true?'el-icon-edit edit':'el-icon-edit-outline edit'" @click="canDisableAddress('ruleForm1')" title="修改企业地址"></i> -->
          <el-form-item prop="province" style="display:inline-block;width:218px;">
            <el-select v-model="ruleForm1.province" placeholder="请选择" :disabled="disabledAddress" @change="getareas">
              <el-option v-for="item in provinceOptins" :key="item.id" :label="item.city_name" :value="item.id+''">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="area" style="display:inline-block;width:218px;">
            <el-select v-model="ruleForm1.area" placeholder="请选择" :disabled="disabledAddress">
              <el-option v-for="item in areaOptins" :key="item.id" :label="item.city_name" :value="item.id+''">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form-item>
        <el-form-item prop="companyAddress">
          <el-input v-model="walletCompany.companyAddress" :disabled="disabledAddress" placeholder="请输入详细地址" class="input-width"></el-input>
        </el-form-item>
        <!-- <el-form-item>
          <el-button type="primary" @click="submitRuleForm1('ruleForm1')">保存</el-button>
        </el-form-item> -->
      </el-form>
      <div class="public-table-title public-c666" style="font-weight:500;margin: 40px 0;">
        银行卡信息
      </div>
      <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="150px" style="width:600px;">
        <el-form-item label="账户类型：">
          <span v-if="memberType == 2">企业会员</span>
          <span v-if="memberType == 3">个人会员</span>
        </el-form-item>
        <el-form-item label="绑定通联手机号码：">
          <span v-text="walletCompany.legalPhone"></span>
          <el-button type="primary" size="small" style="margin-left: 110px;" @click="dialogApply=true">修改</el-button>
        </el-form-item>
        <el-form-item label="开户行地区代码：">
          <span v-text="walletCompany.bankCtiyNo || '——'"></span>
        </el-form-item>
        <el-form-item label="开户行支行名称：">
          <span v-text="walletCompany.bankName || '——'"></span>
        </el-form-item>
        <el-form-item label="支付行号：">
          <span v-text="walletCompany.unionBank || '——'"></span>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitRuleForm1('walletCompany')">保存</el-button>
          <el-button onclick="window.history.go(-1)">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog title="绑定通联手机号码" :visible.sync="dialogApply" custom-class="wallet-drawcash-dialog">
      <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="155px" class="demo-ruleForm">
        <el-form-item label="新手机号码：" prop="phone">
          <el-input v-model="ruleForm2.phone" placeholder="请输入新手机号码"></el-input>
        </el-form-item>
        <el-form-item label="短信验证：" prop="code">
          <el-input v-model="ruleForm2.code" placeholder="请输入短信验证" style="width:200px;"></el-input>
          <el-button type="primary" @click="getVerificationCode" :loading="loading2">{{getCodeText}}</el-button>
        </el-form-item>
        <el-form-item style="text-align: right;margin-top: 50px;">
          <el-button type="primary" @click="submitRuleForm2('ruleForm2')" :loading="loading22">确定</el-button>
          <el-button @click="dialogApply=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    console.log(window)
    var isValitrPhone = (rule, value, callback) => {
      if (value == '') {
        callback(new Error('请输入新手机号码'))
      } else {
        if (this.isPhone(value)) {
          callback();
        } else {
          callback(new Error('请输入正确新手机号码'))
        }
      }
    }
    return {
      disabledAddress: false,
      dialogApply: false,
      ruleForm1: {
        companyName: '',
        businessLicense: '',
        companyAddress: '',
        area: '',
        province: ''
      },
      rules1: {
        area: [{
          required: true,
          message: '请选择市县区',
          trigger: 'change'
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
      walletCompany: {
        // "accountNo": "string",
        // "area": "130100",
        // "bankCtiyNo": "开户行地区代码 ",
        // "bankName": "开户行支行名",
        // "businessLicense": "营业执照号",
        // "companyAddress": "地址",
        // "companyName": "企业名称",
        // "country": "string",
        // "doBusinessUrl": "string",
        // "id": 0,
        // "identityType": 0,
        // "identitycardUrl1": "string",
        // "identitycardUrl2": "string",
        // "legalIds": "法人证件号码",
        // "legalName": "法人姓名",
        // "legalPhone": "string",
        // "licenseUrl": "string",
        // "memberNum": "string",
        // "organizationCode": "string",
        // "parentBankName": "string",
        // "province": "省份",
        // "telephone": "联系电话",
        // "unionBank": "支付行号",
        // "wmemberId": 0
      },
      //重置手机
      ruleForm2: {
        phone: '',
        code: ''
      },
      rules2: {
        phone: [{
          required: true,
          validator: isValitrPhone,
          trigger: 'blur'
        }],
        code: [{
          required: true,
          message: '请输入短信验证',
          trigger: 'blur'
        }],
      },
      loading2: false,
      getCodeText: '获取验证码',
      loading22: false,
      memberType: '',
    }
  },
  mounted() {
    this.findMember()
  },
  methods: {
    isPhone(obj) {
      var result = true;
      var isPhone = /^((\+?86)|(\(\+86\)))?(13[0123456789][0-9]{8}|15[0123456789][0-9]{8}|17[0123456789][0-9]{8}|18[0123456789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
      if (!isPhone.test(obj)) {
        result = false;
      }
      return result;
    },
    //根据city_code查找省id
    getCityCode(city_code) {
      $.ajax({
        url: this.DFPAYDOMAIN + '/wcommon/getProvince',
        type: 'GET',
        dataType: 'json',
        success: (res) => {
          console.log(res, '6')
          if (res.code == 0) {
            this.provinceOptins = res.data
            res.data.forEach((item) => {
              if (city_code == item.city_code) {
                this.ruleForm1.province = item.id + ''
              }
            })
            this.getCityCode2()
          } else {
            this.$message.error(res.msg)
          }
        }
      })
    },
    //根据city_code查找市县id
    getCityCode2() {
      $.ajax({
        url: this.DFPAYDOMAIN + '/wcommon/queryCityByParentId',
        type: 'POST',
        dataType: 'json',
        data: {
          parentId: this.ruleForm1.province
        },
        success: (res) => {
          console.log(res, '6')
          if (res.code == 0) {
            this.ruleForm1.area = ''
            this.areaOptins = res.data

            res.data.forEach((item) => {
              if (this.walletCompany.area == item.city_code) {
                this.ruleForm1.area = item.id + ''
              }
            })

          } else {
            this.$message.error(res.msg)
          }
        }
      })
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
    //查询多粉会员信息
    findMember() {
      $.ajax({
        url: this.DFPAYDOMAIN + '/walletMember/findMember',
        type: 'GET',
        dataType: 'JSON',
        success: (res) => {
          console.log(res, '查询多粉会员信息')
          if (res.code == 0) {
            this.walletCompany = res.data.walletCompany
            this.walletCompany.legalPhone = res.data.phone
            this.memberType = res.data.memberType
            this.getCityCode(res.data.walletCompany.province)
          } else {
            this.$message.error(res.msg)
          }
        }
      })
    },
    //获取短信验证码
    getVerificationCode() {
      const time = () => {
        let t = 60
        this.getCodeText = t + 's'
        var s = setInterval(() => {
          this.getCodeText = t-- + 's'
          if (t < 0) {
            this.loading2 = false
            this.getCodeText = '获取验证码'
            clearInterval(s)
          }
        }, 1000)
      }
      this.$refs['ruleForm2'].validateField('phone', (valid) => {
        if (!valid) {
          this.loading2 = true
          $.ajax({
            url: this.DFPAYDOMAIN + '/walletMember/sendVerificationCode',
            type: 'POST',
            dataType: 'JSON',
            data: {
              phone: this.ruleForm2.phone,
              wmemberId: this.walletCompany.wmemberId,
              verificationCodeType: 9,
            },
            success: (res) => {
              console.log(res, '获取短信验证码')
              if (res.code == 0) {
                time()
              } else {
                this.$message({
                  showClose: true,
                  message: res.msg,
                  type: 'error',
                  duration: 1500,
                  onClose: () => {
                    this.loading2 = false
                    this.findMember()
                  }
                });
              }
            }
          })
        } else {
          return false;
        }
      });
    },
    cancle(formName) {
      this.$refs[formName].resetFields();
    },
    // 确认重置手机
    submitRuleForm2(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading22 = true
          $.ajax({
            url: this.DFPAYDOMAIN + '/walletMember/reset',
            type: 'POST',
            dataType: 'JSON',
            data: {
              newPhone: this.ruleForm2.phone,
              verificationCode: this.ruleForm2.code,
              wmemberId: this.walletCompany.wmemberId
            },
            success: (res) => {
              console.log(res, '确认重置手机')
              if (res.code == 0) {
                this.dialogApply = false
                this.findMember()
              } else {
                this.$message.error(res.msg)
              }
              this.loading22 = false
            }
          })
        } else {
          return false;
        }
      });
    },
    //保存修改企业地址
    submitRuleForm1(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
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
          $.ajax({
            url: this.DFPAYDOMAIN + '/walletCompany/updateAddress',
            type: 'POST',
            dataType: 'JSON',
            data: {
              companyAddress: this.walletCompany.companyAddress,
              memberId: this.walletCompany.wmemberId,
              province: form1.province,
              area: form1.area
            },
            success: (res) => {
              if (res.code == 0) {
                this.findMember()
              } else {
                this.$message.error(res.msg + res.code)
              }
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}

</script>
