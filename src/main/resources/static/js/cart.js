// Load cart
async function loadCart() {
    if (!isLoggedIn()) {
        window.location.href = 'login.html';
        return;
    }
    
    try {
        const user = getCurrentUser();
        const cart = await apiRequest(`/cart/${user.id}`);
        displayCart(cart);
    } catch (error) {
        console.error('Error loading cart:', error);
    }
}

// Display cart
function displayCart(cart) {
    const container = document.getElementById('cart-container');
    const summary = document.getElementById('cart-summary');
    
    if (!cart.items || cart.items.length === 0) {
        container.innerHTML = '<p>Your cart is empty.</p>';
        summary.innerHTML = '';
        return;
    }
    
    container.innerHTML = cart.items.map(item => `
        <div class="cart-item">
            <div class="cart-item-info">
                <h3>${item.product.name}</h3>
                <p class="price">${formatCurrency(item.price)}</p>
            </div>
            <div class="cart-item-actions">
                <div class="quantity-controls">
                    <button onclick="updateQuantity(${item.product.id}, ${item.quantity - 1})">-</button>
                    <span>${item.quantity}</span>
                    <button onclick="updateQuantity(${item.product.id}, ${item.quantity + 1})">+</button>
                </div>
                <p><strong>${formatCurrency(item.price * item.quantity)}</strong></p>
                <button class="btn btn-danger" onclick="removeItem(${item.product.id})">Remove</button>
            </div>
        </div>
    `).join('');
    
    const total = cart.items.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    
    summary.innerHTML = `
        <h3>Cart Summary</h3>
        <p class="total">Total: ${formatCurrency(total)}</p>
        <button class="btn btn-success btn-block" onclick="checkout()">Proceed to Checkout</button>
        <button class="btn btn-danger btn-block" onclick="clearCart()" style="margin-top: 1rem;">Clear Cart</button>
    `;
}

// Update quantity
async function updateQuantity(productId, quantity) {
    if (quantity < 1) return;
    
    try {
        const user = getCurrentUser();
        await apiRequest(`/cart/${user.id}/items/${productId}?quantity=${quantity}`, {
            method: 'PUT'
        });
        loadCart();
        updateCartCount();
    } catch (error) {
        showNotification('Error updating quantity', 'error');
    }
}

// Remove item
async function removeItem(productId) {
    try {
        const user = getCurrentUser();
        await apiRequest(`/cart/${user.id}/items/${productId}`, {
            method: 'DELETE'
        });
        showNotification('Item removed from cart');
        loadCart();
        updateCartCount();
    } catch (error) {
        showNotification('Error removing item', 'error');
    }
}

// Clear cart
async function clearCart() {
    if (!confirm('Are you sure you want to clear your cart?')) return;
    
    try {
        const user = getCurrentUser();
        await apiRequest(`/cart/${user.id}`, {
            method: 'DELETE'
        });
        showNotification('Cart cleared');
        loadCart();
        updateCartCount();
    } catch (error) {
        showNotification('Error clearing cart', 'error');
    }
}

// Checkout (simplified - in production would need address selection and payment)
async function checkout() {
    alert('Checkout feature requires address setup. For demo purposes, this creates an order with default address.');
    // In production, you would show a modal to select shipping/billing addresses
    // and payment method, then call: apiRequest('/orders?customerId=...&shippingAddressId=...&billingAddressId=...&paymentMethod=...')
    showNotification('For full checkout, please set up addresses first', 'error');
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadCart();
});
