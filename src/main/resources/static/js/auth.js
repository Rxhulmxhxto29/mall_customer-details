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

// Handle registration
document.getElementById('register-form')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('email').value;
    const phoneNumber = document.getElementById('phoneNumber').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const errorDiv = document.getElementById('register-error');
    
    if (password !== confirmPassword) {
        errorDiv.textContent = 'Passwords do not match';
        return;
    }
    
    try {
        const response = await apiRequest('/auth/register', {
            method: 'POST',
            body: JSON.stringify({ firstName, lastName, email, phoneNumber, password })
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
