import styles from "./AppBar.module.scss";

interface AppBarProps {
	title?: string;
	isAboveImg?: boolean;
	children: React.ReactNode;
}

export default function AppBar({ title, isAboveImg, children }: AppBarProps) {
	return (
		<div
			className={
				isAboveImg ? styles.appbarTransparentContainer : styles.appbarContainer
			}
		>
			{title && !isAboveImg && (
				<div className={styles.appbarTitle}>{title}</div>
			)}
			{children}
		</div>
	);
}
