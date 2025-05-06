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
            background: #181818;
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
            background-color: #333;
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
            background: #222;
            padding: 2rem 0;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
        }
        .review-form {
            background: #222;
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
        }
        .review-form textarea {
            flex: 1;
            min-width: 0;
            min-height: 80px;
            max-width: 100%;
            box-sizing: border-box;
        }
        .review-form button {
            width: 100%;
            max-width: 100%;
            box-sizing: border-box;
            align-self: flex-start;
        }
        .review {
            background: #222;
            padding: 2rem 3rem;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            margin: 2rem 0;
            position: relative;
            width: 100%;
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
        .delete-review {
            background-color: var(--accent-color);
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: var(--border-radius);
            cursor: pointer;
            font-size: 0.9rem;
            transition: background-color 0.3s;
        }
        .delete-review:hover {
            background-color: #c0392b;
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
        .logo {
            font-size: 2.1rem;
            font-weight: bold;
            color: #ffb6c1;
            text-decoration: none;
            letter-spacing: 2px;
            text-shadow: 0 2px 8px #222, 0 0 2px #fff;
            margin-bottom: 1.5rem;
            display: inline-block;
        }
        .nav-menu {
            display: flex;
            flex-direction: row;
            gap: 3rem;
            list-style: none;
            margin: 0;
            padding: 0;
            align-items: center;
        }
        .nav-menu li {
            margin: 0;
            padding: 0;
        }
        .nav-menu a {
            color: #222;
            background: #ffb6c1;
            text-decoration: none;
            font-weight: 600;
            padding: 0.5rem 2rem;
            border-radius: 8px;
            font-size: 1.1rem;
            transition: background 0.2s;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 160px;
            box-sizing: border-box;
        }
        .nav-menu a:hover {
            background: #ff69b4;
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
    <div class="container fade-in">
        <header class="header">
            <div class="header-content">
                <a href="index.jsp" class="logo">NekoMart</a>
                <nav>
                    <ul class="nav-menu">
                        <li><a href="toys.jsp" class="btn btn-secondary">Toys</a></li>
                        <li><a href="cart.jsp" class="btn btn-secondary">Cart</a></li>
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
</body>
</html> 