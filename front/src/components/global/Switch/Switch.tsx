import { Form } from "react-bootstrap";
import styles from "./Switch.module.scss";

type SwitchProps = {
	label?: string;
	isOn: boolean;
	setIsOn: (value: boolean) => void;
};

export default function Switch({ label, isOn, setIsOn }: SwitchProps) {
	return (
		<Form className={styles.switchContainer}>
			<label className={isOn ? styles.switchLabel_active : styles.switchLabel}>
				{label}
			</label>
			<Form.Check type="switch" onChange={() => setIsOn(!isOn)} />
		</Form>
	);
}
