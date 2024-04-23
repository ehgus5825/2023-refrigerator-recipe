import styles from "./Modal.module.scss";

type ModalBodyProps = {
	children: React.ReactNode;
};

export default function ModalHeader({ children }: ModalBodyProps) {
	return <div className={styles.modalBody}>{children}</div>;
}
