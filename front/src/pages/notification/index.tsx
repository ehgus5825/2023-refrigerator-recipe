import { useEffect, useState } from "react";
import styles from "./styles.module.scss";

import { notification, readNotification } from "@/api/notification";

import { useIntersectionObserver } from "@/hooks";
import { NotificationList } from "@/types";

import BackLayout from "@/components/layout/BackLayout";
import {
	CupStraw,
	ExclamationTriangleFill,
	HandThumbsUpFill,
} from "react-bootstrap-icons";
import Link from "next/link";

export default function Notification() {
	const TYPE = [
		{
			type: "EXPIRATION_DATE",
			title: "유통기한",
			icon: <ExclamationTriangleFill />,
		},
		{
			type: "HEART",
			title: "좋아요",
			icon: <HandThumbsUpFill />,
		},
		{
			type: "INGREDIENT",
			title: "식재료",
			icon: <CupStraw />,
		},
	];
	const [notificationData, setNotificationData] = useState<NotificationList[]>(
		[],
	);
	const [page, setPage] = useState(0);
	const [isDataLoaded, setIsDataLoaded] = useState(false);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

	const onLinkClick = (id: number) => {
		readNotification(id);
	};

	useEffect(() => {
		!isScrollEnd &&
			(async () => {
				const data = await notification(page);
				data.length !== 0
					? setNotificationData((prev) => [...prev, ...data])
					: setIsScrollEnd(true);
				setIsDataLoaded(true);
			})();
	}, [page]);

	useIntersectionObserver(setPage, isDataLoaded);

	return (
		<BackLayout title={"알림"}>
			<div className={styles.notificationContainer}>
				{notificationData.map((item) => {
					const matchingType = TYPE.find(
						(typeItem) => typeItem.type === item.type,
					);
					if (matchingType) {
						const { title, icon } = matchingType;
						return (
							<Link
								key={item.id}
								href={item.path}
								className={styles.link}
								onClick={() => onLinkClick(item.id)}
							>
								<div className={styles.notificationList}>
									<span className={styles.icon}>{icon}</span>
									<span className={styles.ment}>
										<span>
											[{title}] {item.message}
										</span>
										<span className={styles.time}>{item.registerTime}</span>
									</span>
								</div>
							</Link>
						);
					}
				})}
			</div>
		</BackLayout>
	);
}
