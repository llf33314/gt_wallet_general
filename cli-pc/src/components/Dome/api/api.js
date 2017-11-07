import axios from './../../../http'

let base = 'http://rap.duofee.com'
/* 登录请求 */
export const requestLogin = params => { return axios.post(`${base}/mockjsdata/5/duofentv/79B4DE7C/activation.do`, params).then(res => res.data) }
