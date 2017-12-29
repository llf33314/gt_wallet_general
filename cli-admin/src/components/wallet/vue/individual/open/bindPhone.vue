
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
        <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="150px" style="width:600px;">
            <el-form-item label="手机号码：" prop="legalName">
                <el-input v-model="ruleForm2.legalName" placeholder="请输入绑定的手机号码" class="input-width"></el-input>
            </el-form-item>
            <el-form-item label="验证码：" prop="legalIds">
                <el-input v-model="ruleForm2.legalIds" placeholder="请输入手机验证码" class="input-width" style="width: 330px;"></el-input>
                <el-button type="primary" style="float:right">获取验证码</el-button>
            </el-form-item>
            <el-form-item>
               <el-button type="primary">保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            ruleForm2: {
                verificationCode: '',
                id: ''
            },
            rules2: {
                verificationCode: [{
                    required: true,
                    message: '请输入手机验证码',
                    trigger: 'blur'
                }],
            },
        }
    }
}
</script>


