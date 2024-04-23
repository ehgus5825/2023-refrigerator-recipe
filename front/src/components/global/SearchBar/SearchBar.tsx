import { Search } from "react-bootstrap-icons";
import Input from "../Input/Input";
import styles from "@/scss/pages.module.scss";
import { FormEvent } from "react";

type SearchInputProps = {
	keyword?: string;
	setKeyword?: Function;
	handleSubmit?: Function;
	handleSearchBtnClick?: Function;
	placeholder?: string;
	disabled?: boolean;
	focus?: boolean;
};

export default function SearchBar({
	keyword,
	setKeyword,
	handleSubmit,
	handleSearchBtnClick,
	placeholder,
	disabled,
	focus,
}: SearchInputProps) {
	const onSearchBtnClick = () => {
		handleSearchBtnClick && handleSearchBtnClick();
	};

	const onSearchSubmit = (e: FormEvent<HTMLFormElement>) => {
		e.preventDefault();
		handleSubmit && handleSubmit();
	};

	return (
		<form
			className="d-flex align-items-center gap-3"
			onSubmit={(e) => onSearchSubmit(e)}
		>
			<Input
				value={keyword}
				setValue={setKeyword}
				placeholder={placeholder}
				usage="search"
				focus={focus}
				disabled={disabled}
			/>
			<Search className={styles.icon} onClick={onSearchBtnClick} />
		</form>
	);
}
