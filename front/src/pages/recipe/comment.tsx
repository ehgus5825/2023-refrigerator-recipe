import { useEffect, useRef, useState } from "react";

import {
	getCommentsByLike,
	getCommentsByDate,
	getMyComments,
	getHeartCommentIDs,
} from "@/api";
import { useIntersectionObserver } from "@/hooks";
import { CommentSortType, RecipeComment } from "@/types";

import RecipeCommentLayout from "@/components/layout/RecipeCommentLayout";
import SortBar from "@/components/recipe/Bar/SortBar";
import Comment from "@/components/recipe/Comment/Comment";
import CommentInputForm from "@/components/recipe/Comment/CommentInputForm";

import styles from "@/scss/pages.module.scss";

type RecipeCommentPageProps = {
	recipeID: number;
	recipeName: string;
};

export default function RecipeCommentPage({
	recipeID,
	recipeName,
}: RecipeCommentPageProps) {
	const [myCommentData, setMyCommentData] = useState<RecipeComment[]>([]);
	const [otherCommentData, setOtherCommentData] = useState<RecipeComment[]>([]);

	const [sortType, setSortType] = useState<CommentSortType>("좋아요순");
	const getCommentsBySortType = {
		좋아요순: getCommentsByLike,
		최신순: getCommentsByDate,
	};

	const [page, setPage] = useState(0);
	const [isDataLoaded, setIsDataLoaded] = useState(false);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

	const [modifyMode, setModifyMode] = useState(false);
	const [heartCommentIDs, setHeartCommentIDs] = useState<number[]>([]);

	const [comment, setComment] = useState("");
	const [commentID, setCommentID] = useState(0);
	const commentInputRef = useRef(null);

	useEffect(() => {
		(async () => {
			const myData = await getMyComments(recipeID);
			setMyCommentData(myData);

			// NOTE: localstorage에 저장할지 고민 중
			const heartData = await getHeartCommentIDs();
			setHeartCommentIDs(heartData);
		})();
	}, []);

	useEffect(() => {
		(async () => {
			setPage(0);
			const otherData = await getCommentsBySortType[sortType](recipeID, 0);
			setOtherCommentData(otherData);
			setIsScrollEnd(false);
			setIsDataLoaded(true);
		})();
	}, [sortType]);

	useEffect(() => {
		(async () => {
			if (page != 0 && !isScrollEnd) {
				const otherData = await getCommentsBySortType[sortType](recipeID, page);
				otherData.length !== 0
					? setOtherCommentData((prev) => [...prev, ...otherData])
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
				{[...myCommentData, ...otherCommentData]?.map((comment) => (
					<div key={comment.commentID}>
						<Comment
							comment={comment}
							isLiked={heartCommentIDs.includes(comment.commentID)}
							setComment={setComment}
							setCommentID={setCommentID}
							setModifyMode={setModifyMode}
							setMyCommentData={setMyCommentData}
							setOtherCommentData={setOtherCommentData}
							setHeartCommentIDs={setHeartCommentIDs}
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
	const recipeID = Number(context.query.recipeID);
	const recipeName = context.query.recipeName;

	return {
		props: {
			recipeID,
			recipeName,
		},
	};
}
