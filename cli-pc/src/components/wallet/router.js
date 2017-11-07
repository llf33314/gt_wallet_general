const index = ()=>import( '@/components/wallet/vue/index')
const update = ()=>import( '@/components/wallet/vue/update')
export const wallet = [
    {
        path:'/wallet/index',
        component: index,
        name:'index'
    },
    {
        path:'/wallet/update',
        component: update,
        name:'update'
    }
]