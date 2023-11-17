import Link from "next/link";
import { Button } from "react-bootstrap";
import { EmojiSmileFill, HouseFill, Google } from "react-bootstrap-icons";
import styles from "./LoginBtn.module.scss";

export default function LoginBtn() {
	const LoginBtns = [
		{
			icon: <EmojiSmileFill />,
			title: "이메일로 로그인하기",
			link: "../member/email",
		},
		{
			icon: <Google />,
			title: "구글로 로그인하기",
			link: "",
		},
		{
			icon: <HouseFill />,
			title: "네이버로 로그인하기",
			link: "http://115.85.181.24:8080/oauth2/authorization/naver",
		},
	];

	return (
		<div className={`d-grid gap-2`}>
			{LoginBtns.map((items) => (
				<Link legacyBehavior href={items.link} key={items.title}>
					<Button className={styles.loginBtn} variant="primary" size="lg">
						{items.icon}
						<span>{items.title}</span>
					</Button>
				</Link>
			))}
		</div>
	);
}
