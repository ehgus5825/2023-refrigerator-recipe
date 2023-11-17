import instance from "./interceptors";

export const getMatchedIngredients = async (keyword: string) => {
	const url = `/api/word-completion/ingredient?keyword=${keyword}`;
	try {
		const response = await instance.get(url);
		return response.data.data.slice(0, 5);
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const getIngredientUnit = async (name: string) => {
	const url = `/api/ingredients/unit?name=${name}`;
	try {
		const response = await instance.get(url);
		return response.data.unit;
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const getExpiringIngredients = async (day: number) => {
	const url = `/api/ingredients/deadline/${day}`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		throw error;
	}
};

export const requestIngredient = async (name: string, unit: string) => {
	const url = `/api/ingredients/propose`;
	const body = { name, unit };
	try {
		await instance.post(url, body);
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const addIngredient = async (body: Object) => {
	const url = `/api/ingredients`;
	try {
		await instance.post(url, body);
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const deleteIngredient = async (ingredientID: number) => {
	const url = `/api/ingredients/${ingredientID}`;
	try {
		await instance.delete(url);
	} catch (error) {
		console.log(error);
		throw error;
	}
};
