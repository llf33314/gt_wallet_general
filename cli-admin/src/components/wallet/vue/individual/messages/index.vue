<style lang="less">
.wallet-company-messages {
}
</style>
      <template>
  <div class="wallet-company-messages">
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item onclick="window.history.go(-1)">多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>个人信息</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-content public-top20">
      <el-form :model="walletCompany" :rules="rules1" ref="walletCompany" label-width="150px" style="width:600px;">
        <el-form-item label="姓名：">
          <span v-text="walletIndividual.name"></span>
        </el-form-item>
        <el-form-item label="身份证号：">
          <span v-text="walletIndividual.identityCardNo"></span>
        </el-form-item>
        <el-form-item label="帐号类型：">
          <span v-if="memberType==3">个人会员</span>
          <span v-if="memberType==2">企业会员</span>
        </el-form-item>
        <el-form-item label="绑定通联手机号码：">
          <span v-text="phone"></span>
          <el-button type="primary" size="small" style="margin-left: 20px;" @click="dialogApply=true">修改</el-button>
        </el-form-item>
        <el-form-item>
          <el-button onclick="window.history.go(-1)">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog title="绑定通联手机号码" :visible.sync="dialogApply" @close="loading22=false" custom-class="wallet-drawcash-dialog">
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
      if (!value) {
        return callback(new Error('请输入新手机号码'));
      }
      setTimeout(() => {
        if (value.length != 11) {
          callback(new Error('请输入正确新手机号码'));
        } else {
          callback();
        }
      }, 1000);
    };
    return {
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
      walletIndividual: {},
      phone: "",
      memberType: '',
      walletCompany: {
        "accountNo": "string",
        "area": "130100",
        "bankCtiyNo": "开户行地区代码 ",
        "bankName": "开户行支行名",
        "businessLicense": "营业执照号",
        "companyAddress": "地址",
        "companyName": "企业名称",
        "country": "string",
        "doBusinessUrl": "string",
        "id": 0,
        "identityType": 0,
        "identitycardUrl1": "string",
        "identitycardUrl2": "string",
        "legalIds": "法人证件号码",
        "legalName": "法人姓名",
        "legalPhone": "string",
        "licenseUrl": "string",
        "memberNum": "string",
        "organizationCode": "string",
        "parentBankName": "string",
        "province": "省份",
        "telephone": "联系电话",
        "unionBank": "支付行号",
        "wmemberId": 0
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
      loading22: false
    }
  },
  mounted() {
    this.findMember()
  },
  methods: {
    //查询多粉会员信息
    findMember() {
      $.ajax({
        url: this.DFPAYDOMAIN + '/walletMember/findMember',
        type: 'GET',
        dataType: 'JSON',
        success: (res) => {
          console.log(res, '查询多粉会员信息')
          if (res.code == 0) {
            this.walletIndividual = res.data.walletIndividual
            this.phone = res.data.phone
            this.memberType = res.data.memberType
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
              wmemberId: this.walletIndividual.wmemberId
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
  }
}

</script>
      