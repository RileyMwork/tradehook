let allOrders = [];
let currentPage = 1;
const ordersPerPage = 5;

document.addEventListener('DOMContentLoaded', async () => {
    await loadWebhookTradingApiKey();
    await loadOrderList();

    // Pagination button events
    document.getElementById('prev-page').addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage--;
        renderOrdersPage(currentPage);
    }
});

document.getElementById('next-page').addEventListener('click', () => {
    const totalPages = Math.ceil(allOrders.length / ordersPerPage);
    if (currentPage < totalPages) {
        currentPage++;
        renderOrdersPage(currentPage);
    }
});

});

async function loadWebhookTradingApiKey() {
    try {
        const response = await fetch('/user/webhooktrading-key');
        const container = document.getElementById('webhooktrading-api-form-template');

        if (response.ok) {
            const apiKey = await response.text();
            container.querySelector('h3').textContent = apiKey;
        } else {
            container.querySelector('h3').textContent = 'Failed to load API key';
        }
    } catch (err) {
        console.error('Error fetching WebhookTrading API key:', err);
    }
}

async function loadOrderList() {
    try {
        const response = await fetch('order/list');
        const tableBody = document.getElementById('order-table-body');

        if (!response.ok) {
            tableBody.innerHTML = '<tr><td colspan="5">Failed to load orders</td></tr>';
            return;
        }

        allOrders = await response.json();
        tableBody.innerHTML = ''; // Clear existing rows

        if (allOrders.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="5">No orders found.</td></tr>';
        } else {
            currentPage = 1;
            renderOrdersPage(currentPage);
        }
    } catch (err) {
        console.error('Error loading orders:', err);
    }
}

function renderOrdersPage(page) {
    const tableBody = document.getElementById('order-table-body');
    tableBody.innerHTML = '';

    const startIndex = (page - 1) * ordersPerPage;
    const endIndex = startIndex + ordersPerPage;
    const paginatedOrders = allOrders.slice(startIndex, endIndex);

    paginatedOrders.forEach(order => {
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

    const totalPages = Math.ceil(allOrders.length / ordersPerPage);

    // 🛠 Debug Log — check pagination status
    console.log(`Rendering page ${page} of ${totalPages} | total orders: ${allOrders.length}`);

    // Update page info
    document.getElementById('page-info').textContent = `Page ${page} of ${totalPages}`;

    // Enable/disable buttons based on page
    document.getElementById('prev-page').disabled = (page <= 1);
    document.getElementById('next-page').disabled = (page >= totalPages);
}


function showAlpacaMessage(text, color) {
    const messageDiv = document.getElementById('alpaca-success-message');

    messageDiv.textContent = text;
    messageDiv.style.color = color;
    messageDiv.style.opacity = '1';

    setTimeout(() => {
        messageDiv.style.opacity = '0';
    }, 5000);

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
        const res = await fetch('/user/alpaca-keys/update', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            const msg = await res.text();
            showAlpacaMessage(msg, 'green');
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

document.getElementById('generate-webhooktrading-key-button').addEventListener('click', async (e) => {
    e.preventDefault();

    try {
        const res = await fetch('/user/webhooktrading-key/update', {
            method: 'PUT'
        });

        if (res.ok) {
            const newKey = await res.text();
            document.getElementById('webhooktrading-api-key').textContent = newKey;
        } else {
            alert('Failed to generate new WebhookTrading API key.');
        }
    } catch (err) {
        console.error('Error:', err);
        alert('Error connecting to server.');
    }
});
