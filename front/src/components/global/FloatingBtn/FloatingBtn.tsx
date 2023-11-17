import styles from "./FloatingBtn.module.scss";

type FloatingBtnProps = {
	label: string;
};

export default function FloatingBtn({ label }: FloatingBtnProps) {
	return <button className={styles.floatingBtn}>{label}</button>;
}
