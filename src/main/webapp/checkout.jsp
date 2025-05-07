<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Checkout - NekoMart</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            background: url('images/Frontend/wallpaperflare.com_wallpaper.jpg') no-repeat center center fixed;
            background-size: cover;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
            color: #f5f5f5;
        }
        .container {
            background-color: rgba(34, 34, 34, 0.55);
            border-radius: 16px;
            box-shadow: none;
            color: #f5f5f5;
        }
        .container th, .container td {
            color: #f5f5f5;
        }
        .container a {
            color: #ffb6c1;
        }
        .container a:hover {
            color: #ff69b4;
        }
        .container a.btn {
            color: #ffb6c1;
            border: 2px solid #ffb6c1;
            background: transparent;
            font-weight: bold;
            transition: color 0.3s, background 0.3s;
        }
        .container a.btn:hover {
            color: #fff;
            background: #ff69b4;
            border-color: #ff69b4;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>${message}</h2>
        <a href="toys.jsp" class="btn">Continue Shopping</a>
    </div>
</body>
</html>