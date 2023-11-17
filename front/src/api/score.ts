import instance from "./interceptors";

export const getRatedRecipes = async (page: number) => {
	const url = `/api/my-score/list?page=${page}`;
	try {
		const response = await instance.get(url);
		return response.data.scores;
	} catch (error) {
		console.error(error);
	}
};
