import { request } from "../request";

export function fetchQueryNews(condition: Partial<Condition.Common>) {
	return request.post<Dto.Page<Dto.News>>("/news/query", condition);
}

export function fetchRemoveNews(ids: number[]) {
	const searchParams = new URLSearchParams();
	ids.forEach(id => searchParams.append("primaryKeys", id.toString()));
	return request.post(`/news/remove?${searchParams.toString()}`);
}

export function fetchSaveNews(news: Partial<Dto.News>) {
	return request.post("/news/save", news);
}

export function fetchUpdateNews(news: Partial<Dto.News>) {
	return request.post("/news/update", {
		...news,
		category: news.category
	});
}
