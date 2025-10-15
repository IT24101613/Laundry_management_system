<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin - Manage Complaints</title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            margin: 20px; 
            background-color: #f8f9fa;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1 { 
            color: #333; 
            margin-bottom: 20px;
            border-bottom: 2px solid #dc3545;
            padding-bottom: 10px;
        }
        .admin-badge {
            display: inline-block;
            background: #dc3545;
            color: white;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 0.8rem;
            margin-left: 10px;
        }
        table { 
            width: 100%; 
            border-collapse: collapse; 
            margin-top: 20px; 
            background: white;
        }
        th, td { 
            border: 1px solid #dee2e6; 
            padding: 12px; 
            text-align: left; 
        }
        th { 
            background-color: #f8f9fa; 
            font-weight: 600;
            color: #495057;
        }
        tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        tr:hover {
            background-color: #e9ecef;
        }
        .resolved { 
            color: #28a745; 
            font-weight: bold; 
            background-color: #d4edda;
            padding: 4px 8px;
            border-radius: 4px;
        }
        .pending { 
            color: #ffc107; 
            font-weight: bold; 
            background-color: #fff3cd;
            padding: 4px 8px;
            border-radius: 4px;
        }
        .action-links a { 
            margin-right: 10px; 
            text-decoration: none; 
            color: #28a745; 
            font-weight: 500;
            padding: 4px 8px;
            border-radius: 4px;
            background-color: #d4edda;
        }
        .action-links a:hover { 
            background-color: #c3e6cb;
            color: #155724;
        }
        .back-link { 
            display: inline-block; 
            margin-top: 20px; 
            color: #007bff; 
            text-decoration: none; 
            padding: 8px 16px;
            border: 1px solid #007bff;
            border-radius: 4px;
        }
        .back-link:hover { 
            background-color: #007bff;
            color: white;
        }
        .empty-state {
            text-align: center;
            padding: 40px;
            color: #6c757d;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Manage Complaints <span class="admin-badge">ADMIN</span></h1>

        <c:choose>
            <c:when test="${not empty complaints}">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Customer Name</th>
                            <th>Complaint Text</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="complaint" items="${complaints}">
                            <tr>
                                <td>${complaint.id}</td>
                                <td>${complaint.customerName}</td>
                                <td>${complaint.complaintText}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${complaint.resolved}">
                                            <span class="resolved">Resolved</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="pending">Pending</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="action-links">
                                    <c:if test="${!complaint.resolved}">
                                        <a href="<c:url value="/admin/complaints/resolve/${complaint.id}" />" 
                                           onclick="return confirm('Are you sure you want to resolve this complaint?');">
                                            Resolve
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <h3>No Complaints Found</h3>
                    <p>There are no complaints to manage at the moment.</p>
                </div>
            </c:otherwise>
        </c:choose>

        <a href="<c:url value="/admin/dashboard" />" class="back-link">← Back to Admin Dashboard</a>
    </div>
</body>
</html>