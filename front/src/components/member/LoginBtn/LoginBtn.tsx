import Link from "next/link";
import { Button } from "react-bootstrap";
import { EmojiSmileFill, HouseFill, Google } from "react-bootstrap-icons";
import styles from "./LoginBtn.module.scss";

export default function LoginBtn() {
	return (
		<div className={styles.loginBtnContainer}>
			<Link legacyBehavior href="../member/email">
				<Button className={styles.loginBtn} variant="primary" size="lg">
					<EmojiSmileFill />
					<span>로그인</span>
				</Button>
			</Link>
		</div>
	);
}
