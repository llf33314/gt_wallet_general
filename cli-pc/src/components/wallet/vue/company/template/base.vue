<template>
  <div>
    <div class="public-content">
      <div class="public-table-title">
        企业信息
        <span class="public-c999 public-f13">按照营业执照上的内容逐字填写</span>
      </div>
      <el-form :model="ruleForm1" :rules="rules1" ref="ruleForm1" label-width="150px" class="demo-ruleForm">
        <el-form-item label="企业名称：" prop="companyName">
          <el-input v-model="ruleForm1.companyName" placeholder="请输入企业名称" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="营业执照号：" prop="businessLicense">
          <el-input v-model="ruleForm1.businessLicense" placeholder="请输入营业执照号" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="企业地址：">
          <el-form-item prop="province" style="display:inline-block;">
            <el-select v-model="ruleForm1.province" placeholder="请选择" @change="getareas">
              <el-option v-for="item in provinceOptins" :key="item.id" :label="item.city_name" :value="item.id+''">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="area" style="display:inline-block;">
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
      <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="150px" class="demo-ruleForm">
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
      <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3" label-width="150px" class="demo-ruleForm">
        <el-form-item label="企业对公账户：" prop="accountNo">
          <el-input v-model="ruleForm3.accountNo" @blur="getBankCardBin" placeholder="请输入企业对公账户" class="input-width"></el-input>
        </el-form-item>
        <el-form-item label="支付行号：" prop="unionBank" v-if="isUnionBankFlag">
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
    </div>
    <div class="public-bottom-btns">
      <el-button type="primary" @click="avtive1">下一步</el-button>
    </div>
  </div>
</template>
<script>
  export default {
    data() {
      return {
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
        ruleForm2: {
          legalName: '',
          legalIds: '',
          legalPhone: '15902042654',
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
          }],
          legalPhone: [{
            required: true,
            message: '请输入法人手机号码',
            trigger: 'blur'
          }],
        },
        ruleForm3: {
          accountNo: '6217212008009165086',
          unionBank: '',
        },
        rules3: {
          accountNo: [{
            required: true,
            message: '请输入对公账号',
            trigger: 'blur'
          }],
          unionBank: [{
            required: true,
            message: '请输入支付行号',
            trigger: 'blur'
          }],
        },
        activeFlag: {},
        isUnionBankFlag: false
      }
    },
    mounted() {
      this.getProvince()
    },
    methods: {
      //判断是否要填支付行号
      isUnionBank(name) {
        console.log(name, 'name')
        const bankList = [
          '工商银行', '中国银行', '农业银行', '建设银行', '中信银行', '广大银行', '华夏银行', '平安银行', '招商银行', '兴业银行',
          '浦发银行', '邮储银行', '宁波银行', '南京银行', '农信湖南'
        ]
        bankList.forEach((item) => {
          if (name == item) {
            this.isUnionBankFlag = false
          } else {
            this.isUnionBankFlag = true
          }
        })
      },
      //查询银行卡bin信息
      getBankCardBin() {
        $.ajax({
          url: this.DFPAYDOMAIN + '/getBankCardBin',
          type: 'POST',
          dataType: 'json',
          data: {
            bankCardNo: this.ruleForm3.accountNo
          },
          success: (res) => {
            console.log(res, '****')
            if (res.code != 0) {
              this.$message.error(res.msg);
            } else {
              if (res.data.cardState == 1) {
                this.isUnionBank(res.data.bankName)
              } else {
                this.$message.error('无效帐号');
              }
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
      avtive1() {
        this.submitForm("ruleForm1", 'type1');
        this.submitForm("ruleForm2", 'type2');
        this.submitForm("ruleForm3", 'type3');
        if (this.activeFlag.type1 && this.activeFlag.type2 && this.activeFlag.type3) {
          console.log(this.ruleForm1, 'this.ruleForm1')
          this.provinceOptins.forEach((item) => {
            if (this.ruleForm1.province == item.id) {
              console.log(item.city_code, 'provinceOptins')
            }
          })
          this.areaOptins.forEach((item) => {
            if (this.ruleForm1.area == item.id) {
              console.log(item.city_code, 'areaOptins')
            }
          })
          this.$emit("baseObj",this.ruleForm2);
          console.log(this.ruleForm2, 'this.ruleForm2')
          console.log(this.ruleForm3, 'this.ruleForm3')
        }
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
    }
  }

</script>
