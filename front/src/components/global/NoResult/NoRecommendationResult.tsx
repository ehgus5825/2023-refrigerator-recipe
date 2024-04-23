import styles from "./NoResult.module.scss";

export default function NoRecommendationResult() {
	return (
		<div className={styles.noResultContainer}>
			<div className={styles.noResultTitle}>아직 추천 레시피가 없습니다!</div>
			레시피를 추천 받으시려면, <br />
			냉장고에 식재료가 등록되어 있어야해요
		</div>
	);
}
