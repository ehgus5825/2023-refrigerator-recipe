import { Form, InputGroup } from "react-bootstrap";
import styles from "./InputContent.module.scss";

export default function InputContent(props: any) {
	const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
		if (e.key === "Enter" && props.onEnterPress) {
			props.onEnterPress();
		}
	};
	return (
		<InputGroup className={`${styles.loginEmailInput} mb-3`}>
			<Form.Control
				placeholder={props.title}
				aria-label="Recipient's username"
				aria-describedby="basic-addon2"
				onChange={props.onChange}
				onKeyDown={handleKeyDown}
				type={props.type}
			/>
		</InputGroup>
	);
}
