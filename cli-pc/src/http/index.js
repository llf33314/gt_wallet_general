import axios from 'axios'
import Promise from 'promise'
const Axios = axios.create({
  //baseURL: "http://192.168.3.98:7072",
  timeout: 10000
  // headers: {'Content-Type': 'multipart/form-data'}
  // responseType: "json",
  // withCredentials: true, // 是否允许带cookie这些
})

// 添加请求拦截器
Axios.interceptors.request.use(
  config => {
    console.log(config)
    if (config.type == 'upload') {
      delete config.type
      return config
    }
    if (config.method == 'post') {
      //return window.JSON.stringify(config)
    }
    return config
  },
  err => {
    return Promise.reject(err)
  }
)

// http response 拦截器 返回状态判断
Axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    return Promise.reject(error.response.data)
  }
)
export default Axios
