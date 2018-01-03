
<style lang="less" scoped>
.public-crumbs {
  margin-bottom: 40px;
}
</style>
<template>
    <div>
        <el-breadcrumb separator="/" class="public-crumbs">
            <el-breadcrumb-item>多粉钱包</el-breadcrumb-item>
            <el-breadcrumb-item>绑定手机</el-breadcrumb-item>
        </el-breadcrumb>
        <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="155px" class="demo-ruleForm">
            <el-form-item label="手机号码：" prop="phone">
                <el-input v-model="ruleForm2.phone" type="number" placeholder="请输入绑定的手机号码" style="width:367px;"></el-input>
            </el-form-item>
            <el-form-item label="短信验证：" prop="code">
                <el-input v-model="ruleForm2.code" type="number" placeholder="请输入短信验证" style="width:250px;"></el-input>
                <el-button type="primary" @click="getVerificationCode" :loading="loading2">{{getCodeText}}</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitRuleForm2('ruleForm2')" :loading="loading22">确定</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
    data() {
        var validatorIsPhone = (rule, value, callback) => {
            if (value == '') {
                callback(new Error('请输入手机号码'))
            } else {
                if (this.isPhone(value)) {
                    callback();
                } else {
                    callback(new Error('请输入正确手机号码'))
                }
            }
        }
        return {
            //重置手机
            ruleForm2: {
                phone: '',
                code: ''
            },
            rules2: {
                phone: [{
                    required: true,
                    validator: validatorIsPhone,
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
    methods: {
        isPhone(obj) {
            var result = true;
            var isPhone = /^((\+?86)|(\(\+86\)))?(13[0123456789][0-9]{8}|15[0123456789][0-9]{8}|17[0123456789][0-9]{8}|18[0123456789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
            if (!isPhone.test(obj)) {
                result = false;
            }
            return result;
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
                            wmemberId: this.$route.params.memberId,
                            verificationCodeType: 9,
                        },
                        success: (res) => {
                            console.log(res, '获取短信验证码')
                            if (res.code == 0) {
                                time()
                            } else {
                                this.$message.error(res.msg)
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
                        url: this.DFPAYDOMAIN + '/walletMember/bindingPhone',
                        type: 'POST',
                        dataType: 'JSON',
                        data: {
                            phone: this.ruleForm2.phone,
                            code: this.ruleForm2.code,
                            wmemberId: this.$route.params.memberId,
                        },
                        success: (res) => {
                            console.log(res, '确认重置手机')
                            if (res.code == 0) {
                                this.$message({
                                    message: res.msg,
                                    type: 'success',
                                    duration: 1500,
                                    onClose: () => {
                                        this.$router.replace({
                                            path: '/wallet/individual/index'
                                        })
                                    }
                                });
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


