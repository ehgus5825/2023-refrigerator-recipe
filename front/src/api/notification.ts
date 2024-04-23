import instance from "./interceptors";

// 알림 아이콘 활성화 조회

export const getIsNotificationRead = async () => {
	const url = `/api/notifications/sign`;
	try {
		const response = await instance.get(url);
		return response.data.sign;
	} catch (error) {
		console.log(error);
	}
};

// 알림 아이콘 비활성화

export const notificationSignOff = async () => {
	const url = `/api/notifications/sign/off`;

	try {
		const response = await instance.put(url);
	} catch (error) {
		console.log(error);
	}
}

// 알림 목록 조회

export const notification = async (page: number) => {
	const url = `/api/notifications?page=${page}`;
	try {
		const response = await instance.get(url);

		console.log(response.data.data);

		return response.data.data;
	} catch (error) {
		console.log(error);
	}
};

// 알림 목록 조회

export const expirationNotification = async () => {
	const url = `/api/notifications/expiration`;

	try {
		const response = await instance.get(url);

		console.log(response.data.data);

		return response.data.data;
	} catch (error) {
		console.log(error);
	}
};

// 알림 단건 읽기 (읽음 상태 변경)

export const readNotification = (id: number) => {
	const url = `/api/notifications/${id}`;
	instance
		.put(url)
		.catch((error) => {
			console.log(error);
		});
};