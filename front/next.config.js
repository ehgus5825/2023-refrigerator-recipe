const rewrites = async () => {
	return [
		{
			source: "/:path*",
			destination: "http://127.0.0.1:8080/:path*",
		},
	];
};

module.exports = { rewrites };
