// 异步加载
const error404 = ()=>import('@/components/ErrorState/vue/404')
const errorRouter = {
  path: '/error/index',
  name: 'index',
  component: error404,
  redirect:'/error/404',
  children:[
    {
      path: '/error/404',
      name: 'error404',
      component: error404,
    }
  ]
}
export default errorRouter
