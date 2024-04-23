import AppBar from "./AppBar/AppBar";
import styles from "./Layout.module.scss";
import {BackIcon} from "./AppBar/AppBarIcons";
import { MypageSettingIcon } from "./AppBar/MypageSettingIcon";

type layoutProps = {
	title?: string;
	onBackClick?: Function;
	children: React.ReactNode;
};

// AppBar : 뒤로가기 아이콘

export default function MypageBackLayout({ title, onBackClick, children }: layoutProps) {
	const handleBackClick = () => {
		onBackClick && onBackClick();
	};

	return (
		<div className={styles.layoutContainer}>
			<AppBar title={title}>
				<BackIcon onBackClick={handleBackClick} />
                <div className={styles.appbarBtnGroup}>
					<MypageSettingIcon />
                </div>
			</AppBar>
			<div className={styles.layoutContentWithApp}>{children}</div>
		</div>
	);
}
