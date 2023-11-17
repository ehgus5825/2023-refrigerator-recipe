import SearchTags from "./SearchTags";
import styles from "./SearchPanel.module.scss";
import { getRecipeLastSearches, getRecipeRecommendationSearches } from "@/api";
import { useEffect, useState } from "react";

export default function SearchPanel() {
	const [lastSearches, setLastSearches] = useState([]);
	const [recommendationSearches, setRecommendationSearches] = useState([]);

	useEffect(() => {
		(async () => {
			const lateSearchesData = await getRecipeLastSearches();
			setLastSearches(lateSearchesData);
			const recommendationSearchesData =
				await getRecipeRecommendationSearches();
			setRecommendationSearches(recommendationSearchesData);
		})();
	}, []);

	return (
		<div className={styles.panelContainer}>
			{lastSearches && lastSearches.length !== 0 && (
				<div className={styles.partContainer}>
					<div className={styles.title}>최근 검색어</div>
					<SearchTags
						tagData={lastSearches}
						setTagData={setLastSearches}
						deleteBtn
					/>
				</div>
			)}

			{recommendationSearches && recommendationSearches.length !== 0 && (
				<div className={styles.partContainer}>
					<div>
						<div className={styles.title}>추천 검색어</div>
						<div className={styles.description}>
							유통기한이 얼마남지 않은 식재료에요!
						</div>
					</div>
					<SearchTags tagData={recommendationSearches} />
				</div>
			)}
		</div>
	);
}
