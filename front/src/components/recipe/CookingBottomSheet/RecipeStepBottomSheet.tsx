import BottomSheet from "@/components/global/BottomSheet/BottomSheet";
import styles from "./CookingBottomSheet.module.scss";
import { RecipeStep } from "@/types";
import { useEffect, useState } from "react";

type RecipeStepBottomSheetProps = {
	show: boolean;
	onHide: Function;
	onNextShow: Function;
	recipeName: string;
	recipeSteps: RecipeStep[];
};

export default function RecipeStepBottomSheet({
	show,
	onHide,
	onNextShow,
	recipeName,
	recipeSteps,
}: RecipeStepBottomSheetProps) {
	const [activeIndex, setActiveIndex] = useState(0);
	const [progress, setProgress] = useState(50);

	useEffect(() => {
		setProgress((activeIndex / (recipeSteps.length - 1)) * 100);
	}, [activeIndex]);

	const onPrevBtnClick = () => {
		setActiveIndex((prev) => prev - 1);
	};
	const onNextBtnClick = () => {
		setActiveIndex((prev) => prev + 1);
	};
	const onFinishBtnClick = () => {
		onHide();
		onNextShow();
	};

	return (
		<BottomSheet show={show} onHide={onHide} style={{ height: "100%" }}>
			<BottomSheet.Header
				title={`${recipeName} 요리하기`}
				onHide={() => onHide()}
			/>

			<BottomSheet.Body>
				<div className={styles.container}>
					<div className={styles.stepContainer}>
						<span className={styles.activeStep}>
							{recipeSteps[activeIndex].step.padStart(2, "0")}
						</span>
						<span className={styles.lastStep}>{`/ ${String(
							recipeSteps.length,
						).padStart(2, "0")}`}</span>
					</div>

					<div className={styles.progressbar}>
						<div style={{ width: `${progress}%` }} />
					</div>

					<div className={styles.stepContent}>
						{recipeSteps[activeIndex].image && (
							<img
								className={styles.image}
								src={recipeSteps[activeIndex].image.replace(/,+$/, "")}
							/>
						)}

						<span className={styles.explanation}>
							{recipeSteps[activeIndex].explanation}
						</span>
					</div>
				</div>
			</BottomSheet.Body>

			<BottomSheet.Footer>
				<button onClick={onPrevBtnClick} disabled={activeIndex === 0}>
					이전
				</button>
				{activeIndex + 1 === recipeSteps.length ? (
					<button onClick={onFinishBtnClick}>완료</button>
				) : (
					<button onClick={onNextBtnClick}>다음</button>
				)}
			</BottomSheet.Footer>
		</BottomSheet>
	);
}
