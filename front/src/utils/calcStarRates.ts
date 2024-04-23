export const calcStarRates = (score: number, SIZE: number) => {
	let starRates = [0, 0, 0, 0, 0];
	let starVerScore = (score * (SIZE * 5)) / 5;
	let index = 0;
	while (starVerScore >= SIZE) {
		starRates[index] = SIZE;
		index += 1;
		starVerScore -= SIZE;
	}
	starRates[index] = starVerScore;
	return starRates.slice(0, 5);
};
