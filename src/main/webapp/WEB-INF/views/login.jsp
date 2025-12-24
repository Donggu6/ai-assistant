<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>로그인</title>
</head>
<body>

	<h2>로그인</h2>

	<input type="text" id="email" placeholder="이메일">
	<input type="password" id="password" placeholder="비밀번호">
	<button onclick="login()">로그인</button>

	<br>
	<br>
	<a href="/views/register.jsp">회원가입</a>

	<script>
    function login() {
      const email = document.getElementById("email").value;
      const password = document.getElementById("password").value;

      fetch("/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
      })
      .then(res => {
        if (!res.ok) {
          throw new Error("로그인 실패: 계정 정보를 확인하세요.");
        }
        return res.json();
      })
      .then(data => {
        alert("로그인 성공! 🎉");

        // 토큰 저장
        localStorage.setItem("accessToken", data.accessToken);
        localStorage.setItem("refreshToken", data.refreshToken);

        // (선택) 요약 API 호출 테스트
        return fetch("/api/ai/summarize", {
          method: "POST",
          headers: {
            "Authorization": "Bearer " + data.accessToken,
            "Content-Type": "application/json"
          },
          body: JSON.stringify({ text: "이건 테스트 요약 요청입니다." })
        });
      })
      .then(res => {
        if (res && res.ok) return res.json();
      })
      .then(result => {
        if (result) {
          alert("요약 결과: " + result.summary);
        }

        // 이후 페이지 이동
        window.location.href = "/views/dashboard.jsp";
      })
      .catch(err => {
        alert(err.message);
      });
    }
  </script>

</body>
</html>
