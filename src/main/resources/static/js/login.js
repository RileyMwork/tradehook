document.getElementById('login-form').addEventListener('submit', async function(e) {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const response = await fetch('/authenticate-user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
    });

    const errorMessageContainer = document.getElementById('error-message');
    const errorTextElement = document.getElementById('error-text');

    if (response.ok) {
        // Hide error in case it was shown before
        errorMessageContainer.style.display = 'none';

        // Redirect on success
        window.location.href = '/home';
    } else {
        // Show error
        const errorText = await response.text();
        errorTextElement.innerText = errorText;
        errorMessageContainer.style.display = 'block';
    }
});

// ✅ Optional UX Improvement: hide error on user input
['email', 'password'].forEach(id => {
    document.getElementById(id).addEventListener('input', () => {
        document.getElementById('error-message').style.display = 'none';
    });
});
