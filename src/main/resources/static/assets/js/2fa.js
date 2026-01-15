// 2fa.js

document.addEventListener("DOMContentLoaded", () => {

  const params = new URLSearchParams(location.search);
  const email = params.get("email");

  const codeInput = document.getElementById("code");
  const btn = document.getElementById("btn");
  const msg = document.getElementById("msg");

  if (!email) {
    msg.textContent = "이메일 정보가 없습니다. 다시 로그인하세요.";
    btn.disabled = true;
    return;
  }

  btn.addEventListener("click", async () => {
    const code = codeInput.value.trim();
    msg.textContent = "";

    if (!/^\d{6}$/.test(code)) {
      msg.textContent = "6자리 숫자 코드를 입력하세요.";
      return;
    }

    try {
      const res = await fetch("/api/2fa/verify", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, code })
      });

      if (!res.ok) {
        const text = await res.text();
        msg.textContent = "2FA 실패: " + text;
        return;
      }

      const data = await res.json();
      localStorage.setItem("ACCESS_TOKEN", data.token);

      // 성공 → 대시보드로 이동
      location.href = "/dashboard.html";

    } catch (e) {
      console.error(e);
      msg.textContent = "서버 오류가 발생했습니다.";
    }
  });

});
