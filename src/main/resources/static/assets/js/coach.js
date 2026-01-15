const chat = document.getElementById("chat");
const input = document.getElementById("input");
const sendBtn = document.getElementById("sendBtn");

let aiStreamingEl = null;

// 같은 도메인에서 열면 자동으로 ws/wss 선택
const wsProtocol = location.protocol === "https:" ? "wss:" : "ws:";
const wsUrl = `${wsProtocol}//${location.host}/ws/coach`;
const socket = new WebSocket(wsUrl);

socket.onopen = () => {
	addAI("안녕하세요. 지금 상황을 한 줄로 말해줘. 숫자(원가/판매가/배송/수수료) 주면 바로 계산해줄게.");
};

socket.onmessage = (event) => {
	// 서버가 chunk로 보내므로 같은 말풍선에 계속 붙여서 “실시간 타이핑”처럼 보이게
	if (!aiStreamingEl) aiStreamingEl = addAI("");
	aiStreamingEl.textContent += event.data;
	scrollBottom();
};

socket.onclose = () => addAI("\n(연결이 끊겼어. 페이지 새로고침 후 다시 시도해줘.)");

function addUser(text) {
	const el = document.createElement("div");
	el.className = "msg user";
	el.textContent = text;
	chat.appendChild(el);
	scrollBottom();
}

function addAI(text) {
	const el = document.createElement("div");
	el.className = "msg ai";
	el.textContent = text;
	chat.appendChild(el);
	scrollBottom();
	return el;
}

function scrollBottom() {
	chat.scrollTop = chat.scrollHeight;
}

function send() {
	const msg = input.value.trim();
	if (!msg) return;

	// 새 질문 시작: AI 스트리밍 말풍선 초기화
	aiStreamingEl = null;

	addUser(msg);
	socket.send(msg);
	input.value = "";
	input.focus();
}

sendBtn.addEventListener("click", send);
input.addEventListener("keydown", (e) => {
	if (e.key === "Enter") send();
});

document.querySelectorAll(".chip").forEach(chip => {
	chip.addEventListener("click", () => {
		input.value = chip.dataset.msg;
		send();
	});
});
