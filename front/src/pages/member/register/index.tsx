import { useState } from "react";
import styles from "./styles.module.scss";

import InputBtn from "@/components/member/InputBtn/InputBtn";
import InputContent from "@/components/member/InputContent/InputContent";
import ModalOnBtn from "@/components/member/ModalOnBtn/ModalOnBtn";

import { duplicate } from "@/api/member";
import BackLayout from "@/components/layout/BackLayout";

export default function Register() {
	const [register, setRegister] = useState(false);
	// 양식에 맞게 작성, 이메일 인증 후 가입이 되게 만드는 ~~
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [nickname, setNickname] = useState("");
	const [checkPw, setCheckPw] = useState("");

	const onEmailHandler = (e: any) => {
		setEmail(e.target.value);
	}; // 이메일 작성

	const onPasswordHandler = (e: any) => {
		setPassword(e.target.value);
	}; // 비밀번호 작성

	const onCheckedPwHandler = (e: any) => {
		setCheckPw(e.target.value);
	}; // 비밀번호 확인 작성

	const onNicknameHandler = (e: any) => {
		setNickname(e.target.value);
	}; // 닉네임 작성

	const onDuplicateCheckClick = () => {
		duplicate(email);
	}; // 이메일 중복 확인 클릭

	return (
		<BackLayout title={"회원가입"}>
			<form className={styles.registerContainer}>
				<InputBtn
					formTitle="이메일"
					btnTitle="중복 확인"
					onClick={onDuplicateCheckClick}
					onChange={onEmailHandler}
				/>

				<InputContent
					title="비밀번호"
					type="password"
					onChange={onPasswordHandler}
				/>

				<span className={styles.regularPw}>
					<span>문자, 숫자, 특수 문자(@$!%*#?&)의 조합으로 </span>
					<span>이루어진 길이 4이상 16이하 문자</span>
				</span>


				<InputContent
					title="비밀번호 확인"
					type="password"
					onChange={onCheckedPwHandler}
				/>

				<div className={styles.checkPw}>
					{password === checkPw ? (
						checkPw === "" ? (
							""
						) : (
							<span className={styles.checkPwTrue}>
								비밀번호가 일치합니다.
							</span>
						)
					) : (
						<span className={styles.regularPw}>
							비밀번호가 일치하지 않습니다.
						</span>
					)}
				</div>

				<div className={styles.InputContentNickname}>
					<InputContent
						title="닉네임"
						type="text"
						onChange={onNicknameHandler}
					/>
				</div>

				<span className={styles.regularPw}>
					<span>
						영어, 한글, 띄어쓰기 포함 3~10자리 문자열
					</span>
				</span>


				<div className={styles.modalBtn}>
					<ModalOnBtn
						title="가입하기"
						ment="가입"
						email={email}
						password={password}
						nickname={nickname}
						disabled={password !== checkPw}
					/>
				</div>
			</form>
		</BackLayout >
	);
}
