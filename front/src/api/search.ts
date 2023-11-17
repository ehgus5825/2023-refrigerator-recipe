import instance from "./interceptors";

export const searchRecipe = async (page: number, body: any) => {
	const url = `/api/recipe/search?page=${page}`;
	try {
		const response = await instance.post(url, body);
		return response.data.data;
	} catch (error) {
		console.log(error);
	}
};

export const getRecipeSearchSuggestions = async (keyword: string) => {
	const url = `/api/word-completion/recipe?keyword=${keyword}`;
	try {
		const response = await instance.get(url);
		return response.data.data.slice(0, 10);
	} catch (error) {
		console.error(error);
	}
};

export const getRecipeLastSearches = async () => {
	const url = `/api/search-word/last`;
	try {
		const response = await instance.get(url);
		return response.data.data.slice(0, 5);
	} catch (error) {
		console.error(error);
	}
};

export const getRecipeRecommendationSearches = async () => {
	const url = `/api/search-word/recommend`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

export const deleteLateSearch = async (word: string) => {
	const url = `/api/search-word?word=${word}`;
	try {
		await instance.delete(url);
	} catch (error) {
		console.error(error);
	}
};
