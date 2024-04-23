import { useEffect, useState } from "react";

export const useValidateIngredient = (ingredient: any, dependencies: any[]) => {
	const [isValid, setIsValid] = useState(false);

	useEffect(() => {
		const isAllEntered = Object.values(ingredient).every((value) => value);
		const isVolumePositive = ingredient.volume > 0;
		setIsValid(isAllEntered && isVolumePositive);
	}, dependencies);

	return isValid;
};
