import { RecipeDetail } from "@/types";
import Stars from "../Stars/Stars";
import styles from "./RecipeInfo.module.scss";
import { BookmarkIcon } from "./BookmarkIcon";
import { useEffect, useState } from "react";
import { getBookmarkIDs } from "@/api";

type RecipeDescriptionProps = {
	recipe: RecipeDetail;
	setRecipe: Function;
};

export default function RecipeDescription({
	recipe,
	setRecipe,
}: RecipeDescriptionProps) {
	const recipeExtraInfo = [
		["난이도", recipe.difficulty],
		["조리시간", recipe.cookingTime],
		recipe.kcal !== "0kcal" ? ["칼로리", recipe.kcal] : null,
		recipe.scoreAvg ? ["별점", recipe.scoreAvg] : null,
	] as [string, string | number][];

	// TODO: 별점 세로 가운데 정렬
	// HACK: 별점 NaN 에러 - Error: <rect> attribute width: Expected length, "NaN".

	return (
		<div className={styles.recipeInfoTopContainer}>
			<div className={styles.recipeNameInbun}>
				<div className={styles.recipeName}>{recipe.recipeName} </div>
				<div className={styles.recipeInbun}>{recipe.servings}</div>
				<span />
				<BookmarkIcon
					recipeID={recipe.recipeID}
					isBookmarked={recipe.isBookmarked}
					setRecipe={setRecipe}
				/>
			</div>

			<div className={styles.recipeDescription}>{recipe.description}</div>

			<div className={styles.recipeExtraInfo}>
				{recipeExtraInfo
					.filter((info) => info)
					.map(([key, value]) => (
						<div key={key}>
							<div className={styles.recipeInfoKey}>{key}</div>
							{key === "별점" && (
								<Stars id={recipe.recipeID} score={Number(value)} />
							)}
							<div className={styles.recipeInfoValue}>
								{key === "별점" && value ? Number(value).toFixed(1) : value}
							</div>
						</div>
					))}
			</div>
		</div>
	);
}
