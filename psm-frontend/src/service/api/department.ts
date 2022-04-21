import { request } from "../request";

export function fetchQueryDepartment(condition: Partial<Condition.Common>) {
	return request.post<Dto.Page<Dto.Department>>("/department/query", condition);
}

export function fetchRemoveDepartment(ids: number[]) {
	const searchParams = new URLSearchParams();
	ids.forEach(id => searchParams.append("primaryKeys", id.toString()));
	return request.post(`/department/remove?${searchParams.toString()}`);
}

export function fetchSaveDepartment(department: Partial<Dto.Department>) {
	return request.post("/department/save", department);
}

export function fetchUpdateDepartment(department: Partial<Dto.Department>) {
	return request.post("/department/update", department);
}
