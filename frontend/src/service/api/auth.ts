import { request } from '../request';

/**
 * 登录
 * @param phone - 手机号
 * @param pwdOrCode - 密码或验证码
 * @param type - 登录方式: pwd - 密码登录; sms - 验证码登录
 */
export function fetchLogin(username: string, password: string, type: 'pwd' | 'sms') {
  return request.post<ApiAuth.Token>('/user/login', { username, password });
  // return mockRequest.post<ApiAuth.Token>('/loginByCode', { username, code: pwdOrCode });
}

/** 获取用户信息 */
export function fetchUserInfo() {
  return request.get<ApiAuth.UserInfo>('/user/profile');
}
