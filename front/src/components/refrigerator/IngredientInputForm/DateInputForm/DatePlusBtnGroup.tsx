import moment from "moment";
import styles from "../IngredientInputForm.module.scss";

type DatePlusBtnGroupProps = {
	setDate: Function;
};

export default function DatePlusBtnGroup({ setDate }: DatePlusBtnGroupProps) {
	const DAYS = [3, 5, 7, 14];

	const onPlusDayBtnClick = (day: number) => {
		const updatedDate = moment().add(day, "days");
		setDate(updatedDate.format("YYYY-MM-DD"));
	};

	return (
		<div className={styles.datePlusBtnGroup}>
			{DAYS.map((day) => (
				<button
					key={day}
					className={styles.datePlusBtn}
					onClick={() => onPlusDayBtnClick(day)}
				>
					{day}일
				</button>
			))}
		</div>
	);
}
