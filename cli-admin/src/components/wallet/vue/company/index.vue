<style lang="less">
  .wallet-individual-index {
    .title-dps {
      display: inline-block;
      width: 200px;
    }
    .ellipsis {
      margin-right: 20px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }

</style>
<template>
  <section class="wallet-index wallet-individual-index">
    <div class="public-content">
      <div class="top-msg">
        <div class="gray">
          <div class="public-fl">
            <div class="row1 public-c333">
              <span class="title" style="width:70px;text-align:right">企业名称：</span>
              <span class="title-dps ellipsis" v-text="walletCompany.companyName" :title="walletCompany.companyName" style="vertical-align: top;"></span>
              <span class="title">认证类型：</span>
              <span>企业认证</span>
              <span class="title" style="margin-left: 110px;">安全手机号码：</span>
              <span v-text="WalletBank.phone">1564004646</span>
            </div>
            <!-- <div class="row1 public-c333">
              <span class="title" style="width:70px;text-align:right">银行卡：</span>
              <span class="title-dps" v-text="WalletBank.cardNo">1264 4568 7894 465</span>
              <span class="title">银行卡类型：</span>
              <span v-if="WalletBank.cardClass==1">个人账户</span>
              <span v-if="WalletBank.cardClass==2">对公账号</span>
            </div> -->
          </div>
          <div class="public-fr">
            <!-- <set-pwd></set-pwd> -->
            <el-button @click="goToMsg" size="small" type="primary">企业信息</el-button>
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
                <span class="num" v-text="IndexStatistics.total">0</span>元</p>
            </li>
            <li>
              <p class="name">待结算(未入账)</p>
              <p>
                <span class="num" v-text="IndexStatistics.waitBalance">0</span>元</p>
            </li>
            <li>
              <p class="name">可用余额</p>
              <p>
                <span class="num" v-text="IndexStatistics.balance">0</span>元</p>
            </li>
          </ul>
          <div class="bts">
            <el-button @click="goToDrawCash" size="small" type="primary">提现</el-button>
            <!-- <el-button @click="addBank" type="primary">新增个人银行卡</el-button> -->
          </div>
        </div>
      </div>
      <div class="public-table-title public-c666" style="margin-top: 30px;">近期交易记录</div>
      <!-- <gt-null-data class="public-top20">您近期没有交易记录</gt-null-data> -->
      <div class="table-top-conent">
        <div class="row">
          <span class="title public-c333">交易类型：</span>
          <span @click="tType=0" class="select-title" :style="tType==0?'color:#20a0ff;':''">消费</span>
          <span @click="tType=1" class="select-title" :style="tType==1?'color:#20a0ff;':''">提现</span>
        </div>
        <div>
          <span class="title public-c333">时间范围：</span>
          <el-date-picker v-if="tType==0" :clearable="false" format="yyyy-MM-dd" v-model="value4" @change="selectDraw" type="datetimerange" :picker-options="pickerOptions2"
            placeholder="选择时间范围" align="right" style="width:250px;">
          </el-date-picker>
          <el-date-picker v-if="tType==1" :clearable="false" format="yyyy-MM-dd" v-model="value4" @change="selectDraw" type="datetimerange" :picker-options="pickerOptions2"
            placeholder="选择时间范围" align="right" style="width:250px;">
          </el-date-picker>
        </div>
      </div>
      <div v-if="tType==0">
        <el-table ref="multipleTable" :data="tableData3" class="public-top20" @selection-change="handleSelectionChange">
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
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" current-page.sync="100" :page-size="100"
              layout="prev, pager, next, jumper" :total="1000">
            </el-pagination>
          </div>
        </div>
      </div>
      <div v-if="tType==1">
        <el-table ref="multipleTable" :data="getDrawCashList.records" class="public-top20" @selection-change="handleSelectionChange">
          <el-table-column label="申请时间" show-overflow-tooltip align="center">
            <template slot-scope="scope">{{ scope.row.ctime}}</template>
          </el-table-column>
          <el-table-column prop="externalOrderNo" label="流水号" show-overflow-tooltip align="center">
          </el-table-column>
          <el-table-column prop="name" label="银行账户" show-overflow-tooltip align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.ctime}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="amount" label="提现金额(元)" show-overflow-tooltip align="center">
            <template slot-scope="scope">
              <span style="color:red">-{{ scope.row.amount}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="fee" label="手续费（元）" show-overflow-tooltip align="center">
          </el-table-column>
          <el-table-column prop="name" label="到账金额（元）" show-overflow-tooltip align="center">
            <template slot-scope="scope">
              <span>{{scope.row.amount - scope.row.fee}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="status " label="状态" show-overflow-tooltip align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.status == 'success'">成功</span>
              <span v-if="scope.row.status == 'pending'">进行中</span>
              <span v-if="scope.row.status == 'fail'">失败</span>
            </template>
          </el-table-column>
        </el-table>
        <div class="public-top20">
          <div class="public-fr">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange2" current-page.sync="getDrawCashList.pages"
              :page-size="10" layout="prev, pager, next, jumper" :total="getDrawCashList.total">
            </el-pagination>
          </div>
        </div>
      </div>
    </div>
    <!-- <draw-cash></draw-cash> -->
  </section>
</template>
<script>
  import DrawCash from "./../template/drawcash.vue";
  import SetPwd from "./../template/setPwd.vue";
  import {
    wallet
  } from '../../api/index'
  export default {
    components: {
      DrawCash,
      SetPwd
    },
    data() {
      return {
        IndexStatistics: {},
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
        value4: '',
        data: {},
        //会员信息
        walletCompany: {
          "accountNo": "string",
          "area": "string",
          "bankCtiyNo": "string",
          "bankName": "string",
          "businessLicense": "string",
          "companyAddress": "string",
          "companyName": "string",
          "country": "string",
          "doBusinessUrl": "string",
          "id": 0,
          "identityType": 0,
          "identitycardUrl1": "string",
          "identitycardUrl2": "string",
          "legalIds": "string",
          "legalName": "string",
          "legalPhone": "string",
          "licenseUrl": "string",
          "memberNum": "string",
          "organizationCode": "string",
          "parentBankName": "string",
          "province": "string",
          "telephone": "string",
          "unionBank": "string",
          "wmemberId": 0
        },
        // 银行信息
        WalletBank: {
          "bankCode": "string",
          "bankName": "string",
          "cardCheck": 0,
          "cardClass": 2,
          "cardLenth": 0,
          "cardNo": "string",
          "cardState": 0,
          "cardType": 0,
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
        },
        // 提现分页查询
        getDrawCashList: {
          "asc": true,
          "condition": {},
          "current": 0,
          "limit": 0,
          "offset": 0,
          "offsetCurrent": 0,
          "openSort": true,
          "orderByField": "string",
          "pages": 1,
          "records": [{
              "amount": '订单金额 ',
              "busId": 0,
              "ctime": "订单生成时间 ",
              "externalOrderNo": "云账号订单",
              "fee": '手续费 ',
              "id": 0,
              "status": "success",
              "sysOrderNo": "系统订单号",
              "wmemberId": 0
            },
            {
              "amount": 1000,
              "busId": 0,
              "ctime": "2017-12-12T02:15:59.112Z",
              "externalOrderNo": "string",
              "fee": 10,
              "id": 0,
              "status": "pending",
              "sysOrderNo": "string",
              "wmemberId": 0
            }
          ],
          "searchCount": true,
          "size": 0,
          "total": 13,
        },
        //提现分页查询
        drawListParams: {
          wmemberId: '',
          startTime: '',
          endTime: '',
          current: 1,
          total: '',
          size: 10
        }
      }
    },
    mounted() {
      this.findMember()

      this.getIndexStatistics()
    },
    methods: {
      //提现 分页查询
      handleCurrentChange2(val) {
        console.log(val, '提现 分页查询');
        this.drawListParams.current = val
        this.getDrawPage()
      },
      // 提现 选择时间范围
      selectDraw(e) {
        console.log(e, '提现 选择时间范围')
        this.drawListParams.current = 1
        if (e) {
          this.drawListParams.startTime = this.DateFormat(Date.parse(e[0]), 'yyyy-MM-dd')
          this.drawListParams.endTime = this.DateFormat(Date.parse(e[1]), 'yyyy-MM-dd')
        } else {
          this.drawListParams.startTime = ''
          this.drawListParams.endTime = ''
        }
        this.getDrawPage()
      },
      // 提现 分页查询
      getDrawPage() {
        console.log(this.drawListParams, 'this.drawListParams')
        $.ajax({
          url: this.DFPAYDOMAIN + '/walletMoney/getPage',
          type: 'post',
          dataType: 'json',
          data: this.drawListParams,
          success: (res) => {
            console.log(res, '提现 分页查询')
            if (res.code == 0) {

            } else {
              this.$message.error(res.msg)
            }
          }
        })
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
              //this.walletCompany = res.data.walletCompany
              //this.WalletBank = res.data.WalletBank
              this.drawListParams.wmemberId = res.data.id
            } else {
              this.$message.error(res.msg)
            }
          }
        })
      },
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
      handleCurrentChange(val) {
        console.log(val, '提现 分页查询');
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
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },

      addBank() {
        this.$router.push({
          path: '/wallet/individual/addBank/' + this.data.memberId
        })
      },
      //企业信息
      goToMsg() {
        this.$router.push({
          path: '/wallet/company/messages'
        })
      },
      //提现
      goToDrawCash() {
        this.$router.push({
          path: '/wallet/company/drawcash'
        })
      },
      //获取首页总计数据
      getIndexStatistics() {
        $.ajax({
          url: this.DFPAYDOMAIN + '/walletIndexStatistics/getIndexStatistics',
          type: 'get',
          success: (res) => {
            console.log(res, "获取首页总计数据")
            if (res.code == 0) {
              this.IndexStatistics = res.data;
            } else {
              this.$message.error(res.msg);
            }
          }
        })
      }
    }
  }

</script>
