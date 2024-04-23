import { IngredientType, RecipeFilterName } from "../types";

export interface RecipeBrief {
	recipeId: number;
	recipeName: string;
	recipeImage: string;
	scoreAvg: number;
	views: number;
}

export interface RecipeDetail extends RecipeBrief {
	description: string;
	cookingTime: string;
	kcal: string;
	servings: string;
	difficulty: string;
	ingredients: RecipeIngredient[];
	courses: RecipeStep[];
	isBookmarked: boolean;
	isCooked: boolean;
}

export interface RecipeIngredient {
	ingredientId: number;
	name: string;
	type: IngredientType;
	volume: string;
	transUnit: string;
	transVolume: string;
	isOwned: boolean;
}

export interface RecipeStep {
	step: string;
	explanation: string;
	courseImage: string;
}

export interface RecipeDeductedIngredient {
	name: string;
	volume: string;
	unit: string;
}

export interface RecipeCalculatedIngredient extends RecipeDeductedIngredient {
	recipeIngredientId: number;
	type: IngredientType;
}

export interface RecipeComment {
	commentId: number;
	nickname: string;
	heart: number;
	createDate: string;
	modifiedState: boolean;
	content: string;
	likedState: boolean;
}

export interface BookmarkedRecipe extends RecipeBrief {
	bookmarkId: number;
}

export interface RatedRecipe extends RecipeBrief {
	scoreID: number;
	myScore: number;
}

export interface MatchedRecipe extends RecipeBrief {
	percent: number;
}

export interface RecipeFilter {
	key: string;
	name: string;
	activeItem: string;
	fetchFilterMenu: string[];
}
