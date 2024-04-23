import { useEffect, useState } from "react";
import styles from "./styles.module.scss";

import { expirationNotification, notification } from "@/api/notification";

import { useIntersectionObserver } from "@/hooks";
import { NotificationList } from "@/types";
import { readNotification } from "@/api/notification";

import BackLayout from "@/components/layout/BackLayout";
import NotificationGallery from "@/components/member/NotificationGallery/NotificationGallery";

import {
	CupStraw,
	ExclamationTriangleFill,
	HandThumbsUpFill,
} from "react-bootstrap-icons";
import Link from "next/link";

const TYPE = [
	{
		type: "유통기한 3일 전",
		title: "유통기한 3일 전",
		icon: <ExclamationTriangleFill className={styles.iconColor} />,
	},
	{
		type: "유통기한 1일 전",
		title: "유통기한 1일 전",
		icon: <ExclamationTriangleFill className={styles.iconColor} />,
	},
	{
		type: "좋아요",
		title: "좋아요",
		icon: <HandThumbsUpFill className={styles.iconColor} />,
	},
	{
		type: "식재료",
		title: "식재료",
		icon: <CupStraw className={styles.iconColor} />,
	},
];

const onLinkClick = (id: number) => {
	readNotification(id);
};

export default function Notification() {

	const [notificationData, setNotificationData] = useState<NotificationList[]>([],);
	const [expNotificationData, setExpNotificationData] = useState<NotificationList[]>([],);
	const [page, setPage] = useState(0);
	const [reset, setReset] = useState("start");
	const [isDataLoaded, setIsDataLoaded] = useState(false);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

	useEffect(() => {
		(async () => {
			setPage(0);

			const expData = await expirationNotification();
			const data = await notification(page);

			setExpNotificationData(expData);
			setNotificationData(data);
			setIsScrollEnd(false);
			setIsDataLoaded(true);
		})();
	}, [reset]);

	useEffect(() => {
		(async () => {
			if (page != 0 && !isScrollEnd) {

				const data = await notification(page);

				data.length !== 0
					? setNotificationData((prev) => [...prev, ...data])
					: setIsScrollEnd(true);
			}
		})()
	}, [page]);

	useIntersectionObserver(setPage, isDataLoaded);

	return (
		<BackLayout title={"알림"}>
			<div className={styles.notificationContainer}>
				{expNotificationData.map((item) => {
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
								<div className={styles.notificationList} style={item.readStatus === true ? { backgroundColor: "white" } : { backgroundColor: "#F2F2F2" }}>
									<div>
										<span className={styles.icon}>{icon}</span>
									</div>
									<div className={styles.spanContainer}>
										<span className={styles.title}>
											[{title}]
										</span>
										<span className={styles.ment}>
											{item.message}
										</span>
										<span className={styles.time}>{item.registerTime}</span>
									</div>
								</div>
							</Link>
						);
					}
				})}
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
								<div className={styles.notificationList} style={item.readStatus === true ? { backgroundColor: "white" } : { backgroundColor: "#F2F2F2" }}>
									<div>
										<span className={styles.icon}>{icon}</span>
									</div>
									<div className={styles.spanContainer}>
										<span className={styles.title}>
											[{title}]
										</span>
										<span className={styles.ment}>
											{item.message}
										</span>
										<span className={styles.time}>{item.registerTime}</span>
									</div>
								</div>
							</Link>
						);
					}
				})}
				{isDataLoaded && <div id="end-of-list"></div>}
			</div>
		</BackLayout>
	);
}
