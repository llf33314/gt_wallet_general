<style lang="less">
.wallet-company-news {
  position: relative;
  .tips {
    top: -72px;
    left: 173px;
    position: absolute;
    background-color: #e91e63;
    border-radius: 50%;
    font-size: 12px;
    color: #fff;
    padding: 1px;
    z-index: 2;
    min-width: 14px;
    min-height: 10px;
    display: inline-block;
    text-align: center;
  }
}
</style>

<template>
  <div class="wallet-company-news">
    <span class="tips" v-text="readstate">12</span>
    <div style="padding-bottom:20px;">
      <el-select v-model="form.msgType" clearable placeholder="请选择消息类型">
        <el-option v-for="item in filters" :key="item.msgType" :label="item.msgTypeDesc" :value="item.msgType">
        </el-option>
      </el-select>
    </div>
    <el-table :data="tableData3" ref="multipleTable" style="width: 100%" @selection-change="handleSelectionChange" @filter-change="filterChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="ctime" label="日期" width="180">
        <template slot-scope="scope">
          <span v-if="scope.row.state==0" style="color: #000;" v-text="DateFormat(scope.row.ctime,'yyyy-MM-dd hh:mm')"></span>
          <span v-if="scope.row.state==1" v-text="DateFormat(scope.row.ctime,'yyyy-MM-dd hh:mm')"></span>
        </template>
      </el-table-column>
      <el-table-column prop="descContent" label="内容">
        <template slot-scope="scope">
          <span v-if="scope.row.state==0" style="color: #000;" v-text="scope.row.descContent"></span>
          <span v-if="scope.row.state==1" v-text="scope.row.descContent"></span>
        </template>
      </el-table-column>
      <el-table-column prop="state" label="状态" width="180">
        <template slot-scope="scope">
          <span v-if="scope.row.state==0" style="color: #000;">未读</span>
          <span v-if="scope.row.state==1">已读</span>
        </template>
      </el-table-column>
      <el-table-column prop="state " label="消息类型" width="180">
        <template slot-scope="scope">
          <el-tag type="primary">{{scope.row.msgTypeDesc}}</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 20px" v-if="tableData3.length !=0">
      <el-button size="small" @click="toggleSelection(tableData3)">全选</el-button>
      <el-button size="small" @click="upstate">标记已读</el-button>
      <el-pagination style="float:right" @current-change="handleCurrentChange" :current-page.sync="form.current" :page-size="10" layout="prev, pager, next, jumper" :total="page.total">
      </el-pagination>
    </div>
  </div>
</template>
      <script>
export default {
  data() {
    return {
      tableData3: [],
      multipleSelection: [],
      filters: [
        {
          value: '',
          text: ''
        }
      ],
      form: {
        current: 1,
        wmemberId: 7,
        size: 10,
        msgType: ''
      },
      page: {
        total: 1,
      },
      readstate: 0,
      selectIds: []
    }
  },
  mounted() {
    this.form.wmemberId = window.sessionStorage.walletId
    this.getPage()
    this.getMsgTypeResult()
    this.getReadState()
  },
  watch: {
    'form.msgType': function () {
      this.form.current = 1
      setTimeout(() => {
        this.getPage()
      }, 100)
    }
  },
  methods: {
    //获取未读记录数
    getReadState() {
      $.ajax({
        url: this.DFPAYDOMAIN + "/walletMessage/79B4DE7C/getReadState",
        type: "POST",
        dataType: "JSON",
        data: {
          wMemberId: window.sessionStorage.walletId
        },
        success: res => {
          console.log(res, "获取未读记录数");
          if (res.code == 0 || res.code == 1009) {
            this.readstate = res.data;
          } else {
            this.$message.error(res.msg);
          }
        }
      });
    },
    filterChange() {
      this.form.current = 1
      this.getPage()
    },
    //分页查询list
    getPage() {
      $.ajax({
        url: this.DFPAYDOMAIN + "/walletMessage/79B4DE7C/getPage",
        type: "POST",
        dataType: "JSON",
        data: this.form,
        success: res => {
          console.log(res, "分页查询list");
          if (res.code == 0 || res.code == 1009) {
            if (res.data) {
              this.tableData3 = res.data.records || [];
              this.page.total = res.data.total || 0
            }else{
              this.tableData3 = [];
              this.page.total = 0
            }
          } else {
            this.$message.error(res.msg);
            this.tableData3 = []
          }
        }
      });
    },
    //获取消息类型
    getMsgTypeResult() {
      $.ajax({
        url: this.DFPAYDOMAIN + "/walletMessage/79B4DE7C/getMsgTypeResult",
        type: "POST",
        dataType: "JSON",
        success: res => {
          console.log(res, "获取消息类型");
          if (res.code == 0 || res.code == 1009) {
            const d = res.data || [];
            const n = []
            d.forEach((item) => {
              n.push({
                text: item.msgTypeDesc,
                value: item.msgType,
              })
            })
            this.filters = d
          } else {
            this.$message.error(res.msg);
          }
        }
      });
    },
    //分页查询
    handleCurrentChange(val) {
      this.form.current = val
      this.getPage()
    },
    filterTag(value, row) {
      this.form.msgType = value
    },
    //全选
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    //选择
    handleSelectionChange(e) {
      this.selectIds = e
      console.log(e, 56)
    },
    //已读
    upstate() {
      const listStr = []
      this.selectIds.forEach(item => {
        listStr.push(item.id)
      })
      if (listStr.length == 0) {
        this.$message.error('请选择消息')
      } else {
        $.ajax({
          url: this.DFPAYDOMAIN + "/walletMessage/79B4DE7C/upstate",
          type: "POST",
          dataType: "JSON",
          data: { listStr: listStr.join(",") },
          success: res => {
            console.log(res, "已读");
            if (res.code == 0 || res.code == 1009) {
              this.form.current = 1
              this.$message({
                message: res.msg,
                type: 'success'
              });
              this.getPage()
              this.getReadState()
            } else {
              this.$message.error(res.msg);
            }
          }
        });
      }
    },
  }
}

</script>
      