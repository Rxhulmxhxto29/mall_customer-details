# Spring Boot Shopping Mall - Running Instructions

## Quick Start Options

### Option 1: Using Spring Boot Dashboard in VS Code (Recommended)

1. **Install Spring Boot Extension Pack**:
   - Open VS Code Extensions (Ctrl+Shift+X)
   - Search for "Spring Boot Extension Pack"
   - Click Install

2. **Run the Application**:
   - Look for "Spring Boot Dashboard" in the left sidebar
   - Find "MallApplication" in the Apps list
   - Click the play button ▶️ next to it
   - Or right-click and select "Run" or "Debug"

3. **Access the Website**:
   - Open browser: http://localhost:8080
   - Login: test@example.com / password

### Option 2: Using Command Line (If Maven is in PATH)

```bash
# Navigate to project directory
cd "c:\Users\WIN10 HOME 22H2\Desktop\mall"

# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run
```

### Option 3: Using VS Code Tasks

Press `Ctrl+Shift+P` and type "Tasks: Run Task", then select "Spring Boot Run"

## Troubleshooting

### Maven not found in PATH

**Solution**: Add Maven to your system PATH:
1. Open System Properties > Environment Variables
2. Add Maven's `bin` directory to PATH
3. Restart VS Code
4. Try again

### Java version issues

The project requires Java 17+. To check your Java version:
```bash
java -version
```

If you have Java 17 installed but it's not being used:
1. Set JAVA_HOME environment variable to Java 17 location
2. Update PATH to include Java 17's bin directory
3. Restart VS Code

### Port 8080 already in use

Edit `src/main/resources/application.properties`:
```properties
server.port=8081
```

## Next Steps After Starting

1. **Access the website**: http://localhost:8080
2. **Login with test account**: test@example.com / password
3. **Explore features**:
   - Browse products by category
   - Search for products
   - Add items to cart
   - View cart and place orders
   - Check order history

## H2 Database Console

Access the database console at: http://localhost:8080/h2-console

**Connection Details**:
- JDBC URL: `jdbc:h2:mem:malldb`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

Test APIs at: http://localhost:8080/api

**Public endpoints**:
- GET /api/products - All products
- GET /api/categories - All categories
- POST /api/auth/login - Login
- POST /api/auth/register - Register

**Protected endpoints** (require authentication):
- GET /api/cart/{customerId}
- POST /api/cart/{customerId}/items
- GET /api/orders/customer/{customerId}

See README.md for complete API documentation.
