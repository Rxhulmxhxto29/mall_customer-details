// Load shops
async function loadShops() {
    try {
        const shops = await apiRequest('/shops');
        displayShops(shops);
    } catch (error) {
        console.error('Error loading shops:', error);
        showNotification('Error loading shops', 'error');
    }
}

// Display shops
function displayShops(shops) {
    const grid = document.getElementById('shops-grid');
    
    if (!shops || shops.length === 0) {
        grid.innerHTML = '<p>No shops available.</p>';
        return;
    }
    
    grid.innerHTML = shops.map(shop => `
        <div class="shop-card">
            <div class="shop-icon">🏬</div>
            <div class="shop-info">
                <h3>${shop.shopName}</h3>
                <p class="shop-description">${shop.description || ''}</p>
                <div class="shop-details">
                    ${shop.address ? `<p>📍 ${shop.address}</p>` : ''}
                    ${shop.phoneNumber ? `<p>📞 ${shop.phoneNumber}</p>` : ''}
                    ${shop.email ? `<p>📧 ${shop.email}</p>` : ''}
                </div>
                <span class="shop-status ${shop.status.toLowerCase()}">${shop.status}</span>
            </div>
        </div>
    `).join('');
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadShops();
});
