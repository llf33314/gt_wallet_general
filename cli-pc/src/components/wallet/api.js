import axios from './../../http/index'
//http://dfpay.yifriend.net/swagger-ui.html
let base = 'http://dfpay.yifriend.net'
// let base = 'http://192.168.3.98:8301'

export const wallet = {
  //查询会员信息
  findMember() {
    return axios.get(`${base}/findMember`).then(res => res.data)
  },
  // 判断商家是否开通多粉钱包
  isPassWallet() {
    return axios.get(`${base}/isPassWallet`).then(res => res.data)
  },
  //开通会员
  register(param) {
    return axios.get(`${base}/isPassWallet`, param).then(res => res.data)
  },
  //个人开通
  saveIndividual(param) {
    return axios
      .post(`${base}/walletIndividual/saveIndividual`, param,{
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(res => res.data)
  },
  //额度申请
  addDrawCash(param) {
    return axios.post(`${base}/walletQuota/add`, param).then(res => res.data)
  },
  // 上传图片
  upload(param) {
    let formData = new FormData()
    formData.append('file',param)
    return axios
      .post(`${base}/wcommon/upload`,formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        type: 'upload'
      })
      .then(res => res.data)
  }
}
