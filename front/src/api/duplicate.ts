import axios from "axios";

export const duplicate = (email: string) => {
	let data = JSON.stringify({
		email: email,
	});

	let config = {
		method: "post",
		maxBodyLength: Infinity,
		url: "/api/members/email/duplicate",
		headers: {
			"Content-Type": "application/json",
		},
		data: data,
	};

	axios
		.request(config)
		.then((response) => {
			alert("사용 가능한 아이디입니다.");
		})
		.catch((error) => {
			if (
				error.response.data.code === "DUPLICATE_EMAIL" ||
				error.response.data.code === "INCORRECT_EMAIL_FORMAT"
			)
				alert(error.response.data.message);
		});
};
