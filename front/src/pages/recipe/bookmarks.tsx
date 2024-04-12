import { useEffect, useState } from "react";
import { getBookmarks } from "@/api";

import { useIntersectionObserver } from "@/hooks";
import { BookmarkedRecipe } from "@/types";

import router from "next/router";
import { EyeFill, StarFill } from "react-bootstrap-icons";
import styles from "./RecipeGallery.module.scss";

import AppNavLayout from "@/components/layout/AppNavLayout";
import NoBookmarkResult from "@/components/global/NoResult/NoBookmarkResult";

export default function BookmarkedRecipeListPage() {
	const [bookmarkData, setBookmarkData] = useState<BookmarkedRecipe[]>([]);
	const [page, setPage] = useState(0);
	const [isDataLoaded, setIsDataLoaded] = useState(false);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

	const onRecipeClick = (recipeID: number) => {
		router.push(`/recipe/info?recipeID=${recipeID}`);
	};

	useEffect(() => {
		!isScrollEnd &&
			(async () => {
				const data = await getBookmarks(page);
				data.length !== 0
					? setBookmarkData((prev) => [...prev, ...data])
					: setIsScrollEnd(true);
				setIsDataLoaded(true);
			})();
	}, [page]);

	useIntersectionObserver(setPage, isDataLoaded);

	return (
		<AppNavLayout title="북마크">
			{bookmarkData.length !== 0 ? (
				<>
					<div className={styles.recipeGalleryContainer}>
						{bookmarkData.map((recipe) => (
							<div
								key={recipe.recipeId}
								className={styles.recipeContainer}
								onClick={() => onRecipeClick(recipe.recipeId)}
							>
								<img src={recipe.recipeImage}// "/images/no-image.jpg" 
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
												{recipe.scoreAvg.toFixed(1)}
											</div>
										)}
									</div>
								</div>
							</div>
						))}
					</div>
					{isDataLoaded && <div id="end-of-list"></div>}
				</>
			) : (
				<NoBookmarkResult />
			)}
		</AppNavLayout>
	);
}
