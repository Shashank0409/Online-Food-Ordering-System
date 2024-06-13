<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>

<html>
<head>
    <title>Cart</title>
    <link rel="stylesheet" href="cart.css">
</head>
<body>
    <header>
        <nav class="navbar">
            <div class="logo">FoodHub</div>
            <ul class="menu">
                <li><a href="menu.html">Menu</a></li>
            </ul>
        </nav>
    </header>
    
    <main>
        <div class="cart-items">
            <%
            	double cost=0,tax=0;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tt", "root", "root");
                    
                    Statement stmt = con.createStatement();
                    
                    String stmtquery = "select * from table1";
                    
                    ResultSet rs = stmt.executeQuery(stmtquery);
                    
                    Map<String, Integer> itemCost=new HashMap<>();
                    itemCost.put("BIRYANI",250);
                    itemCost.put("KADHAI CHICKEN",180);
                    itemCost.put("BUTTER NAAN",30);
                    itemCost.put("PIZZA",400);
                    itemCost.put("BURGER",150);
                    itemCost.put("COKE",50);
                    
                    Map<String, Integer> itemQuantities = new HashMap<>();
                    cost = 0;
                    
                    while (rs.next()) {
                        itemQuantities.put("BIRYANI", itemQuantities.getOrDefault("BIRYANI",0)+rs.getInt("biryani"));
                        itemQuantities.put("KADHAI CHICKEN", itemQuantities.getOrDefault("KADHAI CHICKEN",0)+rs.getInt("chicken"));
                        itemQuantities.put("BUTTER NAAN", itemQuantities.getOrDefault("BUTTER NAAN",0)+rs.getInt("naan"));
                        itemQuantities.put("PIZZA", itemQuantities.getOrDefault("PIZZA",0)+rs.getInt("pizza"));
                        itemQuantities.put("BURGER", itemQuantities.getOrDefault("BURGER",0)+rs.getInt("burger"));
                        itemQuantities.put("COKE", itemQuantities.getOrDefault("COKE",0)+rs.getInt("coke"));
                        
                        cost += rs.getDouble("cost");
                    }
                    
                    tax = 0.025 * cost;
                    
                    con.close();
                    
                    for (Map.Entry<String, Integer> entry : itemQuantities.entrySet()) {
                        String itemName = entry.getKey();
                        int quantity = entry.getValue();
                        
                        if (quantity > 0) {
            %>
            <div class="cart-item">
                <img src="<%= itemName %>.jpg">
                <div>
                    <h3><%= itemName %></h3>
                    <p>Price: <%= itemCost.get(itemName) %></p>
                    <p>Quantity: <%= quantity %></p>
                </div>
            </div>
            <%
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            %>
        </div>
        <form action="./order">
            <div class="cart-summary">
                <h3>Cart Summary</h3>
                <p>Total Cost: Rs.<span id="total-cost"><%= cost %></span></p>
                <p>Tax: Rs.<span id="tax"><%= tax %></span></p>
                <p>Amount to Pay: Rs.<span id="amount-to-pay"><%= cost+tax %></span></p>
                <button type="submit" class="checkout-button">Order</button>
            </div>
        </form>
    </main>
</body>
</html>
