import { request } from "../request";

export function fetchQueryMethod(condition: Partial<Condition.Common>) {
	return request.post<Dto.Page<Dto.Method>>("/method/query", condition);
}

export function fetchRemoveMethod(ids: number[]) {
	const searchParams = new URLSearchParams();
	ids.forEach(id => searchParams.append("primaryKeys", id.toString()));
	return request.post(`/method/remove?${searchParams.toString()}`);
}

export function fetchSaveMethod(method: Partial<Dto.Method>) {
	return request.post("/method/save", method);
}

export function fetchUpdateMethod(method: Partial<Dto.Method>) {
	return request.post("/method/update", method);
}
