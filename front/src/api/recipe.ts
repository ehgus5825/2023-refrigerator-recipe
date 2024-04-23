import instance from "./interceptors";

// 레시피 정보 조회

export const getRecipe = async (recipeID: number) => {
	const url = `/api/recipe/${recipeID}`;
	try {
		const response = await instance.get(url);
		return response.data;
	} catch (error) {
		console.error(error);
	}
};

// 레시피 추천

export const getRecommendedRecipes = async () => {
	const url = `/api/recipe/recommend`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error: any) {
		const errorCode = error?.response?.data?.code;
		console.log(errorCode);
		if (errorCode === "EMPTY_MEMBER_INGREDIENT") return [];
		else console.error(error);
	}
};

// 레시피 검색

export const searchRecipe = async (page: number, body: any, searchWord: String) => {
	const url = `/api/recipe/search?page=${page}&searchWord=${searchWord}`;
	try {
		const response = await instance.post(url, body);
		return response.data.data;
	} catch (error) {
		console.log(error);
	}
};