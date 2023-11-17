import axios from "axios";

export const authenticate = (checkEmail: string, code: string) => {
	let data = JSON.stringify({
		email: checkEmail,
		inputCode: code,
	});

	let config = {
		method: "post",
		maxBodyLength: Infinity,
		url: "/api/identification/check",
		headers: {
			"Content-Type": "application/json",
		},
		data: data,
	};

	axios
		.request(config)
		.then((response) => {
			console.log(`인증 완료!!`);
		})
		.catch((error) => {
			console.log(error);
		});
};
