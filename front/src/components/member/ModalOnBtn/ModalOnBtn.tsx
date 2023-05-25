import { useState } from "react";
import { Button } from "react-bootstrap";
import ConfirmModal from "../ConfirmModal/ConfirmModal";
import styles from "./ModalOnBtn.module.scss";
import axios from "axios";
import { register } from "@/api/register";

export default function ModalOnBtn({
	title,
	ment,
	email,
	password,
	nickname,
	disabled,
}) {
	const [showModal, setShowModal] = useState(false);

	return (
		<div className={`d-grid gap-2`}>
			<Button
				className={styles.modalOnBtn}
				variant="primary"
				size="lg"
				onClick={() => {
					register(email, password, nickname);
				}}
				disabled={disabled}
			>
				{title}
			</Button>

			<ConfirmModal
				ment={ment}
				show={showModal}
				onHide={() => setShowModal(false)}
			/>
		</div>
	);
}
