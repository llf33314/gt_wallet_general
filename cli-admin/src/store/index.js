import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    wallet: {
      vipMsg:{}
    }
  },
  mutations: {
    getVipMsg(state, data) {
      state.wallet.vipMsg = data
    }
  }
})
export default store
