import AppBar from "./AppBar/AppBar";
import styles from "./Layout.module.scss";
import { BackIcon } from "./AppBar/AppBarIcons";
import NavBar from "./NavBar/NavBar";

type layoutProps = {
	title?: string;
	activeTab: string;
	children: React.ReactNode;
};

export default function BackNavLayout({
	title,
	activeTab,
	children,
}: layoutProps) {
	return (
		<div className={styles.layoutContainer}>
			<AppBar title={title}>
				<BackIcon />
			</AppBar>
			<div className={styles.layoutContentWithAppNav}>{children}</div>
			<NavBar activeLabel={activeTab} />
		</div>
	);
}
