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
				<div>
					<InputContent
						title="닉네임"
						type="text"
						onChange={onNicknameHandler}
					/>
					<div>
						<span className={styles.regularPw}>
							<span>영어, 한글, 띄어쓰기 포함 3자리 이상 10자리 이하 문자</span>
						</span>
					</div>
				</div>

				<ModalOnBtn3 title="변경하기" ment="변경" nick={nick} />
			</div>
		</BackLayout>
	);
}
