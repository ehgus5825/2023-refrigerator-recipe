import Link from "next/link";
import { Bell, CircleFill } from "react-bootstrap-icons";
import styles from "./AppBar.module.scss";
import { useEffect, useState } from "react";
import { getIsNotificationRead } from "@/api/notification";

export const NotificationIcon = () => {
	const [isNotificationRead, setIsNotificationRead] = useState(false);
	useEffect(() => {
		(async () => {
			const data = await getIsNotificationRead();
			setIsNotificationRead(data);
		})();
	}, []);

	return (
		<Link href={`/notification`} style={{ position: "relative" }}>
			<Bell className={styles.appbarIcon} style={{ width: "22" }} />
			{isNotificationRead && <CircleFill className={styles.notiDotIcon} />}
		</Link>
	);
};
