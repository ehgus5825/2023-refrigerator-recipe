import { useEffect, useState } from "react";
import Link from "next/link";

import { FILTERS } from "@/components/recipe/Bar/filters";
import { getRecipes, searchRecipe } from "@/api";
import { useIntersectionObserver } from "@/hooks";
import { RecipeBrief, RecipeFilter } from "@/types";

import AppNavLayout from "@/components/layout/AppNavLayout";
import SearchBar from "@/components/global/SearchBar/SearchBar";
import FilterBar from "@/components/recipe/Bar/FilterBar";
import RecipeList from "@/components/recipe/RecipeList/RecipeList";
import FilterMenuBottomSheet from "@/components/recipe/FilterMenuBottomSheet/FilterMenuBottomSheet";

import styles from "@/scss/pages.module.scss";
import FloatingBtn from "@/components/global/FloatingBtn/FloatingBtn";

export default function RecipeListPage() {
	const [recipeData, setRecipeData] = useState<RecipeBrief[]>([]);
	const [page, setPage] = useState(0);
	const [isDataLoaded, setIsDataLoaded] = useState(false);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

	const [filters, setFilters] = useState<RecipeFilter[]>(FILTERS);
	const [activeFilter, setActiveFilter] = useState({
		name: "",
		activeItem: "",
		menu: [],
	});
	const [isFilterMenuBottomSheetShow, setIsFilterMenuBottomSheetShow] =
		useState(false);

	const [query, setQuery] = useState({});

	useEffect(() => {
		const activeFilters = filters.filter(
			(filter) => filter.activeItem !== "전체",
		);
		const activeFilterParams = activeFilters.reduce((acc, item) => {
			acc[item.key] = item.activeItem;
			return acc;
		}, {} as { [key: string]: string });
		setQuery(activeFilterParams);
	}, [filters]);

	useEffect(() => {
		(async () => {
			setPage(0);
			const data = query ? await searchRecipe(0, query) : await getRecipes(0);
			setRecipeData(data);
			setIsScrollEnd(false);
			setIsDataLoaded(true);
		})();
	}, [query]);

	useEffect(() => {
		if (page !== 0 && !isScrollEnd) {
			(async () => {
				const data = query
					? await searchRecipe(page, query)
					: await getRecipes(page);
				data.length !== 0
					? setRecipeData((prev) => [...prev, ...data])
					: setIsScrollEnd(true);
				setIsDataLoaded(true);
			})();
		}
	}, [page]);

	useIntersectionObserver(setPage, isDataLoaded);

	return (
		<>
			<AppNavLayout title="레시피">
				<div className={styles.fixedContainer}>
					<Link
						href={`/recipe/search?query=`}
						style={{ textDecoration: "none" }}
					>
						<SearchBar placeholder="궁금한 레시피를 검색해보세요!" disabled />
					</Link>
					<FilterBar
						filters={filters}
						setActiveFilter={setActiveFilter}
						setIsFilterMenuBottomSheetShow={setIsFilterMenuBottomSheetShow}
					/>
				</div>

				<div style={{ marginTop: "90px" }}>
					<RecipeList recipeData={recipeData} />
					{isDataLoaded && <div id="end-of-list"></div>}
				</div>

				<Link href={`/recipe/recommendation`}>
					<FloatingBtn label="추천 레시피 보기" />
				</Link>
			</AppNavLayout>

			<FilterMenuBottomSheet
				show={isFilterMenuBottomSheetShow}
				onHide={() => setIsFilterMenuBottomSheetShow(false)}
				activeFilter={activeFilter}
				setFilters={setFilters}
			/>
		</>
	);
}
