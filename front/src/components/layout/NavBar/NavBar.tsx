import React from "react";
import router from "next/router";
import { NAV_ITEMS } from "./navItems";
import styles from "./NavBar.module.scss";

type NavbarProps = {
	activeLabel: string;
};

export default function NavBar({ activeLabel }: NavbarProps) {
	return (
		<div className={styles.navContainer}>
			{NAV_ITEMS.map((item) => (
				<div
					key={item.label}
					className={
						activeLabel == item.label ? styles.navItem_active : styles.navItem
					}
					onClick={() => router.replace(item.route)}
				>
					{item.icon}
					<div className={styles.navLabel}>{item.label}</div>
				</div>
			))}
		</div>
	);
}
