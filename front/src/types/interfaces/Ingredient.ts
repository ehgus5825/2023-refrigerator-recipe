import { Storage } from "@/types";

export interface IngredientBrief {
	ingredientID: number;
	name: string;
	remainDays: string;
	image: string;
	phoneme?: string;
}

export interface IngredientDetail extends IngredientBrief {
	storage: Storage;
	expirationDate: string;
	registrationDate: string;
	volume: number;
	unit: string;
}
