<%@ page import="com.nekomart.model.*,java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect("login.jsp"); return; }
    ToyLinkedList toys = com.nekomart.util.FileUtil.readToys();
    String selectedAge = request.getParameter("ageGroup");
    if (selectedAge != null && !selectedAge.isEmpty()) {
        // Filter toys by selected age group
        ToyLinkedList filteredToyList = new ToyLinkedList();
        for (Toy t : toys.getAll()) {
            if (t.getAgeGroup() == Integer.parseInt(selectedAge)) {
                filteredToyList.add(t);
            }
        }
        toys = filteredToyList;
    }
%>
<html>
<head>
    <title>Toys - NekoMart</title>
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
        }
        .container.fade-in {
            background-color: rgba(34, 34, 34, 0.55);
            border: none;
            box-shadow: none;
            width: 100%;
            max-width: 1200px;
            padding: 2rem;
            text-align: center;
            border-radius: 16px;
        }
        .header {
            background: transparent;
            box-shadow: none;
            margin-bottom: 2rem;
        }
        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 2rem;
            position: relative;
        }
        .header-left {
            flex: 1;
            display: flex;
            justify-content: flex-start;
            align-items: center;
        }
        .header-center {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .header-right {
            flex: 1;
            display: flex;
            justify-content: flex-end;
            align-items: center;
        }
        .logo {
            font-size: 2.5rem;
            font-weight: bold;
            color: #ffb6c1;
            text-shadow: 2px 2px 8px #222, 0 0 2px #fff;
            margin-bottom: 1.5rem;
            display: inline-block;
            text-align: center;
        }
        .toy-card {
            background: rgba(34,34,34,0.80);
            border-radius: 16px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.06);
            padding: 1rem;
            transition: transform 0.3s;
            height: 100%;
            display: flex;
            flex-direction: column;
        }
        .filters {
            background: rgba(34,34,34,0.55);
            color: #fff;
            border-radius: 16px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.06);
        }
        .admin-section {
            background: rgba(34,34,34,0.80);
            border-radius: 16px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.06);
        }
        .toy-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: var(--border-radius);
            transition: transform 0.3s;
        }
        .toy-card:hover {
            transform: translateY(-5px);
        }
        .toy-card:hover .toy-image {
            transform: scale(1.05);
        }
        .toy-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 2rem;
            padding: 2rem 0;
        }
        .toy-name {
            font-size: 1.2rem;
            font-weight: bold;
            color: #f5f5f5;
            text-decoration: none;
            margin: 1rem 0;
            display: block;
        }
        .toy-name:hover {
            color: var(--primary-color);
        }
        .rating {
            color: var(--secondary-color);
            font-size: 1.1rem;
            margin: 0.5rem 0;
        }
        .star {
            color: var(--secondary-color);
        }
        .toy-price {
            font-size: 1.3rem;
            font-weight: bold;
            color: var(--primary-color);
            margin: 0.5rem 0;
        }
        .toy-age {
            background-color: #333;
            padding: 0.3rem 0.6rem;
            border-radius: var(--border-radius);
            font-size: 0.9rem;
            display: inline-block;
            margin: 0.5rem 0;
            color: #f5f5f5;
        }
        .admin-section h3 {
            color: var(--primary-color);
            margin-bottom: 1rem;
        }
        .admin-form {
            display: grid;
            gap: 1rem;
            max-width: 500px;
        }
        .action-buttons {
            display: flex;
            gap: 0.5rem;
            margin-top: 1rem;
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
        .btn, .btn-secondary, .btn-danger {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 120px;
            height: 40px;
            padding: 0 1.5rem;
            font-size: 1.05rem;
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
        .btn:hover, .btn-secondary:hover, .btn-danger:hover {
            background: #ff69b4;
        }
    </style>
</head>
<body>
    <div class="container fade-in">
        <header class="header">
            <div class="header-content">
                <div class="header-left">
                    <nav>
                        <ul class="nav-menu">
                            <li><a href="toys.jsp" class="btn btn-secondary">Toys</a></li>
                            <li><a href="cart.jsp" class="btn btn-secondary">Cart</a></li>
                        </ul>
                    </nav>
                </div>
                <div class="header-center">
                    <a href="index.jsp" class="logo">NekoMart</a>
                </div>
                <div class="header-right">
                    <form action="user" method="post" style="margin-left:auto;">
                        <input type="hidden" name="action" value="logout">
                        <button type="submit" class="btn btn-secondary" style="min-width:100px;">Logout</button>
                    </form>
                </div>
            </div>
        </header>

        <form action="toys.jsp" method="get">
            <div class="form-group">
                <label for="ageGroup" style="color: #ffb6c1; font-weight: bold;">Filter by Age Group:</label>
                <select name="ageGroup" id="ageGroup" class="form-control">
                    <option value="">All Ages</option>
                    <option value="3" <%= "3".equals(selectedAge) ? "selected" : "" %>>3+</option>
                    <option value="7" <%= "7".equals(selectedAge) ? "selected" : "" %>>7+</option>
                    <option value="12" <%= "12".equals(selectedAge) ? "selected" : "" %>>12+</option>
                </select>
            </div>
            <button class="btn btn-secondary" type="submit">Filter</button>
        </form>

        <form action="toy" method="post" style="margin-top: 1rem;">
            <input type="hidden" name="action" value="sort">
            <button class="btn btn-secondary" type="submit">Sort by Age Group</button>
        </form>

        <div class="toy-grid">
            <% for (Toy t : toys.getAll()) {
                double avgRating = com.nekomart.util.FileUtil.getAverageRating(t.getId());
            %>
            <div class="toy-card">
                <% if (t.getImage() != null && !t.getImage().isEmpty()) { %>
                    <img src="images/toys/<%= t.getImage() %>" alt="<%= t.getName() %>" class="toy-image">
                <% } else { %>
                    <img src="images/toys/default.jpg" alt="No image available" class="toy-image">
                <% } %>
                <a href="toy-details.jsp?id=<%= t.getId() %>" class="toy-name"><%= t.getName() %></a>
                <div class="rating">
                    <% for (int i = 1; i <= 5; i++) { %>
                        <span class="star"><%= i <= avgRating ? "★" : "☆" %></span>
                    <% } %>
                    (<%= String.format("%.1f", avgRating) %>)
                </div>
                <div class="toy-price">$<%= t.getPrice() %></div>
                <div class="toy-age">Age: <%= t.getAgeGroup() %>+</div>
                <div class="action-buttons">
                    <form action="cart" method="post" style="flex: 1;">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="id" value="<%= t.getId() %>">
                        <button class="btn" type="submit" style="width: 100%;">Add to Cart</button>
                    </form>
                    <% if ("admin".equals(user.getRole())) { %>
                    <form action="toy" method="post" style="flex: 1;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="<%= t.getId() %>">
                        <button class="btn btn-danger" type="submit" style="width: 100%;">Delete</button>
                    </form>
                    <% } %>
                </div>
            </div>
            <% } %>
        </div>

        <% if ("admin".equals(user.getRole())) { %>
        <div class="admin-section">
            <h3>Add New Toy</h3>
            <form action="toy" method="post" enctype="multipart/form-data" class="admin-form">
                <input type="hidden" name="action" value="add">
                <div class="form-group">
                    <input type="text" name="name" placeholder="Toy Name" required>
                </div>
                <div class="form-group">
                    <input type="text" name="description" placeholder="Description" required>
                </div>
                <div class="form-group">
                    <input type="number" step="0.01" name="price" placeholder="Price" required>
                </div>
                <div class="form-group">
                    <input type="number" name="ageGroup" placeholder="Age Group" required>
                </div>
                <div class="form-group">
                    <input type="file" name="image" accept="image/*">
                </div>
                <button class="btn btn-secondary" type="submit">Add Toy</button>
            </form>
        </div>
        <% } %>
    </div>
</body>
</html>