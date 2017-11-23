import Vue from 'vue'
import App from './App'
import router from './router/index'
require('normalize.css')
import axios from './http/index'
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  axios,
  template: '<App/>',
  components: { App }
})
