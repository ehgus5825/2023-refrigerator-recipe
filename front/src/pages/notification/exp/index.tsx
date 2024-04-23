import { useEffect, useState } from "react";

import { getExpiringIngredients } from "@/api";
import { IngredientBrief } from "@/types";

import BackLayout from "@/components/layout/BackLayout";
import IngredientGrid from "@/components/refrigerator/IngredientGrid/IngredientGrid";

type ExpiringIngredientListPageProps = {
	query: string;
};

export default function ExpiringIngredientListPage({ query }: ExpiringIngredientListPageProps) {
	const [expiringIngredientData, setExpiringIngredientData] = useState<IngredientBrief[]>([]);

	useEffect(() => {
		query &&
			(async () => {
				const data = await getExpiringIngredients(query);
				
				setExpiringIngredientData(data);
			})();
	}, [query]);

	return (
		<BackLayout title={`유통기한 ${query}일 남은 식재료`}>
			<IngredientGrid ingredientData={expiringIngredientData} />
		</BackLayout>
	);
}

export async function getServerSideProps(context: any) {
	const query = context.query?.query;

	return {
		props: {
			query,
		},
	};
}
