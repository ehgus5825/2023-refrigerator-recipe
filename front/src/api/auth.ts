import router from "next/router";
import instance from "./interceptors";

const JWT_EXPIRY_TIME = 24 * 60 * 60 * 1000;

// 일반 로그인

export const login = async (email: string, password: string) => {
	const url = `/api/auth/login`;
	const body = { email, password };
	try {
		const response = await instance.post(url, body);
		loginSuccess(response);
		router.push("/refrigerator");
	} catch (error: any) {
		alert(error.response.data.message);
	}
};

// 토큰 재발행

export const silentRefresh = async () => {
	const preLoginPages = [
		"/member/email",
		"/member/login",
		"/member/password/find",
		"/member/register",
		"/member/success",
	];

	const url = `/api/auth/reissue`;
	try {
		const response = await instance.post(url);
		loginSuccess(response);
	} catch (error) {
		console.error(error);

		const url = new URL(window.location.href);
		const path = url.pathname;
		preLoginPages.includes(path)
			? router.push(path)
			: // TODO: 로그인 만료 안내 모달
			  router.push("/");
	}
};

const loginSuccess = (response: any) => {
	instance.defaults.headers.common[
		"Authorization"
	] = `Bearer ${response.data.accessToken}`;
	setTimeout(silentRefresh, JWT_EXPIRY_TIME - 60000);
};

// 로그아웃

export const logout = () => {
	instance
		.get("/api/auth/logout")
		.then(function (response) {
			instance.defaults.headers.common = {};
			alert("정상적으로 로그아웃 되었습니다.");
			router.replace("/");
		})
		.catch(function (error) {
			console.log(error);
		});
};

// 임시 토큰 발행

export const findPassword = async (email: string) => {
	return new Promise((resolve, reject) => {
		instance
			.post("/api/auth/issue/temporary-token", {
				email: email,
			})
			.then(function (response) {
				const accessToken = response.data.authToken;
				instance.defaults.headers.common[
					"Authorization"
				] = `Bearer ${accessToken}`;
				localStorage.setItem("accessToken", accessToken);
				console.log("토큰 재발행!");
				resolve(response);
			})
			.catch(function (error) {
				reject(error);
			});
	});
};
