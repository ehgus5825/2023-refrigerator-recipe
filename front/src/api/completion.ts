import instance from "./interceptors";

// 레시피 자동 완성

export const getRecipeSearchSuggestions = async (keyword: string) => {
	const url = `/api/recipe/search/word-completion?keyword=${keyword}`;
	try {
		const response = await instance.get(url);
		return response.data.data.slice(0, 10);
	} catch (error) {
		console.error(error);
	}
};

// 식재료 자동 완성

export const getMatchedIngredients = async (keyword: string) => {
	const url = `/api/ingredient/search/word-completion?keyword=${keyword}`;
	try {
		const response = await instance.get(url);
		return response.data.data.slice(0, 5);
	} catch (error) {
		console.log(error);
		throw error;
	}
};