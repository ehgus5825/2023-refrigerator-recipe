import router from "next/router";
import instance from "./interceptors";

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
