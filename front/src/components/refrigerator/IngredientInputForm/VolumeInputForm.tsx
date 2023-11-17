import Input from "@/components/global/Input/Input";
import styles from "./IngredientInputForm.module.scss";

type volumeinputType = {
	volume: number | string;
	setVolume?: Function;
	unit: string;
	disabled?: boolean;
};

export default function VolumeInputForm({
	volume,
	setVolume,
	unit,
	disabled,
}: volumeinputType) {
	return (
		<div className={styles.volumeInputForm}>
			<Input
				value={volume}
				setValue={setVolume}
				valueType="number"
				disabled={disabled}
				placeholder="숫자를 입력해주세요"
			/>
			<div className={styles.unit}>{unit}</div>
		</div>
	);
}
