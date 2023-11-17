import router from "next/router";
import styles from "./ScrollContent.module.scss";

export default function ScrollContent(props: any) {
	const onContentClick = (recipeId: number) => {
		router.push(`/recipe/info?recipeID=${recipeId}`);
	};
	// const onScoreClick = (recipeId: number) => {
	// 	router.push(`/recipe/info?recipeID=${recipeId}`);
	// };
	const onContentPlusClick = (content: string) => {
		router.push(`/recipe/${content}`);
	};
	return props.content === "ratings" ? (
		<div className={styles.scroll}>
			{props.starPreview?.map((i: any) => (
				<div className={styles.content} key={i.scoreId}>
					<button
						className={styles.recipeBtn}
						onClick={() => onContentClick(i.recipeId)}
					>
						<img className={styles.recipeImage} src={i.recipeImage} />
					</button>
					<div className={styles.recipeNameWrapper}>
						<div className={styles.recipeName}>{i.recipeName}</div>
					</div>
				</div>
			))}
			<div className={styles.content}>
				<button
					className={styles.recipeBtn__plus}
					onClick={() => {
						onContentPlusClick("ratings");
					}}
				>
					<span>
						<span className={styles.plus}>+</span>
						<span>더보기</span>
					</span>
				</button>
			</div>
		</div>
	) : (
		<div className={styles.scroll}>
			{props.bookPreview?.map((i: any) => (
				<div className={styles.content} key={i.recipeId}>
					<button
						className={styles.recipeBtn}
						onClick={() => onContentClick(i.recipeId)}
					>
						<img
							className={styles.recipeImage}
							src={i.recipeImage}
							alt={i.recipeName}
						/>
					</button>
					<div className={styles.recipeNameWrapper}>
						<div className={styles.recipeName}>{i.recipeName}</div>
					</div>
				</div>
			))}
			<div className={styles.content}>
				<button
					className={styles.recipeBtn__plus}
					onClick={() => {
						onContentPlusClick("bookmarks");
					}}
				>
					<span>
						<span className={styles.plus}>+</span>
						<span>더보기</span>
					</span>
				</button>
			</div>
		</div>
	);
}
