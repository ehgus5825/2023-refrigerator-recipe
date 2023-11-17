import router from "next/router";
import instance from "./interceptors";

export const unregister = async () => {
	try {
		const response = await instance.delete("/api/members");
		alert("탈퇴가 완료되었습니다.");
		router.replace("/");
	} catch (error) {
		console.log(error);
	}
};
