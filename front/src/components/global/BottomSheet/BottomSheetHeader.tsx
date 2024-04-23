import { MouseEventHandler } from "react";
import { XLg } from "react-bootstrap-icons";
import styles from "./BottomSheet.module.scss";

type BottomSheetHeaderProps = {
	title: string;
	onHide: MouseEventHandler<SVGElement>;
};

export default function BottomSheetHeader({
	title,
	onHide,
}: BottomSheetHeaderProps) {
	return (
		<div className={styles.bottomsheetHeader}>
			<span>{title}</span>
			<XLg className={styles.closeBtn} onClick={onHide} />
		</div>
	);
}
