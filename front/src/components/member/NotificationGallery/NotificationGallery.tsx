import styles from "./NotificationGallery.module.scss";

import { readNotification } from "@/api/notification";
import { NotificationList } from "@/types";

import {
	CupStraw,
	ExclamationTriangleFill,
	HandThumbsUpFill,
} from "react-bootstrap-icons";
import Link from "next/link";

type NotificationGalleryProps = {
	notificationData: NotificationList[];
};

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

export default function NotificationGallery({ notificationData }: NotificationGalleryProps) {

	return (
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
							<div className={styles.notificationList} style={item.readStatus === true ? {backgroundColor: "white"} : {backgroundColor: "#F2F2F2"}}>
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
		</div>
	);
}
