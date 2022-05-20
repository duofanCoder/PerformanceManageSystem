import { request } from '../request';

export function fetchQueryRecord(condition: Partial<Condition.Common>) {
  return request.post<Dto.Page<Dto.Record>>('/record/query', condition);
}

export function fetchRemoveRecord(ids: number[]) {
  const searchParams = new URLSearchParams();
  ids.forEach((id) => searchParams.append('primaryKeys', id.toString()));
  return request.post(`/record/remove?${searchParams.toString()}`);
}

export function fetchSaveRecord(record: Partial<Dto.Record>) {
  const obj = Object.create(null);
  for (const [k, v] of record.scoreMap) {
    obj[k] = v;
  }

  console.log(obj);

  return request.post('/record/save', {
    ...record,
    scoreMap: obj,
  });
}

export function fetchUpdateRecord(record: Partial<Dto.Record>) {
  return request.post('/record/update', record);
}
