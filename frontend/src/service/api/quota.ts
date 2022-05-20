import { request } from '../request';

export function fetchQueryQuota(condition: Partial<Condition.Common>) {
  return request.post<Dto.Page<Dto.Quota>>('/quota/query', condition);
}

export function fetchRemoveQuota(ids: number[]) {
  const searchParams = new URLSearchParams();
  ids.forEach((id) => searchParams.append('primaryKeys', id.toString()));
  return request.post(`/quota/remove?${searchParams.toString()}`);
}

export function fetchSaveQuota(quota: Partial<Dto.Quota>) {
  return request.post('/quota/save', quota);
}

export function fetchUpdateQuota(quota: Partial<Dto.Quota>) {
  return request.post('/quota/update', quota);
}
