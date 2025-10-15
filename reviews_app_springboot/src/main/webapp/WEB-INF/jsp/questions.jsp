<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Q&A Forum</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background-color: #f8f9fa;
            color: #2c3e50;
            line-height: 1.6;
            padding: 40px 20px;
        }

        .container {
            max-width: 1000px;
            margin: 0 auto;
        }

        .header {
            text-align: center;
            margin-bottom: 40px;
            padding-bottom: 20px;
            border-bottom: 2px solid #e9ecef;
        }

        h1 {
            font-size: 2.5rem;
            font-weight: 700;
            color: #1a1a1a;
            margin-bottom: 10px;
            letter-spacing: -0.5px;
        }

        .subtitle {
            color: #6c757d;
            font-size: 1.1rem;
            font-weight: 400;
        }

        .actions-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding: 16px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
        }

        .ask-question-btn {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            padding: 12px 24px;
            border-radius: 8px;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .ask-question-btn:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 123, 255, 0.3);
        }

        .questions-list {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        .question-card {
            background: white;
            border-radius: 12px;
            padding: 24px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #e9ecef;
            transition: all 0.3s ease;
        }

        .question-card:hover {
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
            transform: translateY(-2px);
        }

        .question-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 12px;
        }

        .question-title {
            font-size: 1.2rem;
            font-weight: 600;
            color: #1a1a1a;
            margin-bottom: 8px;
        }

        .question-meta {
            color: #6c757d;
            font-size: 0.9rem;
        }

        .question-status {
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 0.8rem;
            font-weight: 600;
            text-transform: uppercase;
        }

        .status-resolved {
            background-color: #d4edda;
            color: #155724;
        }

        .status-pending {
            background-color: #fff3cd;
            color: #856404;
        }

        .question-text {
            color: #495057;
            font-size: 1rem;
            line-height: 1.7;
            margin: 16px 0;
        }

        .question-actions {
            display: flex;
            gap: 12px;
            margin-top: 16px;
            padding-top: 16px;
            border-top: 1px solid #e9ecef;
        }

        .action-btn {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            text-decoration: none;
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 0.9rem;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .view-btn {
            color: #007bff;
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
        }

        .view-btn:hover {
            background-color: #e9ecef;
            border-color: #adb5bd;
        }

        .resolve-btn {
            color: #28a745;
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
        }

        .resolve-btn:hover {
            background-color: #c3e6cb;
            border-color: #28a745;
        }

        .delete-btn {
            color: #dc3545;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
        }

        .delete-btn:hover {
            background-color: #f5c6cb;
            border-color: #dc3545;
        }

        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #6c757d;
        }

        .empty-state h3 {
            font-size: 1.5rem;
            margin-bottom: 10px;
        }

        @media (max-width: 768px) {
            body {
                padding: 20px 15px;
            }

            h1 {
                font-size: 2rem;
            }

            .actions-bar {
                flex-direction: column;
                gap: 15px;
            }

            .question-header {
                flex-direction: column;
                gap: 10px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Q&A Forum</h1>
            <p class="subtitle">Ask questions and get answers from the community</p>
        </div>

        <div class="actions-bar">
            <div>
                <strong>Total Questions: ${questions.size()}</strong>
            </div>
            <a href="<c:url value='/questions/new'/>" class="ask-question-btn">
                + Ask New Question
            </a>
        </div>

        <div class="questions-list">
            <c:choose>
                <c:when test="${not empty questions}">
                    <c:forEach var="question" items="${questions}">
                        <div class="question-card">
                            <div class="question-header">
                                <div>
                                    <div class="question-title">${question.questionText}</div>
                                    <div class="question-meta">
                                        Asked by <strong>${question.customerName}</strong> 
                                        on ${question.createdAt}
                                    </div>
                                </div>
                                <span class="question-status ${question.resolved ? 'status-resolved' : 'status-pending'}">
                                    ${question.resolved ? 'Resolved' : 'Pending'}
                                </span>
                            </div>
                            
                            <div class="question-actions">
                                <a href="<c:url value='/questions/${question.id}'/>" class="action-btn view-btn">
                                    View & Reply
                                </a>
                                
                                <c:if test="${currentUser.role == 'admin'}">
                                    <c:if test="${!question.resolved}">
                                        <a href="<c:url value='/questions/${question.id}/resolve'/>" class="action-btn resolve-btn">
                                            Resolve
                                        </a>
                                    </c:if>
                                    <a href="<c:url value='/questions/${question.id}/delete'/>" class="action-btn delete-btn"
                                       onclick="return confirm('Are you sure you want to delete this question?');">
                                        Delete
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <h3>No Questions Yet</h3>
                        <p>Be the first to ask a question!</p>
                        <a href="<c:url value='/questions/new'/>" class="ask-question-btn" style="margin-top: 20px;">
                            + Ask First Question
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>
