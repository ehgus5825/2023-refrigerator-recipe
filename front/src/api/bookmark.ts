import instance from "./interceptors";

// 북마크 추가

export const addBookmark = async (recipeID: number) => {
	const url = `/api/recipe/${recipeID}/bookmark/add`;
	try {
		const response = await instance.post(url);
	} catch (error) {
		console.error(error);
	}
};

// 북마크 삭제

export const removeBookmark = async (recipeID: number) => {
	const url = `/api/recipe/${recipeID}/bookmark/deleted`;
	try {
		const response = await instance.delete(url);
	} catch (error) {
		console.error(error);
	}
};

// 북마크 목록 조회

export const getBookmarks = async (page: number) => {

	const url = `/api/my-page/bookmarks?page=${page}`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};