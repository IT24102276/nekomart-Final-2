<%@ page import="com.nekomart.model.*,java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect("login.jsp"); return; }
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    if (cart == null) cart = new ArrayList<>();
    double total = 0;
%>
<html>
<head>
    <title>Cart - NekoMart</title>
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
    </style>
</head>
<body>
    <div class="container">
        <h2>Your Cart</h2>
        <table>
            <tr><th>Name</th><th>Qty</th><th>Price</th><th>Action</th></tr>
            <%
                for (CartItem item : cart) {
                    total += item.getToy().getPrice() * item.getQuantity();
            %>
            <tr>
                <td><%= item.getToy().getName() %></td>
                <td>
                    <form action="cart" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="<%= item.getToy().getId() %>">
                        <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" style="width:40px;">
                        <button class="btn" type="submit">Update</button>
                    </form>
                </td>
                <td>$<%= item.getToy().getPrice() %></td>
                <td>
                    <form action="cart" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="id" value="<%= item.getToy().getId() %>">
                        <button class="btn" type="submit">Remove</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
        <h3>Total: $<%= total %></h3>
        <form action="checkout" method="post">
            <button class="btn" type="submit">Checkout</button>
        </form>
        <a href="toys.jsp" class="btn">Back to Toys</a>
    </div>
</body>
</html>