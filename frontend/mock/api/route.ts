import type { MockMethod } from 'vite-plugin-mock';

const routes: AuthRoute.Route[] = [
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
          permissions: ['COMMON'],
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
          permissions: ['COMMON'],
        },
      },
      {
        name: 'user_profile',
        path: '/user/profile',
        component: 'self',
        meta: {
          title: '个人信息',
          requiresAuth: true,
          permissions: ['COMMON'],
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

function dataMiddleware(data: AuthRoute.Route[]): ApiRoute.Route {
  const routeHomeName: AuthRoute.RouteKey = 'dashboard_analysis';

  function sortRoutes(sorts: AuthRoute.Route[]) {
    return sorts.sort((next, pre) => Number(next.meta?.order) - Number(pre.meta?.order));
  }

  return {
    routes: sortRoutes(data),
    home: routeHomeName,
  };
}

const apis: MockMethod[] = [
  {
    url: '/mock/getUserRoutes',
    method: 'post',
    response: (): Service.MockServiceResult => {
      return {
        code: 200,
        message: 'ok',
        data: dataMiddleware(routes),
      };
    },
  },
];

export default apis;
