import { CheckCircle, CheckCircleFill, CheckLg } from "react-bootstrap-icons";
import { RecipeIngredient } from "@/types";
import styles from "./RecipeInfo.module.scss";

type RecipeIngredientsProps = {
	servings: string;
	ingredients: RecipeIngredient[];
};

export default function RecipeIngredients({
	servings,
	ingredients,
}: RecipeIngredientsProps) {
	const TYPE = ["주재료", "부재료", "양념"];

	const typedIngredients = TYPE.reduce((acc, type) => {
		const typeIngredients = ingredients.filter(
			(ingredient) => ingredient.type === type,
		);
		if (typeIngredients.length !== 0) {
			acc.set(type, typeIngredients);
		}
		return acc;
	}, new Map());

	return (
		<div className={styles.recipeInfoContainer}>
			<div className={styles.recipeInfoHeader}>
				<div>레시피 재료</div>
				<span />
				<button disabled>{servings}</button>
			</div>

			<div className={styles.recipeIngredientTable}>
				{Array.from(typedIngredients).map(([type, typeIngredients]) => (
					<div key={type}>
						<span className={styles.recipeIngredientType}>{type}</span>
						<div>
							{typeIngredients.map((ingredient: RecipeIngredient) => (
								<div
									key={ingredient.ingredientID}
									className={
										ingredient.isOwned
											? styles.recipeIngredientInfoSelected
											: styles.recipeIngredientInfo
									}
								>
									<div>{ingredient.name}</div>

									{ingredient.isOwned && (
										<CheckCircleFill className={styles.recipeIngredientIcon} />
									)}

									<span />

									<div className={styles.recipeIngredientVolume}>
										{ingredient.volume}
									</div>
								</div>
							))}
						</div>
					</div>
				))}
			</div>
		</div>
	);
}
