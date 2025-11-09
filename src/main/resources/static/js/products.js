let allProducts = [];
let categories = [];

// Load categories for filter
async function loadCategoryFilter() {
    try {
        categories = await apiRequest('/categories');
        const select = document.getElementById('category-filter');
        
        select.innerHTML = '<option value="">All Categories</option>' + 
            categories.map(cat => `<option value="${cat.id}">${cat.name}</option>`).join('');
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

// Load all products
async function loadProducts() {
    try {
        allProducts = await apiRequest('/products');
        displayProducts(allProducts);
    } catch (error) {
        console.error('Error loading products:', error);
    }
}

// Display products
function displayProducts(products) {
    const grid = document.getElementById('products-grid');
    
    if (products.length === 0) {
        grid.innerHTML = '<p>No products found.</p>';
        return;
    }
    
    grid.innerHTML = products.map(product => `
        <div class="product-card">
            <div class="product-image">📦</div>
            <div class="product-info">
                <h3>${product.name}</h3>
                <p class="price">${formatCurrency(product.price)}</p>
                <p class="description">${product.description || ''}</p>
                <p class="stock">In Stock: ${product.stockQuantity}</p>
                <button class="btn btn-success btn-block" onclick="addToCart(${product.id})">
                    Add to Cart
                </button>
            </div>
        </div>
    `).join('');
}

// Search products
document.getElementById('search-btn')?.addEventListener('click', () => {
    const keyword = document.getElementById('search-input').value.toLowerCase();
    const filtered = allProducts.filter(p => 
        p.name.toLowerCase().includes(keyword) || 
        (p.description && p.description.toLowerCase().includes(keyword))
    );
    displayProducts(filtered);
});

// Filter by category
document.getElementById('category-filter')?.addEventListener('change', (e) => {
    const categoryId = e.target.value;
    if (categoryId) {
        const filtered = allProducts.filter(p => p.category && p.category.id == categoryId);
        displayProducts(filtered);
    } else {
        displayProducts(allProducts);
    }
});

// Add to cart
async function addToCart(productId) {
    if (!isLoggedIn()) {
        window.location.href = 'login.html';
        return;
    }
    
    try {
        const user = getCurrentUser();
        await apiRequest(`/cart/${user.id}/items?productId=${productId}&quantity=1`, {
            method: 'POST'
        });
        showNotification('Product added to cart!');
        updateCartCount();
    } catch (error) {
        showNotification('Error adding product to cart', 'error');
    }
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadCategoryFilter();
    loadProducts();
});
