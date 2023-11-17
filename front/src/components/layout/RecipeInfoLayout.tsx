import AppBar from "./AppBar/AppBar";
import { BackIcon } from "./AppBar/AppBarIcons";
import styles from "./Layout.module.scss";
import NavBar from "./NavBar/NavBar";

type layoutProps = {
	recipeName: string;
	children: React.ReactNode;
	isAppbarAboveImg?: boolean;
};

export default function RecipeInfoLayout({
	recipeName,
	children,
	isAppbarAboveImg,
}: layoutProps) {
	return (
		<div className={styles.layoutContainer}>
			<AppBar title={recipeName} isAboveImg={true}>
				<BackIcon />
			</AppBar>
			<div className={styles.layoutContentWithAppNav}>{children}</div>
			<NavBar activeLabel="레시피" />
		</div>
	);
}
