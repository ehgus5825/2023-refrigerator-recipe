import { useEffect, useState } from "react";
import styles from "./RecipeInfo.module.scss";
import { RecipeComment } from "@/types";
import Comment from "../Comment/Comment";
import Link from "next/link";

type RecipeCommentsPreviewProps = {
	recipeID: number;
	recipeName: string;
	commentData: RecipeComment[];
	commentNum: number;
};

export default function RecipeCommentsPreview({
	recipeID,
	recipeName,
	commentData,
	commentNum,
}: RecipeCommentsPreviewProps) {

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
					<div key={comment.commentId}>
						<Comment
							comment={comment}
							preview
						/>
					</div>
				))}
			</div>
		</div>
	);
}
