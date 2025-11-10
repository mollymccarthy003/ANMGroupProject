<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Food Truck API Documentation</title>
    <link rel="stylesheet" type="text/css" href="swagger-ui/swagger-ui.css">
    <style>
        body { margin: 0; padding: 0; }
        #swagger-ui { height: 100vh; }
    </style>
</head>
<body>
<div id="swagger-ui"></div>

<script src="swagger-ui/swagger-ui-bundle.js"></script>
<script src="swagger-ui/swagger-ui-standalone-preset.js"></script>
<script>
    const ui = SwaggerUIBundle({
        url: '<%= request.getContextPath() %>/openapi.json',
        dom_id: '#swagger-ui',
        presets: [
            SwaggerUIBundle.presets.apis,
            SwaggerUIStandalonePreset
        ],
        layout: "StandaloneLayout",
        deepLinking: true
    });
</script>
</body>
</html>
