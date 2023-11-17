import axios from "axios";
import router from "next/router";

export const register = (email: string, password: string, nickname: string) => {
	let data = JSON.stringify({
		email: email,
		password: password,
		nickname: nickname,
	});

	let config = {
		method: "post",
		maxBodyLength: Infinity,
		url: "/api/members/join",
		headers: {
			"Content-Type": "application/json",
		},
		data: data,
	};

	axios
		.request(config)
		.then((response) => {
			console.log(`회원가입 완.`);
			router.push("/");
		})
		.catch((error) => {
			alert(error.response.data.message);
		});
};
