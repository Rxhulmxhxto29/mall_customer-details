# Database Queries Guide

This document contains useful SQL queries to view and manage data in the H2 database.

## Access H2 Console

1. Open your browser and go to: **http://localhost:8080/h2-console**
2. Login with these credentials:
   - **JDBC URL**: `jdbc:h2:mem:malldb`
   - **User Name**: `SA`
   - **Password**: (leave empty)
3. Click **"Connect"**

## View Users and Admins

Once connected, run these SQL queries:

### View All Customers (Users)
```sql
SELECT c.id, c.email, c.first_name, c.last_name, c.status, c.registration_date
FROM customers c;
```

### View Customers with their Roles
```sql
SELECT c.id, c.email, c.first_name, c.last_name, r.name as role
FROM customers c
JOIN customer_roles cr ON c.id = cr.customer_id
JOIN roles r ON cr.role_id = r.id;
```

### View Only Admins
```sql
SELECT c.id, c.email, c.first_name, c.last_name
FROM customers c
JOIN customer_roles cr ON c.id = cr.customer_id
JOIN roles r ON cr.role_id = r.id
WHERE r.name = 'ROLE_ADMIN';
```

### View Only Regular Users (Customers)
```sql
SELECT c.id, c.email, c.first_name, c.last_name
FROM customers c
JOIN customer_roles cr ON c.id = cr.customer_id
JOIN roles r ON cr.role_id = r.id
WHERE r.name = 'ROLE_CUSTOMER';
```

### View All Roles
```sql
SELECT * FROM roles;
```

## Current Test User

Your application already has one test user created automatically:

- **Email**: `test@example.com`
- **Password**: `password`
- **Role**: `ROLE_CUSTOMER`

## Additional Useful Queries

### View All Products
```sql
SELECT p.id, p.name, p.price, p.stock_quantity, p.status, c.name as category
FROM products p
LEFT JOIN categories c ON p.category_id = c.id;
```

### View All Orders
```sql
SELECT o.id, o.order_number, o.order_date, o.status, o.total_amount, 
       c.email as customer_email
FROM orders o
JOIN customers c ON o.customer_id = c.id;
```

### View Cart Items
```sql
SELECT ci.id, c.email as customer_email, p.name as product_name, 
       ci.quantity, ci.price, (ci.quantity * ci.price) as subtotal
FROM cart_items ci
JOIN carts cart ON ci.cart_id = cart.id
JOIN customers c ON cart.customer_id = c.id
JOIN products p ON ci.product_id = p.id;
```

### View Order Items
```sql
SELECT oi.id, o.order_number, p.name as product_name, 
       oi.quantity, oi.price, (oi.quantity * oi.price) as subtotal
FROM order_items oi
JOIN orders o ON oi.order_id = o.id
JOIN products p ON oi.product_id = p.id;
```

### View Customer Addresses
```sql
SELECT a.id, c.email as customer_email, a.street, a.city, a.state, 
       a.country, a.zip_code, a.type, a.is_default
FROM addresses a
JOIN customers c ON a.customer_id = c.id;
```

### View All Categories
```sql
SELECT * FROM categories;
```

### View Payments
```sql
SELECT p.id, o.order_number, p.amount, p.payment_method, 
       p.status, p.payment_date, p.transaction_id
FROM payments p
JOIN orders o ON p.order_id = o.id;
```

## Database Schema Overview

The application uses the following tables:
- `customers` - User information
- `roles` - User roles (CUSTOMER, ADMIN)
- `customer_roles` - Many-to-many relationship between customers and roles
- `products` - Product catalog
- `categories` - Product categories
- `carts` - Shopping carts
- `cart_items` - Items in shopping carts
- `orders` - Customer orders
- `order_items` - Items in orders
- `addresses` - Customer addresses (shipping/billing)
- `payments` - Payment information

---

**Note**: This is an in-memory H2 database. All data will be reset when you restart the application.
