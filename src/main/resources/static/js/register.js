document.getElementById('register-form').addEventListener('submit', async function(e) {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const alpacaApiKey = document.getElementById('alpaca-api-key').value;
    const alpacaSecretKey = document.getElementById('alpaca-secret-key').value;

    const response = await fetch('/user/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password, alpacaApiKey, alpacaSecretKey })
    });

    const errorMessageContainer = document.getElementById('error-message');
    const errorTextElement = document.getElementById('error-text');

    if (response.ok) {
        // Hide any existing error and redirect
        errorMessageContainer.style.display = 'none';
        window.location.href = '/home';
    } else {
        const errorText = await response.text();
        errorTextElement.innerText = errorText;
        errorMessageContainer.style.display = 'block';
    }
});

// ✅ UX improvement: hide error when user edits any input
['email', 'password', 'alpaca-api-key', 'alpaca-secret-key'].forEach(id => {
    document.getElementById(id).addEventListener('input', () => {
        document.getElementById('error-message').style.display = 'none';
    });
});
