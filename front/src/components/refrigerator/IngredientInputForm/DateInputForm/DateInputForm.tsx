import DateInput from "@/components/global/Input/DateInput";
import styles from "../IngredientInputForm.module.scss";
import DatePlusBtnGroup from "./DatePlusBtnGroup";

type DateInputProps = {
	date: string;
	setDate: Function;
};

export default function DateInputForm({ date, setDate }: DateInputProps) {
	return (
		<div className={styles.dateInputForm}>
			<DateInput date={date} setDate={setDate} />
			<DatePlusBtnGroup setDate={setDate} />
		</div>
	);
}
