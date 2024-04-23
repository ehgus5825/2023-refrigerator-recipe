import { RecipeDeductedIngredient } from "@/types";
import instance from "./interceptors";

// 식재료 차감

export const deductIngredient = async (data: RecipeDeductedIngredient[]) => {
	const url = `/api/ingredients/deduction`;

	try {
		await instance.put(url, { data });
	} catch (error) {
		console.error(error);
	}
};

// 식재료명에 따른 단위 조회

export const getIngredientUnit = async (name: string) => {
	const url = `/api/ingredients/search/unit?name=${name}`;
	try {
		const response = await instance.get(url);
		return response.data.unit;
	} catch (error) {
		console.log(error);
		throw error;
	}
};

// 특정일 만료 식재료 목록 조회

export const getExpiringIngredients = async (days: String) => {
	const url = `/api/ingredients/search/deadline/${days}`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		throw error;
	}
};

// 식재료 요청

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

// 식재료 등록

export const addIngredient = async (body: Object) => {
	const url = `/api/ingredients/register`;
	try {
		await instance.post(url, body);
	} catch (error) {
		console.log(error);
		throw error;
	}
};

// 식재료 삭제

export const deleteIngredient = async (ingredientID: number) => {
	const url = `/api/ingredients/${ingredientID}/delete`;
	try {
		await instance.delete(url);
	} catch (error) {
		console.log(error);
		throw error;
	}
};

// 식재료 전체 삭제 (???)

// 레시피 중 소유한 식재료 목록 조회 (???)