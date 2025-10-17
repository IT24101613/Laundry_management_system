<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body { 
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
            margin: 0; 
            padding: 20px;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
        }
        h1 { 
            color: #2c3e50; 
            text-align: center;
            margin-bottom: 30px;
            font-size: 2.2rem;
            font-weight: 600;
            text-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .admin-badge {
            display: inline-block;
            background: linear-gradient(135deg, #dc3545, #c82333);
            color: white;
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 0.8rem;
            font-weight: 600;
            margin-left: 15px;
            box-shadow: 0 2px 8px rgba(220, 53, 69, 0.3);
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        .dashboard-container { 
            max-width: 900px; 
            margin: 0 auto; 
        }
        .dashboard-card { 
            background: white; 
            border: none; 
            border-radius: 12px; 
            padding: 30px; 
            margin: 20px 0; 
            box-shadow: 0 8px 25px rgba(0,0,0,0.1);
            border-left: 4px solid #007bff;
        }
        h2 {
            color: #495057;
            margin-bottom: 20px;
            font-size: 1.3rem;
            font-weight: 500;
        }
        .dashboard-links { 
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            justify-content: center;
        }
        .dashboard-links a { 
            display: inline-block; 
            padding: 12px 24px; 
            background: linear-gradient(135deg, #007bff, #0056b3); 
            color: white; 
            text-decoration: none; 
            border-radius: 8px; 
            font-weight: 500;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(0,123,255,0.3);
        }
        .dashboard-links a:hover { 
            background: linear-gradient(135deg, #0056b3, #004085);
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(0,123,255,0.4);
        }
        .back-link { 
            display: inline-block; 
            margin-top: 30px; 
            color: #6c757d; 
            text-decoration: none; 
            padding: 10px 20px;
            border: 2px solid #6c757d;
            border-radius: 6px;
            background-color: white;
            font-weight: 500;
            transition: all 0.3s ease;
        }
        .back-link:hover { 
            background-color: #6c757d;
            color: white;
            transform: translateY(-1px);
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <h1>Admin Dashboard <span class="admin-badge">ADMIN</span></h1>
        
        <div class="dashboard-card">
            <h2>Management Options</h2>
            <div class="dashboard-links">
                <a href="<c:url value='/admin/reviews'/>">Manage Reviews</a>
                <a href="<c:url value='/admin/complaints'/>">Manage Complaints</a>
                <a href="<c:url value='/reviews'/>">View All Reviews</a>
                <a href="<c:url value='/complaints'/>">View All Complaints</a>
            </div>
        </div>
        
        <a href="<c:url value='/'/>" class="back-link">Back to Home</a>
    </div>
</body>
</html>
