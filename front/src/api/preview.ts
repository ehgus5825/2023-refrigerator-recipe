import instance from "./interceptors";

export const bookmarkPreview = async () => {
	try {
		const response = await instance.get("/api/my-bookmark/preview");
		//console.log(response.data.bookmarks);
		return response.data;
	} catch (error) {
		console.log(error);
	}
};

export const scorePreview = async () => {
	try {
		const response = await instance.get("/api/my-score/preview");
		//console.log(response.data.scores);
		return response.data;
	} catch (error) {
		console.log(error);
	}
};
