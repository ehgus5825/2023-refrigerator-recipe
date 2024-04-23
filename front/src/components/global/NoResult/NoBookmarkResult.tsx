import styles from "./NoResult.module.scss";

export default function NoBookmarkResult() {
	return (
		<div className={styles.noResultContainer}>
			<div className={styles.noResultTitle}>아직 등록된 북마크가 없습니다!</div>
			북마크에서 레시피를 보시려면, <br />
			레시피에 들어가서 북마크 버튼을 클릭해주세요!
		</div>
	);
}
