import { useEffect, useMemo } from "react";
import { useDispatch, useSelector } from "react-redux";
import { AnyAction, ThunkDispatch } from "@reduxjs/toolkit";

import { IngredientBrief } from "@/types";
import { toPhoneme } from "@/utils";
import { getIngredientSearchData, setKeyword } from "@/store";

import BackLayout from "@/components/layout/BackLayout";
import SearchBar from "@/components/global/SearchBar/SearchBar";
import IngredientGrid from "@/components/refrigerator/IngredientGrid/IngredientGrid";
import NoSearchResult from "@/components/global/NoResult/NoSearchResult";

import styles from "@/scss/pages.module.scss";

export default function SearchIngredientPage() {
	const { ingredientData, keyword } = useSelector(
		({ ingredientSearch }) => ingredientSearch,
	);

	const dispatch: ThunkDispatch<any, undefined, AnyAction> = useDispatch();
	const onKeywordChange = (payload: string) => dispatch(setKeyword(payload));

	useEffect(() => {
		if (ingredientData.length === 0) {
			dispatch(getIngredientSearchData());
		}
	}, []);

	const filteredIngredients = useMemo(() => {
		const keywordPhoneme = toPhoneme(keyword);
		return ingredientData.filter((ingredient: IngredientBrief) =>
			ingredient.phoneme?.includes(keywordPhoneme),
		);
	}, [keyword]);

	const onBackClick = () => {
		dispatch(setKeyword(""));
	};

	return (
		<BackLayout onBackClick={onBackClick}>
			<div className={styles.fixedContainer}>
				<SearchBar
					keyword={keyword}
					setKeyword={onKeywordChange}
					placeholder="궁금한 식재료를 검색해보세요!"
					focus
				/>
			</div>

			<div style={{ marginTop: "70px" }}>
				{keyword &&
					(filteredIngredients.length > 0 ? (
						<IngredientGrid ingredientData={filteredIngredients} />
					) : (
						<NoSearchResult />
					))}
			</div>
		</BackLayout>
	);
}
