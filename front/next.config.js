const rewrites = async () => {
	return [
		{
			source: "/:path*",
			destination: "http://115.85.181.24:8080/:path*",
		},
	];
};

module.exports = { rewrites };
