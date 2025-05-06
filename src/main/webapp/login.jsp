<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login - NekoMart</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Login</h2>
        <form action="user" method="post">
            <input type="hidden" name="action" value="login">
            <input type="text" name="username" placeholder="Username" required><br>
            <input type="password" name="password" placeholder="Password" required><br>
            <button class="btn" type="submit">Login</button>
        </form>
        <p style="color:red">${error}</p>
        <a href="register.jsp">Register</a>
    </div>
</body>
</html>