import Vue from 'vue'
import App from './App'
import router from './router/index'
import store from './store/index'
import qs from 'qs'
require('normalize.css')
require('./assets/css/public.less')
require('./assets/css/wallet.less')
import axios from './http/index'
Vue.config.productionTip = true
Vue.prototype.axios = axios
Vue.prototype.qs = qs
Vue.prototype.store = store
Vue.prototype.DFPAYDOMAIN = window.DFPAYDOMAIN
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
