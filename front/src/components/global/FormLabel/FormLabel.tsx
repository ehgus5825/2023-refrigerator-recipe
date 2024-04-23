import styles from "./FormLabel.module.scss";

type labelProps = {
	label: String;
	subLabel?: String;
	children?: React.ReactNode;
};

export default function FormLabel({ label, subLabel, children }: labelProps) {
	return (
		<div className={styles.labelContentContainer}>
			<div className={styles.labelContainer}>
				<div className={styles.label}>{label}</div>
				<div className={styles.sublabel}>{subLabel}</div>
			</div>
			{children}
		</div>
	);
}
