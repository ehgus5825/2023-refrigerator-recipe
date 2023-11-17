import { Bookmark, BookmarkStarFill } from "react-bootstrap-icons";
import { addBookmark, removeBookmark } from "@/api";
import { RecipeDetail } from "@/types";
import styles from "./RecipeInfo.module.scss";

type BookmarkProps = {
	recipeID: number;
	isBookmarked: boolean;
	setRecipe: Function;
};

export const BookmarkIcon = ({
	recipeID,
	isBookmarked,
	setRecipe,
}: BookmarkProps) => {
	const onAddBookmarkClick = async () => {
		setRecipe((prev: RecipeDetail) => ({
			...prev,
			isBookmarked: true,
		}));
		await addBookmark(recipeID);
	};

	const onRemoveBookmarkClick = async () => {
		setRecipe((prev: RecipeDetail) => ({
			...prev,
			isBookmarked: false,
		}));
		await removeBookmark(recipeID);
	};

	return (
		<div>
			{isBookmarked ? (
				<BookmarkStarFill
					className={styles.bookmarkedIcon}
					onClick={onRemoveBookmarkClick}
				/>
			) : (
				<Bookmark
					className={styles.bookmarkIcon}
					onClick={onAddBookmarkClick}
				/>
			)}
		</div>
	);
};
