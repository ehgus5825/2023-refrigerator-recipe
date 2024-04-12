import Link from "next/link";
import { EyeFill } from "react-bootstrap-icons";
import { RecipeBrief } from "@/types";
import Stars from "../Stars/Stars";
import styles from "./RecipeList.module.scss";

type RecipeListProps = {
	recipeData: RecipeBrief[];
};

export default function RecipeList({ recipeData }: RecipeListProps) {
	return (
		<div className={styles.recipelistContainer}>
			{recipeData.map((recipe) => (
				<Link
					key={recipe.recipeId}
					style={{ textDecoration: "none" }}
					href={`/recipe/info?recipeID=${recipe.recipeId}`}
				>
					<div className={styles.recipeContainer}>
						<img src={recipe.recipeImage} // "/images/no-image.jpg" 
							 className={styles.recipeImage} />
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
				</Link>
			))}
		</div>
	);
}
