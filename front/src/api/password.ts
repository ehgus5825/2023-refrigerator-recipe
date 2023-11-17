import router from "next/router";
import instance from "./interceptors";

export const changePassword = (password: string) => {
	return new Promise((resolve, reject) => {
		instance
			.put("/api/members/password", {
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
