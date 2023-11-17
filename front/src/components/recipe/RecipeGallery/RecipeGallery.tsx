import router from "next/router";
import { EyeFill, StarFill } from "react-bootstrap-icons";
import { RecipeBrief, BookmarkedRecipe } from "@/types";
import styles from "./RecipeGallery.module.scss";

type RecipeGalleryProps = {
	recipeData: RecipeBrief[] | BookmarkedRecipe[];
};

export default function RecipeGallery({ recipeData }: RecipeGalleryProps) {
	const onRecipeClick = (recipeID: number) => {
		router.push(`/recipe/info?recipeID=${recipeID}`);
	};

	return (
		<div className={styles.recipeGalleryContainer}>
			{recipeData.map((recipe) => (
				<div
					key={recipe.recipeID}
					className={styles.recipeContainer}
					onClick={() => onRecipeClick(recipe.recipeID)}
				>
					<img src={recipe.image} />
					<div className={styles.recipeNameInfoContainer}>
						<span className={styles.recipeName}>{recipe.recipeName}</span>
						<div className={styles.recipeInfoContainer}>
							<div className={styles.recipeInfo}>
								<EyeFill width="18" height="18" />
								{recipe.views}
							</div>
							{recipe.scoreAvg != 0 && (
								<div className={styles.recipeInfo}>
									<StarFill width="14" height="14" />
									{recipe.scoreAvg}
								</div>
							)}
						</div>
					</div>
				</div>
			))}
		</div>
	);
}
