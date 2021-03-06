module.exports = {
	title: "Crisp",
	description: "Documentation for the Crisp programming language",
	base: "/crisp/",
	themeConfig: {
		nav: [
			{ text: "Source", link: "https://github.com/swissChili/crisp" },
			{ text: "Guide", link: "/guide/" },
			{ text: "Developer Docs", link: "/dev/" }
		],
		sidebarDepth: 2,
		displayAllHeaders: true,
		sidebar: [
			"/guide/",
			"/dev/"
		]
	}
}