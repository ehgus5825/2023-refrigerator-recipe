import BottomSheet from "@/components/global/BottomSheet/BottomSheet";
import styles from "./CookingBottomSheet.module.scss";
import { useEffect, useState } from "react";
import { deductIngredient } from "@/api";
import { CheckCircle, CheckCircleFill } from "react-bootstrap-icons";
import { RecipeIngredient } from "@/types";

type IngredientDeductionBottomSheetProps = {
	show: boolean;
	onHide: Function;
	onNextShow: Function;
	ingredients: RecipeIngredient[];
};

export default function IngredientDeductionBottomSheet({
	show,
	onHide,
	onNextShow,
	ingredients,
}: IngredientDeductionBottomSheetProps) {
	const [isDeductionAvailable, setIsDeductionAvailable] = useState<number[]>(
		[],
	);
	const [isDeductionSelected, setIsDeductionSelected] = useState<number[]>([]);

	useEffect(() => {
		const filteredData = ingredients
			.filter((ingredient) => ingredient.isOwned)
			.map((ingredient) => ingredient.ingredientID);
		setIsDeductionAvailable(filteredData);
		setIsDeductionSelected(filteredData);
	}, []);

	const onNextBtnClick = () => {
		const selectedIngredients = ingredients
			.filter((ingredient) =>
				isDeductionSelected.includes(ingredient.ingredientID),
			)
			.map(({ name, transVolume, transUnit }) => ({
				name: name,
				volume: transVolume,
				unit: transUnit,
			}));
		deductIngredient(selectedIngredients);
		onNextShow();
		onHide();
	};

	const onCancelBtnClick = () => {
		onNextShow();
		onHide();
	};

	const onIngredientClick = (ingredientID: number) => {
		if (isDeductionAvailable.includes(ingredientID)) {
			isDeductionSelected.includes(ingredientID)
				? setIsDeductionSelected((prev) =>
						prev.filter((id) => id !== ingredientID),
				  )
				: setIsDeductionSelected((prev) => [...prev, ingredientID]);
		}
	};

	return (
		<BottomSheet show={show} onHide={onHide} style={{ height: "100%" }}>
			<BottomSheet.Header title="식재료 차감하기" onHide={() => onHide()} />

			<BottomSheet.Body>
				<div className={styles.container}>
					<div className={styles.descriptionContainer}>
						<span>차감하지 않을 식재료가 있나요?</span>
						선택된 식재료는 자동으로 용량이 차감됩니다 <br />
						차감하지 않을 식재료를 선택 취소해주세요
					</div>

					<div className={styles.ingredientList}>
						{ingredients.map(({ ingredientID, ...ingredient }) => {
							if (ingredient.transVolume && ingredient.transUnit) {
								return (
									<div
										key={ingredientID}
										className={
											isDeductionSelected.includes(ingredientID)
												? styles.selected
												: undefined
										}
										onClick={() => onIngredientClick(ingredientID)}
									>
										{isDeductionSelected.includes(ingredientID) ? (
											<CheckCircleFill className={styles.icon} />
										) : (
											isDeductionAvailable.includes(ingredientID) && (
												<CheckCircle />
											)
										)}

										<span>{ingredient.name}</span>
										{`${ingredient.transVolume} ${ingredient.transUnit}`}
									</div>
								);
							}
						})}
					</div>
				</div>
			</BottomSheet.Body>

			<BottomSheet.Footer>
				<button onClick={onCancelBtnClick}>취소</button>
				<button onClick={onNextBtnClick}>확인</button>
			</BottomSheet.Footer>
		</BottomSheet>
	);
}
