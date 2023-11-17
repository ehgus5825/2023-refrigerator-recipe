import { useEffect, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { AnyAction, ThunkDispatch } from "@reduxjs/toolkit";
import Link from "next/link";
import { Search } from "react-bootstrap-icons";

import { useIntersectionObserver } from "@/hooks";
import { Storage } from "@/types";
import {
	setInit,
	setIsExpired,
	setPage,
	setStorage,
	getIngredientData,
} from "@/store/refrigerator";

import AppNavLayout from "@/components/layout/AppNavLayout";
import StorageTab from "@/components/refrigerator/StorageTab/StorageTab";
import IngredientGrid from "@/components/refrigerator/IngredientGrid/IngredientGrid";
import Switch from "@/components/global/Switch/Switch";

import styles from "@/scss/pages.module.scss";

export default function RefrigeratorPage() {
	const { data, isDataEnd, settings } = useSelector(
		({ ingredientData }) => ingredientData,
	);

	const dispatch: ThunkDispatch<any, undefined, AnyAction> = useDispatch();
	const onStorageClick = (payload: Storage) => {
		dispatch(setStorage(payload));
		dispatch(setInit());
	};
	const onIsExpiredToggle = (payload: boolean) => {
		dispatch(setIsExpired(payload));
		dispatch(setInit());
	};

	useEffect(() => {
		dispatch(getIngredientData(settings));
	}, [settings]);

	useEffect(() => {
		return () => {
			dispatch(setInit());
		};
	}, []);

	const scrollRef = useRef<HTMLDivElement>(null);
	const increasePage = () => dispatch(setPage(1));
	useIntersectionObserver(increasePage, isDataEnd);

	return (
		<AppNavLayout title="냉장고">
			<div className={styles.fixedContainer}>
				<div className="d-flex align-items-center gap-3">
					<StorageTab storage={settings.storage} setStorage={onStorageClick} size="sm" />
					<Link href={`/refrigerator/search`}>
						<Search className={styles.icon} />
					</Link>
				</div>
				<Switch
					label="유통기한 지난 식재료만 보기"
					isOn={settings.isExpired}
					setIsOn={onIsExpiredToggle}
				/>
			</div>

			<div
				id="scroll-area"
				style={{ marginTop: "90px", height: `calc(100vh - 205px)` }}
				className={styles.scrollContainer}
				ref={scrollRef}
			>
				<IngredientGrid ingredientData={data} />
				{!isDataEnd && <div id="end-of-list" />}
			</div>
		</AppNavLayout>
	);
}
