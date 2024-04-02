import router from "next/router";
import styles from "./ScrollContent.module.scss";
import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';

// Import Swiper styles
import 'swiper/css';
import 'swiper/css/pagination';

// import required modules
import { Scrollbar } from 'swiper/modules';

export default function ScrollContent(props: any) {

	const onContentClick = (recipeId: number) => {
		router.push(`/recipe/info?recipeID=${recipeId}`);
	};

	const onContentPlusClick = (content: string) => {
		router.push(`/recipe/${content}`);
	};

	return (
		<div className={styles.test}>
			<Swiper
				slidesPerView={3}
				// centeredSlides={true}
				spaceBetween={0}
				grabCursor={true}
				pagination={{
					clickable: true,
				}}
				modules={[Scrollbar]}
				className="mySwiper"
			>
				{props.starPreview?.map((i: any) => (
					<SwiperSlide key={i.scoreId} className={styles.testimg}>
						<div>
							<button className={styles.scroll_btnstyle}
								onClick={() => onContentClick(i.recipeId)}
							>
								<img className={styles.imgContainer}
									src={i.recipeImage}
									alt={i.recipeName}
								/>
							</button>
							<div className={styles.imgSpan}>
								<div>{i.recipeName}</div>
							</div>
						</div>
					</SwiperSlide>
				))}
				{props.bookPreview?.map((i: any) => (
					<SwiperSlide key={i.bookmarkId}>
						<div>
							<button className={styles.scroll_btnstyle}
								onClick={() => onContentClick(i.recipeId)}
							>
								<img className={styles.imgContainer}
									src={i.recipeImage}
									alt={i.recipeName}
								/>
							</button>
							<div className={styles.imgSpan}>
								<div>{i.recipeName}</div>
							</div>
						</div>
					</SwiperSlide>
				))}
				<SwiperSlide>
					<div>
						<button className={styles.scroll_btnstyle2}
							onClick={() => { onContentPlusClick(props.content); }}
						>
							<span className={styles.spanPlus}>+</span>
						</button>
					</div>
				</SwiperSlide>
			</Swiper>
		</div>
	)
}