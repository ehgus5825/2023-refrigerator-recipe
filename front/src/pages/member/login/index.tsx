import styles from "./styles.module.scss";
import LoginLink from "@/components/member/LoginLink/LoginLink";
import LoginBtn from "@/components/member/LoginBtn/LoginBtn";
import LoginBtnBar from "@/components/member/LoginBtnBar/LoginBtnBar";

export default function Login() {
	return (
		<div className={styles.loginContainer}>
			<img
				alt="냉장고를 부탁해"
				src="https://velog.velcdn.com/images/j-kyung99/post/64842983-2c78-4362-ba99-365d867017bd/image.jpg"
			/>
			<LoginLink />
			<LoginBtnBar />
			<div className={`d-grid gap-2`}>
				<LoginBtn />
			</div>
		</div>
	);
}
