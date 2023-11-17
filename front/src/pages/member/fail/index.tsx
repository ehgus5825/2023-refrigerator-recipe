import router from "next/router";
import { useEffect, useState } from "react";

export default function Fail() {
	const [error, setError] = useState<string | null>(null);
	useEffect(() => {
		const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
		const paramValue = urlParams.get("errorCode");
		setError(paramValue);
	}, []);

	useEffect(() => {
		if (error === "WITHDRAWN_MEMBER") {
			alert(`이미 탈퇴한 회원입니다.`);
		} else if (error === "NOT_FOUND_MEMBER") {
			alert(`회원을 찾을 수 없습니다.`);
		}
		router.replace("/");
	}, [error]);

	return null;
}
