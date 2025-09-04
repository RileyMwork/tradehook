document.addEventListener('DOMContentLoaded', async () => {
    await loadTradehookApiKey();
    await loadOrderList();
});

async function loadTradehookApiKey() {
    try {
        const response = await fetch('/tradehook/user/tradehook-key');
        const container = document.getElementById('tradehook-api-form-template');

        if (response.ok) {
            const apiKey = await response.text();

            // Replace <h3>ExampleTradehookAPIKey</h3> with real key
            container.querySelector('h3').textContent = apiKey;
        } else {
            container.querySelector('h3').textContent = 'Failed to load API key';
        }
    } catch (err) {
        console.error('Error fetching Tradehook API key:', err);
    }
}

async function loadOrderList() {
    try {
        const response = await fetch('/tradehook/order/list');
        const tableBody = document.getElementById('order-table-body');

        if (!response.ok) {
            tableBody.innerHTML = '<tr><td colspan="5">Failed to load orders</td></tr>';
            return;
        }

        const orders = await response.json();
        tableBody.innerHTML = ''; // Clear existing rows

        if (orders.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="5">No orders found.</td></tr>';
        } else {
            orders.forEach(order => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${order.orderId}</td>
                    <td>${order.ticker}</td>
                    <td>${order.side}</td>
                    <td>${order.qty}</td>
                    <td>${order.createdAt}</td>
                `;
                tableBody.appendChild(row);
            });
        }
    } catch (err) {
        console.error('Error loading orders:', err);
    }
}

function showAlpacaMessage(text, color) {
    const messageDiv = document.getElementById('alpaca-success-message');

    messageDiv.textContent = text;
    messageDiv.style.color = color;
    messageDiv.style.opacity = '1'; // fully visible

    // Clear and fade out after 5 seconds
    setTimeout(() => {
        messageDiv.style.opacity = '0'; // smoothly fades out
    }, 5000);

    // Clear the text after fade-out completes (e.g., 1s later)
    setTimeout(() => {
        messageDiv.textContent = '';
    }, 6000);
}

document.getElementById('alpaca-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const apiKey = document.getElementById('alpaca-api-key').value;
    const secretKey = document.getElementById('alpaca-secret-key').value;

    const payload = {
        alpacaApiKey: apiKey,
        alpacaSecretKey: secretKey
    };

    try {
        const res = await fetch('/tradehook/user/alpaca-keys/update', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            const msg = await res.text();
            showAlpacaMessage(msg, 'green');

            // Clear inputs
            document.getElementById('alpaca-api-key').value = '';
            document.getElementById('alpaca-secret-key').value = '';
        } else {
            const err = await res.text();
            showAlpacaMessage(`Error: ${err}`, 'red');
        }
    } catch (err) {
        console.error('Error:', err);
        showAlpacaMessage('Error connecting to server.', 'red');
    }
});

document.getElementById('generate-tradehook-key-button').addEventListener('click', async (e) => {
    e.preventDefault();

    try {
        const res = await fetch('/tradehook/user/tradehook-key/update', {
            method: 'PUT'
        });

        if (res.ok) {
            const newKey = await res.text();
            document.getElementById('tradehook-api-key').textContent = newKey;
        } else {
            alert('Failed to generate new Tradehook API key.');
        }
    } catch (err) {
        console.error('Error:', err);
        alert('Error connecting to server.');
    }
});

document.getElementById('delete-tradehook-key-button').addEventListener('click', async (e) => {
    e.preventDefault();

    try {
        const res = await fetch('/tradehook/user/tradehook-key/update', {
            method: 'PUT'
        });

        if (res.ok) {
            const newKey = await res.text();
            document.getElementById('tradehook-api-key').textContent = newKey;
        } else {
            alert('Failed to generate new Tradehook API key.');
        }
    } catch (err) {
        console.error('Error:', err);
        alert('Error connecting to server.');
    }
});