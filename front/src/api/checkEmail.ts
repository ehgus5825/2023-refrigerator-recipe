import axios from "axios";

export const checkEmail = (checkEmail: string) => {
	let data = JSON.stringify({
		email: checkEmail,
	});

	let config = {
		method: "post",
		maxBodyLength: Infinity,
		url: "/api/identification/send",
		headers: {
			"Content-Type": "application/json",
		},
		data: data,
	};

	axios
		.request(config)
		.then((response) => {
			console.log(response);
		})
		.catch((error) => {
			console.log(error);
		});
};
