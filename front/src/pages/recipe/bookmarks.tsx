import { useEffect, useState } from "react";
import { getBookmarks } from "@/api";

import { useIntersectionObserver } from "@/hooks";
import { BookmarkedRecipe } from "@/types";

import AppNavLayout from "@/components/layout/AppNavLayout";
import RecipeGallery from "@/components/recipe/RecipeGallery/RecipeGallery";

export default function BookmarkedRecipeListPage() {
	const [bookmarkData, setBookmarkData] = useState<BookmarkedRecipe[]>([]);
	const [page, setPage] = useState(0);
	const [isDataLoaded, setIsDataLoaded] = useState(false);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

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
			<RecipeGallery recipeData={bookmarkData} />
			{isDataLoaded && <div id="end-of-list"></div>}
		</AppNavLayout>
	);
}
