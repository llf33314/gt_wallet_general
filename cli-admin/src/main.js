import Vue from 'vue'
import App from './App'
import router from './router/index'
import store from './store/index'
import qs from 'qs'
require('normalize.css')
require('./assets/css/public.less')
require('./assets/css/wallet.less')
import _$ from 'jquery'
window.$ = _$
import axios from './http/index'
Vue.config.productionTip = true
Vue.prototype.axios = axios
Vue.prototype.qs = qs
Vue.prototype.store = store
Vue.prototype.DFPAYDOMAIN = window.DFPAYDOMAIN
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)
import {
  DateFormat,
} from './assets/js/index'
Vue.prototype.DateFormat = DateFormat
Vue.prototype.isPhone = window.isPhone
// import is_js from 'is_js'
// Vue.prototype.is = is_js
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {
    App
  }
})
