import AppBar from "./AppBar/AppBar";
import { BackIcon } from "./AppBar/AppBarIcons";
import styles from "./Layout.module.scss";
import NavBar from "./NavBar/NavBar";

type layoutProps = {
	recipeName: string;
	children: React.ReactNode;
	onBackClick? : Function;
	isAppbarAboveImg?: boolean;
};

// AppBar : 뒤로가기 아이콘
// NavBar

export default function RecipeInfoLayout({
	recipeName,
	children,
	onBackClick,
	isAppbarAboveImg,
}: layoutProps) {

	const handleBackClick = () => {
		onBackClick && onBackClick();
	};

	return (
		<div className={styles.layoutContainer}>
			<AppBar title={recipeName} isAboveImg={true}>
				<BackIcon onBackClick={handleBackClick} />
			</AppBar>
			<div className={styles.layoutContentWithAppNav}>{children}</div>
			<NavBar activeLabel="레시피" />
		</div>
	);
}
