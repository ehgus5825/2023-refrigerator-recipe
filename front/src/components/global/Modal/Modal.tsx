import ModalHeader from "./ModalHeader";
import ModalBody from "./ModalBody";
import ModalFooter from "./ModalFooter";
import styles from "./Modal.module.scss";

type ModalProps = {
	show: boolean;
	onHide: Function;
	style?: any;
	children: React.ReactNode;
};

function Modal({ show, onHide, style, children }: ModalProps) {
	return (
		<>
			{show && (
				<div>
					<div className={styles.modalBackground} onClick={() => onHide()} />
					<div className={styles.modal} style={style}>
						{children}
					</div>
				</div>
			)}
		</>
	);
}

export default Object.assign(Modal, {
	Header: ModalHeader,
	Body: ModalBody,
	Footer: ModalFooter,
});
