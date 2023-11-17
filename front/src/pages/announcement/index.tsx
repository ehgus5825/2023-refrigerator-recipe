import ListGroup from "react-bootstrap/ListGroup";
import styles from "./announcement.module.scss";
import { PatchExclamation, PatchExclamationFill } from "react-bootstrap-icons";

export default function Announcement() {
	const alertClicked = () => {
		alert("You clicked the third ListGroupItem");
	};

	return (
		<ListGroup defaultActiveKey="#" className={styles.announcementContainer}>
			<span className={styles.announcementTitle}>공지사항</span>
			<ListGroup.Item action href="#link1" className={styles.announcementList}>
				<PatchExclamation className={styles.icon} />
				3/2(수) 정기점검 안내
			</ListGroup.Item>
			<ListGroup.Item action href="#link2" className={styles.announcementList}>
				<PatchExclamation className={styles.icon} />
				3/11(토) 정기점검 안내
			</ListGroup.Item>
			<ListGroup.Item action href="#link3" className={styles.announcementList}>
				<PatchExclamationFill className={styles.icon} />
				3/13(월) 긴급점검 안내
			</ListGroup.Item>
			<ListGroup.Item action href="#link4" className={styles.announcementList}>
				<PatchExclamationFill className={styles.icon} />
				3/13(월) 긴급점검 안내
			</ListGroup.Item>
			<ListGroup.Item action href="#link5" className={styles.announcementList}>
				<PatchExclamationFill className={styles.icon} />
				3/13(월) 긴급점검 안내
			</ListGroup.Item>
			<ListGroup.Item action href="#link6" className={styles.announcementList}>
				<PatchExclamationFill className={styles.icon} />
				3/13(월) 긴급점검 안내
			</ListGroup.Item>
			<ListGroup.Item action href="#link7" className={styles.announcementList}>
				<PatchExclamationFill className={styles.icon} />
				3/13(월) 긴급점검 안내
			</ListGroup.Item>
			<ListGroup.Item action href="#link8" className={styles.announcementList}>
				<PatchExclamationFill className={styles.icon} />
				3/13(월) 긴급점검 안내
			</ListGroup.Item>
			<ListGroup.Item action href="#link9" className={styles.announcementList}>
				<PatchExclamationFill className={styles.icon} />
				3/13(월) 긴급점검 안내
			</ListGroup.Item>
			<ListGroup.Item action href="#link10" className={styles.announcementList}>
				<PatchExclamationFill className={styles.icon} />
				3/13(월) 긴급점검 안내
			</ListGroup.Item>
			{/* <ListGroup.Item action onClick={alertClicked}>
				식재료 업데이트 안내
			</ListGroup.Item> */}
		</ListGroup>
	);
}
// 화면으로 구성할 경우 현재처럼 링크 넘겨주고, 모달로 구성할 경우 더 생각해보기
