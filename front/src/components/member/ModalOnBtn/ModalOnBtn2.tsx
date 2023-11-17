import { useState } from "react";
import { Button } from "react-bootstrap";
import ConfirmModal from "../ConfirmModal/ConfirmModal";
import styles from "./ModalOnBtn.module.scss";
import { changePassword } from "@/api";

interface ModalOnBtn2Props {
	title: string;
	ment: string;
	password: string;
	disabled: boolean;
}

export default function ModalOnBtn2({
	title,
	ment,
	password,
	disabled,
}: ModalOnBtn2Props) {
	const [modalShow, setModalShow] = useState(false);

	const handleButtonClick = async () => {
		try {
			await changePassword(password);
			setModalShow(true);
		} catch (error: any) {
			alert(error.response.data.message);
			setModalShow(false);
		}
	};
	return (
		<div className={`d-grid gap-2`}>
			<Button
				className={styles.modalOnBtn}
				variant="primary"
				size="lg"
				onClick={handleButtonClick}
				disabled={disabled}
			>
				{title}
			</Button>

			<ConfirmModal
				ment={ment}
				show={modalShow}
				onHide={() => setModalShow(false)}
			/>
		</div>
	);
}
