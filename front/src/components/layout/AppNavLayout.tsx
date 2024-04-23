import AppBar from "./AppBar/AppBar";
import { MyPageIcon, AddIngredientIcon } from "./AppBar/AppBarIcons";
import NavBar from "./NavBar/NavBar";
import styles from "./Layout.module.scss";
import { NotificationIcon } from "./AppBar/NotificationIcon";

type layoutProps = {
	title: string;
	children: React.ReactNode;
};

// AppBar : 마이페이지 아이콘 + 알림 아이콘 + 식재료 추가 아이콘
// NavBar

export default function AppNavLayout({ title, children }: layoutProps) {
	return (
		<div className={styles.layoutContainer}>
			<AppBar title={title}>
				<MyPageIcon />
				<div className={styles.appbarBtnGroup}>
					<NotificationIcon />
					{title == "냉장고" && <AddIngredientIcon />}
				</div>
			</AppBar>
			<div className={styles.layoutContentWithAppNav}>{children}</div>
			<NavBar activeLabel={title} />
		</div>
	);
}
