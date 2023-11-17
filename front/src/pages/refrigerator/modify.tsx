import { AnyAction, ThunkDispatch } from "@reduxjs/toolkit";
import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from "react";
import router from "next/router";

import { calcDday, getDday } from "@/utils";
import { modifyExpirationDate, putIngredientInfo } from "@/store";
import { IngredientDetail, Storage } from "@/types";
import { useValidateIngredient } from "@/hooks";

import BackBottomBtnLayout from "@/components/layout/BackBottomBtnLayout";
import FormLabel from "@/components/global/FormLabel/FormLabel";
import StorageTab from "@/components/refrigerator/StorageTab/StorageTab";
import DateInputForm from "@/components/refrigerator/IngredientInputForm/DateInputForm/DateInputForm";
import VolumeInputForm from "@/components/refrigerator/IngredientInputForm/VolumeInputForm";
import BottomBtn from "@/components/global/BottomBtn/BottomBtn";

import styles from "@/scss/pages.module.scss";

export default function ModifyIngredientPage() {
	const { ingredient } = useSelector(({ ingredientInfo }) => ingredientInfo);
	const [modifiedIngredient, setModifiedIngredient] = useState(ingredient);
	const [isModified, setIsModified] = useState(false);
	const isValid = useValidateIngredient(modifiedIngredient, [
		modifiedIngredient,
	]);

	const dispatch: ThunkDispatch<any, undefined, AnyAction> = useDispatch();

	useEffect(() => {
		const compareKeys = ["storage", "expirationDate", "volume"];
		setIsModified(
			!compareKeys.every((key) => ingredient[key] === modifiedIngredient[key]),
		);
	}, [modifiedIngredient]);

	const modifyIngredient = (key: string, value: Storage | string | number) => {
		setModifiedIngredient((prev: IngredientDetail) => ({
			...prev,
			[key]: value,
		}));
	};

	const onModifyIngredientClick = async () => {
		dispatch(modifyExpirationDate(modifiedIngredient));
		await dispatch(putIngredientInfo(modifiedIngredient));
		router.back();
	};

	return (
		<BackBottomBtnLayout>
			<div className={styles.container}>
				<div className="d-flex">
					<div className="d-flex flex-grow-1 flex-column">
						<div className={styles.title_lg}>{ingredient.name}</div>
						<div className={styles.subtitle}>
							{ingredient.registrationDate} 등록
						</div>
					</div>
					<div className={styles.badge}>수정 중</div>
				</div>

				<div className={styles.ingredientInfoContainer}>
					<FormLabel label="보관 방법">
						<StorageTab
							storage={modifiedIngredient.storage}
							setStorage={(value: Storage) =>
								modifyIngredient("storage", value)
							}
						/>
					</FormLabel>

					<FormLabel
						label="유통기한"
						subLabel={getDday(calcDday(modifiedIngredient.expirationDate))}
					>
						<DateInputForm
							date={modifiedIngredient.expirationDate}
							setDate={(value: string) => {
								modifyIngredient("expirationDate", value);
								modifyIngredient("remainDays", calcDday(value));
							}}
						/>
					</FormLabel>

					<FormLabel label="용량">
						<VolumeInputForm
							volume={modifiedIngredient.volume}
							setVolume={(value: number) => modifyIngredient("volume", value)}
							unit={ingredient.unit}
						/>
					</FormLabel>
				</div>
			</div>

			<BottomBtn
				label="수정하기"
				onClick={onModifyIngredientClick}
				disabled={!isModified || !isValid}
			/>
		</BackBottomBtnLayout>
	);
}
