import { FormEvent, MutableRefObject, useEffect } from "react";
import { X, XCircleFill } from "react-bootstrap-icons";
import { addComment, getMyComments, modifyComment } from "@/api";
import styles from "./Comment.module.scss";

type CommentInputFormProps = {
	recipeID: number;
	setMyCommentData: Function;
	comment: string;
	setComment: Function;
	modifyMode: boolean;
	setModifyMode: Function;
	commentID: number;
	commentInputRef: MutableRefObject<any>;
};

export default function CommentInputForm({
	recipeID,
	setMyCommentData,
	comment,
	setComment,
	modifyMode,
	setModifyMode,
	commentID,
	commentInputRef,
}: CommentInputFormProps) {
	useEffect(() => {
		commentInputRef.current.focus();
	}, [modifyMode]);

	const onCommentRegisterBtnClick = async () => {
		setComment("");
		modifyMode
			? await modifyComment(commentID, comment)
			: await addComment(recipeID, comment);
		const myCommentData = await getMyComments(recipeID);
		setMyCommentData(myCommentData);
		setModifyMode(false);
	};

	const onCommentSubmit = (e: FormEvent<HTMLFormElement>) => {
		e.preventDefault();
		onCommentRegisterBtnClick();
	};

	const onStatusCloseBtnClick = () => {
		setComment("");
		setModifyMode(false);
	};

	const onClearBtnClick = () => {
		setComment("");
		commentInputRef.current.focus();
	};

	return (
		<div className={styles.commentInputFormContainer}>
			{modifyMode && (
				<div className={styles.commentInputFormStatusBar}>
					<span className={styles.commentStatus}>댓글 수정 중 ...</span>
					<X onClick={onStatusCloseBtnClick} />
				</div>
			)}
			<div className={styles.commentInputContainer}>
				<form
					className="d-flex flex-grow-1"
					onSubmit={(e) => onCommentSubmit(e)}
				>
					<input
						className={styles.commentBox}
						placeholder="레시피에 댓글을 남겨보세요"
						onChange={(e) => setComment(e.target.value)}
						value={comment}
						ref={commentInputRef}
					/>
				</form>
				{comment && (
					<XCircleFill className={styles.clearIcon} onClick={onClearBtnClick} />
				)}
				<button
					disabled={!Boolean(comment)}
					onClick={onCommentRegisterBtnClick}
				>
					{modifyMode ? "수정" : "등록"}
				</button>
			</div>
		</div>
	);
}
