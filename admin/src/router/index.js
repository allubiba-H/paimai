import Vue from 'vue'
import Router from 'vue-router'
/* Layout */
import Layout from '@/layout'

Vue.use(Router)

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [{
      path: 'home',
      component: () => import('@/views/home/index'),
      meta: {title: '首页', icon: 'el-icon-s-home'}
    }]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/admin',
    meta: {title: '系统管理', icon: 'el-icon-s-tools'},
    children: [
      {
        path: 'admin',
        component: () => import('@/views/system/admin'),
        meta: {title: '管理员管理', icon: 'el-icon-user'}
      },
      // {
      //   path: 'tree',
      //   component: () => import('@/views/tree/index'),
      //   meta: { title: 'Tree', icon: 'tree' }
      // },
      {
        path: 'huiyuan',
        component: () => import('@/views/huiyuan/admin'),
        meta: {title: '会员管理', icon: 'el-icon-s-custom'},
        children: [
          {
            path: 'admin',
            component: () => import('@/views/huiyuan/admin/index'),
            meta: {title: '会员信息', icon: 'el-icon-info'}
          }, {
            path: 'info',
            component: () => import('@/views/huiyuan/money/index'),
            meta: {title: '会员资金信息', icon: 'el-icon-coin'}
          }
        ]
      }
    ]
  },
  {
    path: '/info',
    component: Layout,
    meta: {title: '基础信息管理', icon: 'el-icon-s-management'},
    children: [
      {
        path: 'ptype',
        component: () => import('@/views/nested/menu1/index'),
        meta: {title: '商品类型管理', icon: 'el-icon-shopping-bag-1'}
      }, {
        path: 'p_status',
        component: () => import('@/views/nested/menu1/index'),
        meta: {title: '拍卖状态管理', icon: 'el-icon-shopping-bag-1'}
      }, {
        path: 'sail_status',
        component: () => import('@/views/nested/menu1/index'),
        meta: {title: '成交状态管理', icon: 'el-icon-shopping-bag-1'}
      }
    ]
  },
  {
    path: '/product',
    component: Layout,
    meta: {title: '拍卖管理', icon: 'el-icon-s-help'},
    children: [
      {
        path: 'check',
        component: () => import('@/views/auction/check/Index'),
        meta: {title: '拍卖信息审核', icon: 'el-icon-s-check'}
      },
      {
        path: 'send',
        component: () => import('@/views/auction/send/Index'),
        meta: {title: '拍卖信息发布', icon: 'el-icon-s-promotion'}
      }
    ]
  },
  {
    path: '/painting',
    component: Layout,
    redirect: '/painting/menu1',
    name: 'Jingpai',
    meta: {
      title: '竞拍记录管理',
      icon: 'el-icon-shopping-bag'
    },
    children: [
      {
        path: 'info',
        component: () => import('@/views/nested/menu1/index'), // Parent router-view
        name: 'Info',
        meta: {title: '竞拍记录信息'},
        children: [
          {
            path: 'menu1-1',
            component: () => import('@/views/nested/menu1/menu1-1'),
            name: 'Menu1-1',
            meta: {title: 'Menu1-1'}
          },
          {
            path: 'menu1-2',
            component: () => import('@/views/nested/menu1/menu1-2'),
            name: 'Menu1-2',
            meta: {title: 'Menu1-2'},
            children: [
              {
                path: 'menu1-2-1',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
                name: 'Menu1-2-1',
                meta: {title: 'Menu1-2-1'}
              },
              {
                path: 'menu1-2-2',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
                name: 'Menu1-2-2',
                meta: {title: 'Menu1-2-2'}
              }
            ]
          },
          {
            path: 'menu1-3',
            component: () => import('@/views/nested/menu1/menu1-3'),
            name: 'Menu1-3',
            meta: {title: 'Menu1-3'}
          }
        ]
      },
      {
        path: 'menu2',
        component: () => import('@/views/nested/menu2/index'),
        name: 'Menu2',
        meta: {title: 'menu2'}
      }
    ]
  },
  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: {title: 'External Link', icon: 'link'}
      }
    ]
  },
  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
]
const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
