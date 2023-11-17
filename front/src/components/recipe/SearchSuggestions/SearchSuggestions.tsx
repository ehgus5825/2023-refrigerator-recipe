import { getRecipeSearchSuggestions } from "@/api";
import { useEffect, useState } from "react";
import styles from "./SearchSuggestions.module.scss";
import router from "next/router";

type SearchSuggestionsProps = {
	keyword: string;
};

export default function SearchSuggestions({ keyword }: SearchSuggestionsProps) {
	const [searchSuggestionData, setSearchSuggestionData] = useState([]);

	useEffect(() => {
		(async () => {
			const data = await getRecipeSearchSuggestions(keyword);
			setSearchSuggestionData(data);
		})();
	}, [keyword]);

	return (
		<div className={styles.searchSuggestionsContainer}>
			{searchSuggestionData.map((searchSuggestion) => (
				<div
					key={searchSuggestion}
					onClick={() => {
						router.replace(`/recipe/search?query=${searchSuggestion}`);
					}}
				>
					{searchSuggestion}
				</div>
			))}
		</div>
	);
}
