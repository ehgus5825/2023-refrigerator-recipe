import router from "next/router";
import { EyeFill } from "react-bootstrap-icons";
import { RecipeBrief } from "@/types";
import Stars from "../Stars/Stars";
import styles from "../RecipeList/RecipeList.module.scss";

type RecipeListProps = {
	recipeData: RecipeBrief[];
};

export default function RecipeGrid({ recipeData }: RecipeListProps) {
	const onRecipeClick = (recipeID: number) => {
		router.push(`/recipe/info?recipeID=${recipeID}`);
	};

	return (
		<div className={styles.recipelistContainer}>
			{recipeData.map((recipe) => (
				<div
					key={recipe.recipeId}
					className={styles.recipeContainer}
					onClick={() => onRecipeClick(recipe.recipeId)}
				>
					<img src={recipe.recipeImage} className={styles.recipeImage} />
					<div className={styles.recipeInfoContainer}>
						<div className={styles.recipeName}>{recipe.recipeName}</div>
						<div className="d-flex gap-3">
							{recipe.scoreAvg != 0 && (
								<Stars id={recipe.recipeId} score={recipe.scoreAvg} label />
							)}
							<div className={styles.recipeInfo}>
								<EyeFill width="16" height="16" />
								{recipe.views}
							</div>
						</div>
					</div>
				</div>
			))}
		</div>
	);
}
