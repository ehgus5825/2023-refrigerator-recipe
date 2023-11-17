import { useEffect, useState } from "react";
import { getRatedRecipes } from "@/api";

import { useIntersectionObserver } from "@/hooks";
import { RatedRecipe } from "@/types";

import AppNavLayout from "@/components/layout/AppNavLayout";
import RecipeGallery from "@/components/recipe/RecipeGallery/RecipeGallery";

export default function RatedRecipeListPage() {
	const [ratedRecipeData, setRatedRecipeData] = useState<RatedRecipe[]>([]);
	const [page, setPage] = useState(0);
	const [isDataLoaded, setIsDataLoaded] = useState(false);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

	useEffect(() => {
		!isScrollEnd &&
			(async () => {
				const data = await getRatedRecipes(page);
				data.length !== 0
					? setRatedRecipeData((prev) => [...prev, ...data])
					: setIsScrollEnd(true);
				setIsDataLoaded(true);
			})();
	}, [page]);

	useIntersectionObserver(setPage, isDataLoaded);

	return (
		<AppNavLayout title="별점">
			<RecipeGallery recipeData={ratedRecipeData} />
			{isDataLoaded && <div id="end-of-list"></div>}
		</AppNavLayout>
	);
}
