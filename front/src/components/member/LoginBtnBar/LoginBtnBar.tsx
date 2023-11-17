import styles from "./LoginBtnBar.module.scss";

export default function LoginBtnBar() {
	return (
		<div className={styles.loginBtnBars}>
			<span className={styles.loginBtnBar}></span>
			<span>로그인</span>
			<span className={styles.loginBtnBar}></span>
		</div>
	);
}
