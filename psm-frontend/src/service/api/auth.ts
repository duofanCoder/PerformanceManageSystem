import { mockRequest, request } from '../request';

/**
 * 获取验证码
 * @param phone - 手机号
 * @returns - 返回boolean值表示是否发送成功
 */
export function fetchSmsCode(phone: string) {
  return mockRequest.post<boolean>('/getSmsCode', { phone });
}

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

/**
 * 刷新token
 * @param refreshToken
 */
export function fetchUpdateToken(refreshToken: string) {
  return mockRequest.post<ApiAuth.Token>('/updateToken', { refreshToken });
}
