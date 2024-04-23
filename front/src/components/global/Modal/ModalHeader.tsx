import styles from "./Modal.module.scss";

type ModalHeaderProps = {
	title: string;
};

export default function ModalHeader({ title }: ModalHeaderProps) {
	return (
		<div className={styles.modalHeader}>
			<span>{title}</span>
		</div>
	);
}
