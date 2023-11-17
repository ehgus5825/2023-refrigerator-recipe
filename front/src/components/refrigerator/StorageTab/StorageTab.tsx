import styles from "./StorageTab.module.scss";

const TAB_ITEMS = ["냉장", "냉동", "실온", "조미료"];

type TabProps = {
	storage: string;
	setStorage: Function;
	size?: "sm" | "lg";
};

export default function StorageTab({
	storage,
	setStorage,
	size = "lg",
}: TabProps) {
	return (
		<div className={styles[`tabContainer_${size}`]}>
			{TAB_ITEMS.map((item) => (
				<button
					key={item}
					className={
						storage === item ? styles.tabItem_selected : styles.tabItem
					}
					onClick={() => setStorage && setStorage(item)}
				>
					{item}
				</button>
			))}
		</div>
	);
}
