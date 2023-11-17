import styles from "./BottomSheet.module.scss";

type BottomSheetBodyProps = {
	children: React.ReactNode;
};

export default function BottomSheetHeader({ children }: BottomSheetBodyProps) {
	return <div className={styles.bottomsheetBody}>{children}</div>;
}
