import instance from "./interceptors";

// 댓글 삭제

export const deleteComment = async (commentID: number) => {
	const url = `/api/comments/${commentID}/delete`;
	try {
		const response = await instance.delete(url);
	} catch (error) {
		console.error(error);
	}
};

// 댓글 수정

export const modifyComment = async (commentID: number, content: string) => {
	const url = `/api/comments/${commentID}/edit`;
	const body = { content: content };
	try {
		const response = await instance.put(url, body);
	} catch (error) {
		console.error(error);
	}
};

// 댓글 하트 누르기

export const likeComment = async (commentID: number) => {
	const url = `/api/comments/${commentID}/heart/add`;
	try {
		const response = await instance.put(url);
	} catch (error) {
		console.error(error);
	}
};

// 댓글 하트 취소

export const unlikeComment = async (commentID: number) => {
	const url = `/api/comments/${commentID}/heart/reduce`;
	try {
		const response = await instance.put(url);
	} catch (error) {
		console.error(error);
	}
};

// 댓글 목록 조회

export const getComments = async (recipeID: string, page: number, sortCondition: String) => {
	// sortCondition 추가 
	const url = `/api/recipe/${recipeID}/comments?page=${page}&sortCondition=${sortCondition}`;

	try {
		const response = await instance.get(url);

		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

// 내 댓글 목록 조회

export const getMyComments = async (recipeID: string) => {
	// sortCondition 추가 
	const url = `/api/recipe/${recipeID}/comments/own`;

	try {
		const response = await instance.get(url);

		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

// 댓글 미리보기 목록 조회

export const getCommentsPreview = async (recipeID: number) => {
	const url = `/api/recipe/${recipeID}/comments/preview`;
	try {
		const response = await instance.get(url);

		return response.data;
	} catch (error) {
		console.error(error);
	}
};

// 댓글 생성

export const addComment = async (recipeID: string, content: string) => {
	const url = `/api/recipe/${recipeID}/comments/write`;
	const body = { content: content };
	try {
		const response = await instance.post(url, body);
	} catch (error) {
		console.error(error);
	}
};