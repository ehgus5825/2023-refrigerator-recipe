import router from "next/router";
import instance from "./interceptors";
import axios from "axios";

// 이메일 체크

export const duplicate = (email: string) => {
	let data = JSON.stringify({
		email: email,
	});

	let config = {
		method: "post",
		maxBodyLength: Infinity,
		url: "/api/members/email/check",
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


// 회원가입

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

// 닉네임 수정

export const nickname = (nick: string) => {
	instance
		.put("/api/members/nickname/modify", {
			nickname: nick,
		})
		.then(function (response) {
			alert(`닉네임 변경이 완료되었습니다.`);
			router.push("/mypage");
		})
		.catch(function (error) {
			console.log(error);
		});
};

// 프로필 수정

export const image = (img: string) => {
	instance
		.put(`/api/members/profileImage/modify?imageNo=${img}`)
		.then(function (response) {
			//location.reload();
		})
		.catch(function (error) {
			console.log(error);
		});
};

// 비밀번호 수정

export const changePassword = (password: string) => {
	return new Promise((resolve, reject) => {
		instance
			.put("/api/members/password/modify", {
				password: password,
			})
			.then(function (response) {
				resolve(response);
			})
			.catch(function (error) {
				reject(error);
				router.reload();
			});
	});
};

// 마이페이지

export const profile = async () => {
	try {
		const response = await instance.get("/api/my-page/member");

		return response.data;
	} catch (error) {
		console.log(error);
	}
};

// 프로필 이미지 목록 조회

export const imageList = async () => {
	try {
		const response = await instance.get("/api/my-page/member/profile/images");
		return response.data.data;
	} catch (error) {
		console.log("프로필 이미지 접근 에러:", error);
		throw error;
	}
};

// 회원 탈퇴

export const unregister = async () => {
	try {
		const response = await instance.delete("/api/members/withdraw");
		alert("탈퇴가 완료되었습니다.");
		router.replace("/");
	} catch (error) {
		console.log(error);
	}
};