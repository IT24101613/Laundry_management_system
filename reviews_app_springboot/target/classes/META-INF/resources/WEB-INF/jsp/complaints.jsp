<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Complaints</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        .complaint-container { border: 1px solid #ccc; padding: 15px; margin-bottom: 10px; border-radius: 5px; }
        .complaint-customer { font-weight: bold; color: #0056b3; }
        .complaint-text { margin-top: 5px; }
        .complaint-status { margin-top: 5px; }
        .resolved { color: green; font-weight: bold; }
        .pending { color: orange; font-weight: bold; }
        .actions a { margin-right: 10px; text-decoration: none; color: #007bff; }
        .actions a:hover { text-decoration: underline; }
        .add-complaint-button { display: inline-block; margin-top: 20px; padding: 10px 15px; background-color: #dc3545; color: white; text-decoration: none; border-radius: 5px; }
        .add-complaint-button:hover { background-color: #c82333; }
    </style>
</head>
<body>
    <h1>Customer Complaints</h1>

    <a href="<c:url value='/complaints/new'/>" class="add-complaint-button">Submit New Complaint</a>

    <div class="complaints-list">
        <c:forEach var="complaint" items="${complaints}">
            <div class="complaint-container">
                <div class="complaint-customer">${complaint.customerName}</div>
                <div class="complaint-text">${complaint.complaintText}</div>
                <div class="complaint-status">
                    <c:choose>
                        <c:when test="${complaint.resolved}">
                            <span class="resolved">Resolved</span>
                        </c:when>
                        <c:otherwise>
                            <span class="pending">Pending</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="actions">
                    <c:if test="${!complaint.resolved && currentUser.role == 'admin'}">
                        <a href="<c:url value='/complaints/resolve/${complaint.id}'/>">Resolve</a>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>
