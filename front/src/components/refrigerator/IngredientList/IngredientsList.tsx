import { useEffect, useState } from "react";
import { Check } from "react-bootstrap-icons";
import { getMatchedIngredients } from "@/api";
import styles from "./IngredientList.module.scss";

type IngredientListProps = {
	keyword: string;
	selectedIngredient: string;
	setSelectedIngredient: Function;
};

export default function IngredientList({
	keyword,
	selectedIngredient,
	setSelectedIngredient,
}: IngredientListProps) {
	const [matchedIngredients, setMatchedIngredients] = useState([]);

	useEffect(() => {
		(async () => {
			const data = await getMatchedIngredients(keyword);
			setMatchedIngredients(data);
			setSelectedIngredient("");
		})();
	}, [keyword]);

	const onIngredientClick = (ingredient: string) => {
		selectedIngredient == ingredient
			? setSelectedIngredient("")
			: setSelectedIngredient(ingredient);
	};

	return (
		<div>
			{matchedIngredients.map((ingredient) => {
				return (
					<div
						key={ingredient}
						className={
							selectedIngredient === ingredient
								? styles.ingredientlist_selected
								: styles.ingredientlist
						}
						onClick={() => onIngredientClick(ingredient)}
					>
						<div className="flex-grow-1">{ingredient}</div>
						{selectedIngredient === ingredient && (
							<Check className={styles.ingredientlistCheck} />
						)}
					</div>
				);
			})}
		</div>
	);
}
