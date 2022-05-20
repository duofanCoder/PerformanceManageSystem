import { request } from "../request";

export function fetchQueryPosition(condition: Partial<Condition.Common>) {
	return request.post<Dto.Page<Dto.Position>>("/position/query", condition);
}

export function fetchRemovePosition(ids: number[]) {
	const searchParams = new URLSearchParams();
	ids.forEach(id => searchParams.append("primaryKeys", id.toString()));
	return request.post(`/position/remove?${searchParams.toString()}`);
}

export function fetchSavePosition(position: Partial<Dto.Position>) {
	return request.post("/position/save", position);
}

export function fetchUpdatePosition(position: Partial<Dto.Position>) {
	return request.post("/position/update", position);
}
