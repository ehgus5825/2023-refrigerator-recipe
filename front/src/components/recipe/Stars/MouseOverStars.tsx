import { calcStarRates } from "@/utils";
import styles from "./Stars.module.scss";

type StarsProps = {
	score: number;
	setScore: Function;
};

export default function MouseOverStars({ score, setScore }: StarsProps) {
	const ICON_SIZE = 42;
	const VIEWBOX_SIZE = 14;
	const starRates = calcStarRates(score, VIEWBOX_SIZE);

	const onStarClick = (event: any, value: number) => {
		const spanLeft = event.target.getBoundingClientRect().left;
		const clickX = event.clientX;
		const newScore = clickX < spanLeft + ICON_SIZE / 2 ? value - 0.5 : value;
		setScore(newScore);
	};

	return (
		<div className="d-flex">
			<div className={styles.mouseOverStarsContainer}>
				{starRates.map((rate, index) => (
					<span
						key={index}
						className={styles.starIcon}
						onClick={(event) => onStarClick(event, index + 1)}
					>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width={ICON_SIZE}
							height={ICON_SIZE}
							viewBox={`0 0 ${VIEWBOX_SIZE} ${VIEWBOX_SIZE}`}
							fill="#e0e0e0"
						>
							<clipPath id={`myStarClip${index}`}>
								<rect width={rate} height={ICON_SIZE} />
							</clipPath>
							<path
								id={`myStar${index}`}
								d="M9,2l2.163,4.279L16,6.969,12.5,10.3l.826,4.7L9,12.779,4.674,15,5.5,10.3,2,6.969l4.837-.69Z"
								transform="translate(-2 -2)"
							/>
							<use
								clipPath={`url(#myStarClip${index})`}
								href={`#myStar${index}`}
								fill="#FFC93C"
							/>
						</svg>
					</span>
				))}
			</div>
		</div>
	);
}
