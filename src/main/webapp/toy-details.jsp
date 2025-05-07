<%@ page import="com.nekomart.model.*,java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect("login.jsp"); return; }
    
    int toyId = Integer.parseInt(request.getParameter("id"));
    ToyLinkedList toys = com.nekomart.util.FileUtil.readToys();
    Toy toy = toys.get(toyId);
    
    if (toy == null) {
        response.sendRedirect("toys.jsp");
        return;
    }
    
    List<Review> reviews = com.nekomart.util.FileUtil.getReviewsForToy(toyId);
    double averageRating = com.nekomart.util.FileUtil.getAverageRating(toyId);
%>
<html>
<head>
    <title><%= toy.getName() %> - NekoMart</title>
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
        #sakura-canvas {
            position: fixed;
            top: 0;
            left: 0;
            pointer-events: none;
            z-index: 1;
        }
        .container {
            position: relative;
            z-index: 2;
            background-color: rgba(0, 0, 0, 0.4);
            border-radius: 16px;
            padding: 2rem;
            width: 100%;
            max-width: 1200px;
            margin: 2rem auto;
            border: 0.5px solid rgba(255,255,255,0.15);
        }
        .toy-details, .toy-info, .review, .review-form {
            background-color: transparent;
            border-radius: 16px;
            box-shadow: none;
            color: #f5f5f5;
        }
        .container th, .container td, .toy-details th, .toy-details td {
            color: #f5f5f5;
        }
        .container a.btn, .toy-details a.btn {
            color: #ffb6c1;
            border: 2px solid #ffb6c1;
            background: transparent;
            font-weight: bold;
            transition: color 0.3s, background 0.3s;
        }
        .container a.btn:hover, .toy-details a.btn:hover {
            color: #fff;
            background: #ff69b4;
            border-color: #ff69b4;
        }
        .toy-details {
            width: 100%;
            margin: 0;
            padding: 2rem 0;
        }
        .toy-header {
            display: block;
            margin-bottom: 2rem;
        }
        .toy-image {
            display: block;
            margin: 0 auto 2rem auto;
            width: 260px;
            height: auto;
            max-height: 340px;
            object-fit: contain;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            background: transparent;
        }
        .toy-header > div {
            width: 100%;
            text-align: left;
        }
        .toy-description, .toy-price, .toy-age, .rating {
            margin-left: 0;
            margin-right: 0;
            margin-bottom: 1.2rem;
        }
        .toy-price {
            font-size: 2.2rem;
            font-weight: bold;
            color: var(--primary-color);
        }
        .toy-age {
            background-color: rgba(51, 51, 51, 0.5);
            padding: 0.7rem 1.2rem;
            border-radius: var(--border-radius);
            font-size: 1.2rem;
            display: inline-block;
            color: #f5f5f5;
        }
        .rating {
            color: var(--secondary-color);
            font-size: 1.1rem;
            margin-bottom: 0.8rem;
        }
        .star {
            color: var(--secondary-color);
            font-size: 1.2em;
        }
        .review-section {
            margin-top: 4rem;
            width: 100%;
        }
        .toy-info {
            background: transparent;
            padding: 2rem 0;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
        }
        .review-form {
            background: transparent;
            padding: 2rem 0;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            margin-bottom: 2.5rem;
            width: 100%;
            max-width: none;
            margin-left: 0;
            margin-right: 0;
        }
        .review-form form {
            display: flex;
            flex-direction: column;
            align-items: stretch;
            gap: 1.2rem;
            width: 100%;
        }
        .form-group {
            display: flex;
            flex-direction: row;
            align-items: center;
            gap: 1rem;
            width: 100%;
        }
        .review-form label {
            font-weight: 500;
            margin-bottom: 0;
            display: block;
            min-width: 90px;
        }
        .review-form select {
            flex: 1;
            max-width: 300px;
            background: transparent;
            color: #f5f5f5;
            border: 1px solid #ffb6c1;
        }
        .review-form textarea {
            flex: 1;
            min-width: 0;
            min-height: 80px;
            max-width: 100%;
            box-sizing: border-box;
            background: transparent;
            color: #f5f5f5;
            border: 1px solid #ffb6c1;
        }
        .review-form button {
            width: 100%;
            max-width: 100%;
            box-sizing: border-box;
            align-self: flex-start;
        }
        .review {
            background-color: rgba(0, 0, 0, 0.4);
            border-radius: 16px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            position: relative;
        }
        .review .delete-review {
            position: absolute;
            bottom: 1rem;
            right: 1rem;
            background: transparent;
            border: 1px solid #ffb6c1;
            color: #ffb6c1;
            padding: 0.5rem 1rem;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .review .delete-review:hover {
            background: rgba(255, 182, 193, 0.2);
            color: #ff69b4;
        }
        .review-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1rem;
        }
        .review-author {
            font-weight: bold;
            color: var(--primary-color);
        }
        .review-date {
            color: #999;
            font-size: 0.9rem;
        }
        .review-content {
            margin-top: 1rem;
            line-height: 1.6;
            color: #f5f5f5;
        }
        .admin-controls {
            position: absolute;
            top: 1rem;
            right: 1rem;
        }
        .page-layout {
            display: flex;
            flex-direction: row;
            gap: 3rem;
            align-items: flex-start;
            width: 100%;
        }
        .sidebar {
            min-width: 220px;
            max-width: 260px;
        }
        .main-content {
            flex: 1;
            min-width: 0;
        }
        @media (max-width: 900px) {
            .page-layout {
                flex-direction: column;
                gap: 0;
            }
            .sidebar {
                max-width: 100%;
                min-width: 0;
            }
        }
        .toy-header > div:last-child {
            flex: 1;
            min-width: 0;
        }
        .header {
            width: 100%;
            margin-bottom: 2rem;
            background: none !important;
            box-shadow: none;
        }
        .header-content {
            display: flex;
            align-items: center;
            justify-content: space-between;
            width: 100%;
            position: relative;
            background: none !important;
        }
        .nav-menu {
            display: flex;
            flex-direction: row;
            align-items: center;
            width: 100%;
            list-style: none;
            margin: 0;
            padding: 0;
        }
        .nav-menu li {
            flex: 1;
            display: flex;
            align-items: center;
        }
        .nav-menu li.left {
            justify-content: flex-start;
            padding-left: 2rem;
        }
        .nav-menu li.center {
            justify-content: center;
        }
        .nav-menu li.right {
            justify-content: flex-end;
            padding-right: 2rem;
        }
        .nav-menu li.left a.btn {
            margin-left: 0;
        }
        .nav-menu li.right a.btn {
            margin-right: 0;
        }
        .logo {
            font-size: 2.1rem;
            font-weight: bold;
            color: #ffb6c1;
            text-decoration: none;
            letter-spacing: 2px;
            text-shadow: 0 2px 8px #222, 0 0 2px #fff;
            background: none;
            border: none;
            padding: 0;
            margin: 0;
            pointer-events: none;
            cursor: default;
            position: static;
            left: unset;
            transform: none;
        }
        .nav-menu a.btn {
            color: #ffb6c1;
            border: 2px solid #ffb6c1;
            background: transparent;
            font-weight: bold;
            transition: color 0.3s, background 0.3s;
            text-decoration: none;
            padding: 0.5rem 2rem;
            border-radius: 8px;
            font-size: 1.1rem;
            min-width: 120px;
            box-sizing: border-box;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }
        .nav-menu a.btn:hover {
            color: #fff;
            background: #ff69b4;
            border-color: #ff69b4;
        }
        .nav-menu form {
            display: none;
        }
        .bottom-buttons {
            display: flex;
            flex-direction: row;
            gap: 1.2rem;
            margin-top: 2.5rem;
        }
        .bottom-buttons .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 160px;
            height: 48px;
            padding: 0 2rem;
            font-size: 1.1rem;
            font-family: inherit;
            font-weight: 600;
            border: none;
            margin: 0;
            box-sizing: border-box;
            line-height: 1;
            vertical-align: middle;
            background: #ffb6c1;
            color: #222;
            cursor: pointer;
            border-radius: 8px;
            text-decoration: none;
            transition: background 0.2s;
        }
        .bottom-buttons .btn:hover {
            background: #ff69b4;
        }
    </style>
</head>
<body>
    <canvas id="sakura-canvas"></canvas>
    <div class="container fade-in">
        <header class="header">
            <div class="header-content">
                <nav style="width:100%;">
                    <ul class="nav-menu">
                        <li class="left"><a href="toys.jsp" class="btn btn-secondary">Toys</a></li>
                        <li class="center"><span class="logo">NekoMart</span></li>
                        <li class="right"><a href="cart.jsp" class="btn btn-secondary">Cart</a></li>
                    </ul>
                </nav>
            </div>
        </header>
        <div class="toy-details">
            <div class="toy-info">
                <div class="toy-header">
                    <div>
                        <% if (toy.getImage() != null && !toy.getImage().isEmpty()) { %>
                            <img src="images/toys/<%= toy.getImage() %>" alt="<%= toy.getName() %>" class="toy-image">
                        <% } else { %>
                            <img src="images/toys/default.jpg" alt="No image available" class="toy-image">
                        <% } %>
                    </div>
                    <div>
                        <h1><%= toy.getName() %></h1>
                        <div class="toy-description"><%= toy.getDescription() %></div>
                        <% if ("admin".equals(user.getRole())) { %>
                            <form action="toy" method="post" style="margin-top:1rem;">
                                <input type="hidden" name="action" value="updateDescription">
                                <input type="hidden" name="id" value="<%= toy.getId() %>">
                                <textarea name="description" rows="3" style="width:100%; background: rgba(255, 255, 255, 0.2); color: #f5f5f5;"><%= toy.getDescription() %></textarea>
                                <button class="btn btn-secondary" type="submit" style="margin-top:0.5rem;">Update Description</button>
                            </form>
                        <% } %>
                        <div class="toy-price">$<%= toy.getPrice() %></div>
                        <div class="toy-age">Age: <%= toy.getAgeGroup() %>+</div>
                        <div class="rating">
                            Average Rating: 
                            <span class="star-rating">
                                <% for (int i = 1; i <= 5; i++) { %>
                                    <span class="star"><%= i <= averageRating ? "★" : "☆" %></span>
                                <% } %>
                            </span>
                            (<%= String.format("%.1f", averageRating) %>)
                        </div>
                        <form action="cart" method="post" style="margin-top: 2rem;">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="id" value="<%= toy.getId() %>">
                            <button class="btn" type="submit">Add to Cart</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="review-section">
                <h2>Reviews</h2>
                <div class="review-form">
                    <form action="review" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="toyId" value="<%= toy.getId() %>">
                        <div class="form-group">
                            <label>Rating:</label>
                            <select name="rating" required>
                                <option value="5">5 Stars</option>
                                <option value="4">4 Stars</option>
                                <option value="3">3 Stars</option>
                                <option value="2">2 Stars</option>
                                <option value="1">1 Star</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Comment:</label>
                            <textarea name="comment" rows="4" required></textarea>
                        </div>
                        <button class="btn" type="submit">Submit Review</button>
                    </form>
                </div>
                <% for (Review review : reviews) { %>
                    <div class="review">
                        <% if (review.getUsername().equals(user.getUsername()) || "admin".equals(user.getRole())) { %>
                            <div class="admin-controls">
                                <form action="review" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%= review.getId() %>">
                                    <input type="hidden" name="toyId" value="<%= toy.getId() %>">
                                    <button type="submit" class="delete-review">Delete Review</button>
                                </form>
                            </div>
                        <% } %>
                        <div class="review-header">
                            <div>
                                <span class="review-author"><%= review.getUsername() %></span>
                                <span class="star-rating">
                                    <% for (int i = 1; i <= 5; i++) { %>
                                        <span class="star"><%= i <= review.getRating() ? "★" : "☆" %></span>
                                    <% } %>
                                </span>
                            </div>
                            <div class="review-date"><%= review.getDate() %></div>
                        </div>
                        <div class="review-content"><%= review.getComment() %></div>
                    </div>
                <% } %>
                <div class="bottom-buttons">
                    <a href="toys.jsp" class="btn btn-secondary">Back to Toys</a>
                    <form action="user" method="post">
                        <input type="hidden" name="action" value="logout">
                        <button type="submit" class="btn btn-secondary">Logout</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="js/sakura.js"></script>
    <script>
        window.addEventListener('load', () => {
            const canvas = document.getElementById('sakura-canvas');
            new Sakura(canvas);
        });
    </script>
</body>
</html> 