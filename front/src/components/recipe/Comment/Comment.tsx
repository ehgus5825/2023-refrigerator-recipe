import { Heart, HeartFill } from "react-bootstrap-icons";
import { RecipeComment } from "@/types";
import { deleteComment, likeComment, unlikeComment } from "@/api/comment";
import styles from "./Comment.module.scss";
import { useState } from "react";

type CommentProps = {
	comment: RecipeComment;
	preview?: boolean;
	setComment?: Function;
	setCommentID?: Function;
	setModifyMode?: Function;
	setMyCommentData?: Function;
	setOtherCommentData?: Function;
};

export default function Comment({
	comment,
	preview,
	setComment,
	setCommentID,
	setModifyMode,
	setMyCommentData,
	setOtherCommentData,
}: CommentProps) {
	const {
		commentId,
		nickname,
		content,
		createDate,
		modifiedState,
		heart,
		likedState,
	} = comment;
	const [isLikedState, setIsLikedState] = useState(likedState);
	const [peopleId, setPeopleId] = useState("");

	const onModifyTextClick = () => {
		setModifyMode && setModifyMode(true);
		setCommentID && setCommentID(commentId);
		setComment && setComment(content);
		// TODO: input focus 하기
	};

	const onDeleteTextClick = async () => {
		// TODO: 삭제 확인 모달
		setMyCommentData &&
			setMyCommentData((prevCommentData: RecipeComment[]) =>
				prevCommentData.filter(
					(commentItem) => commentId !== commentItem.commentId,
				),
			);
		await deleteComment(commentId);
	};

	const onLikeCommentClick = async () => {

		setIsLikedState(!isLikedState);

		setMyCommentData &&
			setMyCommentData((prevCommentData: RecipeComment[]) =>
				prevCommentData.map((commentItem) =>
					commentItem.commentId === commentId
						? { ...commentItem, heart: heart + 1 }
						: commentItem,
				),
			);
		setOtherCommentData &&
			setOtherCommentData((prevCommentData: RecipeComment[]) =>
				prevCommentData.map((commentItem) =>
					commentItem.commentId === commentId
						? { ...commentItem, heart: heart + 1 }
						: commentItem,
				),
			);
		!preview && await likeComment(comment.commentId);
	};

	const onUnlikeCommentClick = async () => {

		setIsLikedState(!isLikedState);

		setMyCommentData &&
			setMyCommentData((prevCommentData: RecipeComment[]) =>
				prevCommentData.map((commentItem) =>
					commentItem.commentId === commentId
						? { ...commentItem, heart: heart - 1 }
						: commentItem,
				),
			);
		setOtherCommentData &&
			setOtherCommentData((prevCommentData: RecipeComment[]) =>
				prevCommentData.map((commentItem) =>
					commentItem.commentId === commentId
						? { ...commentItem, heart: heart - 1 }
						: commentItem,
				),
			);
		!preview && await unlikeComment(comment.commentId);
	};

	return (
		<div className={styles.commentContainer}>
			<div className={styles.commentHeader}>
				<div className={styles.commentNickname}>{nickname}</div>
				{!preview && setMyCommentData && (
					<div className={styles.commentMenu}>
						<div onClick={onModifyTextClick}>수정</div>
						<div onClick={onDeleteTextClick}>삭제</div>
					</div>
				)}
			</div>

			<div className={styles.commentContent}>{content}</div>

			<div className={styles.commentInfo}>
				<div>{createDate}</div>
				{modifiedState && <div>(수정됨)</div>}
				<div> </div>
				{isLikedState ? (
					<HeartFill
						className={styles.commentIcon}
						onClick={onUnlikeCommentClick}
					/>
				) : (
					<Heart className={styles.commentIcon} onClick={onLikeCommentClick} />
				)}
				<div>{comment.heart}</div>
			</div>
		</div>
	);
}
