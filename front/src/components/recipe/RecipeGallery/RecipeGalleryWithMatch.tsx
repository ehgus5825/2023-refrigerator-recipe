import router from "next/router";
import { MatchedRecipe } from "@/types";
import styles from "./RecipeGallery.module.scss";
type RecipeGalleryProps = {
	recipeData: MatchedRecipe[];
};

export default function RecipeGalleryWithMatch({
	recipeData,
}: RecipeGalleryProps) {
	const onRecipeClick = (recipeID: number) => {
		router.push(`/recipe/info?recipeID=${recipeID}`);
	};

	return (
		<div className={styles.recipeGalleryContainer}>
			{recipeData.map((recipe) => (
				<div
					key={recipe.recipeId}
					className={styles.recipeContainer}
					onClick={() => onRecipeClick(recipe.recipeId)}
				>
					<img src="/images/no-image.jpg" 
					/>

					<div className={styles.recipeNameInfoContainer}>
						<div className={styles.recipeNameScoreCol}>
							<span className={styles.recipeName}>{recipe.recipeName}</span>
							<div className={styles.recipeInfo}>
								{recipe.scoreAvg !== 0
									? `★ ${recipe.scoreAvg.toFixed(1)}`
									: ` `}
							</div>
						</div>
						<div className={styles.recipeMatch}>{recipe.percent} %</div>
					</div>
				</div>
			))}
		</div>
	);
}
