import AppBar from "./AppBar/AppBar";
import styles from "./Layout.module.scss";
import { BackIcon } from "./AppBar/AppBarIcons";
import NavBar from "./NavBar/NavBar";

type layoutProps = {
	title?: string;
	onBackClick? : Function;
	activeTab: string;
	children: React.ReactNode;
};

// AppBar : 뒤로가기 아이콘
// NavBar

export default function BackNavLayout({
	title,
	onBackClick,
	activeTab,
	children,
}: layoutProps) {

	const handleBackClick = () => {
		onBackClick && onBackClick();
	};

	return (
		<div className={styles.layoutContainer}>
			<AppBar title={title}>
				<BackIcon onBackClick={handleBackClick}/>
			</AppBar>
			<div className={styles.layoutContentWithAppNav}>{children}</div>
			<NavBar activeLabel={activeTab} />
		</div>
	);
}
