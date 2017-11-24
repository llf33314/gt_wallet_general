<style lang="stylus">
  .wallet-company-open {
    .public-f13 {
      font-weight: 500;
      padding-left: 15px;
    }
    .public-table-title {
      margin-top: 30px;
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
    .active-last{
      text-align: center;
      color: #666666;
      font-size:14px;
      padding-top:60px;
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
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="200px" class="demo-ruleForm">
          <el-form-item label="企业名称：" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入企业名称" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="注册号：" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入注册号" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="单位所在地：" prop="name">
            <el-select v-model="value" placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
            <el-select v-model="value" placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="住所：" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入住所" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="经营范围：" prop="name">
            <el-input v-model="ruleForm.name" type="textarea" placeholder="请输入营业范围" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="营业期限：" prop="name">
            <el-date-picker v-model="value3" type="datetimerange" format="yyyy-MM-dd" class="input-width" placeholder="请选择日期范围">
            </el-date-picker>
            <el-checkbox v-model="checked" style="margin-left:30px;">长期</el-checkbox>
          </el-form-item>
          <el-form-item label="注册资本：" prop="name">
            <el-input v-model="ruleForm.name" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="组织结构代码：" prop="name">
            <el-input v-model="ruleForm.name" class="input-width"></el-input>
          </el-form-item>
        </el-form>
        <div class="public-table-title">
          法定代表人信息
        </div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="200px" class="demo-ruleForm">
          <el-form-item label="法定代表人归属地：" prop="name">
            <el-select v-model="value" placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="法定代表人姓名：" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入法定代表人姓名" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="身份证号：" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入身份证号" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="证件有效期：" prop="name">
            <el-date-picker v-model="value3" type="datetimerange" format="yyyy-MM-dd" class="input-width" placeholder="请选择日期范围">
            </el-date-picker>
            <el-checkbox v-model="checked" style="margin-left:30px;">长期</el-checkbox>
          </el-form-item>
        </el-form>
        <div class="public-table-title">
          实际控制人
        </div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="200px" class="demo-ruleForm">
          <el-form-item label="类型：" prop="name">
            <el-radio-group v-model="radio2">
              <el-radio :label="3">个人</el-radio>
              <el-radio :label="6">企业</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="实际控制人归属地：" prop="name">
            <el-select v-model="value" placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="实际控制人姓名：" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入法定代表人姓名" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="身份证号：" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入身份证号" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="证件有效期：" prop="name">
            <el-date-picker v-model="value3" type="datetimerange" format="yyyy-MM-dd" class="input-width" placeholder="请选择日期范围">
            </el-date-picker>
            <el-checkbox v-model="checked" style="margin-left:30px;">长期</el-checkbox>
          </el-form-item>
        </el-form>
        <div class="public-table-title">
          添加银行卡
        </div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="200px" class="demo-ruleForm">
          <el-form-item label="企业账户：" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入企业账户" class="input-width"></el-input>
            <span class="public-c999" style="font-size:13px;">此为多粉钱包安全卡，绑定后不能直接修改。如需修改请联系：***-***</span>
          </el-form-item>
          <el-form-item label="企业名称：" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入法定代表人姓名" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="银行卡预留手机号：" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入银行卡预留手机号" class="input-width"></el-input>
          </el-form-item>
          <el-form-item label="短信验证：" prop="name">
            <el-input v-model="ruleForm.name" placeholder="请输入验证码" class="input-width"></el-input>
            <el-button type="primary">获取验证码</el-button>
          </el-form-item>
          <el-form-item label="个人银行卡：" prop="name">
            <el-switch v-model="value1" on-text="" off-text="">
            </el-switch>
          </el-form-item>
          <el-form-item label="" prop="name">
            <div style="border:1px solid #dfe6ec;padding:20px 0;border-radius:5px;">
              <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="200px" class="demo-ruleForm">
                <el-form-item label="个人账户：" prop="name" style="margin-bottom: 22px;">
                  <el-input v-model="ruleForm.name" placeholder="请输入个人账户" class="input-width"></el-input>
                </el-form-item>
                <el-form-item label="开户人姓名：" prop="name" style="margin-bottom: 22px;">
                  <el-input v-model="ruleForm.name" placeholder="请输入个人账户" class="input-width"></el-input>
                </el-form-item>
                <el-form-item label="银行卡预留手机号：" prop="name" style="margin-bottom: 22px;">
                  <el-input v-model="ruleForm.name" placeholder="银行卡预留手机号：" class="input-width"></el-input>
                </el-form-item>
                <el-form-item label="短信验证：" prop="name" style="margin-bottom:10px;">
                  <el-input v-model="ruleForm.name" placeholder="请输入验证码" class="input-width"></el-input>
                  <el-button type="primary">获取验证码</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-form-item>
        </el-form>
        <div class="public-bottom-btns">
          <el-button type="primary">下一步</el-button>
        </div>
      </div>
      <div v-show="active==2">
        <div class="public-table-title">
          企业法人营业执照及组织机构代码证
        </div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="200px" class="demo-ruleForm">
          <el-form-item label="上传单位证件照片：" prop="name">
            <span class="public-cred">*</span>企业法人营业执照
          </el-form-item>
          <el-form-item label="" prop="name">
            <gt-material class="public-fl" prop="url" :url="url" v-on:getChangeUrl="getChangeUrl" width="220" height="140"></gt-material>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../img/z1.jpg" alt="">
            </div>
          </el-form-item>
          <el-form-item label="" prop="name">
            组织机构代码证
          </el-form-item>
          <el-form-item label="" prop="name">
            <gt-material class="public-fl" prop="url" :url="url" v-on:getChangeUrl="getChangeUrl" width="220" height="140"></gt-material>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../img/z2.jpg" alt="">
            </div>
          </el-form-item>
        </el-form>
        <div class="public-table-title">
          法定代表人钱多多的证件照片
        </div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="200px" class="demo-ruleForm">
          <el-form-item label="身份证类型：" prop="name">
            <el-radio-group v-model="radio2">
              <el-radio :label="3">二代身份证</el-radio>
              <el-radio :label="6">临时身份证</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="上传法人身份证照片：" prop="name">
            <span class="public-cred">*</span>个人信息页
          </el-form-item>
          <el-form-item label="" prop="name">
            <gt-material class="public-fl" prop="url" :url="url" v-on:getChangeUrl="getChangeUrl" width="220" height="140"></gt-material>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../img/c1.jpg" alt="">
            </div>
          </el-form-item>
          <el-form-item label="" prop="name">
            国徽页
          </el-form-item>
          <el-form-item label="" prop="name">
            <gt-material class="public-fl" prop="url" :url="url" v-on:getChangeUrl="getChangeUrl" width="220" height="140"></gt-material>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../img/c2.jpg" alt="">
            </div>
          </el-form-item>
          <el-form-item label="证件照片：" prop="name">
            <span class="public-cred">*</span>手持身份证
          </el-form-item>
          <el-form-item label="" prop="name">
            <gt-material class="public-fl" prop="url" :url="url" v-on:getChangeUrl="getChangeUrl" width="220" height="140"></gt-material>
            <div class="dome-img public-c333">
              <span>示例：</span>
              <img src="./../img/r1.jpg" alt="">
            </div>
          </el-form-item>
        </el-form>
        <div class="public-bottom-btns">
          <el-button>上一步</el-button>
          <el-button type="primary">下一步</el-button>
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
  export default {
    data() {
      return {
        active: 3,
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
    }
  }

</script>
