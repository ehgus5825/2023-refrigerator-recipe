import styles from "./Modal.module.scss";

type ModalFooterProps = {
	children: React.ReactNode;
};

export default function ModalFooter({ children }: ModalFooterProps) {
	return <div className={styles.modalFooter}>{children}</div>;
}
