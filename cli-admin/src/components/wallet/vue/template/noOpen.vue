<style lang="less">
.no-open {
  padding-top: 70px;
  text-align: center;
  .text {
    font-size: 14px;
    color: #333333;
    padding: 27px 0 70px;
  }
  .el-icon-warning {
    color: #f7ba2a;
    font-size: 35px;
  }
}
.el-alert--info {
  background-color: #fff;
  color: #909399;
}
.el-alert__title {
  font-size: 14px;
  line-height: 18px;
}
.el-alert .el-alert__description {
  font-size: 14px;
  margin: 5px 0 0;
}
</style>
<template>
  <div class="no-open">
    <i class="el-icon-warning"></i>
    <p class="text">您尚未开通多粉钱包，点击下方按钮开通吧！</p>
    <div class="bts">
      <el-button @click="openTips(2)" type="primary">企业开通</el-button>
      <el-button @click="openTips(3)" type="primary">个人开通</el-button>
    </div>
    <el-dialog title="提示" :visible.sync="dialogVisible" width="530px" style="text-align:left">
      <el-alert :title="title" :closable="false" type="info" description="钱包申请类型确定后不可更改" show-icon>
      </el-alert>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="getVipNum" :loading="loading">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {
  wallet
} from './../../api/index'
export default {
  data() {
    return {
      dialogVisible: false,
      title: '',
      type: '',
      loading: false
    }
  },
  methods: {
    individualOpen(memberId) {
      this.$router.push({
        path: '/wallet/individual/open/' + memberId
      })
    },
    companyOpen(memberId) {
      this.$router.push({
        path: '/wallet/company/open/base/' + memberId
      })
    },
    openTips(type) {
      if (type === 2) {
        this.title = '您确认选择企业开通吗？'
      } else {
        this.title = '您确认选择个人开通吗？'
      }
      this.type = type
      this.dialogVisible = true
    },
    //获取会员id
    getVipNum() {
      const type = this.type
      this.dialogVisible = false
      $.ajax({
        url: this.DFPAYDOMAIN + '/walletMember/register',
        type: 'GET',
        dataType: 'json',
        data: {
          memberType: type
        },
        success: (res) => {
          console.log(res, 'res')
          if (res.code == 0) {
            if (type == 2) {
              this.companyOpen(res.data)
            } else {
              this.individualOpen(res.data)
            }
          } else {
            this.$message.error(res.msg);
          }
        }
      })
    },
  }
}

</script>
