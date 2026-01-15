(() => {
	const DEFAULT = {
		mode: "dark",
		color: "blue"
	};

	function loadTheme() {
		try {
			return JSON.parse(localStorage.getItem("theme")) || DEFAULT;
		} catch {
			return DEFAULT;
		}
	}

	function saveTheme(theme) {
		localStorage.setItem("theme", JSON.stringify(theme));
	}

	function applyTheme(theme) {
		document.documentElement.dataset.mode = theme.mode;
		document.documentElement.dataset.color = theme.color;
	}

	function initSettingsPage() {
		const modeSelect = document.getElementById("themeMode");
		const colorSelect = document.getElementById("accentColor");
		const saveBtn = document.getElementById("saveBtn");

		if (!modeSelect || !colorSelect || !saveBtn) return;

		const theme = loadTheme();
		modeSelect.value = theme.mode;
		colorSelect.value = theme.color;

		saveBtn.addEventListener("click", () => {
			const newTheme = {
				mode: modeSelect.value,
				color: colorSelect.value
			};

			saveTheme(newTheme);
			applyTheme(newTheme);

			alert("테마가 저장되었습니다.");
		});
	}

	function initGlobalTheme() {
		const theme = loadTheme();
		applyTheme(theme);
	}

	document.addEventListener("DOMContentLoaded", () => {
		initGlobalTheme();
		initSettingsPage();
	});
})();

function goDashboard() {
	location.href = "dashboard.html";
}