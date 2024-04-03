const rewrites = async () => {
	return [
		{
			source: "/:path*",
			destination: "http://15.165.98.171:8080/:path*",
		},
	];
};

module.exports = { rewrites };
