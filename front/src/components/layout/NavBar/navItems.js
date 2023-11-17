import styles from "./NavBar.module.scss";
import { EggFill, EggFried, BookmarkStarFill } from "react-bootstrap-icons";

export const NAV_ITEMS = [
	{
		label: "냉장고",
		icon: <EggFill className={styles.navIcon} />,
		route: "/refrigerator",
	},
	{
		label: "레시피",
		icon: <EggFried className={styles.navIcon} />,
		route: "/recipe",
	},
	{
		label: "북마크",
		icon: <BookmarkStarFill className={styles.navIcon} />,
		route: "/recipe/bookmarks",
	},
];
