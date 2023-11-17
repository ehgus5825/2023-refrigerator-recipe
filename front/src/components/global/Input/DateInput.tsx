import moment, { Moment } from "moment";
import styles from "./Input.module.scss";
import { ArrowClockwise } from "react-bootstrap-icons";

type DateInputProps = {
	date: string;
	setDate: Function;
};

export default function DateInput({ date, setDate }: DateInputProps) {
	return (
		<div className={styles.inputContainer_info}>
			<input
				type="date"
				className={styles.inputContent}
				value={date}
				onChange={(e) => setDate && setDate(e.target.value)}
				onKeyDown={(e) => e.preventDefault()}
			/>
			<ArrowClockwise
				className={styles.backToTodayBtn}
				onClick={() => setDate(moment().format("YYYY-MM-DD"))}
			/>
		</div>
	);
}
