import { useEffect, useRef } from "react";
import { XCircleFill } from "react-bootstrap-icons";
import styles from "./Input.module.scss";

type InputType = {
	value?: string | number;
	setValue?: Function;
	valueType?: "text" | "number";
	placeholder?: string;
	usage?: "info" | "search" | "add" | "modal";
	focus?: boolean;
	disabled?: boolean;
};

export default function Input({
	value,
	setValue,
	valueType = "text",
	placeholder,
	usage = "info",
	focus,
	disabled,
}: InputType) {
	const inputRef = useRef(null);

	useEffect(() => {
		focus &&
			inputRef.current &&
			(inputRef.current as HTMLInputElement)?.focus();
	}, []);

	const onClearBtnClick = () => {
		setValue && setValue("");
		inputRef.current && (inputRef.current as HTMLInputElement)?.focus();
	};

	return (
		<div className={styles[`inputContainer_${usage}`]}>
			<input
				type={valueType}
				className={styles.inputContent}
				value={value}
				onChange={(e) =>
					setValue &&
					setValue(
						valueType == "text" ? e.target.value : Number(e.target.value),
					)
				}
				placeholder={placeholder}
				disabled={disabled}
				ref={inputRef}
			/>
			{value && !disabled && (
				<XCircleFill className={styles.clearBtn} onClick={onClearBtnClick} />
			)}
		</div>
	);
}
