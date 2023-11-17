import { useEffect } from "react";

export const useIntersectionObserver = (
	setPage: Function,
	isDataLoaded: boolean,
) => {
	useEffect(() => {
		const handleIntersection = (entries: IntersectionObserverEntry[]) => {
			if (entries[0].isIntersecting) setPage();
		};
		const options = { threshold: 0.1 };

		const observer = new IntersectionObserver(handleIntersection, options);
		const target = document.querySelector("#end-of-list");
		target && observer.observe(target);

		return () => {
			target && observer.unobserve(target);
		};
	}, [isDataLoaded]);
};
