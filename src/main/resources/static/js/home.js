// Load categories and featured products
async function loadCategories() {
    try {
        const categories = await apiRequest('/categories');
        const grid = document.getElementById('categories-grid');
        
        grid.innerHTML = categories.slice(0, 6).map(category => `
            <div class="category-card" onclick="window.location.href='products.html?category=${category.id}'">
                <h3>${category.name}</h3>
                <p>${category.description || ''}</p>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

async function loadFeaturedProducts() {
    try {
        const products = await apiRequest('/products');
        const grid = document.getElementById('featured-products-grid');
        
        grid.innerHTML = products.slice(0, 6).map(product => `
            <div class="product-card">
                <div class="product-image">📦</div>
                <div class="product-info">
                    <h3>${product.name}</h3>
                    <p class="price">${formatCurrency(product.price)}</p>
                    <p class="description">${product.description || ''}</p>
                    <p class="stock">In Stock: ${product.stockQuantity}</p>
                    <button class="btn btn-primary btn-block" onclick="addToCart(${product.id})">
                        Add to Cart
                    </button>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading products:', error);
    }
}

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
    loadCategories();
    loadFeaturedProducts();
});
