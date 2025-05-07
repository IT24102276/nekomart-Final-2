<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register - NekoMart</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body.login-register {
            background: url('images/Frontend/wallpaperflare.com_wallpaper (1).jpg') no-repeat center center fixed;
            background-size: cover;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
        }
        .container {
            background-color: rgba(34, 34, 34, 0.55);
            border: none;
            box-shadow: none;
            width: 100%;
            max-width: 400px;
            padding: 2rem;
            text-align: center;
            border-radius: 16px;
        }
        .container h2 {
            color: #ffb6c1;
            font-size: 2.5rem;
            text-shadow: 2px 2px 8px #222, 0 0 2px #fff;
            margin-bottom: 2rem;
            text-align: center;
            background: rgba(0,0,0,0.18);
            border-radius: 8px;
            padding: 0.5rem 0;
            display: inline-block;
            width: 100%;
        }
        .container a {
            color: #ffb6c1;
            font-size: 1.2rem;
            text-decoration: none;
            font-weight: bold;
            display: block;
            text-align: center;
            margin-top: 1rem;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
            transition: color 0.3s ease, background 0.3s ease;
            -webkit-text-stroke: 1px #ff69b4;
            padding: 0.5rem;
            border: 2px solid #ff69b4;
            border-radius: 8px;
            background: rgba(0,0,0,0.12);
        }
        .container a:hover {
            color: #ff69b4;
            background-color: rgba(255, 105, 180, 0.18);
        }
    </style>
</head>
<body class="login-register">
    <div class="container">
        <h2>Register</h2>
        <form action="user" method="post">
            <input type="hidden" name="action" value="register">
            <input type="text" name="username" placeholder="Username" required><br>
            <input type="password" name="password" placeholder="Password" required><br>
            <button class="btn" type="submit">Register</button>
        </form>
        <p style="color:red">${error}</p>
        <a href="login.jsp">Login</a>
    </div>
</body>
</html>