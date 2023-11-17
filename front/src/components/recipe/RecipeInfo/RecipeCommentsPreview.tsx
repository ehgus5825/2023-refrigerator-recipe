import { useEffect, useState } from "react";
import styles from "./RecipeInfo.module.scss";
import { RecipeComment } from "@/types";
import { getHeartCommentIDs } from "@/api";
import Comment from "../Comment/Comment";
import Link from "next/link";

type RecipeCommentsPreviewProps = {
	recipeID: number;
	recipeName: string;
	commentData: RecipeComment[];
	commentNum: number;
	setCommentData: Function;
};

export default function RecipeCommentsPreview({
	recipeID,
	recipeName,
	commentData,
	commentNum,
	setCommentData,
}: RecipeCommentsPreviewProps) {
	const [heartCommentIDs, setHeartCommentIDs] = useState<number[]>([]);

	useEffect(() => {
		(async () => {
			const heartData = await getHeartCommentIDs();
			setHeartCommentIDs(heartData);
		})();
	}, []);

	return (
		<div className={styles.recipeInfoContainer}>
			<div className={styles.recipeInfoHeader}>
				<div>댓글</div>
				<span>{commentNum}</span>
				<Link
					href={`/recipe/comment?recipeID=${recipeID}&recipeName=${recipeName}`}
				>
					<button>댓글 전체 보기</button>
				</Link>
			</div>

			<div className={styles.recipeCommentList}>
				{commentData?.map((comment) => (
					<div key={comment.commentID}>
						<Comment
							comment={comment}
							isLiked={heartCommentIDs.includes(comment.commentID)}
							setOtherCommentData={setCommentData}
							setHeartCommentIDs={setHeartCommentIDs}
							preview
						/>
					</div>
				))}
			</div>
		</div>
	);
}
