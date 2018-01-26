<template>
  <div>
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  mounted() {
    this.isPass();
  },
  methods: {
    // 查询会员信息
    getVipMsg() {
      console.log(6666);
      $.ajax({
        url: this.DFPAYDOMAIN + "/walletMember/findMember",
        type: "GET",
        dataType: "json",
        success: res => {
          console.log(res, "查询会员信息");
          // 开通
          // res.data.status 会员状态(-3：审核不通过，2:删除,-1:锁定用户,0:创建,1:审核中,3:正常使用)
          if (res.code == 0) {
            if (res.data.memberType == 3) {
              //个人会员
              if (res.data.status == 0 || res.data.status == -1) {
                //创建
                const individual = res.data.walletIndividual;

                if (!individual || individual.walletBank == '' || individual.name != '') {
                  //步骤1
                  this.$router.push({
                    path: "/wallet/individual/open/" + res.data.id
                  });
                } else if (res.data.isBindingPhone == 0 && individual) {
                  this.$router.push({
                    path: "/wallet/individual/open/bindPhone/" + res.data.id
                  });
                } else if (
                  res.data.isBindingPhone == 1 &&
                  res.data.status == -1
                ) {
                  //正常使用
                  window.sessionStorage.walletId = res.data.id;
                  this.$router.push({
                    path: "/wallet/individual/index"
                  });
                }
              } else if (res.data.status == 3) {
                //正常使用
                window.sessionStorage.walletId = res.data.id;
                this.$router.push({
                  path: "/wallet/individual/index"
                });
              }
            } else {
              //企业会员
              //步骤1--基本资料
              if (res.data.status == -3) {
                if (
                  !res.data.walletCompany ||
                  res.data.walletCompany.companyName == ""
                ) {
                  this.$router.push({
                    path: "/wallet/company/open/base/" + res.data.id
                  });
                }
                //步骤2 -- 上传图片
                if (res.data.walletCompany.doBusinessUrl == "") {
                  this.$router.push({
                    path: "/wallet/company/open/uploadFil/" + res.data.id
                  });
                }
              }
              if (
                !res.data.walletCompany ||
                res.data.walletCompany.companyName == ""
              ) {
                this.$router.push({
                  path: "/wallet/company/open/base/" + res.data.id
                });
              }
              //步骤2 -- 上传图片
              if (res.data.walletCompany.doBusinessUrl == "") {
                this.$router.push({
                  path: "/wallet/company/open/uploadFil/" + res.data.id
                });
              }
              //审核中
              if (res.data.isBindingPhone == 0 && res.data.status == 1) {
                this.$router.push({
                  path: "/wallet/company/open/auditing"
                });
              }
              //绑定手机
              if (res.data.isBindingPhone == 0 && res.data.status == 2) {
                this.$router.push({
                  path: "/wallet/company/open/bindPhone/" + res.data.id
                });
              }
              //正常使用
              if (res.data.isBindingPhone == 1 && res.data.status == -1) {
                window.sessionStorage.walletId = res.data.id;
                this.$router.push({
                  path: "/wallet/company/index"
                });
              }
              if (res.data.status == 3) {
                //正常使用
                window.sessionStorage.walletId = res.data.id;
                this.$router.push({
                  path: "/wallet/company/index"
                });
              }
            }
          } else if (res.code == 1009) {
            // 未开通
            this.$router.push({
              path: "/wallet/noOpen"
            });
          } else {
            //异常的错误
            this.$message.error(res.msg);
          }
        }
      });
    },
    goToWhere(data, id) {
      var g = [
        data.doBusinessUrl,
        data.identitycardUrl1,
        data.identitycardUrl2,
        data.licenseUrl
      ];
      var flag = false;
      g.forEach(item => {
        if (item == null || item == "") {
          flag = true;
        }
      });
      if (flag) {
        this.$router.push({
          path: "/wallet/company/open/uploadFile/" + id
        });
      } else {
        this.$router.push({
          path: "/wallet/company/open/base/" + id
        });
      }
    },
    // 判断商家是否开通多粉钱包
    isPass() {
      $.ajax({
        url: this.DFPAYDOMAIN + "/walletMember/isPassWallet",
        type: "GET",
        dataType: "json",
        success: res => {
          console.log(res, "判断商家是否开通多粉钱包");
          //0 :已开通, 1:未开通
          if (res.code == 0) {
            if (res.data == 0) {
              this.getVipMsg();
            } else {
              this.$router.push({
                path: "/wallet/noOpen"
              });
            }
          } else {
            this.$message.error(res.msg);
          }
        }
      });
    }
  }
};
</script>
<style lang="less">

</style>
