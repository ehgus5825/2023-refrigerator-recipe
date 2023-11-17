import router from "next/router";
import instance from "./interceptors";

const JWT_EXPIRY_TIME = 24 * 60 * 60 * 1000;

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
