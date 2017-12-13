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
      <el-breadcrumb-item>多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/wallet/index' }">个人信息</el-breadcrumb-item>
      <el-breadcrumb-item>提现</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-content">
      <div class="top-msg">
        <div class="gray">
          <div class="public-fl">
            <div class="row1 public-c333">
              <span class="title title-i">姓名：</span>
              <span>撒个</span>
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
              <p style="padding-left: 20px;">
                <span class="num">0</span>元</p>
            </li>
          </ul>
        </div>
      </div>
      <div class="public-table-title public-c666" style="font-weight:500;margin-bottom:40px;">
        进行提现
      </div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="155px" class="demo-ruleForm">
        <el-form-item label="提现银行卡：">
          <div style="border: 1px solid #dfe6ec;padding:15px 30px 0;">
            <el-radio-group v-model="radio2" style="display:block;">
              <ul>
                <li class="card-item">
                  <p>
                    <el-radio :label="3">&nbsp;</el-radio>
                    <img src="http://maint.deeptel.com.cn/upload//image/3/goodtom/3/20171101/FE7F3E296439B9781D364E7F61134C8F.png" alt="">
                    <span class="font-size name">建设银行</span>
                  </p>
                  <p class="font-size">尾号3365
                    <span class="card-type-tips">银行卡</span>
                  </p>
                  <p class="font-size">1-3个工作日到账</p>
                </li>
              </ul>
            </el-radio-group>
          </div>
          <p class="public-c999" style="padding-top:10px;">
            <span>此卡每天限提现金额为
              <span style="color:#ff4949">100,000</span>元，如需提高提现金额，请点击</span>
            <el-button @click="dialogApply=true" type="primary" size="small">申请</el-button>
          </p>
        </el-form-item>
        <el-form-item label="提现金额：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入提现金额" style="width:250px;"></el-input>
          <p class="public-c999">
            提现金额最低100元，提现手续费2元/笔
          </p>
        </el-form-item>
        <el-form-item label="设置新钱包密码：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入您的新交易密码" style="width:250px;"></el-input>
        </el-form-item>
        <el-form-item style="padding-top:40px;">
          <el-button type="primary" @click="submitForm('ruleForm')">确认提现</el-button>
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
    <el-dialog title="申请提额" :visible.sync="dialogApply" custom-class="wallet-drawcash-dialog">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="155px" class="demo-ruleForm">
        <el-form-item label="银行卡额度：">
          <span style="color:#ff4949">100,000</span>元
        </el-form-item>
        <el-form-item label="需求额度：" prop="name">
          <el-input v-model="ruleForm.name" class="RMBinput" placeholder="请输入金额" style="width:150px;"></el-input>
        </el-form-item>
        <el-form-item label="提额原因：" prop="name">
          <el-input type="textarea" v-model="ruleForm.name" placeholder="请输入原因" style="width:350px;"></el-input>
        </el-form-item>
        <el-form-item style="text-align: right;">
          <el-button type="primary" @click="submitForm('ruleForm')">申请</el-button>
          <el-button @click="resetForm('ruleForm')">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </section>
</template>
<script>
  export default {
    data() {
      return {
        dialogApply: false,
        radio2: '',
        ruleForm: {
          name: '',
        },
        rules: {
          name: [{
              required: true,
              message: '请输入活动名称',
              trigger: 'blur'
            },
            {
              min: 3,
              max: 5,
              message: '长度在 3 到 5 个字符',
              trigger: 'blur'
            }
          ],
        },
      }
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    }
  }

</script>
