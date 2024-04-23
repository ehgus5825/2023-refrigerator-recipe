import Link from "next/link";
import { Bell, BellFill, CircleFill } from "react-bootstrap-icons";
import styles from "./AppBar.module.scss";
import { useEffect, useState } from "react";
import { getIsNotificationRead, notificationSignOff } from "@/api/notification";

export const NotificationIcon = () => {
	const [isNotificationRead, setIsNotificationRead] = useState(false);

	useEffect(() => {
		(async () => {
			const data = await getIsNotificationRead();
			setIsNotificationRead(data);
		})();
	}, []);

	const notificationSignChange = async () => {
		try {
			await notificationSignOff();
		} catch (error) {
			console.log(error);
		}
	};

	return (
		<Link href={`/notification`} style={{ position: "relative" }}>
			<BellFill className={styles.bellIcon} style={{ width: "22" }} onClick={notificationSignChange} />
			{isNotificationRead && <CircleFill className={styles.notiDotIcon} />}
		</Link>
	);
};
