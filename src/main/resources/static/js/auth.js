// Handle login
document.getElementById('login-form')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const errorDiv = document.getElementById('login-error');
    
    try {
        const response = await apiRequest('/auth/login', {
            method: 'POST',
            body: JSON.stringify({ email, password })
        });
        
        saveAuthData(response.token, {
            id: response.id,
            email: response.email,
            firstName: response.firstName,
            lastName: response.lastName
        });
        
        showNotification('Login successful!');
        setTimeout(() => {
            window.location.href = 'index.html';
        }, 1000);
    } catch (error) {
        errorDiv.textContent = 'Invalid email or password';
    }
});

// Load shops for registration
async function loadShopsForRegistration() {
    try {
        const shops = await apiRequest('/shops');
        const shopSelect = document.getElementById('shopId');
        
        if (shopSelect) {
            shopSelect.innerHTML = '<option value="">Choose a shop...</option>' +
                shops.map(shop => `<option value="${shop.shopId}">${shop.shopName}</option>`).join('');
        }
    } catch (error) {
        console.error('Error loading shops:', error);
    }
}

// Load shops when registration page loads
if (document.getElementById('register-form')) {
    loadShopsForRegistration();
}

// Handle registration
document.getElementById('register-form')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('email').value;
    const phoneNumber = document.getElementById('phoneNumber').value;
    const shopId = document.getElementById('shopId').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const errorDiv = document.getElementById('register-error');
    
    if (password !== confirmPassword) {
        errorDiv.textContent = 'Passwords do not match';
        return;
    }
    
    if (!shopId) {
        errorDiv.textContent = 'Please select a shop';
        return;
    }
    
    try {
        const response = await apiRequest('/auth/register', {
            method: 'POST',
            body: JSON.stringify({ firstName, lastName, email, phoneNumber, shopId: parseInt(shopId), password })
        });
        
        saveAuthData(response.token, {
            id: response.id,
            email: response.email,
            firstName: response.firstName,
            lastName: response.lastName
        });
        
        showNotification('Registration successful!');
        setTimeout(() => {
            window.location.href = 'index.html';
        }, 1000);
    } catch (error) {
        errorDiv.textContent = 'Registration failed. Email may already exist.';
    }
});
