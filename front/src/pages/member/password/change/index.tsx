import styles from "./styles.module.scss";
import { useState } from "react";
import InputContent from "@/components/member/InputContent/InputContent";
import ModalOnBtn2 from "@/components/member/ModalOnBtn/ModalOnBtn2";
import BackLayout from "@/components/layout/BackLayout";

export default function change() {
	const [newPassword, setNewPassword] = useState("");
	const [checkPw, setCheckPw] = useState("");

	const onNewPasswordHandler = (e: any) => {
		setNewPassword(e.target.value);
	};

	const onNewCheckedPwHandler = (e: any) => {
		setCheckPw(e.target.value);
	};

	return (
		<BackLayout title={"비밀번호 변경"}>
			<div className={styles.passwordContainer}>
				<div>
					<InputContent
						title="새 비밀번호"
						type="password"
						onChange={onNewPasswordHandler}
					/>
					<div>
						<span className={styles.regularPw}>
							<span>문자, 숫자, 특수 문자(@$!%*#?&)의 조합으로 이루어진</span>
							<span>길이 4이상 16이하 문자</span>
						</span>
					</div>
				</div>
				<div>
					<InputContent
						title="새 비밀번호 확인"
						type="password"
						onChange={onNewCheckedPwHandler}
					/>
					<div className={styles.checkPw}>
						{newPassword === checkPw ? (
							checkPw === "" ? (
								""
							) : (
								<span className={styles.checkPwTrue}>
									비밀번호가 일치합니다.
								</span>
							)
						) : (
							<span className={styles.checkPwFalse}>
								비밀번호가 일치하지 않습니다.
							</span>
						)}
					</div>
				</div>
				<ModalOnBtn2
					title="변경하기"
					ment="변경"
					password={newPassword}
					disabled={newPassword !== checkPw}
				/>
			</div>
		</BackLayout>
	);
}
