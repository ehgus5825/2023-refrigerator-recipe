import { Button } from "react-bootstrap";
import styles from "./LinkBtn.module.scss";
import { login } from "@/api/login";

interface LinkBtn2Props {
	title: string;
	email: string;
	password: string;
}

export default function LinkBtn2({ title, email, password }: LinkBtn2Props) {
	return (
		<Button
			className={styles.loginButton}
			variant="primary"
			size="lg"
			onClick={() => {
				login(email, password);
			}}
		>
			<span>{title}</span>
		</Button>
	);
}
