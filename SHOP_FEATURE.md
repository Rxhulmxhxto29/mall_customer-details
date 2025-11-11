# Shop Feature Documentation

## Overview
The Shop entity has been successfully added to the Shopping Mall application. This feature allows customers to be associated with specific shopping mall locations.

## Backend Implementation

### 1. Shop Entity (`Shop.java`)
**Location**: `src/main/java/com/shopping/mall/entity/Shop.java`

**Properties**:
- `shopId` (Long) - Primary key, auto-generated
- `shopName` (String) - Name of the shop (required)
- `description` (String) - Shop description (optional, max 1000 chars)
- `address` (String) - Physical address (optional)
- `phoneNumber` (String) - Contact phone (optional)
- `email` (String) - Contact email (optional)
- `status` (ShopStatus enum) - ACTIVE, INACTIVE, or CLOSED
- `customers` (Set<Customer>) - One-to-many relationship with customers
- `createdDate` (LocalDateTime) - Auto-generated on creation
- `lastModifiedDate` (LocalDateTime) - Auto-updated on modification

### 2. Customer-Shop Relationship
**Updated**: `Customer.java`

Added `@ManyToOne` relationship:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "shop_id")
private Shop shop;
```

This creates a many-to-one relationship where multiple customers can belong to one shop.

### 3. Shop Repository (`ShopRepository.java`)
**Location**: `src/main/java/com/shopping/mall/repository/ShopRepository.java`

**Methods**:
- `findByShopName(String shopName)` - Find shop by name
- `findByStatus(Shop.ShopStatus status)` - Find shops by status
- `findActiveShops()` - Get all active shops

### 4. Shop Service (`ShopService.java`)
**Location**: `src/main/java/com/shopping/mall/service/ShopService.java`

**Business Logic Methods**:
- `getAllShops()` - Get all shops
- `getActiveShops()` - Get only active shops
- `getShopById(Long shopId)` - Get shop by ID
- `getShopByName(String shopName)` - Get shop by name
- `createShop(Shop shop)` - Create new shop (Admin only)
- `updateShop(Long shopId, Shop shopDetails)` - Update shop (Admin only)
- `deleteShop(Long shopId)` - Delete shop (Admin only)

### 5. Shop Controller (`ShopController.java`)
**Location**: `src/main/java/com/shopping/mall/controller/ShopController.java`

**API Endpoints**:

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/shops` | Get all active shops | Public |
| GET | `/api/shops/{shopId}` | Get shop by ID | Public |
| GET | `/api/shops/name/{shopName}` | Get shop by name | Public |
| POST | `/api/shops` | Create new shop | Admin only |
| PUT | `/api/shops/{shopId}` | Update shop | Admin only |
| DELETE | `/api/shops/{shopId}` | Delete shop | Admin only |

### 6. Data Initialization
**Updated**: `DataInitializer.java`

Three sample shops are automatically created on application startup:

1. **Main Street Mall**
   - Address: 123 Main Street, New York, NY 10001
   - Phone: 555-0100
   - Email: info@mainstreetmall.com
   - Status: ACTIVE

2. **City Center Plaza**
   - Address: 456 Center Ave, New York, NY 10002
   - Phone: 555-0200
   - Email: contact@citycenterplaza.com
   - Status: ACTIVE

3. **Riverside Shopping Center**
   - Address: 789 River Road, New York, NY 10003
   - Phone: 555-0300
   - Email: hello@riversidesc.com
   - Status: ACTIVE

The test customer (test@example.com) is automatically associated with "Main Street Mall".

### 7. Registration Flow
**Updated**: `RegisterRequest.java` and `AuthService.java`

Registration now includes shop selection:
- New field `shopId` added to `RegisterRequest`
- During registration, customers must select a shop
- The selected shop is automatically associated with the customer

## Frontend Implementation

### 1. Shops Page (`shops.html`)
**Location**: `src/main/resources/static/shops.html`

**Features**:
- Displays all active shops in a responsive grid layout
- Shows shop information: name, description, address, phone, email
- Color-coded status badges (Active, Inactive, Closed)
- Accessible from the main navigation menu

### 2. Shops JavaScript (`shops.js`)
**Location**: `src/main/resources/static/js/shops.js`

**Functions**:
- `loadShops()` - Fetches and displays shops from API
- `displayShops(shops)` - Renders shop cards dynamically

### 3. Updated Registration (`register.html`)
**Enhanced**: Registration form now includes shop selection

**New Field**:
- Dropdown menu to select a shop (required)
- Populated dynamically from active shops

### 4. Updated Auth JavaScript (`auth.js`)
**Enhanced**: Registration process includes shop association

**New Functions**:
- `loadShopsForRegistration()` - Loads available shops into dropdown
- Updated `register-form` handler to include `shopId` in registration request

### 5. Updated CSS (`style.css`)
**Added**: Shop-specific styles

**New Classes**:
- `.shops-section` - Shop listing section
- `.shops-grid` - Responsive grid layout for shops
- `.shop-card` - Individual shop card
- `.shop-icon` - Shop icon display
- `.shop-info` - Shop information container
- `.shop-description` - Shop description text
- `.shop-details` - Contact details section
- `.shop-status` - Status badge with color coding

### 6. Navigation Updates
**Updated**: All HTML files now include "Shops" link in navigation menu

Files updated:
- `index.html`
- `products.html`
- `cart.html`
- `orders.html`
- `login.html`
- `register.html`

### 7. Security Configuration
**Updated**: `SecurityConfig.java`

Added `/api/shops/**` to public endpoints, allowing anyone to view shops without authentication.

## Database Schema

### New Table: `shops`
```sql
CREATE TABLE shops (
    shop_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    shop_name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    address VARCHAR(255),
    phone_number VARCHAR(255),
    email VARCHAR(255),
    status VARCHAR(20),
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP
);
```

### Updated Table: `customers`
```sql
ALTER TABLE customers 
ADD COLUMN shop_id BIGINT,
ADD FOREIGN KEY (shop_id) REFERENCES shops(shop_id);
```

## Usage Examples

### 1. View All Shops
Navigate to: `http://localhost:8080/shops.html`

Or via API:
```javascript
GET http://localhost:8080/api/shops
```

### 2. Register with Shop Selection
1. Go to: `http://localhost:8080/register.html`
2. Fill in registration details
3. Select a shop from dropdown
4. Complete registration

### 3. Create New Shop (Admin Only)
```javascript
POST http://localhost:8080/api/shops
Authorization: Bearer <admin-jwt-token>
Content-Type: application/json

{
  "shopName": "New Shopping Mall",
  "description": "Brand new shopping destination",
  "address": "999 New Street, New York, NY 10009",
  "phoneNumber": "555-0999",
  "email": "contact@newmall.com",
  "status": "ACTIVE"
}
```

### 4. View Shop Details
```javascript
GET http://localhost:8080/api/shops/1
```

### 5. Query Customers by Shop (H2 Console)
```sql
SELECT c.id, c.first_name, c.last_name, c.email, s.shop_name
FROM customers c
LEFT JOIN shops s ON c.shop_id = s.shop_id
WHERE s.shop_name = 'Main Street Mall';
```

## Testing the Feature

### 1. Test Shop Display
- Open `http://localhost:8080/shops.html`
- Verify 3 shops are displayed
- Check that all shop details appear correctly

### 2. Test Registration with Shop
- Go to `http://localhost:8080/register.html`
- Verify shop dropdown is populated
- Register a new customer
- Login and verify customer is associated with selected shop

### 3. Test API Endpoints
Using browser console or Postman:
```javascript
// Get all shops
fetch('http://localhost:8080/api/shops')
  .then(res => res.json())
  .then(data => console.log(data));

// Get specific shop
fetch('http://localhost:8080/api/shops/1')
  .then(res => res.json())
  .then(data => console.log(data));
```

### 4. Verify Database
1. Open H2 Console: `http://localhost:8080/h2-console`
2. Login (jdbc:h2:mem:malldb, username: sa, no password)
3. Run queries:

```sql
-- View all shops
SELECT * FROM shops;

-- View customers with their shops
SELECT c.first_name, c.last_name, c.email, s.shop_name
FROM customers c
LEFT JOIN shops s ON c.shop_id = s.shop_id;

-- Count customers per shop
SELECT s.shop_name, COUNT(c.id) as customer_count
FROM shops s
LEFT JOIN customers c ON s.shop_id = c.shop_id
GROUP BY s.shop_name;
```

## Files Created

### Backend (Java)
1. `Shop.java` - Shop entity class
2. `ShopRepository.java` - Shop repository interface
3. `ShopService.java` - Shop service class
4. `ShopController.java` - Shop REST controller

### Frontend (HTML/CSS/JS)
1. `shops.html` - Shops listing page
2. `shops.js` - Shops JavaScript functionality

### Modified Files
- `Customer.java` - Added shop relationship
- `RegisterRequest.java` - Added shopId field
- `AuthService.java` - Added shop association logic
- `DataInitializer.java` - Added shop initialization
- `SecurityConfig.java` - Added shops endpoint to public access
- `register.html` - Added shop selection field
- `auth.js` - Added shop loading and registration logic
- `style.css` - Added shop styles
- All navigation menus - Added Shops link

## Features Summary

✅ Complete Shop entity with all properties (shopId, shopName, etc.)
✅ One-to-many relationship between Shop and Customer
✅ Shop repository with custom queries
✅ Shop service with full CRUD operations
✅ RESTful API endpoints for shop management
✅ Shop listing page with responsive design
✅ Shop selection during customer registration
✅ Sample shops initialized on startup
✅ Public access to view shops
✅ Admin-only access for shop management
✅ Database queries to view shop-customer relationships
✅ Complete frontend integration with navigation
✅ Comprehensive styling for shop displays

## Next Steps (Optional Enhancements)

1. **Shop Products**: Associate products with specific shops
2. **Shop Hours**: Add operating hours for each shop
3. **Shop Images**: Add image upload for shop logos
4. **Shop Reviews**: Allow customers to review shops
5. **Shop Search**: Add search and filter functionality
6. **Shop Manager Role**: Create shop manager role for shop-specific admin
7. **Shop Analytics**: Dashboard showing shop performance metrics
8. **Multi-Shop Orders**: Allow orders from multiple shops
9. **Shop Inventory**: Track inventory per shop
10. **Shop Promotions**: Shop-specific discounts and promotions

## API Response Examples

### Get All Shops
```json
[
  {
    "shopId": 1,
    "shopName": "Main Street Mall",
    "description": "Premier shopping destination in downtown",
    "address": "123 Main Street, New York, NY 10001",
    "phoneNumber": "555-0100",
    "email": "info@mainstreetmall.com",
    "status": "ACTIVE",
    "createdDate": "2025-11-11T11:03:55.123",
    "lastModifiedDate": "2025-11-11T11:03:55.123"
  },
  {
    "shopId": 2,
    "shopName": "City Center Plaza",
    "description": "Modern shopping complex with 100+ stores",
    "address": "456 Center Ave, New York, NY 10002",
    "phoneNumber": "555-0200",
    "email": "contact@citycenterplaza.com",
    "status": "ACTIVE",
    "createdDate": "2025-11-11T11:03:55.456",
    "lastModifiedDate": "2025-11-11T11:03:55.456"
  }
]
```

## Troubleshooting

### Issue: Shops not displaying
**Solution**: Check that:
1. Application is running
2. Navigate to correct URL: `http://localhost:8080/shops.html`
3. Check browser console for JavaScript errors
4. Verify API endpoint: `http://localhost:8080/api/shops`

### Issue: Cannot register with shop
**Solution**: 
1. Verify shops dropdown is populated
2. Check that at least one active shop exists
3. Ensure you selected a shop before submitting

### Issue: Shop not saving
**Solution**:
1. Check you have ADMIN role
2. Verify JWT token is valid
3. Check all required fields are provided

---

**Implementation Complete!** The Shop feature is fully integrated and working in both backend and frontend.
