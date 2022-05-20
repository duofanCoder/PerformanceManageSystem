import type { Router } from 'vue-router';
import { defineStore } from 'pinia';
import {
  getUserInfo,
  transformAuthRouteToMenu,
  transformAuthRoutesToVueRoutes,
  transformAuthRoutesToSearchMenus,
  getCacheRoutes,
} from '@/utils';
import { useTabStore } from '../tab';

interface RouteState {
  /** 是否添加过动态路由 */
  isAddedDynamicRoute: boolean;
  /** 路由首页name */
  routeHomeName: AuthRoute.RouteKey;
  /** 菜单 */
  menus: GlobalMenuOption[];
  /** 搜索的菜单 */
  searchMenus: AuthRoute.Route[];
  /** 缓存的路由名称 */
  cacheRoutes: string[];
}

const adminRoutes: AuthRoute.Route[] = [
  {
    name: 'dashboard',
    path: '/dashboard',
    component: 'basic',
    children: [
      {
        name: 'dashboard_workbench',
        path: '/dashboard/workbench',
        component: 'self',
        meta: {
          title: '工作台',
          requiresAuth: true,

          icon: 'icon-park-outline:workbench',
        },
      },
    ],
    meta: {
      title: '仪表盘',
      icon: 'carbon:dashboard',
      order: 1,
    },
  },
  {
    name: 'system-manage',
    path: '/system-manage',
    component: 'basic',
    children: [
      {
        name: 'system-manage_department',
        path: '/system-manage/department',
        component: 'self',
        meta: {
          title: '部门管理',
          requiresAuth: true,
        },
      },
      {
        name: 'system-manage_group',
        path: '/system-manage/group',
        component: 'self',
        meta: {
          title: '分组管理',
          requiresAuth: true,
        },
      },
      {
        name: 'system-manage_position',
        path: '/system-manage/position',
        component: 'self',
        meta: {
          title: '职务管理',
          requiresAuth: true,
        },
      },
      {
        name: 'system-manage_user',
        path: '/system-manage/user',
        component: 'self',
        meta: {
          title: '用户管理',
          requiresAuth: true,
        },
      },
    ],
    meta: {
      title: '系统管理',
      icon: 'carbon:cics-system-group',
      order: 2,
    },
  },
  {
    name: 'performance',
    path: '/performance',
    component: 'basic',
    children: [
      {
        name: 'performance_method',
        path: '/performance/method',
        component: 'self',
        meta: {
          title: '考核方式',
          requiresAuth: true,
        },
      },
      {
        name: 'performance_quota',
        path: '/performance/quota',
        component: 'self',
        meta: {
          title: '指标管理',
          requiresAuth: true,
        },
      },
      {
        name: 'performance_scheme',
        path: '/performance/scheme',
        component: 'self',
        meta: {
          title: '方案管理',
          requiresAuth: true,
        },
      },
      {
        name: 'performance_record',
        path: '/performance/record',
        component: 'self',
        meta: {
          title: '考核记录',
          requiresAuth: true,
        },
      },
    ],
    meta: {
      title: '绩效设置',
      icon: 'ic:baseline-score',
      order: 3,
    },
  },
  {
    name: 'notification',
    path: '/notification',
    component: 'basic',
    children: [
      {
        name: 'notification_category',
        path: '/notification/category',
        component: 'self',
        meta: {
          title: '新闻分类',
          requiresAuth: true,
        },
      },
      {
        name: 'notification_news',
        path: '/notification/news',
        component: 'self',
        meta: {
          title: '内容管理',
          requiresAuth: true,
        },
      },
    ],
    meta: {
      title: '新闻管理',
      icon: 'mdi:newspaper-variant-outline',
      order: 4,
    },
  },
  {
    name: 'user',
    path: '/user',
    component: 'basic',
    children: [
      {
        name: 'user_profile',
        path: '/user/profile',
        component: 'self',
        meta: {
          title: '个人信息',
          requiresAuth: true,
        },
      },
    ],
    meta: {
      title: '个人中心',
      icon: 'ph:user-bold',
      order: 6,
    },
  },
];

const commonRoutes: AuthRoute.Route[] = [
  {
    name: 'dashboard',
    path: '/dashboard',
    component: 'basic',
    children: [
      {
        name: 'dashboard_workbench',
        path: '/dashboard/workbench',
        component: 'self',
        meta: {
          title: '工作台',
          requiresAuth: true,

          icon: 'icon-park-outline:workbench',
        },
      },
    ],
    meta: {
      title: '仪表盘',
      icon: 'carbon:dashboard',
      order: 1,
    },
  },
  {
    name: 'appraisal',
    path: '/appraisal',
    component: 'basic',
    children: [
      {
        name: 'appraisal_scheme',
        path: '/appraisal/scheme',
        component: 'self',
        meta: {
          title: '考核',
          requiresAuth: true,
        },
      },
    ],
    meta: {
      title: '绩效考核',
      icon: 'ic:baseline-score',
      order: 4,
    },
  },
  {
    name: 'user',
    path: '/user',
    component: 'basic',
    children: [
      {
        name: 'user_scheme',
        path: '/user/scheme',
        component: 'self',
        meta: {
          title: '考核成绩',
          requiresAuth: true,
        },
      },
      {
        name: 'user_profile',
        path: '/user/profile',
        component: 'self',
        meta: {
          title: '个人信息',
          requiresAuth: true,
        },
      },
    ],
    meta: {
      title: '个人中心',
      icon: 'ph:user-bold',
      order: 6,
    },
  },
];

function sortRoutes(sorts: AuthRoute.Route[]) {
  return sorts.sort((next, pre) => Number(next.meta?.order) - Number(pre.meta?.order));
}
export const useRouteStore = defineStore('route-store', {
  state: (): RouteState => ({
    isAddedDynamicRoute: false,
    routeHomeName: 'dashboard_analysis',
    menus: [],
    searchMenus: [],
    cacheRoutes: [],
  }),
  actions: {
    /**
     * 初始化动态路由
     * @param router - 路由实例
     */
    async initDynamicRoute(router: Router) {
      const { initHomeTab } = useTabStore();

      const { id, role } = getUserInfo();
      if (!id) return;
      const routeHomeName: AuthRoute.RouteKey = 'dashboard_analysis';

      let data;
      if (role === 'ADMIN') {
        data = {
          routes: sortRoutes(adminRoutes),
          home: routeHomeName,
        };
      } else {
        data = {
          routes: sortRoutes(commonRoutes),
          home: routeHomeName,
        };
      }

      if (data) {
        this.routeHomeName = data.home;
        this.menus = transformAuthRouteToMenu(data.routes);
        this.searchMenus = transformAuthRoutesToSearchMenus(data.routes);

        const vueRoutes = transformAuthRoutesToVueRoutes(data.routes);
        vueRoutes.forEach((route) => {
          router.addRoute(route);
        });

        this.cacheRoutes = getCacheRoutes(vueRoutes);

        initHomeTab(data.home, router);
        this.isAddedDynamicRoute = true;
      }
    },
    resetStore() {
      this.$reset();
    },
  },
});
