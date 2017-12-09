//个人
export const individual = [
  {
    path: '/wallet/individual/auditing',
    name: 'auditing',
    title: '个人审核中',
    component: resolve => {
      require(['@/components/wallet/vue/individual/auditing'], resolve)
    }
  },
  {
    path: '/wallet/individual/open/:memberId',
    name: 'individualOpen',
    title: '个人开通',
    component: resolve => {
      require(['@/components/wallet/vue/individual/open'], resolve)
    }
  },
  {
    path: '/wallet/individual/index',
    name: 'individualIndex',
    title: '个人主页',
    component: resolve => {
      require(['@/components/wallet/vue/individual/index'], resolve)
    }
  },
  {
    path: '/wallet/individual/drawcash',
    name: 'individualDrawcash',
    title: '个人提现',
    component: resolve => {
      require(['@/components/wallet/vue/individual/drawcash'], resolve)
    }
  },
  {
    path: '/wallet/individual/addBank/:memberId',
    name: 'individualAddBank',
    title: '新增个人银行卡',
    component: resolve => {
      require(['@/components/wallet/vue/individual/addBank'], resolve)
    }
  }
]
//企业
export const company = [
  {
    path: '/wallet/company/open/:memberId',
    name: 'companyOpen',
    title: '企业开通',
    component: resolve => {
      require(['@/components/wallet/vue/company/open'], resolve)
    }
  }
]
export const wallet = [
  {
    path: '/wallet/index',
    name: 'index',
    title: '多粉钱包',
    component: resolve => {
      require(['@/components/wallet/vue/index'], resolve)
    }
  },
  {
    path: '/wallet/noOpen',
    name: 'walletNoOpen',
    title: '未开通',
    component: resolve => {
      require(['@/components/wallet/vue/template/noOpen'], resolve)
    }
  },
  ...individual,
  ...company
]
// //首页
// const index = () =>
//   import ('@/components/wallet/vue/index')
// //编辑
// const update = () =>
//   import ('@/components/wallet/vue/update')
// //提现
// const drawcash = () =>
//   import ('@/components/wallet/vue/drawcash')
// //个人开通
// const personalOpen = () =>
//   import ('@/components/wallet/vue/personalOpen')
// //企业开通
// const companyOpen = () =>
//   import ('@/components/wallet/vue/companyOpen')
// //注册成功
// const registerSuccess = () =>
//   import ('@/components/wallet/vue/registerSuccess')
// //审核中
// const auditing = () =>
//   import ('@/components/wallet/vue/auditing')
// //审核失败
// const auditFailure = () =>
//   import ('@/components/wallet/vue/auditFailure')
// //审核成功
// const auditSuccess = () =>
//   import ('@/components/wallet/vue/auditSuccess')
// //多粉钱包设置
// const walletSet = () =>
//   import ('@/components/wallet/vue/walletSet')
// //多粉钱包设置成功
// const walletSetSuccess = () =>
//   import ('@/components/wallet/vue/walletSetSuccess')

// export const wallet = [{
//     path: '/wallet/index',
//     component: index,
//     name: 'index'
//   },
//   {
//     path: '/wallet/update',
//     component: update,
//     name: 'update'
//   },
//   {
//     path: '/wallet/drawcash',
//     component: drawcash,
//     name: 'drawcash'
//   },
//   {
//     path: '/wallet/personalOpen',
//     component: personalOpen,
//     name: 'personalOpen'
//   },
//   {
//     path: '/wallet/companyOpen',
//     component: companyOpen,
//     name: 'companyOpen'
//   },
//   {
//     path: '/wallet/registerSuccess',
//     component: registerSuccess,
//     name: 'registerSuccess'
//   },
//   {
//     path: '/wallet/auditing',
//     component: auditing,
//     name: 'auditing'
//   },
//   {
//     path: '/wallet/auditFailure',
//     component: auditFailure,
//     name: 'auditFailure'
//   },
//   {
//     path: '/wallet/auditSuccess',
//     component: auditSuccess,
//     name: 'auditSuccess'
//   },
//   {
//     path: '/wallet/walletSet',
//     component: walletSet,
//     name: 'walletSet'
//   },
//   {
//     path: '/wallet/walletSetSuccess',
//     component: walletSetSuccess,
//     name: 'walletSetSuccess'
//   }
// ]
