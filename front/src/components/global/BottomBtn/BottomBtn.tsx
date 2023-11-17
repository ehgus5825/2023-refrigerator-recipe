import { MouseEventHandler } from "react";
import styles from "./BottomBtn.module.scss";

type btnProps = {
	label: String;
	onClick?: MouseEventHandler<HTMLButtonElement> | undefined;
	disabled?: boolean | undefined;
};

export default function BottomBtn({
	label,
	onClick,
	disabled = false,
}: btnProps) {
	return (
		<button className={styles.bottombtn} onClick={onClick} disabled={disabled}>
			{label}
		</button>
	);
}
