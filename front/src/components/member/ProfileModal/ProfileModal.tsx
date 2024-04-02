import styles from "./ProfileModal.module.scss";
import { useEffect, useState } from "react";
import { Button, Modal } from "react-bootstrap";
import { image, imageList } from "@/api/member";

export default function ProfileModal(props: any) {
	const [show, setShow] = useState(props.on);
	const [imgList, setImgList] = useState([]);

	const handleClose = () => {
		setShow(false);
	}

	const handleImageChange = async (event: any) => {
		try {
			let alt = event.target.alt;
			let src = event.target.src;
			image(alt);
			props.onImgChange(src); // 이미지 변경 후 새로운 이미지를 Mypage 컴포넌트로 전달
			handleClose();
		} catch (error) {
			console.log(error);
		}
	};

	useEffect(() => {
		(async () => {
			try {
				const images = await imageList();
				if (images) {
					setImgList(images.map((item: any) => item.profileImage));
				}
			} catch (error) {
				console.log("프로필 이미지 접근 에러:", error);
			}
		})();
	}, []);

	return (
		<>
			<Modal show={show} onHide={handleClose} >
				<Modal.Header closeButton className={styles.headerContainer}>
					<Modal.Title className={styles.title}>프로필 변경</Modal.Title>
				</Modal.Header>
				<Modal.Body className={styles.imageList}>
					<div>
						<button className={styles.btnStyle}>
							<img className={styles.btnImg}
								src={imgList[0]}
								alt={`0`}
								onClick={handleImageChange}
							/>
						</button>
						<button className={styles.btnStyle}>
							<img className={styles.btnImg}
								src={imgList[1]}
								alt={`1`}
								onClick={handleImageChange}
							/>
						</button>
						<button className={styles.btnStyle}>
							<img className={styles.btnImg}
								src={imgList[2]}
								alt={`2`}
								onClick={handleImageChange}
							/>
						</button>
					</div>
					<div>
						<button className={styles.btnStyle}>
							<img className={styles.btnImg}
								src={imgList[3]}
								alt={`3`}
								onClick={handleImageChange}
							/>
						</button>
						<button className={styles.btnStyle}>
							<img className={styles.btnImg}
								src={imgList[4]}
								alt={`4`}
								onClick={handleImageChange}
							/>
						</button>
					</div>
				</Modal.Body>
			</Modal>
		</>
	);
}
