import Vue from 'vue'
import Router from 'vue-router'
import { wallet } from '@/components/wallet/router/index'

Vue.use(Router)

export default new Router({
  routes: [
    ...wallet
  ]
})
