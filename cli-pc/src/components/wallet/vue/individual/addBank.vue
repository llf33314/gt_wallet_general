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
        padding-right: 25px;
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
      line-height: 2.5;
      border-top: 1px solid #ddd;
      padding: 30px 0 50px;
      margin-top: 30px;
    }
  }

  .wallet-drawcash-dialog {
    width: 640px;
  }

</style>
<template>
  <section class="wallet-drawcash">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="155px" class="demo-ruleForm">
      <el-form-item label="姓名：" prop="bankName">
        <el-input v-model="ruleForm.bankName" placeholder="必须与注册人姓名一致"></el-input>
      </el-form-item>
      <el-form-item label="手机号：" prop="phone ">
        <el-input v-model="ruleForm.phone" placeholder="银行预留手机号"></el-input>
      </el-form-item>
      <el-form-item label="银行卡号：" prop="cardNo">
        <el-input v-model="ruleForm.cardNo"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">申请</el-button>
        <el-button @click="resetForm('ruleForm')">取消</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>
<script>
  import {
    wallet
  } from './../../api/index'
  export default {
    data() {
      return {
        number: 0,
        dialogApply: false,
        ruleForm: {
          wMemberId: 9,
          quotaValue: '2222',
          quotaDesc: '34534534534'
        },
        rules: {
          quotaValue: [{
            required: true,
            message: '请输入申请额度',
            trigger: 'blur'
          }],
          quotaDesc: [{
              required: true,
              message: '请输入申请描述',
              trigger: 'blur'
            },
            {
              min: 3,
              max: 75,
              message: '长度在 3 到 100 个字符',
              trigger: 'blur'
            }
          ]
        },
      }
    },
    mounted() {
      this.number = this.store.state.wallet.vipMsg.title
    },
    methods: {
      //提交申请
      save() {
        $.ajax({
          url: this.DFPAYDOMAIN + '/walletQuota/add',
          type: 'POST',
          dataType: 'json',
          data: this.ruleForm,
          success: (res) => {
            console.log(res, '提交申请')
            if (res.code == 0) {
              this.$message({
                message: '提交成功',
                type: 'success'
              });
              this.dialogApply = false
            } else {
              this.$message.error(res.msg);
            }
          }
        })
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.save()
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.dialogApply = false
      },
    }
  }

</script>
