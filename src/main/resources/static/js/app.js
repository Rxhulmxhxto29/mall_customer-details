// API Configuration
const API_BASE_URL = 'http://localhost:8080/api';

// Storage Keys
const TOKEN_KEY = 'authToken';
const USER_KEY = 'user';

// Get auth token
function getAuthToken() {
    return localStorage.getItem(TOKEN_KEY);
}

// Get current user
function getCurrentUser() {
    const userStr = localStorage.getItem(USER_KEY);
    return userStr ? JSON.parse(userStr) : null;
}

// Save auth data
function saveAuthData(token, user) {
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USER_KEY, JSON.stringify(user));
}

// Clear auth data
function clearAuthData() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
}

// Check if user is logged in
function isLoggedIn() {
    return !!getAuthToken();
}

// API Request Helper
async function apiRequest(endpoint, options = {}) {
    const token = getAuthToken();
    
    const defaultHeaders = {
        'Content-Type': 'application/json',
    };
    
    if (token) {
        defaultHeaders['Authorization'] = `Bearer ${token}`;
    }
    
    const config = {
        ...options,
        headers: {
            ...defaultHeaders,
            ...options.headers,
        },
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, config);
        
        if (!response.ok) {
            if (response.status === 401 || response.status === 403) {
                clearAuthData();
                window.location.href = 'login.html';
                throw new Error('Unauthorized');
            }
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        }
        return null;
    } catch (error) {
        console.error('API Request Error:', error);
        throw error;
    }
}

// Update navigation menu based on auth status
function updateNavMenu() {
    const authMenu = document.getElementById('auth-menu');
    if (authMenu) {
        const user = getCurrentUser();
        if (user) {
            authMenu.innerHTML = `
                <span style="margin-right: 1rem;">Hello, ${user.firstName}!</span>
                <a href="#" onclick="logout()">Logout</a>
            `;
        } else {
            authMenu.innerHTML = '<a href="login.html">Login</a>';
        }
    }
}

// Logout function
function logout() {
    clearAuthData();
    window.location.href = 'index.html';
}

// Update cart count
async function updateCartCount() {
    const cartCountEl = document.getElementById('cart-count');
    if (cartCountEl && isLoggedIn()) {
        try {
            const user = getCurrentUser();
            const cart = await apiRequest(`/cart/${user.id}`);
            const totalItems = cart.items.reduce((sum, item) => sum + item.quantity, 0);
            cartCountEl.textContent = totalItems;
        } catch (error) {
            console.error('Error updating cart count:', error);
        }
    }
}

// Format currency
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
    }).format(amount);
}

// Format date
function formatDate(dateString) {
    return new Date(dateString).toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

// Show notification
function showNotification(message, type = 'success') {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 1rem 2rem;
        background-color: ${type === 'success' ? '#27ae60' : '#e74c3c'};
        color: white;
        border-radius: 5px;
        z-index: 1000;
        box-shadow: 0 2px 10px rgba(0,0,0,0.2);
    `;
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.remove();
    }, 3000);
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    updateNavMenu();
    updateCartCount();
});
