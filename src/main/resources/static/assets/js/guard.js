// assets/js/guard.js

function goDashboard() {
  const token = localStorage.getItem("ai_token");

  if (!token) {
    alert("로그인이 필요합니다.");
    location.href = "/login.html";
    return;
  }

  location.href = "/dashboard.html";
}

