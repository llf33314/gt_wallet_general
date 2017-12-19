import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    wallet: {
      id:''
    }
  },
  mutations: {
    getWalletId(state, data) {
      console.log(data,'*****')
      state.wallet.id = data
    }
  }
})
export default store
