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
        <el-date-picker v-if="tType==0" :clearable="false" format="yyyy-MM-dd" v-model="value4" @change="selectDraw" type="datetimerange"
          :picker-options="pickerOptions2" placeholder="选择时间范围" align="right" style="width:250px;">
        </el-date-picker>
        <el-date-picker v-if="tType==1" :clearable="false" format="yyyy-MM-dd" v-model="value4" @change="selectDraw" type="datetimerange"
          :picker-options="pickerOptions2" placeholder="选择时间范围" align="right" style="width:250px;">
        </el-date-picker>
      </div>
    </div>
    <div v-if="tType==0">
      <el-table ref="multipleTable" :data="tableData3" class="public-top20">
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
      <el-table ref="multipleTable" :data="getDrawCashList.records" class="public-top20">
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
</template>
<script>
  export default {
    data() {
      return {
        tType: 0,
        value4: '',
        // 提现分页查询
        getDrawCashList: {},
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
        //
        tableData3: [],
        //提现分页查询
        drawListParams: {
          wmemberId: '',
          startTime: '',
          endTime: '',
          current: 1,
          total: '',
          size: 10,
          records: [],
          searchCount: false,
          openSort: false,
          orderByField: '',
          condition: ''
        }
      }
    },
    methods: {
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
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      //消费 分页查询
      handleCurrentChange(val) {
        console.log(val, '提现 分页查询');
        this.drawListParams.current = val
        this.getDrawPage()
      },
      //提现 分页查询
      handleCurrentChange2(val) {
        console.log(val, '提现 分页查询');
        this.drawListParams.current = val
        this.getDrawPage()
      },
      // 提现 分页查询
      getDrawPage() {
        console.log(this.drawListParams, 'this.drawListParams')
        $.ajax({
          url: this.DFPAYDOMAIN + '/walletMoney/getPage',
          type: 'POST',
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
      //消费list
      walletPayOrderList() {
        $.ajax({
          url: this.DFPAYDOMAIN + '/walletPayOrder/getPage',
          type: 'POST',
          dataType: "JSON",
          data: {
            startTime: '',
            endTime: '',
            wmemberId: 7,
          },
          success: (res) => {
            console.log(res, "获取消费list")
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
