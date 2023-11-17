import axios from "axios";
import instance from "./interceptors";

export const notification = async (page: number) => {
	const url = `/api/notifications?page=${page}&size=12`;
	try {
		const response = await instance.get(url);
		console.log(response.data.data);
		return response.data.data;
	} catch (error) {
		console.log(error);
	}
};

export const readNotification = (id: number) => {
	const url = `/api/notifications/815`;
	instance
		.put(url, { id: id })
		.then((response) => {
			console.log(response.data);
		})
		.catch((error) => {
			console.log(error);
		});
};

export const getIsNotificationRead = async () => {
	const url = `/api/notifications/sign`;
	try {
		const response = await instance.get(url);
		return response.data.sign;
	} catch (error) {
		console.log(error);
	}
};
