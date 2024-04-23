import { useState } from "react";
import router from "next/router";

import BackBottomBtnLayout from "@/components/layout/BackBottomBtnLayout";
import Input from "@/components/global/Input/Input";
import IngredientList from "@/components/refrigerator/IngredientList/IngredientsList";
import RequestIngredient from "@/components/refrigerator/RequestIngredient/RequestIngredient";
import BottomBtn from "@/components/global/BottomBtn/BottomBtn";
import IngredientRequestModal from "@/components/refrigerator/IngredientModal/IngredientRequestModal";

import styles from "@/scss/pages.module.scss";

export default function AddIngredientNamePage() {
	const [keyword, setKeyword] = useState<string>("");
	const [selectedIngredient, setSelectedIngredient] = useState("");
	const [isRequestModalOn, setIsRequestModalOn] = useState(false);

	const onAddIngredientBtnClick = () => {
		router.replace(`/refrigerator/add/info?ingredient=${selectedIngredient}`);
	};

	return (
		<BackBottomBtnLayout>
			<div className={styles.container}>
				<div className={styles.title_md}>어떤 식재료를 추가하시겠어요?</div>
				<Input
					value={keyword}
					setValue={setKeyword}
					placeholder="추가할 식재료를 입력해주세요"
					usage="add"
					focus
				/>
				<IngredientList
					keyword={keyword}
					selectedIngredient={selectedIngredient}
					setSelectedIngredient={setSelectedIngredient}
				/>
			</div>

			{keyword && !selectedIngredient && (
				<RequestIngredient setIsRequestModalOn={setIsRequestModalOn} />
			)}
			<BottomBtn
				label="추가하기"
				disabled={!Boolean(selectedIngredient)}
				onClick={onAddIngredientBtnClick}
			/>

			<IngredientRequestModal
				show={isRequestModalOn}
				onHide={() => setIsRequestModalOn(false)}
				keyword={keyword}
			/>
		</BackBottomBtnLayout>
	);
}
