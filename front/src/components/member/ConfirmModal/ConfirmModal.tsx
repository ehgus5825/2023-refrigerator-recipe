import Link from "next/link";
import { Button, Modal } from "react-bootstrap";

export default function ConfirmModal(props: any) {
	return (
		<Modal
			{...props}
			size="lg"
			aria-labelledby="contained-modal-title-vcenter"
			centered
		>
			<Modal.Body>
				<p>{props.ment}이 완료되었습니다.</p>
			</Modal.Body>
			<Modal.Footer>
				<Link legacyBehavior href="/">
					<Button onClick={props.onHide}>확인</Button>
				</Link>
			</Modal.Footer>
		</Modal>
	);
}
