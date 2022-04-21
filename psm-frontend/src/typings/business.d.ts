/** 用户相关模块 */
declare namespace Auth {
  /**
   * 用户角色类型
   * - super: 超级管理员
   * - admin: 管理员
   * - test: 测试
   * - visitor: 游客
   */
  type RoleType = 'super' | 'ADMIN' | 'test' | 'visitor' | 'COMMON';

  /** 用户信息 */
  interface UserInfo {
    id: number;
    createTime: string;
    name: string;
    updateTime: string;
    department: string;
    gender: boolean;
    mobile: string;
    position: string;
    role: RoleType;
  }
}
