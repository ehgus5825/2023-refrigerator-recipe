import { RecipeFilter } from "@/types";
import styles from "./Bar.module.scss";

type FilterBarProps = {
	filters: RecipeFilter[];
	setActiveFilter: Function;
	setIsFilterMenuBottomSheetShow: Function;
};

export default function FilterBar({
	filters,
	setActiveFilter,
	setIsFilterMenuBottomSheetShow,
}: FilterBarProps) {
	const onFilterBtnClick = async (filter: RecipeFilter) => {
		const data = await filter.fetchFilterMenu();
		setActiveFilter({
			name: filter.name,
			activeItem: filter.activeItem,
			menu: ["전체", ...data],
		});
		setIsFilterMenuBottomSheetShow(true);
	};

	return (
		<div className={styles.filterbarContainer}>
			{filters.map((filter) => (
				<div key={filter.key} onClick={() => onFilterBtnClick(filter)}>
					{filter.activeItem === "전체" ? (
						<button>{filter.name}</button>
					) : (
						<button className={styles.selected}>{filter.activeItem}</button>
					)}
				</div>
			))}
		</div>
	);
}
