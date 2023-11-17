import Link from "next/link";
import { Button } from "react-bootstrap";
import styles from "./LinkBtn.module.scss";
import { GearFill, PencilFill } from "react-bootstrap-icons";

interface LinkBtnProps {
	title: string;
	link: string;
}

export default function LinkBtn({ title, link }: LinkBtnProps) {
	return (
		<Link legacyBehavior href={link}>
			<Button className={styles.linkButton} variant="light" size="lg">
				{title === "비밀번호 변경" ? (
					<GearFill className={styles.mypageIcon} />
				) : (
					<PencilFill className={styles.mypageIcon} />
				)}
				{title}
			</Button>
		</Link>
	);
}
