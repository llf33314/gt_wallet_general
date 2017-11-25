import Vue from 'vue'
import App from './App'
import router from './router/index'
import store from './store/index'
import qs from 'qs'
require('normalize.css')
import axios from './http/index'
Vue.config.productionTip = true

Vue.prototype.axios = axios
Vue.prototype.qs = qs
Vue.prototype.store = store
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
// import gt from 'gt-public-js/lib/gt.min'
Vue.use(ElementUI)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
