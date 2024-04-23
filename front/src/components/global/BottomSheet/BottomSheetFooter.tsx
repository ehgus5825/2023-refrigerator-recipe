import { XLg } from "react-bootstrap-icons";
import styles from "./BottomSheet.module.scss";

type BottomSheetFooterProps = {
	children: React.ReactNode;
};

export default function BottomSheetFooter({
	children,
}: BottomSheetFooterProps) {
	return <div className={styles.bottomsheetFooter}>{children}</div>;
}
