import { request } from "../request";

export function fetchQueryGroup(condition: Partial<Condition.Common>) {
	return request.post<Dto.Page<Dto.Group>>("/group/query", condition);
}

export function fetchRemoveGroup(ids: number[]) {
	const searchParams = new URLSearchParams();
	ids.forEach(id => searchParams.append("primaryKeys", id.toString()));
	return request.post(`/group/remove?${searchParams.toString()}`);
}

export function fetchSaveGroup(group: Partial<Dto.Group>) {
	return request.post("/group/save", group);
}

export function fetchUpdateGroup(group: Partial<Dto.Group>) {
	return request.post("/group/update", group);
}

export function fetchAddUserGroup(groupId:number,userList:number[]) {
	return request.post("/group/user/add", {
		groupId,
		userList
	});
}



