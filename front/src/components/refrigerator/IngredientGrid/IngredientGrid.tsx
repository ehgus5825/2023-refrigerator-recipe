import { useDispatch, useSelector } from "react-redux";
import { getIngredientInfo } from "@/store/refrigerator";
import { AnyAction, ThunkDispatch } from "@reduxjs/toolkit";
import router from "next/router";

import { getDday } from "@/utils";
import { IngredientBrief } from "@/types";

import styles from "./IngredientGrid.module.scss";

type IngredientGridProps = {
	ingredientData: IngredientBrief[];
};

export default function IngredientGrid({
	ingredientData,
}: IngredientGridProps) {
	const { ingredientID } = useSelector(
		({ ingredientInfo }) => ingredientInfo.ingredient,
	);
	const dispatch: ThunkDispatch<any, undefined, AnyAction> = useDispatch();

	const onIngredientClick = async (clickedIngredientID: number) => {
		if (clickedIngredientID != ingredientID) {
			await dispatch(getIngredientInfo(clickedIngredientID));
		}
		router.push(`/refrigerator/info`);
	};

	return (
		<div className={styles.ingredientGridContainer}>
			{ingredientData.map((ingredient: IngredientBrief) => (
				<div
					key={ingredient.ingredientID}
					className={styles.ingredientElementContainer}
				>
					<div onClick={() => onIngredientClick(ingredient.ingredientID)}>
						<img
							src={ingredient.image}
							className={
								ingredient.remainDays.includes("+")
									? styles.ingredientIcon_expired
									: styles.ingredientIcon
							}
						/>
						<div className={styles.ingredientInfo}>
							<span className={styles.ingredientName}>{ingredient.name}</span>
							<span className={styles.ingredientDays}>
								{getDday(ingredient.remainDays)}
							</span>
						</div>
					</div>
				</div>
			))}
		</div>
	);
}
