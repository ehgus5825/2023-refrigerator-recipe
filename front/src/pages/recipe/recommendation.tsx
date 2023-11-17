import { useEffect, useState } from "react";
import { getRecommendedRecipes } from "@/api";

import { MatchedRecipe } from "@/types";

import BackLayout from "@/components/layout/BackLayout";
import RecipeGalleryWithMatch from "@/components/recipe/RecipeGallery/RecipeGalleryWithMatch";
import NoRecommendationResult from "@/components/global/NoResult/NoRecommendationResult";
import BackNavLayout from "@/components/layout/BackNavLayout";

export default function RecommendedRecipeListPage() {
	const [recommendedRecipeData, setRecommendedRecipeData] = useState<
		MatchedRecipe[]
	>([]);

	useEffect(() => {
		(async () => {
			const data = await getRecommendedRecipes();
			setRecommendedRecipeData(data);
		})();
	}, []);

	return (
		<BackNavLayout title="추천 레시피" activeTab="레시피">
			{recommendedRecipeData.length !== 0 ? (
				<RecipeGalleryWithMatch recipeData={recommendedRecipeData} />
			) : (
				<NoRecommendationResult />
			)}
		</BackNavLayout>
	);
}
