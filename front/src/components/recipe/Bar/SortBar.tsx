import styles from "./Bar.module.scss";

type SortBarProps = {
	sortType: "HEART" | "DATE";
	setSortType: Function;
};

export default function SortBar({ sortType, setSortType }: SortBarProps) {
	const onSortBtnClick = () => {
		setSortType(sortType === "HEART" ? "DATE" : "HEART");
	};

	return (
		<div className={styles.sortbarContainer}>
			{["HEART", "DATE"].map((type) => (
				<button
					key={type}
					className={sortType === type ? styles.selected : undefined}
					onClick={onSortBtnClick}
				>
					 {type === 'HEART' ? '좋아요' : '최신순'}
				</button>
			))}
		</div>
	);
}
