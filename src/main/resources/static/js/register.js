document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('registration-form');
    const passwordInput = document.getElementById('password-input'); // Corrected ID
    const confirmPasswordInput = document.getElementById('confirm-password-input'); // Corrected ID
    const registrationButton = document.getElementById('registration-button');

    form.addEventListener('submit', function (event) {
        // Prevent form submission if passwords don't match
        if (passwordInput.value !== confirmPasswordInput.value) {
            event.preventDefault();  // Stop form submission
            alert('Passwords do not match!');
            return; // Do not proceed with AJAX submission if passwords do not match
        }

        // If passwords match, proceed with AJAX form submission
        const email = document.getElementById("email-input").value;
        const password = passwordInput.value;

        const formData = {
            email: email,
            password: password
        };

        // Use Fetch API to submit the form data
        fetch("/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json" // Ensure that we are sending JSON
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (response.ok) {
                // If registration is successful, redirect to home page
                window.location.href = "/home";
            } else {
                // Handle the error case (display error message)
                response.text().then(data => {
                    alert("Error: " + data);
                });
            }
        })
        .catch(error => {
            console.error(error);  // Log the error to understand what's going wrong
            alert("Error: " + error.message);
        });

        // Prevent default form submission (important)
        event.preventDefault();
    });
});
