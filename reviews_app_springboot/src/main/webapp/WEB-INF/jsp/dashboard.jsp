<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background-color: white;
            color: #2c3e50;
            line-height: 1.6;
            padding: 40px 20px;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
        }

        .header {
            text-align: center;
            margin-bottom: 40px;
            padding-bottom: 20px;
            border-bottom: 2px solid black;
        }

        h1 {
            font-size: 2.5rem;
            font-weight: 700;
            color: #1a1a1a;
            margin-bottom: 10px;
            letter-spacing: -0.5px;
        }

        .user-info {
            background: white;
            border-radius: 12px;
            padding: 24px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #e9ecef;
            margin-bottom: 30px;
        }

        .role-badge {
            display: inline-block;
            padding: 6px 12px;
            background-color: #1a1a1a;
            color: white;
            border-radius: 20px;
            font-size: 0.85rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .dashboard-links {
            background: white;
            border-radius: 12px;
            padding: 32px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #e9ecef;
        }

        h2 {
            font-size: 1.5rem;
            font-weight: 600;
            color: #1a1a1a;
            margin-bottom: 24px;
            text-align: center;
        }

        .links-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 16px;
        }

        .button {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            background-color: #1a1a1a;
            color: white;
            text-decoration: none;
            padding: 12px 20px;
            border-radius: 8px;
            font-weight: 600;
            transition: all 0.3s ease;
            width: 100%;
            justify-content: center;
        }

        .button:hover {
            background-color: #333;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        /* Review Actions - Blue */
        .button[href*="/reviews"]:not([href*="/admin"]) {
            background-color: #007bff;
        }

        .button[href*="/reviews"]:not([href*="/admin"]):hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 123, 255, 0.3);
        }

        /* Complaint Actions - Orange */
        .button[href*="/complaints"] {
            background-color: #fd7e14;
        }

        .button[href*="/complaints"]:hover {
            background-color: black;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(253, 126, 20, 0.3);
        }

        /* Admin Actions - Red */
        .button[href*="/admin"] {
            background-color: #dc3545;
        }

        .button[href*="/admin"]:hover {
            background-color: #c82333;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(220, 53, 69, 0.3);
        }

        /* Q&A Forum - Purple */
        .button[href*="/questions"] {
            background-color: #6f42c1;
        }

        .button[href*="/questions"]:hover {
            background-color: #5a32a3;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(111, 66, 193, 0.3);
        }

        /* User Management - Green */
        .button[href*="/select-user"] {
            background-color: #28a745;
        }

        .button[href*="/select-user"]:hover {
            background-color: #218838;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(40, 167, 69, 0.3);
        }

        /* Admin Functions - Professional Colors */
        .admin-dashboard-btn {
            background-color: #6f42c1 !important; /* Purple */
            border: 2px solid #6f42c1;
        }

        .admin-dashboard-btn:hover {
            background-color: #5a32a3 !important;
            border-color: #5a32a3;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(111, 66, 193, 0.3);
        }

        .admin-reviews-btn {
            background-color: #dc3545 !important; /* Red */
            border: 2px solid #dc3545;
        }

        .admin-reviews-btn:hover {
            background-color: #c82333 !important;
            border-color: #c82333;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(220, 53, 69, 0.3);
        }

        .admin-complaints-btn {
            background-color: #fd7e14 !important; /* Orange */
            border: 2px solid #fd7e14;
        }

        .admin-complaints-btn:hover {
            background-color: #e55a00 !important;
            border-color: #e55a00;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(253, 126, 20, 0.3);
        }


        @media (max-width: 768px) {
            body {
                padding: 20px 15px;
            }

            h1 {
                font-size: 2rem;
            }

            .dashboard-links {
                padding: 24px 20px;
            }

            .links-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Welcome, ${currentUser.username}!</h1>
        </div>

        <div class="user-info">
            <p><span class="role-badge">${currentUser.role}</span></p>
        </div>
        
        <div class="dashboard-links">
            <h2>Available Actions</h2>
            
            <div class="links-grid">
                <c:if test="${currentUser.role == 'admin'}">
                    <a href="<c:url value='/admin/dashboard'/>" class="button admin-dashboard-btn">Admin Dashboard</a>
                    <a href="<c:url value='/admin/reviews'/>" class="button admin-reviews-btn">Manage Reviews</a>
                    <a href="<c:url value='/admin/complaints'/>" class="button admin-complaints-btn">Manage Complaints</a>
                </c:if>
                
                <a href="<c:url value='/reviews'/>" class="button">View All Reviews</a>
                <a href="<c:url value='/reviews/new'/>" class="button">Add New Review</a>
                <a href="<c:url value='/complaints'/>" class="button">View Complaints</a>
                <a href="<c:url value='/complaints/new'/>" class="button">Submit Complaint</a>
                <a href="<c:url value='/questions'/>" class="button">Q&A Forum</a>
                <a href="<c:url value='/select-user'/>" class="button">Switch User</a>
            </div>
        </div>
    </div>
</body>
</html>
