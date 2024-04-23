import { requestIngredient } from "@/api";
import styles from "./RequestIngredient.module.scss";
import router from "next/router";
import IngredientRequestModal from "../IngredientModal/IngredientRequestModal";
import { useState } from "react";

type RequestIngredientProps = {
	setIsRequestModalOn: Function;
};

export default function RequestIngredient({
	setIsRequestModalOn,
}: RequestIngredientProps) {
	const onRequestIngredientClick = () => {
		setIsRequestModalOn(true);
		// TODO: 추가할 수 있도록 빠른 시일 내에 등록하고 알려드릴게요 안내하기
	};

	return (
		<div
			className={styles.requestIngredientContainer}
			onClick={onRequestIngredientClick}
		>
			<div className={styles.requestIngredientDescription}>
				추가하고 싶은 식재료가 없다면,
			</div>
			<div className={styles.requestIngredientLink}>
				입력한 식재료 추가 요청하기
			</div>
		</div>
	);
}
