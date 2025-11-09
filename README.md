# Shopping Mall E-Commerce Application

A comprehensive full-stack shopping mall e-commerce application built with Spring Boot backend and HTML/CSS/JavaScript frontend.

## 🔗 Live Demo
**GitHub Repository**: [https://github.com/Rxhulmxhxto29/mall_customer-details](https://github.com/Rxhulmxhxto29/mall_customer-details)

## 🚀 Features

### Customer Features
- **User Authentication**: Register and login with JWT-based authentication
- **Product Browsing**: View products by category with search functionality
- **Shopping Cart**: Add, update, and remove items from cart
- **Order Management**: Place orders and track order history
- **User Profile**: Manage customer information and addresses
- **Payment Integration**: Support for multiple payment methods

### Technical Features
- **RESTful API**: Complete REST API for all operations
- **Security**: Spring Security with JWT authentication
- **Database**: H2 in-memory database (development) / MySQL (production)
- **Responsive Design**: Mobile-friendly frontend interface
- **Data Validation**: Input validation and error handling

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Modern web browser
- (Optional) MySQL database for production

## 🛠️ Technology Stack

### Backend
- **Spring Boot 3.2.0**: Main framework
- **Spring Data JPA**: Database operations
- **Spring Security**: Authentication and authorization
- **JWT (JSON Web Tokens)**: Secure authentication
- **H2 Database**: Development database
- **MySQL**: Production database support
- **Lombok**: Reduce boilerplate code

### Frontend
- **HTML5**: Structure
- **CSS3**: Styling with modern design
- **JavaScript (ES6+)**: Dynamic functionality
- **Fetch API**: Backend communication

## 📦 Project Structure

```
mall/
├── src/
│   ├── main/
│   │   ├── java/com/shopping/mall/
│   │   │   ├── config/           # Configuration classes
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── DataInitializer.java
│   │   │   ├── controller/       # REST Controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── CartController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   └── CategoryController.java
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   └── AuthResponse.java
│   │   │   ├── entity/           # JPA Entities
│   │   │   │   ├── Customer.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Cart.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── Category.java
│   │   │   │   ├── Address.java
│   │   │   │   ├── Payment.java
│   │   │   │   └── Role.java
│   │   │   ├── repository/       # Data Repositories
│   │   │   ├── security/         # Security Components
│   │   │   │   ├── JwtTokenProvider.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   ├── service/          # Business Logic
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── CartService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   └── CategoryService.java
│   │   │   └── MallApplication.java
│   │   └── resources/
│   │       ├── static/           # Frontend files
│   │       │   ├── index.html
│   │       │   ├── login.html
│   │       │   ├── register.html
│   │       │   ├── products.html
│   │       │   ├── cart.html
│   │       │   ├── orders.html
│   │       │   ├── css/style.css
│   │       │   └── js/
│   │       │       ├── app.js
│   │       │       ├── auth.js
│   │       │       ├── home.js
│   │       │       ├── products.js
│   │       │       ├── cart.js
│   │       │       └── orders.js
│   │       └── application.properties
│   └── test/                     # Test files
├── pom.xml
└── README.md
```

## 🚦 Getting Started

### 1. Clone or Navigate to the Project

```bash
cd "c:\Users\WIN10 HOME 22H2\Desktop\mall"
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access the Application

- **Frontend**: Open your browser and go to `http://localhost:8080`
- **H2 Console**: `http://localhost:8080/h2-console`

#### Access H2 Database Console:

1. Open your browser and go to: **http://localhost:8080/h2-console**
2. Login with these credentials:
   - **JDBC URL**: `jdbc:h2:mem:malldb`
   - **User Name**: `SA`
   - **Password**: (leave empty)
3. Click **"Connect"**

## 👤 Test Account

A test account is automatically created when the application starts:

- **Email**: `test@example.com`
- **Password**: `password`

## 📚 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new customer
- `POST /api/auth/login` - Login and get JWT token

### Products
- `GET /api/products` - Get all available products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{categoryId}` - Get products by category
- `GET /api/products/search?keyword={keyword}` - Search products

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID

### Cart (Requires Authentication)
- `GET /api/cart/{customerId}` - Get customer's cart
- `POST /api/cart/{customerId}/items` - Add item to cart
- `PUT /api/cart/{customerId}/items/{productId}` - Update item quantity
- `DELETE /api/cart/{customerId}/items/{productId}` - Remove item from cart
- `DELETE /api/cart/{customerId}` - Clear cart

### Orders (Requires Authentication)
- `GET /api/orders/customer/{customerId}` - Get customer orders
- `GET /api/orders/{orderId}` - Get order by ID
- `POST /api/orders` - Create new order
- `PUT /api/orders/{orderId}/status` - Update order status
- `DELETE /api/orders/{orderId}` - Cancel order

## 🗄️ Database Configuration

### Development (H2 Database)
The application uses H2 in-memory database by default. Configuration is in `application.properties`.

### Production (MySQL)
To use MySQL, uncomment the MySQL configuration in `application.properties` and comment out H2 configuration:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/shopping_mall?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

Create the database:
```sql
CREATE DATABASE shopping_mall;
```

## 🔒 Security

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control (CUSTOMER, ADMIN)
- CORS configuration for frontend-backend communication

## 🎨 Frontend Pages

1. **Home Page** (`index.html`) - Landing page with categories and featured products
2. **Products Page** (`products.html`) - Browse and search products
3. **Cart Page** (`cart.html`) - View and manage shopping cart
4. **Orders Page** (`orders.html`) - View order history
5. **Login Page** (`login.html`) - User authentication
6. **Register Page** (`register.html`) - New customer registration

## 📝 Sample Data

The application automatically initializes with:
- 2 roles (CUSTOMER, ADMIN)
- 6 categories (Electronics, Clothing, Books, Home & Kitchen, Sports, Toys)
- 6 sample products
- 1 test customer account

## 🔧 Configuration

Key configuration properties in `application.properties`:

```properties
# Server Port
server.port=8080

# Database
spring.datasource.url=jdbc:h2:mem:malldb
spring.h2.console.enabled=true

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=your-secret-key
jwt.expiration=86400000
```

## 🧪 Testing

Run tests with:
```bash
mvn test
```

## 📦 Building for Production

Create a production build:
```bash
mvn clean package -DskipTests
```

Run the JAR file:
```bash
java -jar target/mall-1.0.0.jar
```

## 🚀 Deployment

The application can be deployed to:
- **Heroku**: Use Heroku Postgres instead of H2
- **AWS**: Deploy to EC2 or Elastic Beanstalk
- **Docker**: Create a Docker image and deploy to any container platform

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Troubleshooting

### Common Issues

1. **Port 8080 already in use**
   - Change the port in `application.properties`: `server.port=8081`

2. **Cannot connect to database**
   - Verify database credentials in `application.properties`
   - Ensure database server is running

3. **CORS errors**
   - Check CORS configuration in `SecurityConfig.java`
   - Ensure frontend is accessing correct API URL

4. **JWT token expired**
   - Login again to get a new token
   - Adjust token expiration time in `application.properties`

## 📧 Contact

For questions or support, please create an issue in the repository.

## 🎉 Acknowledgments

- Spring Boot team for the excellent framework
- All contributors and testers

---

**Happy Shopping! 🛒**
