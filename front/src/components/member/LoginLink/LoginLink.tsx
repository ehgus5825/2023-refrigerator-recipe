import Link from "next/link";
import styles from "./LoginLink.module.scss";

export default function LoginLink() {
	const LoginLinks = [
		{
			title: "회원가입",
			ment: "아직 회원이 아니신가요?",
			link: "../member/register",
		},
		{
			title: "비밀번호 찾기",
			ment: "비밀번호를 잊으셨나요?",
			link: "../member/password/find",
		},
	];
	return (
		<div className={styles.loginLink}>
			{LoginLinks.map((items) => (
				<span key={items.title}>
					{items.ment}
					<Link legacyBehavior href={items.link}>
						<a>{items.title}</a>
					</Link>
				</span>
			))}
		</div>
	);
}
