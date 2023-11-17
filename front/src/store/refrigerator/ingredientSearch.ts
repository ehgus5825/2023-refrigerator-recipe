import instance from "@/api/interceptors";
import { IngredientBrief } from "@/types";
import { toPhoneme } from "@/utils";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

export interface IngredientSearchState {
	ingredientData: IngredientBrief[];
	keyword: string;
}

const initialState: IngredientSearchState = {
	ingredientData: [],
	keyword: "",
};

export const getIngredientSearchData = createAsyncThunk<IngredientBrief[]>(
	"get/ingredients/search",
	async () => {
		const url = `/api/ingredients/search`;
		const res = await instance.get(url);
		return res.data.data;
	},
);

const ingredientSearchSlice = createSlice({
	name: "ingredientSearch",
	initialState,
	reducers: {
		setKeyword: (state, action) => {
			state.keyword = action.payload;
		},
		removeIngredient: (state, action) => {
			state.ingredientData = state.ingredientData.filter(
				(ingredient) => ingredient.ingredientID !== action.payload,
			);
		},
		modifyExpirationDate: (state, action) => {
			const { ingredientID, remainDays } = action.payload;
			state.ingredientData = state.ingredientData.map((ingredient) =>
				ingredient.ingredientID !== ingredientID
					? ingredient
					: { ...ingredient, remainDays: remainDays },
			);
		},
	},
	extraReducers: (builder) => {
		builder.addCase(getIngredientSearchData.fulfilled, (state, action) => {
			const ingredientDataWithPhoneme = action.payload.map((ingredient) => ({
				...ingredient,
				phoneme: toPhoneme(ingredient.name),
			}));
			state.ingredientData = ingredientDataWithPhoneme;
		});
	},
});

export const { setKeyword, removeIngredient, modifyExpirationDate } =
	ingredientSearchSlice.actions;
export default ingredientSearchSlice.reducer;
