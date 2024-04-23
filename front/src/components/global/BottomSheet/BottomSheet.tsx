import BottomSheetHeader from "./BottomSheetHeader";
import BottomSheetBody from "./BottomSheetBody";
import BottomSheetFooter from "./BottomSheetFooter";
import styles from "./BottomSheet.module.scss";

type BottomSheetProps = {
	show: boolean;
	onHide: Function;
	style?: any;
	children: React.ReactNode;
};

function BottomSheet({ show, onHide, style, children }: BottomSheetProps) {
	return (
		<>
			{show && (
				<div>
					<div
						className={styles.bottomsheetBackground}
						onClick={() => onHide()}
					/>
					<div className={styles.bottomsheet} style={style}>
						{children}
					</div>
				</div>
			)}
		</>
	);
}

export default Object.assign(BottomSheet, {
	Header: BottomSheetHeader,
	Body: BottomSheetBody,
	Footer: BottomSheetFooter,
});
