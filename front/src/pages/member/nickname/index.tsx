import styles from "./styles.module.scss";
import { useState } from "react";
import InputContent from "@/components/member/InputContent/InputContent";
import BackLayout from "@/components/layout/BackLayout";
import ModalOnBtn3 from "@/components/member/ModalOnBtn/ModalOnBtn3";

export default function nickname() {
	const [nick, setNick] = useState("");

	const onNicknameHandler = (e: any) => {
		setNick(e.target.value);
	};

	return (
		<BackLayout title={"닉네임 변경"}>
			<div className={styles.passwordContainer}>

				<InputContent
					title="닉네임"
					type="text"
					onChange={onNicknameHandler}
				/>

				<span className={styles.regularPw}>
					<span>영어, 한글, 띄어쓰기를 포함하여 3 ~ 10자 입력</span>
				</span>

				<ModalOnBtn3 title="변경하기" ment="변경" nick={nick} />
			</div>
		</BackLayout>
	);
}
