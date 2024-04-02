import styles from "./styles.module.scss";
import LoginLink from "@/components/member/LoginLink/LoginLink";
import LoginBtn from "@/components/member/LoginBtn/LoginBtn";

export default function Login() {
	return (
		<div className={styles.loginContainer}>
			<img
				alt="냉장고를 부탁해"
				src="/images/fridge.png"
			/>
			<LoginBtn />
			<LoginLink />
		</div>
	);
}
