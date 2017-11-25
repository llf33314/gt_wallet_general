<style lang="less">
  .wallet-index {
    .no-open {
      padding-top: 70px;
      text-align: center;
      p {
        font-size: 14px;
        color: #333333;
        padding: 27px 0 70px;
      }
      i {
        color: #f7ba2a;
        font-size: 35px;
      }
    }
    .top-msg {
      font-size: 14px;
      border: 1px solid #eeeeee;
      margin-top: 30px;
      overflow: hidden;
      .gray {
        background-color: #f8f8f8;
        padding: 27px 30px;
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
      .bts {
        position: absolute;
        top: 30px;
        right: 25px;
      }
      .iconfont {
        color: #d2d2d2;
        cursor: pointer;
        position: relative;
      }
      .item-tooltip {
        color: #999999;
        line-height: 30px;
      }
    }
    .table-top-conent {
      background-color: #f8f8f8;
      padding: 15px 30px;
      overflow: hidden;
      font-size: 14px;
      .title {
        padding-right: 25px;
      }
      .row {
        padding-bottom: 20px;
      }
      .select-title {
        margin-right: 30px;
        cursor: pointer;
      }
    }
  }

  .wallet-index-select-apply-dialog {
    width: 640px;
    .check-content {
      overflow: hidden;
      padding-left: 30px;
    }
    .check-list-item {
      margin-bottom: 30px;
      margin-left: 10px;
      width: 100px;
      float: left;
    }
    .tips {
      font-size: 13px;
      margin-bottom: 30px;
    }
    .el-dialog__body {
      padding-top: 10px;
    }
  }

</style>
<template>
  <section class="wallet-index">
    <div class="no-open">
      <i class="el-icon-warning"></i>
      <p>您尚未开通多粉钱包，点击下方按钮开通吧！</p>
      <div class="bts">
          <el-button @click="routerTo('/wallet/companyOpen')" type="primary">企业开通</el-button>
          <el-button @click="routerTo('/wallet/personalOpen')"  type="primary">个人开通</el-button>
      </div>
    </div>
    <div class="public-content">
      <div class="top-msg">
        <div class="gray">
          <div class="public-fl">
            <div class="row1 public-c333">
              <span class="title">姓名：</span>
              <span>撒个</span>
              <span class="title">认证类型：</span>
              <span>个人认证</span>
            </div>
            <div class="row2 public-c666">
              <span>请选择使用多粉钱包的应用</span>
              <el-button @click="dialogFormVisible=true" class="public-cb" size="small" style="margin-left:27px;">选择</el-button>
            </div>
          </div>
          <div class="public-fr">
            <el-button @click="dialogEditPassword=true" type="primary">修改密码</el-button>
          </div>
        </div>
        <div class="data-msg">
          <ul class="public-fl public-c333 list">
            <li>
              <p class="name">资产总额
                <el-tooltip effect="light" content="资产总额=待结算+可用提现余额" placement="right">
                  <i class="iconfont gt-bangzhudisc"></i>
                </el-tooltip>
              </p>
              <p>
                <span class="num">0</span>元</p>
            </li>
            <li>
              <p class="name">待结算(未入账)</p>
              <p>
                <span class="num">0</span>元</p>
            </li>
            <li>
              <p class="name">可用余额</p>
              <p>
                <span class="num">0</span>元</p>
            </li>
          </ul>
          <div class="bts">
            <el-button @click="goToDrawCash" type="primary">提现</el-button>
          </div>
        </div>
      </div>
      <div class="public-table-title public-c666">近期交易记录</div>
      <!-- <gt-null-data class="public-top20">您近期没有交易记录</gt-null-data> -->
      <div class="table-top-conent">
        <div class="row">
          <span class="title public-c333">交易类型：</span>
          <span @click="tType=0" class="select-title" :style="tType==0?'color:#20a0ff;':''">消费</span>
          <span @click="tType=1" class="select-title" :style="tType==1?'color:#20a0ff;':''">提现</span>
        </div>
        <div>
          <span class="title public-c333">时间范围：</span>
          <el-date-picker v-model="value4" type="datetimerange" :picker-options="pickerOptions2" placeholder="选择时间范围" align="right">
          </el-date-picker>
          <div v-show="tType==0" style="display:inline-block;">
            <span class="title public-c333" style="padding-left:30px;">来源：</span>
            <el-select v-model="value" placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </div>
        </div>
        <div v-show="tType==0" class="public-c666" style="line-height:1.8;margin-top:15px;">
          <p>1.金额：是订单的交易金额（为扣除交易手续费的金额）</p>
          <p>2.交易手续费用：是承担微信、支付宝、银行机构等“支付通道商”收取的0.6%交易手续费。</p>
          <p>3.到账金额：是实际到多粉钱包的金额（到账金额=金额-交易手续费用）</p>
        </div>
      </div>
      <el-table ref="multipleTable" :data="tableData3" class="public-top20" align="center" border tooltip-effect="dark" style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column label="创建时间" show-overflow-tooltip align="center">
          <template slot-scope="scope">{{ scope.row.date }}</template>
        </el-table-column>
        <el-table-column prop="name" label="订单号" show-overflow-tooltip align="center">
        </el-table-column>
        <el-table-column prop="name" label="金额(元)" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <span class="public-cgreen">+300.00</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="交易手续费用" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <span class="public-cred">+300.00</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="到账金额" show-overflow-tooltip align="center">
        </el-table-column>
        <el-table-column prop="name" label="来源" show-overflow-tooltip align="center">
        </el-table-column>
      </el-table>
      <div class="public-top20">
        <div class="public-fr">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="currentPage3" :page-size="100"
            layout="prev, pager, next, jumper" :total="1000">
          </el-pagination>
        </div>
      </div>
    </div>
    <el-dialog title="修改密码" :visible.sync="dialogEditPassword" custom-class="wallet-index-select-apply-dialog">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="155px" class="demo-ruleForm">
        <el-form-item label="安全手机：">
          158450***46
        </el-form-item>
        <el-form-item label="验证码：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入6位验证码" style="width:250px;"></el-input>
          <el-button type="primary">获取验证码</el-button>
        </el-form-item>
        <el-form-item label="设置新钱包密码：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入您的新交易密码" style="width:250px;"></el-input>
        </el-form-item>
        <el-form-item label="确认新钱包密码：" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请再次输入您的新交易密码" style="width:250px;"></el-input>
        </el-form-item>
        <el-form-item style="text-align: right;">
          <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
          <el-button @click="resetForm('ruleForm')">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="选择应用" :visible.sync="dialogFormVisible" custom-class="wallet-index-select-apply-dialog">
      <div class="public-c999 tips">
        点击 选择后，系统将该行业应用交易金额转入多粉钱包中
        <br> 默认全选应用，如需取消勾选
      </div>
      <div class="check-content">
        <el-checkbox-group v-model="checkList">
          <div class="check-list-item">
            <el-checkbox label="复"></el-checkbox>
          </div>
          <div class="check-list-item">
            <el-checkbox label="复"></el-checkbox>
          </div>
          <div class="check-list-item">
            <el-checkbox label="复"></el-checkbox>
          </div>
          <div class="check-list-item">
            <el-checkbox label="复"></el-checkbox>
          </div>
          <div class="check-list-item">
            <el-checkbox label="复"></el-checkbox>
          </div>
          <div class="check-list-item">
            <el-checkbox label="复"></el-checkbox>
          </div>
          <div class="check-list-item">
            <el-checkbox label="复"></el-checkbox>
          </div>
        </el-checkbox-group>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogFormVisible = false">保存</el-button>
      </div>
    </el-dialog>
  </section>
</template>
<script>
  export default {
    data() {
      return {
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
        dialogEditPassword: false,
        tType: 0,
        dialogFormVisible: false,
        checkList: ['选中且禁用', '复选框 A'],
        tableData3: [{
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }],
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
        multipleSelection: [],
        pickerOptions2: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        value3: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
        value4: ''
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
      //选择应用
      selectApply() {

      },
      //提现
      goToDrawCash(){
        this.$router.push({
          path: '/wallet/drawcash'
        })
      },
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      routerTo(path) {
        console.log(path)
        this.$router.push({
          path: path
        })
      },
      
    }
  }

</script>
