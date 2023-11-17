import instance from "@/api/interceptors";
import { IngredientBrief, Storage } from "@/types";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

export interface IngredientDataState {
	settings: {
		storage: Storage;
		isExpired: boolean;
		page: number;
	};
	data: IngredientBrief[];
	isDataEnd: boolean;
}

const initialState: IngredientDataState = {
	settings: {
		storage: "냉장",
		isExpired: false,
		page: 0,
	},
	data: [],
	isDataEnd: false,
};

export const getIngredientData = createAsyncThunk<
	IngredientBrief[],
	IngredientDataState["settings"]
>("get/ingredients", async (payload) => {
	const { page, storage, isExpired } = payload;
	const url = `/api/ingredients?page=${page}&storage=${storage}&deadline=${isExpired}`;
	const res = await instance.get(url);
	return res.data.data;
});

const ingredientDataSlice = createSlice({
	name: "ingredientData",
	initialState,
	reducers: {
		setInit: (state) => {
			state.settings.page = 0;
			state.isDataEnd = false;
			state.data = [];
		},
		setStorage: (state, action) => {
			state.settings.storage = action.payload;
		},
		setIsExpired: (state, action) => {
			state.settings.isExpired = action.payload;
		},
		setPage: (state, action) => {
			state.settings.page += action.payload;
		},
	},
	extraReducers: (builder) => {
		builder.addCase(getIngredientData.fulfilled, (state, action) => {
			action.payload.length != 0
				? state.data.push(...action.payload)
				: (state.isDataEnd = true);
		});
	},
});

export const { setInit, setStorage, setIsExpired, setPage } =
	ingredientDataSlice.actions;
export default ingredientDataSlice.reducer;
