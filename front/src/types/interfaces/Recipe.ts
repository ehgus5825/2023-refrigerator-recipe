import { IngredientType, RecipeFilterName } from "../types";

export interface RecipeBrief {
	recipeID: number;
	recipeName: string;
	image: string;
	scoreAvg: number;
	views: number;
}

export interface RecipeDetail extends RecipeBrief {
	description: string;
	cookingTime: string;
	kcal: string;
	servings: string;
	difficulty: string;
	isBookmarked: boolean;
	ingredients: RecipeIngredient[];
	courses: RecipeStep[];
	notNull: boolean;
}

export interface RecipeIngredient {
	ingredientID: number;
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
	image: string;
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
	commentID: number;
	nickname: string;
	content: string;
	heart: number;
	date: string;
	modifiedState: boolean;
	isMyComment: boolean;
}

export interface BookmarkedRecipe extends RecipeBrief {
	bookmarkID: number;
}

export interface RatedRecipe extends RecipeBrief {
	scoreID: number;
}

export interface MatchedRecipe extends RecipeBrief {
	match: number;
}

export interface RecipeFilter {
	key: string;
	name: string;
	activeItem: string;
	fetchFilterMenu: () => Promise<any>;
}
