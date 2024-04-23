import AppBar from "./AppBar/AppBar";
import { BackIcon } from "./AppBar/AppBarIcons";
import styles from "./Layout.module.scss";

type layoutProps = {
	title: string;
	onBackClick? : Function;
	children: React.ReactNode;
};

// AppBar : 뒤로가기 아이콘

export default function RecipeCommentLayout({ title, onBackClick , children }: layoutProps) {

	const handleBackClick = () => {
		onBackClick && onBackClick();
	};

	return (
		<div className={styles.layoutContainer}>
			<AppBar title={title}>
				<BackIcon onBackClick={handleBackClick} />
			</AppBar>
			<div className={styles.layoutContentWithAppCommentForm}>{children}</div>
		</div>
	);
}
