// document.addEventListener('DOMContentLoaded', function () {
//     const form = document.getElementById('login-form');
//     const passwordInput = document.getElementById('password-input');
//     const loginButton = document.getElementById('login-button');

//     form.addEventListener('submit', function (event) {
//         // Validate the form fields (you can add more validation as needed)
//         const email = document.getElementById("email-input").value;
//         const password = passwordInput.value;

//         if (!email || !password) {
//             event.preventDefault();  // Stop form submission
//             alert('Please fill in both email and password!');
//             return;
//         }

//         // If email and password are filled, proceed with AJAX form submission
//         const formData = {
//             email: email,
//             password: password
//         };

//         // Use Fetch API to submit the form data
//         fetch("/login", {
//             method: "POST",
//             headers: {
//                 "Content-Type": "application/json" // Ensure that we are sending JSON
//             },
//             body: JSON.stringify(formData)
//         })
//         .then(response => {
//             if (response.ok) {
//                 // If login is successful, redirect to the home page
//                 window.location.href = "/home";
//             } else {
//                 // Handle the error case (display error message)
//                 response.text().then(data => {
//                     alert("Error: " + data);
//                 });
//             }
//         })
//         .catch(error => {
//             console.error(error);  // Log the error to understand what's going wrong
//             alert("Error: " + error.message);
//         });

//         // Prevent default form submission (important)
//         event.preventDefault();
//     });
// });
