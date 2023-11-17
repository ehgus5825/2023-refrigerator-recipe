import {
	getRecipeFoodType,
	getRecipeIngredientCategory,
	getRecipeRecipeType,
	getRecipeDifficulty,
} from "@/api";

export const FILTERS = [
	{
		key: "recipeFoodType",
		name: "요리 유형",
		activeItem: "전체",
		fetchFilterMenu: getRecipeFoodType,
	},
	{
		key: "category",
		name: "주재료 유형",
		activeItem: "전체",
		fetchFilterMenu: getRecipeIngredientCategory,
	},
	{
		key: "recipeType",
		name: "분류",
		activeItem: "전체",
		fetchFilterMenu: getRecipeRecipeType,
	},
	{
		key: "difficulty",
		name: "난이도",
		activeItem: "전체",
		fetchFilterMenu: getRecipeDifficulty,
	},
	// { name: "조리시간", selectedItem: "전체", fetchList: getRecipeCookTime },
	// { name: "별점", selectedItem: "전체", fetchList: getRecipeStar },
];
