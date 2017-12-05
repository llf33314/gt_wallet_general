<style lang="less">
  .wallet-company-open {
    .public-f13 {
      font-weight: 500;
      padding-left: 15px;
    }
    .public-table-title {
      margin-top: 50px;
      margin-bottom: 30px;
    }
    .input-width {
      width: 220px;
    }
    .public-content {
      padding-bottom: 150px;
    }
    .dome-img {
      padding-left: 50px;
      float: left;
      span {
        float: left;
      }
      img {
        vertical-align: text-top;
        height: 140px;
        margin-left: 20px;
        float: left;
      }
    }
    .el-icon-circle-check {
      font-size: 40px;
      color: #34d063;
      margin-bottom: 20px;
    }
    .active-last {
      text-align: center;
      color: #666666;
      font-size: 14px;
      padding-top: 60px;
    }
    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      float: left;
      width: 220px;
      height: 137px;
      background-color: #fbfdff;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #20a0ff;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 220px;
      height: 137px;
      line-height: 137px;
      text-align: center;
    }
    .avatar {
      width: 220px;
      height: 137px;
      display: block;
    }
  }

</style>
<template>
  <section class="wallet-company-open">
    <el-breadcrumb separator="/" class="public-crumbs">
      <el-breadcrumb-item :to="{ path: '/wallet/index' }">多粉钱包</el-breadcrumb-item>
      <el-breadcrumb-item>企业开通</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="public-content">
      <el-steps :active="active" center="true" style="border-bottom: 1px solid #ddd;padding:20px 0;">
        <el-step title="填写基本信息"></el-step>
        <el-step title="上传证件照片"></el-step>
        <el-step title="提交成功"></el-step>
      </el-steps>
      <div v-show="active==1">
        <div class="public-table-title">
          企业信息
          <span class="public-c999 public-f13">按照营业执照上的内容逐字填写</span>
        </div>
        <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="200px" class="demo-ruleForm">
          <el-form-item label="企业名称：" prop="companyName">
            <el-input v-model="ruleForm1.companyName" placeholder="请输入企业名称" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="营业执照号：" prop="businessLicense">
            <el-input v-model="ruleForm1.businessLicense" placeholder="请输入营业执照号" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="企业地址：" prop="companyAddress">
            <el-select v-model="value" placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
            <el-select v-model="value" placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="companyAddress">
            <el-input v-model="ruleForm1.companyAddress" placeholder="请输入详细地址" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="联系电话：" prop="telephone">
            <el-input v-model="ruleForm1.telephone" placeholder="请输入联系电话" class="input-width"></el-input>
          </el-form-item>
        </el-form>
        <div class="public-table-title">
          法定代表人信息
        </div>
        <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="200px" class="demo-ruleForm">
          <el-form-item label="法定代表人姓名：" prop="legalName">
            <el-input v-model="ruleForm2.legalName" placeholder="请输入法定代表人姓名" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="法人证件号码：" prop="legalIds">
            <el-input v-model="ruleForm2.legalIds" placeholder="请输入法人证件号码" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="法人手机号码：" prop="legalPhone">
            <el-input v-model="ruleForm2.legalPhone" placeholder="请输入法人手机号码" class="input-width"></el-input>
          </el-form-item>
        </el-form>
        <div class="public-table-title">
          银行卡信息
        </div>
        <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="200px" class="demo-ruleForm">
          <el-form-item label="开户银行名称：" prop="parentBankName">
            <el-input v-model="ruleForm3.parentBankName" placeholder="请输入开户银行名称" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="开户支行行名称：" prop="bankName">
            <el-input v-model="ruleForm3.bankName" placeholder="请输入开户支行行名称" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="支付行号：" prop="unionBank">
            <el-input v-model="ruleForm3.unionBank" placeholder="请输入支付行号" class="input-width"></el-input>
            <el-tooltip placement="right" effect="light">
              <div slot="content">如非以下银行，则需要填写支付行号。
                <br/> （工商银行、农业银行、中国银行、建设银行、
                <br/> 中信银行、广大银行、华夏银行、平安银行、
                <br/> 招商银行、兴业银行、浦发银行、邮储银行、
                <br/> 宁波银行、南京银行、农信湖南）
              </div>
              <i class="el-icon-question" style="position:relative;color:#666666"></i>
            </el-tooltip>
          </el-form-item>
        </el-form>
        <div class="public-bottom-btns">
          <el-button type="primary" @click="avtive1">下一步</el-button>
        </div>
      </div>
      <div v-show="active==2">
        <div class="public-table-title">
          企业法人营业执照
        </div> 
        <el-form :model="ruleForm4" :rules="rules4" ref="ruleForm4" label-width="170px" class="demo-ruleForm">
          <el-form-item label="营业执照：" prop="doBusinessUrl">
            <el-upload class="avatar-uploader" ref="identitycardUrl1" action="" style="float:left" :multiple="true" :show-file-list="false"
              :http-request="uploadImgDoBusinessUrl" :before-upload="beforeAvatarUpload">
              <img v-if="ruleForm4.doBusinessUrl!=''" :src="ruleForm4.doBusinessUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../../img/c1.jpg" alt="">
            </div>
          </el-form-item>
          <el-form-item label="开户许可证：" prop="licenseUrl">
            <el-upload class="avatar-uploader" ref="licenseUrl" action="" style="float:left" :multiple="true" :show-file-list="false"
              :http-request="uploadImgLicenseUrl" :before-upload="beforeAvatarUpload">
              <img v-if="ruleForm4.licenseUrl!=''" :src="ruleForm4.licenseUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../../img/c1.jpg" alt="">
            </div>
          </el-form-item>
          <el-form-item label="身份证正面：" prop="identitycardUrl1">
            <el-upload class="avatar-uploader" ref="identitycardUrl1" action="" style="float:left" :multiple="true" :show-file-list="false"
              :http-request="uploadImgIdentitycardUrl1" :before-upload="beforeAvatarUpload">
              <img v-if="ruleForm4.identitycardUrl1!=''" :src="ruleForm4.identitycardUrl1" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../../img/c1.jpg" alt="">
            </div>
          </el-form-item>
          <el-form-item label="身份证背面：" prop="identitycardUrl2">
            <el-upload class="avatar-uploader" ref="identitycardUrl2" action="" style="float:left" :show-file-list="false" :http-request="uploadImgIdentitycardUrl2"
              :before-upload="beforeAvatarUpload">
              <img v-if="ruleForm4.identitycardUrl2!=''" :src="ruleForm4.identitycardUrl2" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../../img/c2.jpg" alt="">
            </div>
          </el-form-item>
        </el-form>
        <div class="public-bottom-btns">
          <el-button type="primary" @click="avtive2('ruleForm4')">下一步</el-button>
        </div>
      </div>
      <div v-show="active == 3">
        <div class="public-c666 active-last">
          <i class="el-icon-circle-check"></i>
          <p>恭喜您完成多粉钱包的注册</p>
          <p>身份信息与银行卡信息需要X个工作日进行审核验证，请您耐心等候。</p>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
  import {
    wallet
  } from './../../api/index'

  export default {
    data() {
      return {
        ruleForm1: {
          companyName: '',
          businessLicense: '',
          companyAddress: '',
          telephone: '',
        },
        rules1: {
          companyName: [{
              required: true,
              message: '请输入企业名称',
              trigger: 'blur'
            },
            {
              min: 3,
              max: 5,
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
              max: 5,
              message: '长度在 3 到 30 个字符',
              trigger: 'blur'
            }
          ],
          businessLicense: [{
              required: true,
              message: '请输入营业执照号',
              trigger: 'blur'
            },
            {
              min: 15,
              max: 15,
              message: '长度在15 个字符',
              trigger: 'blur'
            }
          ],
        },
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
              min: 3,
              max: 5,
              message: '长度在 2 到 20 个字符',
              trigger: 'blur'
            }
          ],
          legalIds: [{
            required: true,
            message: '请输入法人证件号码',
            trigger: 'blur'
          }],
          legalPhone: [{
              required: true,
              message: '请输入法人手机号码',
              trigger: 'blur'
            },
            {
              min: 11,
              max: 11,
              message: '长度在11个字符',
              trigger: 'blur'
            }
          ],
        },

        ruleForm3: {
          parentBankName: '',
          bankName: '',
          unionBank: '',
        },
        rules3: {
          parentBankName: [{
              required: true,
              message: '请输入开户银行名称',
              trigger: 'blur'
            },
            {
              min: 3,
              max: 5,
              message: '长度在 2 到 20 个字符',
              trigger: 'blur'
            }
          ],
          bankName: [{
            required: true,
            message: '请输入开户支行行名称',
            trigger: 'blur'
          }],
          unionBank: [{
              required: true,
              message: '请输入支付行号',
              trigger: 'blur'
            },
            {
              min: 11,
              max: 11,
              message: '长度在11个字符',
              trigger: 'blur'
            }
          ],
        },
        ruleForm4: {
          doBusinessUrl: '',
          licenseUrl: '',
          identitycardUrl1: '',
          identitycardUrl2: '',
        },
        rules4: {
          doBusinessUrl: [{
            required: true,
            message: '请上传营业执照',
            trigger: 'change'
          }],
          licenseUrl: [{
            required: true,
            message: '请上传开户许可证',
            trigger: 'change'
          }],
          identitycardUrl1: [{
            required: true,
            message: '请上传身份证正面',
            trigger: 'change'
          }],
          identitycardUrl2: [{
            required: true,
            message: '请上传身份证背面',
            trigger: 'change'
          }],
        },
        activeFlag: {},
        active: 2,
        // *************
        value1: true,
        radio2: 3,
        checked: true,
        value3: '',
        options: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
        value: '',
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
      avtive1() {
        // this.active = 2
        this.submitForm("ruleForm1", 'type1');
        this.submitForm("ruleForm2", 'type2');
        this.submitForm("ruleForm3", 'type3');

        if (this.activeFlag.type1 && this.activeFlag.type2 && this.activeFlag.type3) {
          this.active = 2
        }
        console.log(this.activeFlag, 'this.activeFlag')
      },
      avtive2(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(66)
          } else {
            return false
          }
        });
      },
      //确认开通
      submitForm(formName, flagType) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.activeFlag[flagType] = true
          } else {
            this.activeFlag[flagType] = false
          }
        });
      },
      uploadImgDoBusinessUrl(e) {
        wallet.upload(e.file).then(res => {
          this.ruleForm4.doBusinessUrl = res.data
        })
      },
      uploadImgLicenseUrl(e) {
        wallet.upload(e.file).then(res => {
          this.ruleForm4.licenseUrl = res.data
        })
      },
      uploadImgIdentitycardUrl1(e) {
        wallet.upload(e.file).then(res => {
          this.ruleForm4.identitycardUrl1 = res.data
        })
      },
      uploadImgIdentitycardUrl2(e) {
        wallet.upload(e.file).then(res => {
          this.ruleForm4.identitycardUrl2 = res.data
        })
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
        const isLt5M = file.size / 1024 / 1024 < 5;
        if (!isJPG) {
          this.$message.error('上传图片只能是 JPG/PNG 格式!');
        }
        if (!isLt5M) {
          this.$message.error('上传图片大小不能超过 5MB!');
        }
        return isJPG && isLt5M;
      },
    }
  }

</script>
