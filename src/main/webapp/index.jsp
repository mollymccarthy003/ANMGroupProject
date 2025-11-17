<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Food Truck App</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        h1 { color: #2c3e50; }
        nav a { margin-right: 20px; text-decoration: none; color: #2980b9; font-weight: bold; }
        nav a:hover { color: #e74c3c; }
    </style>
</head>
<body>
<h1>Welcome to the Food Truck App</h1>
<nav>
    <a href="index.jsp">Home</a>
    <a href="swagger.jsp">API Documentation</a>
</nav>

<p>Use the links above to explore the app or view API documentation.</p>

<h2>Available Endpoints</h2>
<ul>
    <li>GET /api/trucks - List all trucks</li>
    <li>GET /api/trucks/{id} - Get truck by ID</li>
    <li>
        POST /api/trucks - Add a truck
        <details>
            <summary>Signature</summary>
            <pre><code>
    {
        "name": "string",
        "foodType": "string"
    }
            </code></pre>
        </details>
    </li>
    </li>
    <li>PUT /api/trucks/{id} - Update truck</li>
    <li>DELETE /api/trucks/{id} - Delete truck</li>
    <li>GET /api/schedule - List all schedule entries</li>
    <!-- TODO: We need to normalize this endpoint. For now I just changed it to the format
            Austin used to in the DB seed script - Just to avoid any confusion for now -->
    <li>GET /api/schedule?date=M/D/YYYY - Filter by date</li>
    <li>GET /api/schedule?location_id={id} - Filter by location</li>
    <li>
        POST /api/schedule - Add a schedule entry
        <details>
            <summary>Signature</summary>
            <pre><code>
    {
        "truck_id": {"id": number},
        "location_id": {"id": number},
        "day_of_week": "string",
        "date": "M/D/YYYY",
        "start_time": "HH:MM:SS",
        "end_time": "HH:MM:SS"
    }
            </code></pre>
        </details>
    </li>
    <li>DELETE /api/schedule/{id} - Delete schedule entry</li>
</ul>
</body>
</html>
