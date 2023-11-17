import router from "next/router";
import instance from "./interceptors";
import axios from "axios";

export const profile = async () => {
	try {
		const response = await instance.get("/api/members");
		return response;
	} catch (error) {
		console.log("잘못된 접근입니다.");
	}
}; // 회원 정보 조회

export const image = (img: any) => {
	instance
		.put("/api/members/profile", {
			imageName: img,
		})
		.then(function (response) {
			//location.reload();
		})
		.catch(function (error) {
			console.log(error);
		});
}; // 프로필 이미지 변경

export const nickname = (nick: string) => {
	instance
		.put("/api/members/nickname", {
			nickname: nick,
		})
		.then(function (response) {
			alert(`닉네임 변경이 완료되었습니다.`);
			router.push("/mypage");
		})
		.catch(function (error) {
			console.log(error);
		});
}; // 닉네임 변경

export const imageList = async () => {
	try {
		const response = await axios.get("/api/members/profile/list");
		return response.data.data.map((item: any) => item.imageUrl);
	} catch (error) {
		console.log("프로필 이미지 접근 에러:", error);
		throw error;
	}
}; // 프로필 이미지 목록
