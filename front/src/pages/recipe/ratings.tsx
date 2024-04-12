import { useEffect, useState } from "react";
import { getRatedRecipes } from "@/api";

import { useIntersectionObserver } from "@/hooks";
import { RatedRecipe } from "@/types";

import router from "next/router";
import { EyeFill, StarFill } from "react-bootstrap-icons";
import styles from "./RecipeGallery.module.scss";

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

	const onRecipeClick = (recipeID: number) => {
		router.push(`/recipe/info?recipeID=${recipeID}`);
	};

	return (
		<AppNavLayout title="별점">
			<div className={styles.recipeGalleryContainer}>
				{ratedRecipeData.map((recipe) => (
					<div
						key={recipe.recipeId}
						className={styles.recipeContainer}
						onClick={() => onRecipeClick(recipe.recipeId)}
					>
						<img src="/images/no-image.jpg" 
						/>
						<div className={styles.recipeNameInfoContainer}>
							<span className={styles.recipeName}>{recipe.recipeName}</span>
							<div className={styles.recipeInfoContainer}>
								<div className={styles.recipeInfo}>
									<EyeFill width="18" height="18" />
									{recipe.views}
								</div>
								{recipe.scoreAvg !== 0 && (
									<div className={styles.recipeInfo}>
										<StarFill width="14" height="14" className={styles.starIcon} />
										{recipe.myScore.toFixed(1)}
									</div>
								)}
							</div>
						</div>
					</div>
				))}
			</div>
			{isDataLoaded && <div id="end-of-list"></div>}
		</AppNavLayout>
	);
}
