import { Html, Head, Main, NextScript } from "next/document";

export default function Document() {
	return (
		<Html lang="en">
			<Head>
				{/* <meta
					httpEquiv="Content-Security-Policy"
					content="upgrade-insecure-requests"
				/> */}
				<meta http-equiv="Content-Security-Policy" content="img-src 'self' https://example.com"/>
				<link rel="icon" href="/favicon.ico" />
			</Head>
			<body>
				<Main />
				<NextScript />
			</body>
		</Html>
	);
}
