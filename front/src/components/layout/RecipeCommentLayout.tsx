import AppBar from "./AppBar/AppBar";
import { BackIcon } from "./AppBar/AppBarIcons";
import styles from "./Layout.module.scss";

type layoutProps = {
	title: string;
	children: React.ReactNode;
};

export default function RecipeCommentLayout({ title, children }: layoutProps) {
	return (
		<div className={styles.layoutContainer}>
			<AppBar title={title}>
				<BackIcon />
			</AppBar>
			<div className={styles.layoutContentWithAppCommentForm}>{children}</div>
		</div>
	);
}
