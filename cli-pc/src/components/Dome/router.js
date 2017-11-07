// 异步加载
const dome = () =>
  import ('@/components/Dome/vue/index')
const child1 = () =>
  import ('@/components/Dome/vue/child1')
const child2 = () =>
  import ('@/components/Dome/vue/child2')
const button = () =>
  import ('@/components/Dome/vue/button')
export const domeRouter = [{
    path: '/dome/index',
    name: 'dome',
    component: dome,
    redirect: '/dome/child1',
    children: [{
        path: '/dome/child1',
        name: 'child1',
        component: child1,
      },
      {
        path: '/dome/child2',
        name: 'child2',
        component: child2,
      }
    ]
  },
  {
    path: '/dome/button',
    name: 'button',
    component: button,
  }
]
