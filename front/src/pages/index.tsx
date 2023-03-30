import Head from "next/head";
import Image from "next/image";
import { Inter } from "next/font/google";
import { Button } from "react-bootstrap";
import Login from "./login/Login";

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
	return (
		<>
			<Head>
				<title>냉장고를 부탁해</title>
				<meta name="description" content="Generated by create next app" />
				<meta name="viewport" content="width=device-width, initial-scale=1" />
				<link rel="icon" href="/favicon.ico" />
			</Head>
			<Login />
		</>
	);
}