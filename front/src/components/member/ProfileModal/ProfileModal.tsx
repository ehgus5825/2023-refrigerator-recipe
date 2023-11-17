import styles from "./ProfileModal.module.scss";
import { useEffect, useState } from "react";
import { Button, Modal } from "react-bootstrap";
import { image, imageList } from "@/api/profile";
import Mypage from "@/pages/mypage";

export default function ProfileModal(props: any) {
	const [img, setImg] = useState(props.img);
	const [show, setShow] = useState(props.on);
	const [imgList, setImgList] = useState([]);

	const handleClose = () => setShow(false);
	//const handleShow = () => setShow(true);

	const handleImageChange = async (event: any) => {
		try {
			let src = event.target.src;
			let imageName = src.substring(src.lastIndexOf("/") + 1);
			image(imageName);
			console.log("프로필 변경 완료");
			props.onImgChange(imageName); // 이미지 변경 후 새로운 이미지를 Mypage 컴포넌트로 전달
			handleClose();
		} catch (error) {
			console.log(error);
		}
	};

	useEffect(() => {
		const fetchImage = async () => {
			try {
				const images = await imageList();
				if (images) {
					setImgList(images);
				}
			} catch (error) {
				console.log("프로필 이미지 접근 에러:", error);
			}
		};
		fetchImage();
	}, []);

	return (
		<>
			<Modal show={show} onHide={handleClose}>
				<Modal.Header closeButton>
					<Modal.Title>프로필 변경</Modal.Title>
				</Modal.Header>
				<Modal.Body className={styles.profileImages}>
					{" "}
					{imgList.map((url, index) => (
						<button key={index}>
							<img
								src={url}
								alt={`Image ${index}`}
								onClick={handleImageChange}
							/>
						</button>
					))}
				</Modal.Body>
				{/* <Modal.Footer>
					<Button variant="secondary" onClick={handleClose}>
						취소
					</Button>
					<Button variant="primary" onClick={handleClose}>
						변경하기
					</Button>
				</Modal.Footer> */}
			</Modal>
		</>
	);
}
