import { disassemble } from "hangul-js";

export const toPhoneme = (word: string) => {
	return disassemble(word).join();
};
