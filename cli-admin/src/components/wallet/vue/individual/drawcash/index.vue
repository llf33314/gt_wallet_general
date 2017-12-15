<style lang="less">
  .wallet-drawcash {
    .top-msg {
      font-size: 14px;
      border: 1px solid #eeeeee;
      margin-top: 30px;
      overflow: hidden;
      .gray {
        background-color: #f8f8f8;
        padding: 20px 30px 5px;
        overflow: hidden;
        .public-fr {
          margin-top: 15px;
        }
      }
      .row1 {
        margin-bottom: 15px;
      }
      .title {
        padding-right: 15px;
      }
    }
    .data-msg {
      position: relative;
      padding: 15px 0;
      overflow: hidden;
      .num {
        color: #20a0ff;
        font-size: 24px;
        padding-right: 10px;
      }
      .list {
        width: 90%;
        li {
          float: left;
          width: 33.33%;
          box-sizing: border-box;
          padding-left: 30px;
          &:nth-of-type(2) {
            border-left: 1px solid #eeeeee;
            border-right: 1px solid #eeeeee;
          }
        }
      }
      .name {
        margin-bottom: 10px;
      }
    }
    .card-list.el-form-item__content {
      border: 1px solid #dfe6ec;
    }
    .card-item {
      overflow: hidden;
      margin-bottom: 15px;
      position: relative;
      line-height: 40px;
      height: 40px;
      p {
        width: 33.33%;
        float: left;
        &:nth-of-type(1) {
          img {
            width: 40px;
            height: 40px;
            position: absolute;
            top: 0;
            left: 50px;
          }
        }
        &:nth-of-type(2) {
          text-align: center
        }
        &:nth-of-type(3) {
          text-align: right
        }
      }
      .font-size {
        font-size: 14px;
      }
      .name {
        padding-left: 80px;
      }
    }
    .card-type-tips {
      background-color: #fcba65;
      color: #ffffff;
      font-size: 10px;
      padding: 2px 5px;
      line-height: 20px;
      margin-left: 5px;
    }
    .bottom-dps {
      font-size: 13px;
      border-top: 1px solid #ddd;
      padding: 30px 0 50px;
      margin-top: 30px;
      line-height: 2;
    }
    .title-i {
      width: 70px;
      text-align: right;
      display: inline-block;
    }
  }

  .wallet-drawcash-dialog {
    width: 640px;
  }

</style>
<template>
  <section class="wallet-drawcash">
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item onclick="window.history.go(-1)">多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>提现</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-content">
      <div class="top-msg">
        <div class="gray">
          <div class="public-fl">
            <div class="row1 public-c333">
              <span class="title title-i">企业名称：</span>
              <span v-text="name">撒个</span>
            </div>
            <div class="row1 public-c333">
              <span class="title title-i">认证类型：</span>
              <span>个人认证</span>
            </div>
          </div>
        </div>
        <div class="data-msg">
          <ul class="public-fl public-c333 list">
            <li>
              <p class="name">可用余额</p>
              <p>
                <span class="num" v-text="total"></span>元</p>
            </li>
          </ul>
        </div>
      </div>
      <div class="public-table-title public-c666" style="font-weight:500;margin: 40px 0;">
        进行提现
      </div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="115px" class="demo-ruleForm">
        <el-form-item label="手机号码：">
          <span v-text="phone"></span>
          <span class="public-c666">(绑定通联的手机号码)</span>
        </el-form-item>
        <el-form-item label="提现银行卡：" prop="bankId">
          <div>
            <el-radio-group v-model="walletBanksIndex" style="display:block;">
              <ul v-for="(item,index) in walletBanks" style="border: 1px solid #dfe6ec;padding:15px 30px 0;margin-bottom:20px;">
                <li class="card-item">
                  <p>
                    <span>
                      <el-radio :label="index">&nbsp;</el-radio>
                    </span>
                    <img src="http://maint.deeptel.com.cn/upload//image/3/goodtom/3/20171101/FE7F3E296439B9781D364E7F61134C8F.png" alt="">
                    <span class="font-size name" v-text="item.bankName">建设银行</span>
                  </p>
                  <p class="font-size">尾号
                    <span v-text="substring(item.cardNo)"></span>
                    <span class="card-type-tips" v-text="item.cardClass==1?'个人银行卡':'对公账号'">银行卡</span>
                  </p>
                  <p class="font-size">1-3个工作日到账</p>
                </li>
              </ul>
            </el-radio-group>
          </div>
          <!-- <p v-if="walletBanks.length == 1 && walletBanks[0].cardClass != 1" @click="dialogApply4=true" style="color:#409EFF;cursor:pointer;display: inline-block;">添加个人账户</p> -->
          <p class="public-c999" style="margin-top:-10px;">
            <span>此账户提现额度为
              <span style="color:#ff4949" v-text="withdrawQuota">100,000</span>元,</span>
            如需提高提现金额，请点击
            <el-button @click="dialogApply=true" type="primary" size="small">申请</el-button>
          </p>
        </el-form-item>

        <el-form-item label="提现金额：" prop="money">
          <el-input v-model="ruleForm.money" type="number" placeholder="请输入提现金额" style="width:250px;"></el-input>
          <p class="public-c999">
            提现金额最低100元，提现手续费2元/笔
          </p>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')" :loading="loading">提交</el-button>
          <el-button onclick="window.history.go(-1)">返回</el-button>
        </el-form-item>
      </el-form>
      <div class="public-c333 bottom-dps">
        <p>提现手续费怎么收取？</p>
        <p>答：每次提现算做一笔，提现手续费按每笔2元收取。</p>
        <p>提现限额为多少？</p>
        <p>答：单笔最低100元，最高50000元，单日无限制。</p>
        <p>提现到账时间？</p>
        <p>答：1-3个工作日。</p>
      </div>
    </div>
    <el-dialog title="提现短信验证" :visible.sync="dialogApply2" :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false"
      custom-class="wallet-drawcash-dialog">
      <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="155px" class="demo-ruleForm">
        <el-form-item label="短信验证码：" prop="verificationCode">
          <el-input v-model="ruleForm2.verificationCode" placeholder="请输入手机短信验证码"></el-input>
        </el-form-item>
        <el-form-item style="text-align: right;">
          <el-button type="primary" @click="submitForm2('ruleForm2')" :loading="loading2">确认</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="申请提额" :visible.sync="dialogApply" custom-class="wallet-drawcash-dialog">
      <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="155px" class="demo-ruleForm">
        <el-form-item label="银行卡额度：">
          <span style="color:#ff4949" v-text="withdrawQuota">100,000</span>元
        </el-form-item>
        <el-form-item label="需求额度：" prop="quotaValue">
          <el-input v-model="ruleForm3.quotaValue" class="RMBinput" placeholder="请输入金额" style="width:150px;"></el-input>
        </el-form-item>
        <el-form-item label="提额原因：" prop="quotaDesc">
          <el-input type="textarea" v-model="ruleForm3.quotaDesc" placeholder="请输入原因" style="width:350px;"></el-input>
        </el-form-item>
        <el-form-item style="text-align: right;">
          <el-button type="primary" @click="submitForm3('ruleForm3')" :loading="loading3">申请</el-button>
          <el-button @click="dialogApply=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="添加个人账户" :visible.sync="dialogApply4" custom-class="wallet-drawcash-dialog">
      <el-form :model="ruleForm4" :rules="rules4" ref="ruleForm4" label-width="125px" class="demo-ruleForm">
        <el-form-item label="法人姓名：">
          <span v-text="legalName">法人姓名</span>
        </el-form-item>
        <el-form-item label="法人账户：" prop="cardNo">
          <el-input v-model="ruleForm4.cardNo" placeholder="请输入法人个人账户"></el-input>
        </el-form-item>
        <el-form-item label="手机号码：" prop="phone">
          <el-input v-model="ruleForm4.phone" placeholder="请输入银行卡预留手机号码"></el-input>
        </el-form-item>
        <!-- <el-form-item label="短信验证：" prop="quotaDesc">
                <el-input v-model="ruleForm4.quotaDesc" placeholder="请输入手机验证码" style="width:318px;"></el-input>
                <el-button type="primary">获取验证码</el-button>
              </el-form-item> -->
        <el-form-item style="text-align: right;">
          <el-button type="primary" @click="submitRuleForm4('ruleForm4')" :loading="loading4">确认</el-button>
          <el-button @click="dialogApply4=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="确认绑定银行卡" :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false" :visible.sync="bindBankCardDialog"
      custom-class="wallet-drawcash-dialog">
      <el-form :model="ruleForm5" :rules="rules5" ref="ruleForm5" label-width="125px" class="demo-ruleForm">
        <el-form-item label="短信验证：" prop="verificationCode">
          <el-input v-model="ruleForm5.verificationCode" placeholder="请输入手机验证码" style="width:318px;"></el-input>
        </el-form-item>
        <el-form-item style="text-align: right;">
          <el-button type="primary" @click="submitRuleForm5('ruleForm5')" :loading="loading5">确认</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </section>
</template>
<script>
  export default {
    data() {
      var validateMoney = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入提现金额'));
        } else if (value < 100) {
          callback(new Error('提现金额最低100元!'));
        } else if (value > this.withdrawQuota) {
          callback(new Error('提现额度为' + this.withdrawQuota + '元'));
        } else if (value > this.total) {
          callback(new Error('可用余额' + this.total + '元'));
        } else {
          callback();
        }
      };
      return {
        name: '',
        phone: '',
        total: 0,
        id: '',
        walletBanks: [
          // {
          //   "bankCode": "string",
          //   "bankName": "建设银行",
          //   "cardCheck": 0,
          //   "cardClass": 1,
          //   "cardLenth": 0,
          //   "cardNo": "4416467964644666",
          //   "cardState": 1,
          //   "cardType": 1,
          //   "cvv2": "string",
          //   "id": 1,
          //   "identityNo": "string",
          //   "identityType": 0,
          //   "isSafeCard": 0,
          //   "memberNum": "string",
          //   "name": "string",
          //   "phone": "string",
          //   "status": 0,
          //   "tranceNum": "string",
          //   "transDate": "string",
          //   "unionBank": "string",
          //   "validate": "string",
          //   "wmemberId": 0
          // },
          {
            "bankCode": "string",
            "bankName": "中国银行",
            "cardCheck": 0,
            "cardClass": 2,
            "cardLenth": 0,
            "cardNo": "4416467964644123",
            "cardState": 0,
            "cardType": 2,
            "cvv2": "string",
            "id": 0,
            "identityNo": "string",
            "identityType": 0,
            "isSafeCard": 0,
            "memberNum": "string",
            "name": "string",
            "phone": "string",
            "status": 0,
            "tranceNum": "string",
            "transDate": "string",
            "unionBank": "string",
            "validate": "string",
            "wmemberId": 0
          }
        ],
        //

        walletBanksIndex: '',
        ruleForm: {
          money: '',
          bankId: ''
        },
        rules: {
          bankId: [{
            required: true,
            message: '请选择提现银行卡',
            trigger: 'change'
          }],
          money: [{
            required: true,
            validator: validateMoney,
            trigger: 'blur'
          }],
        },
        loading: false,
        withdrawQuota: '0',
        //确认提现
        loading2: false,
        ruleForm2: {
          id: '',
          verificationCode: ''
        },
        rules2: {
          verificationCode: [{
            required: true,
            message: '请输入手机短信验证码',
            trigger: 'change'
          }],
        },
        dialogApply2: false,
        //申请额度
        dialogApply: false,
        loading3: false,
        ruleForm3: {
          wMemberId: '',
          quotaValue: '',
          quotaDesc: ''
        },
        rules3: {
          quotaValue: [{
            required: true,
            message: '请输入申请额度',
            trigger: 'blur'
          }],
          quotaDesc: [{
            required: true,
            message: '请输入申请描述',
            trigger: 'blur'
          }],
        },
        //添加个人账户
        legalName: '法人',
        dialogApply4: false,
        ruleForm4: {
          "bankName": "",
          "cardNo": "",
          "isSafeCard": '',
          "memberId": '',
          "phone": "",
          "unionBank": ""
        },
        rules4: {
          cardNo: [{
            required: true,
            message: '请输入法人个人账户',
            trigger: 'blur'
          }],
          phone: [{
              required: true,
              message: '请输入银行卡预留手机号码',
              trigger: 'blur'
            },
            {
              min: 11,
              max: 11,
              message: '请输入正确银行卡预留手机号码',
              trigger: 'blur'
            }
          ],
        },
        loading4: false,
        //确认绑定银行卡
        bindBankCardDialog: false,
        bindBankCardParams: {
          id: '',
          verificationCode: ''
        },
        ruleForm5: {
          "bankName": "",
        },
        rules5: {
          verificationCode: [{
            required: true,
            message: '请输入手机验证码',
            trigger: 'blur'
          }],
        },
        loading5: false
      }
    },
    watch: {
      //获取bankId
      walletBanksIndex() {
        this.ruleForm.bankId = this.walletBanks[this.walletBanksIndex].id


      }
    },
    mounted() {

      //this.getWalletBanksByMemberId()
      this.findMember()

    },
    methods: {
      //申请额度提交
      walletQuotaAdd() {
        console.log(this.ruleForm3, 'this.ruleForm3')
        $.ajax({
          url: this.DFPAYDOMAIN + '/walletQuota/add',
          type: 'POST',
          dataType: 'JSON',
          data: this.ruleForm3,
          success: res => {
            console.log(res, '申请额度提交')
            if (res.code == 0) {
              this.$message({
                message: res.msg,
                type: 'success',
                duration: 2000,
                onClose: () => {
                  this.dialogApply = false
                }
              });
            } else {
              this.$message.error(res.msg)
            }
            this.loading3 = false
          }
        })
      },
      //申请额度验证
      submitForm3(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.loading3 = true
            this.walletQuotaAdd()
          } else {
            return false;
          }
        });
      },
      //短信验证提交
      confirm() {
        $.ajax({
          url: this.DFPAYDOMAIN + '/walletMoney/confirm',
          type: 'POST',
          dataType: 'JSON',
          data: this.ruleForm2,
          success: res => {
            console.log(res, '提现确认')
            if (res.code == 0) {
              this.$message({
                message: res.msg,
                type: 'success',
                duration: 2000,
                onClose: () => {
                  this.getTotal()
                  this.findMember()
                  this.dialogApply2 = false
                }
              });
            } else {
              this.$message.error(res.msg)
            }
            this.loading2 = false
          }
        })
      },
      //短信验证
      submitForm2(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.loading2 = true
            this.confirm()
          } else {
            return false;
          }
        });
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
              const data = res.data
              const name = data.walletIndividual.name
              this.name = name

              this.phone = res.data.phone
              this.ruleForm3.wMemberId = data.id
              this.ruleForm4.memberId = data.id
              this.ruleForm4.bankName = name

              this.withdrawQuota = data.withdrawQuota

              this.legalName = name

              this.getWalletBanksByMemberId(data.id)
              this.getTotal(data.id)
            } else {
              this.$message.error(res.msg)
            }
          }
        })
      },
      //提交提现申请
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.withdrawApply()
          } else {
            return false;
          }
        });
      },
      //提现(成功后会返回订单id),支付确认时需要传递
      withdrawApply() {
        console.log(this.ruleForm, 'this.ruleForm')
        this.loading = true
        $.ajax({
          url: this.DFPAYDOMAIN + '/walletMoney/withdrawApply',
          type: 'POST',
          dataType: 'JSON',
          data: this.ruleForm,
          success: res => {
            console.log(res, '提现')
            if (res.code == 0) {
              this.confirm.id = res.data
              this.dialogApply2 = true
            } else {
              this.$message.error(res.msg)
            }
            this.loading = false
          }
        })
      },
      //获取余额(提现页面展示)
      getTotal() {
        $.ajax({
          url: this.DFPAYDOMAIN + '/walletMoney/getTotal',
          type: 'POST',
          dataType: 'JSON',
          data: {
            wMemberId: 7
          },
          success: res => {
            console.log(res, '获取余额')
            if (res.code == 0) {
              this.total = res.data
            } else {
              this.$message.error(res.msg)
            }
          }
        })
      },
      //获取会员银行卡列表
      getWalletBanksByMemberId(wmemberId) {
        $.ajax({
          url: this.DFPAYDOMAIN + '/getWalletBanksByMemberId',
          type: 'GET',
          dataType: 'JSON',
          data: {
            wmemberId: wmemberId
          },
          success: res => {
            console.log(res, '获取会员银行卡列表')
            if (res.code == 0) {
              this.walletBanks = res.data
            } else {
              this.$message.error(res.msg)
            }
          }
        })
      },
      //截取
      substring(val) {
        return val.substring(val.length - 4)
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      //添加个人账户
      submitRuleForm4(formName) {
        console.log(this.ruleForm4, 'ruleForm4')
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.loading4 = true
            //成功fn

            this.bindBankCardDialog = true
            this.bindBankCardParams.id = res.data || 0
            //
            $.ajax({
              url: this.DFPAYDOMAIN + '/addBank',
              type: 'POST',
              data: this.ruleForm4,
              dataType: 'json',
              success: (res) => {
                console.log(res, '添加个人账户')
                if (res.code == 0) {
                  this.bindBankCardDialog = true
                  this.bindBankCardParams.id = res.data || 0
                } else {
                  this.$message.error(res.msg)
                }
                this.loading4 = false
              }
            })
          } else {
            return false;
          }
        });
      },
      //确认绑定
      submitRuleForm5(formName) {
        console.log(this.ruleForm5, 'ruleForm5')
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.loading5 = true
            //成功fn
            this.getTotal()
            this.getWalletBanksByMemberId()
            this.findMember()
            this.bindBankCardDialog = false
            this.dialogApply4 = false
            //
            $.ajax({
              url: this.DFPAYDOMAIN + '/bindBankCard',
              type: 'POST',
              data: this.ruleForm5,
              dataType: 'json',
              success: (res) => {
                console.log(res, '确认绑定')
                if (res.code == 0) {
                  this.$message({
                    message: res.msg,
                    type: 'success',
                    duration: 2000,
                    onClose: () => {
                      this.getTotal()
                      this.getWalletBanksByMemberId()
                      this.findMember()
                      this.bindBankCardDialog = false
                    }
                  })
                } else {
                  this.$message.error(res.msg)
                  this.loading5 = true
                }
                this.loading5 = false
              }
            })
          } else {
            return false;
          }
        });
      }
    }
  }

</script>
