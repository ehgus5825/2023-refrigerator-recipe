import { calcStarRates } from "@/utils";
import styles from "./Stars.module.scss";

type StarsProps = {
	id: number;
	score: number;
	label?: boolean;
};

export default function Stars({ id, score, label }: StarsProps) {
	const ICON_SIZE = 16;
	const VIEWBOX_SIZE = 14;
	const starRates = calcStarRates(score, VIEWBOX_SIZE);

	return (
		<div className="d-flex">
			<div className={styles.starsContainer}>
				{starRates.map((rate, index) => (
					<span key={index} className={styles.starIcon}>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width={ICON_SIZE}
							height={ICON_SIZE}
							viewBox={`0 0 ${VIEWBOX_SIZE} ${VIEWBOX_SIZE}`}
							fill="#e0e0e0"
						>
							<clipPath id={`star${id}Clip${index}`}>
								<rect width={rate} height={ICON_SIZE} />
							</clipPath>
							<path
								id={`star${id}${index}`}
								d="M9,2l2.163,4.279L16,6.969,12.5,10.3l.826,4.7L9,12.779,4.674,15,5.5,10.3,2,6.969l4.837-.69Z"
								transform="translate(-2 -2)"
							/>
							<use
								clipPath={`url(#star${id}Clip${index})`}
								href={`#star${id}${index}`}
								fill="#FFC93C"
							/>
						</svg>
					</span>
				))}
			</div>
			{label && (
				<div className={styles.scoreInfo}>{` ${score.toFixed(1)}`}</div>
			)}
		</div>
	);
}
