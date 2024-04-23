import moment from "moment";

export const getDday = (remainDays: string) => {
	return remainDays === "0" ? `D-Day` : `D${remainDays}`;
};

export const calcDday = (date: string) => {
	const today = moment();
	const diffDays = moment(date).diff(today, "days");
	return diffDays == 0
		? "0"
		: diffDays < 0
		? `+${diffDays * -1}`
		: `-${diffDays + 1}`;
};
