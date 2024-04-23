import Link from "next/link";
import styles from "./LoginLink.module.scss";

export default function LoginLink() {
	return (
		<div className={styles.loginLink}>
			<span>
				<Link legacyBehavior href="../member/register">
					<a>회원가입</a>
				</Link>
			</span>
			<span>|</span>
			<span>
				<Link legacyBehavior href="../member/password/find">
					<a>비밀번호 찾기</a>
				</Link>
			</span>	
		</div>
	);
}
