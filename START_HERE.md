# 🎉 Shopping Mall Project - Ready to Run!

## ✅ Project Status: COMPLETE

Your full-stack shopping mall e-commerce application is fully set up and ready to run!

---

## 🚀 Three Ways to Run the Application

### **Method 1: Double-click the Batch File (Easiest!)**

1. Navigate to: `c:\Users\WIN10 HOME 22H2\Desktop\mall`
2. Double-click `run.bat`
3. Wait for the application to start (1-2 minutes first time)
4. Open browser: http://localhost:8080
5. Login: `test@example.com` / `password`

### **Method 2: Using VS Code Terminal**

```bash
# In VS Code, open a new terminal (Ctrl + `)
cd "c:\Users\WIN10 HOME 22H2\Desktop\mall"
mvn spring-boot:run
```

### **Method 3: Using VS Code Tasks (After installing Java extensions)**

1. Press `Ctrl+Shift+P`
2. Type "Tasks: Run Task"
3. Select "Spring Boot: Run"

---

## 📦 Install Required VS Code Extensions (Recommended)

For the best experience, install these extensions:

```vscode-extensions
vmware.vscode-boot-dev-pack,redhat.java,vscjava.vscode-java-debug,vscjava.vscode-maven
```

**Manual Installation:**
1. Open Extensions (Ctrl+Shift+X)
2. Search for "Spring Boot Extension Pack"
3. Click Install

Once installed, you'll see a "Spring Boot Dashboard" in the sidebar where you can run the app with one click!

---

## 🎯 What's Included

### Backend Features
✅ **10 Database Entities**
- Customer, Product, Cart, Order, Category
- Address, Payment, Role, CartItem, OrderItem

✅ **REST API Endpoints**
- Authentication (login, register)
- Products (browse, search, filter)
- Cart management (add, update, remove)
- Order placement and tracking
- Category management

✅ **Security**
- JWT authentication
- Password encryption
- Role-based access control

### Frontend Features
✅ **6 Web Pages**
- Home page with featured products
- Product catalog with search
- Shopping cart management
- Order history
- Login & Registration

✅ **Responsive Design**
- Mobile-friendly interface
- Modern UI with smooth animations
- Easy navigation

---

## 📱 Using the Application

### 1. **Start the Application**
Use any of the three methods above

### 2. **Access the Website**
Open your browser and go to: **http://localhost:8080**

### 3. **Login**
Use the test account:
- Email: `test@example.com`
- Password: `password`

### 4. **Explore Features**

**Browse Products**
- View all products on the home page
- Click "Products" to see the full catalog
- Use search to find specific items
- Filter by category

**Shopping Cart**
- Click "Add to Cart" on any product
- View cart by clicking "Cart" in navigation
- Update quantities with +/- buttons
- Remove items as needed

**Place Orders**
- Add items to cart
- Go to cart page
- Click "Proceed to Checkout"
- (Note: Full checkout requires address setup)

**View Order History**
- Click "Orders" in navigation
- See all your past orders
- Track order status

---

## 🗄️ Database Access

### H2 Console (View Database)
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:malldb`
- Username: `sa`
- Password: (leave empty)

### Sample Data Included
- 6 Categories (Electronics, Clothing, Books, etc.)
- 6 Products with prices and stock
- 1 Test customer account
- Default addresses and roles

---

## 🔧 Troubleshooting

### Maven not found
**Error**: `'mvn' is not recognized`

**Solutions**:
1. Add Maven to your system PATH:
   - Find Maven installation directory (e.g., `C:\apache-maven-3.x`)
   - Add the `bin` folder to PATH environment variable
   - Restart VS Code

2. OR use VS Code Spring Boot Dashboard (no PATH needed)

### Java version issues
**Error**: Java 8 detected, need Java 17+

**Solution**:
1. Download Java 17: https://adoptium.net/
2. Install Java 17
3. Set JAVA_HOME to Java 17 directory
4. Update PATH to use Java 17
5. Restart VS Code
6. Verify: `java -version`

### Port 8080 already in use
**Error**: Port 8080 is already in use

**Solution**:
Edit `src/main/resources/application.properties`:
```properties
server.port=8081
```
Then access at: http://localhost:8081

### Application won't start
1. Check terminal for error messages
2. Ensure Java 17+ is installed
3. Ensure Maven is installed
4. Try: `mvn clean install` first
5. Check logs in terminal for specific errors

---

## 📚 API Documentation

### Public Endpoints (No Authentication)
```
GET  /api/products              - Get all products
GET  /api/products/{id}         - Get product by ID
GET  /api/products/search       - Search products
GET  /api/categories            - Get all categories
POST /api/auth/login            - Login
POST /api/auth/register         - Register new customer
```

### Protected Endpoints (Requires JWT Token)
```
GET    /api/cart/{customerId}              - Get cart
POST   /api/cart/{customerId}/items        - Add to cart
PUT    /api/cart/{customerId}/items/{id}   - Update cart item
DELETE /api/cart/{customerId}/items/{id}   - Remove from cart

GET    /api/orders/customer/{customerId}   - Get orders
POST   /api/orders                          - Create order
```

**Authentication**:
1. Login via `/api/auth/login`
2. Get JWT token from response
3. Add header: `Authorization: Bearer <token>`

---

## 🎨 Customization Tips

### Change Colors
Edit `src/main/resources/static/css/style.css`

### Add More Products
Edit `src/main/java/com/shopping/mall/config/DataInitializer.java`

### Modify Logo
Change the emoji (🛍️) in each HTML file's navbar

### Add Categories
Modify `DataInitializer.java` to add more categories

---

## 📖 Project Structure
```
mall/
├── run.bat                      ⭐ Double-click to run!
├── pom.xml                      - Maven configuration
├── README.md                    - Full documentation
├── RUNNING.md                   - Running instructions
├── src/
│   ├── main/
│   │   ├── java/.../mall/
│   │   │   ├── MallApplication.java
│   │   │   ├── controller/      - REST APIs
│   │   │   ├── service/         - Business logic
│   │   │   ├── repository/      - Database access
│   │   │   ├── entity/          - Data models
│   │   │   ├── security/        - JWT & auth
│   │   │   └── config/          - Configuration
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/          - Frontend files
│   │           ├── *.html
│   │           ├── css/
│   │           └── js/
└── .vscode/
    ├── launch.json              - Run configurations
    └── tasks.json               - Build tasks
```

---

## 🎓 Learning Resources

- **Spring Boot**: https://spring.io/guides
- **Spring Security**: https://spring.io/projects/spring-security
- **JWT**: https://jwt.io/introduction
- **REST API**: https://restfulapi.net/

---

## ✨ Next Steps

1. **Run the application** using `run.bat` or VS Code
2. **Explore the website** at http://localhost:8080
3. **Test the features** - browse, add to cart, place orders
4. **View the database** in H2 Console
5. **Customize** the application to your needs
6. **Add features** like product images, reviews, etc.

---

## 🆘 Need Help?

1. Check **RUNNING.md** for detailed instructions
2. Check **README.md** for complete documentation
3. Look at terminal output for error messages
4. Verify Java 17+ and Maven are installed
5. Ensure no other app is using port 8080

---

## 🎉 You're All Set!

**Just double-click `run.bat` or run `mvn spring-boot:run` to start!**

Your shopping mall is ready to serve customers! 🛒

---

**Test Account:**
- Email: `test@example.com`
- Password: `password`

**Website:** http://localhost:8080

**Enjoy your new e-commerce platform!** 🚀
