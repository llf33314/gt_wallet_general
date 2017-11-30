<style lang="less">


</style>
<template>
  <div>
    <el-button @click="dialogEditPassword=true" type="primary">修改密码</el-button>
    <el-dialog title="修改密码" :visible.sync="dialogEditPassword" custom-class="wallet-index-select-apply-dialog">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="155px" class="demo-ruleForm">
        <el-form-item label="安全手机：">
          158450***46
        </el-form-item>
        <el-form-item label="验证码：" prop="code">
          <el-input v-model="ruleForm.code" placeholder="请输入6位验证码" style="width:250px;"></el-input>
          <el-button type="primary">获取验证码</el-button>
        </el-form-item>
        <el-form-item label="设置新钱包密码：" prop="pwd">
          <el-input v-model="ruleForm.pwd" placeholder="请输入您的新交易密码" type="password" style="width:250px;"></el-input>
        </el-form-item>
        <el-form-item label="确认新钱包密码：" prop="confirm">
          <el-input v-model="ruleForm.confirm" placeholder="请再次输入您的新交易密码" type="password" style="width:250px;"></el-input>
        </el-form-item>
        <el-form-item style="text-align: right;">
          <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
          <el-button @click="resetForm('ruleForm')">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    data() {
      var confirm = (rule, value, callback) => {
        console.log(value, '***********')
        if (value === '') {
          callback(new Error('请再次输入您的新交易密码'));
        } else if (value !== this.ruleForm.pwd) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      }
      return {
        dialogEditPassword: false,
        ruleForm: {
          code: '8888',
          pwd: '',
          confirm: '',
          wmemberId: '0'
        },
        rules: {
          yzm: [{
            required: true,
            message: '请输入验证码',
            trigger: 'blur'
          }],
          pwd: [{
              required: true,
              message: '请输入新钱包密码',
              trigger: 'blur'
            },
            {
              min: 6,
              max: 20,
              message: '长度在 6 到 20 个字符',
              trigger: 'blur'
            }
          ],
          confirm: [{
            validator: confirm,
            trigger: 'blur'
          }],
        },
      }
    },
    methods: {
      save() {
        console.log(this.ruleForm, 'this.ruleForm')
        $.ajax({
          url: this.DFPAYDOMAIN + '/setpwd',
          type: 'POST',
          dataType: 'json',
          data: this.ruleForm,
          success: (res) => {
            if (res.code == 0) {
              this.$message({
                message: '修改密码成功',
                type: 'success'
              });
              this.dialogEditPassword = false
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
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();

        this.dialogEditPassword = false
      },
    }
  }

</script>
