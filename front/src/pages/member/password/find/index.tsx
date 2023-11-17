import { useState } from "react";
import router from "next/router";
import { Button } from "react-bootstrap";
import styles from "./styles.module.scss";
import InputContent from "@/components/member/InputContent/InputContent";
import { findPassword } from "@/api/password";
import BackLayout from "@/components/layout/BackLayout";

export default function change() {
	const [email, setEmail] = useState("");

	const onEmailHandler = (e: any) => {
		setEmail(e.target.value);
	}; // 이메일 작성

	return (
		<BackLayout title={"비밀번호 찾기"}>
			<div className={styles.passwordContainer}>
				<InputContent title="이메일" type="email" onChange={onEmailHandler} />

				<Button
					className={styles.linkButton}
					variant="primary"
					onClick={() => {
						findPassword(email)
							.then(() => {
								router.push("/member/password/change");
							})
							.catch((error) => {
								alert(error.response.data.message);
								location.reload();
							});
					}}
				>
					다음으로
				</Button>

				{/* 비밀번호 변경 화면으로 이동 */}
			</div>
		</BackLayout>
	);
}
