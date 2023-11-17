import { configureStore } from "@reduxjs/toolkit";
import ingredientData from "./refrigerator/ingredientData";
import ingredientInfo from "./refrigerator/ingredientInfo";
import ingredientSearch from "./refrigerator/ingredientSearch";

export const store = configureStore({
	reducer: {
		ingredientData,
		ingredientInfo,
		ingredientSearch,
	},
});
