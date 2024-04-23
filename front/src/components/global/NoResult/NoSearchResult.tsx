import styles from "./NoResult.module.scss";

type NoResultProps = {
	keyword?: string;
};

export default function NoSearchResult({ keyword }: NoResultProps) {
	return (
		<div className={styles.noResultContainer}>
			{keyword && <div className={styles.noResultKeyword}>{keyword}</div>}
			{keyword && <div>에 관한</div>}
			<div>검색 결과가 없어요</div>
		</div>
	);
}
