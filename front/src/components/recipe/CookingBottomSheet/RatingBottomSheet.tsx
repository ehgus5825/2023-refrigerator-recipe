import BottomSheet from "@/components/global/BottomSheet/BottomSheet";
import styles from "./CookingBottomSheet.module.scss";
import { useState } from "react";
import { rateRecipe } from "@/api";
import MouseOverStars from "../Stars/MouseOverStars";

type RatingBottomSheetProps = {
	show: boolean;
	onHide: Function;
	recipeID: number;
};

export default function RatingBottomSheet({
	show,
	onHide,
	recipeID,
}: RatingBottomSheetProps) {
	const [score, setScore] = useState(0);

	const onCancelBtnClick = () => {
		onHide();
	};

	const onNextBtnClick = async () => {
		rateRecipe(recipeID, score);
		onHide();
	};

	return (
		<BottomSheet show={show} onHide={onHide} style={{ height: "100%" }}>
			<BottomSheet.Header title="별점 남기기" onHide={() => onHide()} />

			<BottomSheet.Body>
				<div className={styles.container}>
					<div className={styles.descriptionContainer}>
						<span>레시피는 어땠나요?</span>
						레시피에 별점을 남겨주세요
					</div>
					<div className={styles.starsContainer}>
						<MouseOverStars score={score} setScore={setScore} />
					</div>
				</div>
			</BottomSheet.Body>

			<BottomSheet.Footer>
				<button onClick={onCancelBtnClick}>취소</button>
				<button onClick={onNextBtnClick} disabled={score === 0}>
					확인
				</button>
			</BottomSheet.Footer>
		</BottomSheet>
	);
}
