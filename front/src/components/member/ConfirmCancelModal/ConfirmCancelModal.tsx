import { useState } from "react";
import { Button, Modal } from "react-bootstrap";
import styles from "./ConfirmCancelModal.module.scss";
import { GearFill, PencilFill } from "react-bootstrap-icons";

export default function ConfirmCancelModal(props: {
	title: string;
	ment: string;
	api: any;
}) {
	const [show, setShow] = useState(false);

	const handleClose = () => setShow(false);
	const handleShow = () => setShow(true);

	return (
		<>
			<Button
				className={styles.linkButton}
				variant="light"
				onClick={handleShow}
			>			
				{props.title}
			</Button>

			<Modal show={show} onHide={handleClose}>
				<Modal.Header>
					<Modal.Title>{props.title}</Modal.Title>
				</Modal.Header>
				<Modal.Body>{props.ment}</Modal.Body>
				<Modal.Footer>
					<Button
						onClick={() => {
							props.api();
						}}
					>
						확인
					</Button>
					<Button variant="secondary" onClick={handleClose}>
						취소
					</Button>
				</Modal.Footer>
			</Modal>
		</>
	);
}
