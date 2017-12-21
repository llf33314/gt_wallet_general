<style lang="less"></style>
<template>
  <div>
    <div class="table-top-conent">
      <div class="row">
        <span class="title public-c333">交易类型：</span>
        <span @click="tType=0" class="select-title" :style="tType==0?'color:#20a0ff;':''">消费</span>
        <span @click="tType=1" class="select-title" :style="tType==1?'color:#20a0ff;':''">提现</span>
      </div>
      <div>
        <span class="title public-c333">时间范围：</span>
        <el-date-picker v-show="tType==0" :clearable="false" format="yyyy-MM-dd" v-model="value" @change="selectDraw" type="datetimerange" :picker-options="pickerOptions2" placeholder="选择时间范围" align="right" style="width:250px;">
        </el-date-picker>
        <el-date-picker v-show="tType==1" :clearable="false" format="yyyy-MM-dd" v-model="value2" @change="selectDraw2" type="datetimerange" :picker-options="pickerOptions2" placeholder="选择时间范围" align="right" style="width:250px;">
        </el-date-picker>
      </div>
    </div>
    <div v-show="tType==0">
      <el-table ref="multipleTable" :data="payOrderList.records" class="public-top20">
        <el-table-column label="创建时间" show-overflow-tooltip align="center">
          <template slot-scope="scope">{{ scope.row.ctime }}</template>
        </el-table-column>
        <el-table-column prop="externalNo" label="订单号" show-overflow-tooltip align="center">
        </el-table-column>
        <el-table-column prop="amount" label="消费金额（元）" align="center">
          <template slot-scope="scope">
            <span style="color:#67C23A">+{{scope.row.amount}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="fee" label="交易手续费用（元）" align="center">
          <template slot-scope="scope">
            <span style="color:#F56C6C">-{{scope.row.fee}}</span>
          </template>
        </el-table-column>
        <el-table-column label="到账金额（元）" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <span>{{scope.row.amount - scope.row.fee}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="payType" label="来源" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.payType == 1">微信</span>
            <span v-if="scope.row.payType == 2">支付宝</span>
          </template>
        </el-table-column>
      </el-table>
      <div class="public-top20">
        <div class="public-fr">
          <el-pagination @current-change="handleCurrentChange" current-page.sync="payOrderList.pages" :page-size="10" layout="prev, pager, next, jumper" :total="payOrderList.total">
          </el-pagination>
        </div>
      </div>
    </div>
    <div v-show="tType==1">
      <el-table ref="multipleTable" :data="getDrawCashList.records" class="public-top20">
        <el-table-column label="申请时间" show-overflow-tooltip align="center">
          <template slot-scope="scope">{{ scope.row.ctime}}</template>
        </el-table-column>
        <el-table-column prop="externalOrderNo" label="流水号" show-overflow-tooltip align="center">
        </el-table-column>
        <el-table-column prop="bankCardNo" label="银行账户" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.bankCardNo|| '——'}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="amount" label="提现金额(元)" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <span style="color:#F56C6C">-{{ scope.row.amount}}</span>
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
          <el-pagination @current-change="handleCurrentChange2" current-page.sync="getDrawCashList.pages" :page-size="10" layout="prev, pager, next, jumper" :total="getDrawCashList.total">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tType: 0,
      value: "",
      value2: "",
      // 提现分页查询
      getDrawCashList: {},
      pickerOptions2: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      },
      //
      tableData3: [],
      //提现分页查询
      drawListParams: {
        wmemberId: "",
        startTime: "",
        endTime: "",
        current: 1,
        size: 10
      },
      payOrderList: {}
    };
  },
  watch: {
    tType() {
      this.drawListParams.wmemberId = window.sessionStorage.walletId;
      this.getDrawPage();
      this.walletPayOrderList();
    }
  },
  mounted() {
    console.log(
      window.sessionStorage.walletId,
      "window.sessionStorage.walletId"
    );
    this.drawListParams.wmemberId = window.sessionStorage.walletId;
    this.getDrawPage();
    this.walletPayOrderList();
  },
  methods: {
    // 消费 选择时间范围
    selectDraw(e) {
      console.log(e, "提现 选择时间范围");
      this.drawListParams.current = 1;
      if (e) {
        this.drawListParams.startTime = this.DateFormat(
          Date.parse(e[0]),
          "yyyy-MM-dd"
        );
        this.drawListParams.endTime = this.DateFormat(
          Date.parse(e[1]),
          "yyyy-MM-dd"
        );
      } else {
        this.drawListParams.startTime = "";
        this.drawListParams.endTime = "";
      }
      this.walletPayOrderList();
    },
    //消费 分页查询
    handleCurrentChange(val) {
      this.drawListParams.current = val;
      this.walletPayOrderList();
    },
    //消费list
    walletPayOrderList() {
      $.ajax({
        url: this.DFPAYDOMAIN + "/walletPayOrder/getPage",
        type: "POST",
        dataType: "JSON",
        data: this.drawListParams,
        success: res => {
          console.log(res, "获取消费list");
          if (res.code == 0) {
            this.payOrderList = res.data;
          } else {
            this.$message.error(res.msg);
          }
        }
      });
    },
    // 提现 选择时间范围
    selectDraw2(e) {
      console.log(e, "提现 选择时间范围");
      this.drawListParams.current = 1;
      if (e) {
        this.drawListParams.startTime = this.DateFormat(
          Date.parse(e[0]),
          "yyyy-MM-dd"
        );
        this.drawListParams.endTime = this.DateFormat(
          Date.parse(e[1]),
          "yyyy-MM-dd"
        );
      } else {
        this.drawListParams.startTime = "";
        this.drawListParams.endTime = "";
      }
      this.getDrawPage();
    },
    //提现 分页查询
    handleCurrentChange2(val) {
      this.drawListParams.current = val;
      this.getDrawPage();
    },
    // 提现 分页查询
    getDrawPage() {
      $.ajax({
        url: this.DFPAYDOMAIN + "/walletMoney/getPage",
        type: "POST",
        dataType: "json",
        data: this.drawListParams,
        success: res => {
          console.log(res, "提现 分页查询");
          if (res.code == 0) {
            this.getDrawCashList = res.data;
          } else {
            this.$message.error(res.msg);
          }
        }
      });
    }
  }
};
</script>
