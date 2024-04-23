import { useEffect, useRef, useState } from "react";

import { getComments, getMyComments } from "@/api";
import { useIntersectionObserver } from "@/hooks";
import { CommentSortType, RecipeComment } from "@/types";

import RecipeCommentLayout from "@/components/layout/RecipeCommentLayout";
import SortBar from "@/components/recipe/Bar/SortBar";
import Comment from "@/components/recipe/Comment/Comment";
import CommentInputForm from "@/components/recipe/Comment/CommentInputForm";

import styles from "@/scss/pages.module.scss";

type RecipeCommentPageProps = {
	recipeID: string;
	recipeName: string;
};

export default function RecipeCommentPage({
	recipeID,
	recipeName,
}: RecipeCommentPageProps) {
	const [myCommentData, setMyCommentData] = useState<RecipeComment[]>([]);
	const [otherCommentData, setOtherCommentData] = useState<RecipeComment[]>([]);

	const [sortType, setSortType] = useState<CommentSortType>("HEART");

	const [page, setPage] = useState(0);
	const [isDataLoaded, setIsDataLoaded] = useState(false);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

	const [modifyMode, setModifyMode] = useState(false);

	const [comment, setComment] = useState("");
	const [commentID, setCommentID] = useState(0);
	const commentInputRef = useRef(null);

	useEffect(() => {
		(async () => {
			setPage(0);

			const mydata = await getMyComments(recipeID);
			const data = await getComments(recipeID, 0, sortType);

			console.log(mydata);

			setMyCommentData(mydata);
			setOtherCommentData(data);
			setIsScrollEnd(false);
			setIsDataLoaded(true);
		})();
	}, [sortType]);

	useEffect(() => {
		(async () => {
			if (page != 0 && !isScrollEnd) {

				const data = await getComments(recipeID, page, sortType);

				data.length !== 0
					? setOtherCommentData((prev) => [...prev, ...data])
					: setIsScrollEnd(true);
			}
		})();
	}, [page]);

	useIntersectionObserver(setPage, isDataLoaded);

	return (
		<RecipeCommentLayout title={recipeName}>
			<div className={styles.fixedContainer}>
				<SortBar sortType={sortType} setSortType={setSortType} />
			</div>
			<div
				className={styles.commentListContainer}
				style={{ marginTop: "46px" }}
			>
				{[...myCommentData]?.map((comment) => (
					<div key={comment.commentId}>
						<Comment
							comment={comment}
							setComment={setComment}
							setCommentID={setCommentID}
							setModifyMode={setModifyMode}
							setMyCommentData={setMyCommentData}
						/>
					</div>
				))}
				{[...otherCommentData]?.map((comment) => (
					<div key={comment.commentId}>
						<Comment
							comment={comment}
							setComment={setComment}
							setCommentID={setCommentID}
							setModifyMode={setModifyMode}
							setOtherCommentData={setOtherCommentData}
						/>
					</div>
				))}
				{isDataLoaded && <div id="end-of-list" />}
			</div>
			<CommentInputForm
				recipeID={recipeID}
				setMyCommentData={setMyCommentData}
				comment={comment}
				setComment={setComment}
				modifyMode={modifyMode}
				setModifyMode={setModifyMode}
				commentID={commentID}
				commentInputRef={commentInputRef}
			/>
		</RecipeCommentLayout>
	);
}

export async function getServerSideProps(context: any) {
	const recipeID = context.query.recipeID;
	const recipeName = context.query.recipeName;

	return {
		props: {
			recipeID,
			recipeName,
		},
	};
}
