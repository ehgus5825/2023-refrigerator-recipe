import { unregister } from "@/api/unregister";
import Link from "next/link";
import { Button, Modal } from "react-bootstrap";

interface UnregisterModalsProps {
	show: boolean;
	handleClose: () => void;
}

export default function UnregisterModal({
	show,
	handleClose,
}: UnregisterModalsProps) {
	return (
		<Modal show={show} onHide={handleClose}>
			<Modal.Header>
				<Modal.Title>회원탈퇴</Modal.Title>
			</Modal.Header>
			<Modal.Body>정말 탈퇴하시겠습니까?</Modal.Body>
			<Modal.Footer>
				<Link legacyBehavior href="/">
					<Button
						onClick={() => {
							unregister();
							console.log("회원탈퇴 완.");
						}}
					>
						확인
					</Button>
				</Link>
				<Button variant="secondary" onClick={handleClose}>
					취소
				</Button>
			</Modal.Footer>
		</Modal>
	);
}
