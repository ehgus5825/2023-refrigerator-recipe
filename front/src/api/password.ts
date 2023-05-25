import router from "next/router";
import instance from "./interceptors";
import { AxiosError } from "axios";

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
			});
	});
};

export const findPassword = async (email: string) => {
	instance
		.post("/api/members/password/find", {
			email: email,
		})
		.then(function (response) {
			const accessToken = response.data.authToken;
			instance.defaults.headers.common[
				"Authorization"
			] = `Bearer ${accessToken}`;
			localStorage.setItem("accessToken", accessToken);
			console.log("토큰 재발행!");
		})
		.catch(function (error) {
			console.log(error);
		});
};
