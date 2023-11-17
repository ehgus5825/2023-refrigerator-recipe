import { Button, Form, InputGroup } from "react-bootstrap";
import styles from "./InputBtn.module.scss";

export default function InputBtn(props: any) {
	return (
		<InputGroup className={`${styles.inputBtn} mb-3`}>
			<Form.Control
				placeholder={props.formTitle}
				aria-label="Recipient's username"
				aria-describedby="basic-addon2"
				onChange={props.onChange}
			/>
			<Button
				variant="outline-secondary"
				className="button-addon2"
				onClick={props.onClick}
			>
				{props.btnTitle}
			</Button>
		</InputGroup>
	);
}
