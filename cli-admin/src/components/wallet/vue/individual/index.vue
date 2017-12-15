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
              <span class="title" style="width:70px;text-align:right">姓名：</span>
              <span class="title-dps ellipsis" v-text="walletIndividual.name" style="vertical-align: top;"></span>
              <span class="title">认证类型：</span>
              <span>个人认证</span>
            </div>
          </div>
          <div class="public-fr" style="margin-top:0;">
            <el-button @click="goToMsg" size="small" type="primary">个人信息</el-button>
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
        <el-tabs v-model="activeName" type="card" style="margin-top: 40px;margin-bottom: 20px;">
          <el-tab-pane label="交易记录" name="record"></el-tab-pane>
          <el-tab-pane label="消息中心" name="news"></el-tab-pane>
        </el-tabs>
        <router-view></router-view>
      </div>  
       
    </div>
  </section>
</template>
<script>
  export default {
   
    data() {
      return {
        activeName: 'record',
        IndexStatistics: {},
        walletIndividual: {},
        wmemberId:''
      }
    },
    mounted() {
      this.findMember()
      this.getIndexStatistics()
      console.log(this.$route.name)
      if(this.$route.name == 'wallet-record'){
        this.activeName = 'record'
        this.$router.push({
          path: '/wallet/individual/record'
        })
      }else{
        this.activeName = 'news'
        this.$router.push({
          path: '/wallet/individual/news'
        })
      }
    },
    watch: {
      activeName() {
        this.$router.push({
          path: '/wallet/individual/' + this.activeName
        })
      }
    },
    methods: {
      //查询多粉会员信息
      findMember() {
        $.ajax({
          url: this.DFPAYDOMAIN + '/walletMember/findMember',
          type: 'GET',
          dataType: 'JSON',
          success: (res) => {
            console.log(res, '查询多粉会员信息')
            if (res.code == 0) {
              this.wmemberId = res.data.id
              this.walletIndividual = res.data.walletIndividual
            } else {
              this.$message.error(res.msg)
            }
          }
        })
      },
      //企业信息
      goToMsg() {
        this.$router.push({
          path: '/wallet/individual/messages'
        })
      },
      //提现
      goToDrawCash() {
        this.$router.push({
          path: '/wallet/individual/drawcash'
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
      },
    }
  }

</script>
