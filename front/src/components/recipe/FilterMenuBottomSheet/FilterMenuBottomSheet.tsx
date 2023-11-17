import { CheckCircleFill } from "react-bootstrap-icons";
import BottomSheet from "@/components/global/BottomSheet/BottomSheet";
import styles from "./FilterMenuBottomSheet.module.scss";
import { RecipeFilter } from "@/types";

type FilterMenuBottomSheetProps = {
	show: boolean;
	onHide: Function;
	activeFilter: any;
	setFilters: Function;
};

export default function FilterMenuBottomSheet({
	show,
	onHide,
	activeFilter,
	setFilters,
}: FilterMenuBottomSheetProps) {
	const onFilterItemClick = (filterItem: string) => {
		setFilters((prevFilters: RecipeFilter[]) =>
			prevFilters.map((prevFilter) => {
				return prevFilter.name === activeFilter.name
					? { ...prevFilter, activeItem: filterItem }
					: prevFilter;
			}),
		);
		onHide();
	};

	return (
		<BottomSheet show={show} onHide={onHide}>
			<BottomSheet.Header title={activeFilter.name} onHide={() => onHide()} />
			<BottomSheet.Body>
				<div className={styles.filterMenu}>
					{activeFilter.menu.map((filterItem: string) => (
						<div key={filterItem} onClick={() => onFilterItemClick(filterItem)}>
							{activeFilter.activeItem === filterItem ? (
								<div>
									<span>{filterItem}</span>
									{activeFilter.activeItem === filterItem && (
										<CheckCircleFill />
									)}
								</div>
							) : (
								<span>{filterItem}</span>
							)}
						</div>
					))}
				</div>
			</BottomSheet.Body>
		</BottomSheet>
	);
}
