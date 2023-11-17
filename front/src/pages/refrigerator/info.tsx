import Link from "next/link";
import { useState } from "react";
import { useSelector } from "react-redux";
import { Trash3 } from "react-bootstrap-icons";

import { getDday } from "@/utils";

import BackBottomBtnLayout from "@/components/layout/BackBottomBtnLayout";
import FormLabel from "@/components/global/FormLabel/FormLabel";
import Input from "@/components/global/Input/Input";
import VolumeInputForm from "@/components/refrigerator/IngredientInputForm/VolumeInputForm";
import BottomBtn from "@/components/global/BottomBtn/BottomBtn";
import IngredientDeleteModal from "@/components/refrigerator/IngredientModal/IngredientDeleteModal";

import styles from "@/scss/pages.module.scss";

export default function IngredientInfoPage() {
	const { ingredient } = useSelector(({ ingredientInfo }) => ingredientInfo);

	const [isDeleteModalOn, setIsDeleteModalOn] = useState(false);
	const onDeleteIngredientClick = () => {
		setIsDeleteModalOn(true);
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
					<Trash3 className={styles.icon} onClick={onDeleteIngredientClick} />
				</div>

				<div className={styles.ingredientInfoContainer}>
					<FormLabel label="보관 방법">
						<Input value={ingredient.storage} disabled />
					</FormLabel>

					<FormLabel label="유통기한" subLabel={getDday(ingredient.remainDays)}>
						<Input value={ingredient.expirationDate} disabled />
					</FormLabel>

					<FormLabel label="용량">
						<VolumeInputForm
							volume={ingredient.volume}
							unit={ingredient.unit}
							disabled
						/>
					</FormLabel>
				</div>
			</div>

			<Link href={`/refrigerator/modify`}>
				<BottomBtn label="수정하기" />
			</Link>

			<IngredientDeleteModal
				show={isDeleteModalOn}
				onHide={() => setIsDeleteModalOn(false)}
				ingredientID={ingredient.ingredientID}
				ingredientName={ingredient.name}
			/>
		</BackBottomBtnLayout>
	);
}
