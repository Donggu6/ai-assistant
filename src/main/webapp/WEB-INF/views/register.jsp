<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>회원가입</title>
</head>
<body>

	<h2>회원가입</h2>

	<form method="post" action="/api/auth/register">
		<input type="email" name="email" placeholder="이메일"> <input
			type="password" name="password" placeholder="비밀번호">
		<button type="submit">가입하기</button>
	</form>

	<a href="/login">로그인으로</a>

</body>
</html>
