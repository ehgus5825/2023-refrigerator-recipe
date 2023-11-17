import styles from "./Bar.module.scss";

type SortBarProps = {
	sortType: "좋아요순" | "최신순";
	setSortType: Function;
};

export default function SortBar({ sortType, setSortType }: SortBarProps) {
	const onSortBtnClick = () => {
		setSortType(sortType === "좋아요순" ? "최신순" : "좋아요순");
	};

	return (
		<div className={styles.sortbarContainer}>
			{["좋아요순", "최신순"].map((type) => (
				<button
					key={type}
					className={sortType === type ? styles.selected : undefined}
					onClick={onSortBtnClick}
				>
					{type}
				</button>
			))}
		</div>
	);
}
