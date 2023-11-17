import { Heart, HeartFill } from "react-bootstrap-icons";
import { RecipeComment } from "@/types";
import { deleteComment, likeComment, unlikeComment } from "@/api";
import styles from "./Comment.module.scss";

type CommentProps = {
	comment: RecipeComment;
	isLiked: boolean;
	preview?: boolean;
	setComment?: Function;
	setCommentID?: Function;
	setModifyMode?: Function;
	setMyCommentData?: Function;
	setOtherCommentData?: Function;
	setHeartCommentIDs?: Function;
};

export default function Comment({
	comment,
	isLiked,
	preview,
	setComment,
	setCommentID,
	setModifyMode,
	setMyCommentData,
	setOtherCommentData,
	setHeartCommentIDs,
}: CommentProps) {
	const {
		commentID,
		nickname,
		content,
		date,
		modifiedState,
		heart,
		isMyComment,
	} = comment;

	const onModifyTextClick = () => {
		setModifyMode && setModifyMode(true);
		setCommentID && setCommentID(commentID);
		setComment && setComment(content);
		// TODO: input focus 하기
	};
	const onDeleteTextClick = async () => {
		// TODO: 삭제 확인 모달
		setMyCommentData &&
			setMyCommentData((prevCommentData: RecipeComment[]) =>
				prevCommentData.filter(
					(commentItem) => commentID !== commentItem.commentID,
				),
			);
		await deleteComment(commentID);
	};

	const onLikeCommentClick = async () => {
		setHeartCommentIDs &&
			setHeartCommentIDs((prevIDs: number[]) => [...prevIDs, commentID]);
		setMyCommentData &&
			setMyCommentData((prevCommentData: RecipeComment[]) =>
				prevCommentData.map((commentItem) =>
					commentItem.commentID === commentID
						? { ...commentItem, heart: heart + 1 }
						: commentItem,
				),
			);
		setOtherCommentData &&
			setOtherCommentData((prevCommentData: RecipeComment[]) =>
				prevCommentData.map((commentItem) =>
					commentItem.commentID === commentID
						? { ...commentItem, heart: heart + 1 }
						: commentItem,
				),
			);
		await likeComment(comment.commentID);
	};
	const onUnlikeCommentClick = async () => {
		setHeartCommentIDs &&
			setHeartCommentIDs((prevIDs: number[]) =>
				prevIDs.filter((id) => id !== commentID),
			);
		setMyCommentData &&
			setMyCommentData((prevCommentData: RecipeComment[]) =>
				prevCommentData.map((commentItem) =>
					commentItem.commentID === commentID
						? { ...commentItem, heart: heart - 1 }
						: commentItem,
				),
			);
		setOtherCommentData &&
			setOtherCommentData((prevCommentData: RecipeComment[]) =>
				prevCommentData.map((commentItem) =>
					commentItem.commentID === commentID
						? { ...commentItem, heart: heart - 1 }
						: commentItem,
				),
			);
		await unlikeComment(comment.commentID);
	};

	return (
		<div className={styles.commentContainer}>
			<div className={styles.commentHeader}>
				<div className={styles.commentNickname}>{nickname}</div>
				{isMyComment && !preview && (
					<div className={styles.commentMenu}>
						<div onClick={onModifyTextClick}>수정</div>
						<div onClick={onDeleteTextClick}>삭제</div>
					</div>
				)}
			</div>

			<div className={styles.commentContent}>{content}</div>

			<div className={styles.commentInfo}>
				<div>{date}</div>
				{modifiedState && <div>(수정됨)</div>}
				<div> </div>
				{isLiked ? (
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
