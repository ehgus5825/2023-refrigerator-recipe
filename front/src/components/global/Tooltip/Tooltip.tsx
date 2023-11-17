import styles from "./Tooltip.module.scss";

type TooltipProps = {
	messageLines: string[];
	style?: any;
	children: React.ReactNode;
};

export default function Tooltip({
	messageLines,
	style,
	children,
}: TooltipProps) {
	return (
		<div className={styles.tooltipContainer}>
			{children}
			<div className={styles.tooltip} style={style}>
				{messageLines.map((line, index) => (
					<span key={index}>
						{line}
						<br />
					</span>
				))}
			</div>
		</div>
	);
}
