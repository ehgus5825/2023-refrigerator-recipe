import instance from "./interceptors";

export const getBookmarkIDs = async () => {
	const url = `/api/my-bookmark/added`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

export const getBookmarks = async (page: number) => {
	const url = `/api/my-bookmark/list?page=${page}`;
	try {
		const response = await instance.get(url);
		return response.data.bookmarks;
	} catch (error) {
		console.error(error);
	}
};

export const addBookmark = async (recipeID: number) => {
	const url = `/api/my-bookmark/add/${recipeID}`;
	try {
		const response = await instance.post(url);
	} catch (error) {
		console.error(error);
	}
};

export const removeBookmark = async (recipeID: number) => {
	const url = `/api/my-bookmark/remove/${recipeID}`;
	try {
		const response = await instance.delete(url);
	} catch (error) {
		console.error(error);
	}
};
