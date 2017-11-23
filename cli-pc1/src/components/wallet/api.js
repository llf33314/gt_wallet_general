import axios from './../../http/index'
//http://dfpay.yifriend.net/swagger-ui.html
let base = 'http://dfpay.yifriend.net'
export const Individual = {
  isPassWallet() {
    return axios.get(`${base}/isPassWallet`).then(res => res.data)
  }
}
