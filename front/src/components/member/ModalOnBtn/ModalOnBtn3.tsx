import { useState } from "react";
import { Button } from "react-bootstrap";
import ConfirmModal from "../ConfirmModal/ConfirmModal";
import styles from "./ModalOnBtn.module.scss";
import { nickname } from "@/api";

interface ModalOnBtnProps {
	title: string;
	ment: string;
	nick: string;
}

export default function ModalOnBtn({ title, ment, nick }: ModalOnBtnProps) {
	const [showModal, setShowModal] = useState(false);

	return (
		<div className={`d-grid gap-2`}>
			<Button
				className={styles.modalOnBtn}
				variant="primary"
				size="lg"
				onClick={() => {
					nickname(nick);
				}}
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
