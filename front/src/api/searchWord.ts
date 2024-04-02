import instance from "./interceptors";

// 추천 검색어 조회

export const getRecipeRecommendationSearches = async () => {
	const url = `/api/recipe/search/words/recommend`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

// 최근 검색어 조회

export const getRecipeLastSearches = async () => {
	const url = `/api/recipe/search/words/last`;
	try {
		const response = await instance.get(url);
		return response.data.data.slice(0, 5);
	} catch (error) {
		console.error(error);
	}
};

// 최근 검색어 삭제

export const deleteLateSearch = async (word: string) => {
	const url = `/api/recipe/search/words/delete?searchWord=${word}`;
	try {
		await instance.delete(url);
	} catch (error) {
		console.error(error);
	}
};
