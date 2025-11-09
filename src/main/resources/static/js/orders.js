// Load orders
async function loadOrders() {
    if (!isLoggedIn()) {
        window.location.href = 'login.html';
        return;
    }
    
    try {
        const user = getCurrentUser();
        const orders = await apiRequest(`/orders/customer/${user.id}`);
        displayOrders(orders);
    } catch (error) {
        console.error('Error loading orders:', error);
    }
}

// Display orders
function displayOrders(orders) {
    const container = document.getElementById('orders-container');
    
    if (!orders || orders.length === 0) {
        container.innerHTML = '<p>You have no orders yet.</p>';
        return;
    }
    
    container.innerHTML = orders.map(order => `
        <div class="order-card">
            <div class="order-header">
                <div>
                    <h3>Order #${order.orderNumber}</h3>
                    <p>${formatDate(order.orderDate)}</p>
                </div>
                <div>
                    <span class="order-status ${order.status.toLowerCase()}">${order.status}</span>
                    <p style="margin-top: 0.5rem;"><strong>${formatCurrency(order.totalAmount)}</strong></p>
                </div>
            </div>
            <div class="order-items">
                <h4>Items:</h4>
                ${order.items.map(item => `
                    <div class="order-item">
                        <span>${item.product.name} (x${item.quantity})</span>
                        <span>${formatCurrency(item.price * item.quantity)}</span>
                    </div>
                `).join('')}
            </div>
            ${order.trackingNumber ? `<p style="margin-top: 1rem;"><strong>Tracking:</strong> ${order.trackingNumber}</p>` : ''}
        </div>
    `).join('');
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadOrders();
});
