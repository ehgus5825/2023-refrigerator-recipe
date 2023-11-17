import router from "next/router";
import { useEffect } from "react";

export default function Success() {
	useEffect(() => {
		const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
		const paramValue = urlParams.get("token");
		console.log(paramValue); //TODO: 지워야됨 ...

		router.push("/mypage");
	}, []);
}
