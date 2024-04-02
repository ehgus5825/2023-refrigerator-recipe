import instance from "@/api/interceptors";
import { IngredientDetail } from "@/types";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

export interface IngredientInfoState {
	ingredient: IngredientDetail;
}

const initialState: IngredientInfoState = {
	ingredient: {
		storage: "냉장",
		expirationDate: "",
		registrationDate: "",
		volume: 0,
		unit: "",
		ingredientID: 0,
		name: "",
		remainDays: "",
		image: "",
	},
};

// 식재료 단건 조회

export const getIngredientInfo = createAsyncThunk<IngredientDetail, number>(
	"get/ingredients/info",
	async (payload) => {
		const url = `/api/ingredients/${payload}/details`;
		const res = await instance.get(url);
		return res.data;
	},
);

// 식재료 수정

export const putIngredientInfo = createAsyncThunk<
	IngredientDetail,
	IngredientDetail
>("modify/ingredients/info", async (payload) => {
	const { ingredientID, storage, expirationDate, volume } = payload;
	const url = `/api/ingredients/${ingredientID}/modify`;
	const body = { storage, expirationDate, volume };
	await instance.put(url, body);
	return payload;
});

const ingredientInfoSlice = createSlice({
	name: "ingredientInfo",
	initialState,
	reducers: {},
	extraReducers: (builder) => {
		builder.addCase(getIngredientInfo.fulfilled, (state, action) => {
			state.ingredient = action.payload;
		});
		builder.addCase(putIngredientInfo.fulfilled, (state, action) => {
			state.ingredient = action.payload;
		});
	},
});

export default ingredientInfoSlice.reducer;
