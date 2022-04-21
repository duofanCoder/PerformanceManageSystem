import { request } from '../request';

export function fetchQueryScheme(condition: Partial<Condition.Common>) {
  return request.post<Dto.Page<Dto.Scheme>>('/scheme/query', condition);
}

export function fetchRemoveScheme(ids: number[]) {
  const searchParams = new URLSearchParams();
  ids.forEach((id) => searchParams.append('primaryKeys', id.toString()));
  return request.post(`/scheme/remove?${searchParams.toString()}`);
}

export function fetchSaveScheme(scheme: Partial<Dto.Scheme>) {
  return request.post('/scheme/save', scheme);
}

export function fetchUpdateScheme(scheme: Partial<Dto.Scheme>) {
  return request.post('/scheme/update', scheme);
}
