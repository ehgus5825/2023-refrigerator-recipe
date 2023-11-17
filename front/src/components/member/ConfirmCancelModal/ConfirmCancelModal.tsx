import { useState } from "react";
import { Button, Modal } from "react-bootstrap";
import styles from "./ConfirmCancelModal.module.scss";

export default function ConfirmCancelModal(props: {
	style: string;
	variant: string;
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
				className={`${styles[props.style]}`}
				variant={props.variant}
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
