import { Storage } from "@/types";

export interface IngredientBrief {
	ingredientID: number;
	name: string;
	remainDays: string;
	image: string;
	phoneme?: string;
	volume: number;
	unit: string;
}

export interface IngredientDetail extends IngredientBrief {
	storage: Storage;
	expirationDate: string;
	registrationDate: string;
}
