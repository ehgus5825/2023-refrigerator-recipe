import instance from "./interceptors";

// 별점 생성 (요리하기)

export const rateRecipe = async (recipeID: number, score: number) => {
	const url = `/api/recipe/${recipeID}/cooking?score=${score}`;

	try {
		await instance.post(url);
	} catch (error) {
		console.error(error);
	}
};

// 별점 목록 조회

export const getRatedRecipes = async (page: number) => {

	const url = `/api/my-page/scores?page=${page}`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};