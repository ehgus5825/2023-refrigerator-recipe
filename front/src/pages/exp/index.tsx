import { useRouter } from "next/router";
import { useEffect, useState } from "react";

import { getExpiringIngredients } from "@/api";

import BackLayout from "@/components/layout/BackLayout";
import IngredientGrid from "@/components/refrigerator/IngredientGrid/IngredientGrid";

export default function ExpiringIngredientListPage(day: any) {
	const [expiringIngredientData, setExpiringIngredientData] = useState([]);

	useEffect(() => {
		!isNaN(day) &&
			(async () => {
				const data = await getExpiringIngredients(day);
				setExpiringIngredientData(data);
			})();
	}, [day]);

	return (
		<BackLayout title={`유통기한 ${day}일 남은 식재료`}>
			<IngredientGrid ingredientData={expiringIngredientData} />
		</BackLayout>
	);
}

export async function getServerSideProps(context: any) {
	const day = Number(context.quer.day);

	return {
		props: {
			day,
		},
	};
}
