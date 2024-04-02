import { useEffect, useState } from "react";
import styles from "./styles.module.scss";
import LinkBtn from "@/components/member/LinkBtn/LinkBtn";
import ScrollContent from "@/components/member/ScrollContent/ScrollContent";
import ProfileModal from "@/components/member/ProfileModal/ProfileModal";
import { profile } from "@/api/member";
import MypageBackLayout from "@/components/layout/MypageBackLayout"; import NoContentResult from "@/components/global/NoResult/NoContentResult";
;

export default function Mypage() {
	const [nick, setNick] = useState("");
	const [img, setImg] = useState("");
	const [bookPreview, setBookPreview] = useState<Array<object>>([]);
	const [starPreview, setStarPreview] = useState<Array<object>>([]);
	const [bookmarkCount, setBookmarkCount] = useState(0);
	const [starCount, setStarCount] = useState(0);
	const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);

	const openProfileModal = () => {
		setIsProfileModalOpen(true);
	};

	const closeProfileModal = () => {
		setIsProfileModalOpen(false);
	};

	const handleImgChange = (newImg: string) => {
		setImg(newImg);
	};

	useEffect(() => {
		(async () => {
			try {
				const response = await profile();
				const existingNick = (response !== undefined) ? response.myProfile.nickname : "";
				const existingImage = (response !== undefined) ? response.myProfile.profileImage : "";

				setNick(existingNick);
				setImg(existingImage);

				setBookPreview(response.myBookmarks.bookmarks);
				setBookmarkCount(response.myBookmarks.count);

				setStarPreview(response.myScores.myScorePreviews);
				setStarCount(response.myScores.myScoreSize);

			} catch (error) {
				console.error(error);
			}
		})();
	}, []); // 개인 정보 조회 받아오기

	return (
		<MypageBackLayout title={"마이페이지"}>
			<div className={styles.mypageContainer}>
				<div className={styles.mypageMe}>
					<img
						className={styles.mypageImg}
						src={img}
						onClick={openProfileModal}
					/>
					{isProfileModalOpen && (
						<div onClick={closeProfileModal}>
							<div>
								<ProfileModal
									on={true}
									onImgChange={handleImgChange}
								/>
							</div>
						</div>
					)}
					<div className={styles.square}>
						<span className={styles.mypageNickContainer}>
							<span className={styles.mypageNickname}> {nick}</span>
							<LinkBtn
								title={"닉네임 변경"}
								link={"../member/nickname"}
							></LinkBtn>
						</span>
					</div>
				</div>
				<div className={styles.mypageStar}>
					<span className={styles.mypageStarTitle}>
						내가 남긴 별점 ({starCount})
					</span>
					{starCount !== 0 ?
						(<ScrollContent content="ratings" starPreview={starPreview} />) :
						(<NoContentResult keyword="별점" />)}
				</div>
				<div className={styles.mypageBookmark}>
					<span className={styles.mypageBookmarkTitle}>
						북마크 ({bookmarkCount})
					</span>
					{bookmarkCount !== 0 ?
						(<ScrollContent content="bookmarks" bookPreview={bookPreview} />) :
						(<NoContentResult keyword="북마크" />)}
				</div>
			</div>
		</MypageBackLayout>
	);
}
