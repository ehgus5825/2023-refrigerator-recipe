import router from "next/router";
import styles from "./SearchPanel.module.scss";
import { deleteLateSearch } from "@/api";

type SearchTagsProps = {
	tagData: string[];
	setTagData?: Function;
	deleteBtn?: boolean;
};

export default function SearchTags({
	tagData,
	setTagData,
	deleteBtn,
}: SearchTagsProps) {
	const onSearchTagClick = (tag: string) => {
		router.replace(`/recipe/search?query=${tag}`);
	};

	const onDeleteTagBtnClick = async (tag: string) => {
		await deleteLateSearch(tag);
		setTagData &&
			setTagData((prevTags: string[]) =>
				prevTags.filter((prevTag) => prevTag !== tag),
			);
	};

	return (
		<div className={styles.tagsContainer}>
			{tagData.map((tag) => (
				<button key={tag} className={styles.tag}>
					<span onClick={() => onSearchTagClick(tag)}>{tag}</span>
					{deleteBtn && <div onClick={() => onDeleteTagBtnClick(tag)}>×</div>}
				</button>
			))}
		</div>
	);
}
