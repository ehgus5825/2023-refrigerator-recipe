import { useEffect, useState } from "react";
import styles from "./styles.module.scss";
import { PencilFill, GearFill } from "react-bootstrap-icons";
import LinkBtn from "@/components/member/LinkBtn/LinkBtn";
import ScrollContent from "@/components/member/ScrollContent/ScrollContent";
import ProfileModal from "@/components/member/ProfileModal/ProfileModal";
import { logout } from "@/api/logout";
import { unregister } from "@/api/unregister";
import { profile, nickname, bookmarkPreview, scorePreview } from "@/api";
import BackLayout from "@/components/layout/BackLayout";
import ConfirmCancelModal from "@/components/member/ConfirmCancelModal/ConfirmCancelModal";
import { Button } from "react-bootstrap";

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

	const fetchProfile = async () => {
		try {
			const response = await profile();
			const existingNick = response !== undefined ? response.data.nickname : "";
			setNick(existingNick);
			const existingImage =
				response !== undefined ? response.data.profileImage : "";
			setImg(existingImage);
		} catch (error) {}
	}; // 회원 정보 가져오기

	const fetchBookmarkPreview = async () => {
		try {
			const response = await bookmarkPreview();
			setBookPreview(response.bookmarks);
			setBookmarkCount(response.count);
		} catch (error) {
			console.log(error);
		}
	};

	const fetchScorePreview = async () => {
		try {
			const response = await scorePreview();
			setStarPreview(response.scores);
			setStarCount(response.count);
			//console.log(starPreview);
		} catch (error) {
			console.log(error);
		}
	};

	const handleImgChange = (newImg: string) => {
		setImg(newImg);
	};

	useEffect(() => {
		fetchProfile();
	}, [img]); // 개인 정보 조회 받아오기

	useEffect(() => {
		fetchBookmarkPreview();
		fetchScorePreview();
	}, []);

	const onNicknameBtnClick = () => {
		//setInputActive(true);
	}; // 닉네임 변경 클릭

	return (
		<BackLayout title={"마이페이지"}>
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
									img={img}
									onImgChange={handleImgChange}
								/>
							</div>
						</div>
					)}
					<div className={styles.mypageBackground}>
						<span className={styles.mypageNickContainer}>
							안녕하세요, <span className={styles.mypageNickname}> {nick}</span>{" "}
							님{" "}
						</span>
					</div>
					<div className={styles.mypageChange}>
						<LinkBtn
							title={"닉네임 변경"}
							link={"../member/nickname"}
						></LinkBtn>
						<span className={styles.mypageBar}>|</span>
						<LinkBtn
							title={"비밀번호 변경"}
							link={"../member/password/change"}
						></LinkBtn>
					</div>
				</div>
				<div className={styles.mypageStar}>
					<span className={styles.mypageStarTitle}>
						내가 남긴 별점 ({starCount})
					</span>
					<ScrollContent content="ratings" starPreview={starPreview} />
				</div>
				<div className={styles.mypageBookmark}>
					<span className={styles.mypageBookmarkTitle}>
						북마크 ({bookmarkCount})
					</span>
					<ScrollContent content="bookmarks" bookPreview={bookPreview} />
				</div>
				<div>
					{/* <LinkBtn title={"비밀번호 변경"} link={"../member/password/change"} /> */}
					<ConfirmCancelModal
						style="logoutBtn"
						variant="primary"
						title="로그아웃"
						ment="로그아웃 하시겠습니까?"
						api={logout}
					/>
				</div>
				<div className={styles.mypageUnregister}>
					<span>회원을 탈퇴하시겠습니까?</span>
					<ConfirmCancelModal
						style="unregisterBtn"
						variant="link"
						title="회원탈퇴"
						ment="정말 탈퇴하시겠습니까?"
						api={unregister}
					/>
				</div>
			</div>
		</BackLayout>
	);
}
