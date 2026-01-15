const TOKEN_KEY = "ai_token";
const PLAN_KEY = "ai_plan";

function saveAuth(token, plan) {
	localStorage.setItem(TOKEN_KEY, token);
	localStorage.setItem(PLAN_KEY, plan || "FREE");
}

function clearAuth() {
	localStorage.removeItem(TOKEN_KEY);
	localStorage.removeItem(PLAN_KEY);
}

function getToken() {
	return localStorage.getItem(TOKEN_KEY);
}

function getPlan() {
	return localStorage.getItem(PLAN_KEY) || "FREE";
}



async function apiFetch(url, options = {}) {
	const token = getToken();

	const headers = Object.assign(
		{ "Content-Type": "application/json" },
		options.headers || {}
	);

	if (token) headers["Authorization"] = "Bearer " + token;

	const res = await fetch(url, { ...options, headers });
	const text = await res.text();

	let data = {};
	try {
		data = text ? JSON.parse(text) : {};
	} catch {
		data = { raw: text };
	}

	if (!res.ok) {
		const msg = data?.message || data?.error || ("HTTP " + res.status);
		throw new Error(msg);
	}

	return data;
}

const AuthApi = {
	token: getToken,
	plan: getPlan,

	async register(email, password, twoFactorEnabled) {
		await apiFetch("/api/auth/register", {
			method: "POST",
			body: JSON.stringify({
				email,
				password,
				twoFactorEnabled: !!twoFactorEnabled
			})
		});
		return true;
	},

	async login(email, password) {
		const data = await apiFetch("/api/auth/login", {
			method: "POST",
			body: JSON.stringify({ email, password })
		});
		return data;
	},

	async verify2fa(twoFactorToken, code) {
		const data = await apiFetch("/api/auth/2fa/verify", {
			method: "POST",
			body: JSON.stringify({ twoFactorToken, code })
		});
		return data;
	},

	async logout() {
		try {
			await fetch("/api/auth/logout", { method: "POST" });
		} catch (e) { }
		clearAuth();
		location.href = "/index.html";
	}
};

// ✅ HTML에서 바로 쓰게 전역 등록
window.AuthApi = AuthApi;
