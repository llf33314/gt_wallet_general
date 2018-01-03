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
Vue.config.productionTip = false
Vue.prototype.axios = axios
Vue.prototype.qs = qs
Vue.prototype.store = store
Vue.prototype.DFPAYDOMAIN = window.DFPAYDOMAIN
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)
import {
  DateFormat,
  escapeHTML
} from './assets/js/index'
Vue.prototype.DateFormat = DateFormat
Vue.prototype.escapeHTML = escapeHTML
Vue.prototype.isPhone = window.isPhone
import 'gt-js/dist/gt.min.js'
const GtDialog = () => import('./components/public/dialog/component')
Vue.component('el-dialog',GtDialog)                // 弹出框
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {
    App
  }
})
