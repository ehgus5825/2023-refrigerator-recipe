import Modal from "@/components/global/Modal/Modal";
import styles from "./IngredientModal.module.scss";
import Input from "@/components/global/Input/Input";
import { useState } from "react";
import { requestIngredient } from "@/api";
import router from "next/router";

type modalProps = {
	show: boolean;
	onHide: Function;
	keyword: string;
};

export default function IngredientRequestModal({
	show,
	onHide,
	keyword,
}: modalProps) {
	const [unit, setUnit] = useState("");

	const onRequestIngredientClick = async () => {
		await requestIngredient(keyword, unit);
		onHide();
		router.back();
	};

	return (
		<Modal show={show} onHide={onHide}>
			<Modal.Header title="식재료 추가 요청" />

			<Modal.Body>
				<div className={styles.messageContainer}>
					<div className={styles.message}>
						해당 식재료에 적합한 단위를 입력하고 <br />
						식재료 추가 요청을 해주세요!
					</div>
					<div className={styles.inputContainer}>
						<span>이름</span>
						<Input value={keyword} usage="modal" disabled />
					</div>
					<div className={styles.inputContainer}>
						<span>단위</span>
						<Input setValue={setUnit} usage="modal" />
					</div>
				</div>
			</Modal.Body>

			<Modal.Footer>
				<button onClick={() => onHide()}>취소</button>
				<button onClick={onRequestIngredientClick} disabled={!Boolean(unit)}>
					확인
				</button>
			</Modal.Footer>
		</Modal>
	);
}
