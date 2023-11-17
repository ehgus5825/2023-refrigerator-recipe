import { useEffect, useState } from "react";

import { getCommentsPreview, getRecipe } from "@/api";
import { RecipeComment, RecipeDetail } from "@/types";

import RecipeInfoLayout from "@/components/layout/RecipeInfoLayout";
import RecipeDescription from "@/components/recipe/RecipeInfo/RecipeDescription";
import RecipeIngredients from "@/components/recipe/RecipeInfo/RecipeIngredients";
import RecipeSteps from "@/components/recipe/RecipeInfo/RecipeSteps";
import RecipeCommentsPreview from "@/components/recipe/RecipeInfo/RecipeCommentsPreview";

import RecipeStepBottomSheet from "@/components/recipe/CookingBottomSheet/RecipeStepBottomSheet";
import IngredientDeductionBottomSheet from "@/components/recipe/CookingBottomSheet/IngredientDeductionBottomSheet";
import RatingBottomSheet from "@/components/recipe/CookingBottomSheet/RatingBottomSheet";

import styles from "@/scss/pages.module.scss";

type RecipeInfoPageProps = {
	recipeID: number;
};

export default function RecipeInfoPage({ recipeID }: RecipeInfoPageProps) {
	const [recipe, setRecipe] = useState<RecipeDetail | null>();
	const [isOwned, setIsOwned] = useState(false);

	const [commentData, setCommentData] = useState<RecipeComment[]>([]);
	const [commentNum, setCommentNum] = useState(0);

	const [isRecipeStepBottomSheetShow, setIsRecipeStepBottomSheetShow] =
		useState(false);
	const [
		isIngredientDeductionBottomSheetShow,
		setIsIngredientDeductionBottomSheetShow,
	] = useState(false);
	const [isRatingBottomSheetShow, setIsRatingBottomSheetShow] = useState(false);

	useEffect(() => {
		(async () => {
			const recipeData = await getRecipe(recipeID);
			setRecipe({
				...recipeData,
				courses: recipeData.courses.sort(
					(a: any, b: any) => parseInt(a.step) - parseInt(b.step),
				),
			});

			const data = await getCommentsPreview(recipeID);
			setCommentData(data.comments);
			setCommentNum(data.count);
		})();
	}, []);

	useEffect(() => {
		recipe &&
			setIsOwned(recipe.ingredients.some((ingredient) => ingredient.isOwned));
	}, [recipe]);

	const onRecipeStepNextShow = () => {
		isOwned
			? setIsIngredientDeductionBottomSheetShow(true)
			: setIsRatingBottomSheetShow(true);
	};

	return (
		<>
			{recipe && (
				<RecipeInfoLayout recipeName={recipe.recipeName}>
					<img src={recipe.image} className={styles.backgroundImg} />
					<div className={styles.recipeInfoContainer}>
						<RecipeDescription recipe={recipe} setRecipe={setRecipe} />
						<RecipeIngredients
							servings={recipe.servings}
							ingredients={recipe.ingredients}
						/>
						<RecipeSteps
							recipeSteps={recipe.courses}
							setIsRecipeStepBottomModalShow={setIsRecipeStepBottomSheetShow}
						/>
						<RecipeCommentsPreview
							recipeID={recipeID}
							recipeName={recipe.recipeName}
							commentData={commentData}
							commentNum={commentNum}
							setCommentData={setCommentData}
						/>
					</div>
				</RecipeInfoLayout>
			)}

			{recipe && (
				<RecipeStepBottomSheet
					show={isRecipeStepBottomSheetShow}
					onHide={() => setIsRecipeStepBottomSheetShow(false)}
					onNextShow={onRecipeStepNextShow}
					recipeName={recipe.recipeName}
					recipeSteps={recipe.courses}
				/>
			)}

			{recipe && isOwned && (
				<IngredientDeductionBottomSheet
					show={isIngredientDeductionBottomSheetShow}
					onHide={() => setIsIngredientDeductionBottomSheetShow(false)}
					onNextShow={() => setIsRatingBottomSheetShow(true)}
					ingredients={recipe.ingredients}
				/>
			)}

			<RatingBottomSheet
				show={isRatingBottomSheetShow}
				onHide={() => setIsRatingBottomSheetShow(false)}
				recipeID={recipeID}
			/>
		</>
	);
}

export async function getServerSideProps(context: any) {
	const recipeID = Number(context.query.recipeID);

	return {
		props: {
			recipeID,
		},
	};
}
