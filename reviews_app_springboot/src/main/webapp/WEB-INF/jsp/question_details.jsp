<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Question Details</title>
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
            max-width: 800px;
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

        .question-card {
            background: white;
            border-radius: 12px;
            padding: 32px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #e9ecef;
            margin-bottom: 30px;
        }

        .question-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 20px;
        }

        .question-meta {
            color: #6c757d;
            font-size: 0.9rem;
            margin-bottom: 16px;
        }

        .question-status {
            padding: 6px 16px;
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
            font-size: 1.1rem;
            line-height: 1.7;
            margin: 20px 0;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
            border-left: 4px solid #007bff;
        }

        .answers-section {
            background: white;
            border-radius: 12px;
            padding: 32px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #e9ecef;
            margin-bottom: 30px;
        }

        .answers-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 24px;
            padding-bottom: 16px;
            border-bottom: 2px solid #e9ecef;
        }

        .answers-title {
            font-size: 1.5rem;
            font-weight: 600;
            color: #1a1a1a;
        }

        .answer-card {
            background: #f8f9fa;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 16px;
            border-left: 4px solid #007bff;
            transition: all 0.3s ease;
        }

        .answer-card.admin-answer {
            background: #fff5f5;
            border-left-color: #dc3545;
        }

        .answer-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 12px;
        }

        .answer-author {
            font-weight: 600;
            color: #007bff;
        }

        .answer-author.admin {
            color: #dc3545;
        }

        .answer-meta {
            color: #6c757d;
            font-size: 0.85rem;
        }

        .answer-text {
            color: #495057;
            font-size: 1rem;
            line-height: 1.6;
        }

        .answer-form {
            background: white;
            border-radius: 12px;
            padding: 32px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #e9ecef;
            margin-bottom: 30px;
        }

        .form-group {
            margin-bottom: 24px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #1a1a1a;
            font-size: 0.95rem;
        }

        textarea {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            background-color: #ffffff;
            color: #2c3e50;
            font-size: 1rem;
            font-family: 'Inter', sans-serif;
            transition: all 0.3s ease;
            resize: vertical;
            min-height: 100px;
            line-height: 1.6;
        }

        textarea:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.1);
        }

        .submit-btn {
            background-color: #007bff;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1rem;
            font-weight: 600;
            font-family: 'Inter', sans-serif;
            transition: all 0.3s ease;
            width: 100%;
        }

        .submit-btn:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 123, 255, 0.3);
        }

        .back-link {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            color: #6c757d;
            text-decoration: none;
            font-weight: 500;
            padding: 12px 20px;
            border: 1px solid #e9ecef;
            border-radius: 8px;
            background-color: #ffffff;
            transition: all 0.3s ease;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
        }

        .back-link:hover {
            background-color: #f8f9fa;
            border-color: #adb5bd;
            transform: translateY(-1px);
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .empty-answers {
            text-align: center;
            padding: 40px 20px;
            color: #6c757d;
        }

        @media (max-width: 768px) {
            body {
                padding: 20px 15px;
            }

            h1 {
                font-size: 2rem;
            }

            .question-card, .answers-section, .answer-form {
                padding: 24px 20px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Question Details</h1>
        </div>

        <!-- Question Card -->
        <div class="question-card">
            <div class="question-header">
                <div>
                    <div class="question-meta">
                        Asked by <strong>${question.customerName}</strong> 
                        on ${question.createdAt}
                    </div>
                </div>
                <span class="question-status ${question.resolved ? 'status-resolved' : 'status-pending'}">
                    ${question.resolved ? 'Resolved' : 'Pending'}
                </span>
            </div>
            
            <div class="question-text">
                ${question.questionText}
            </div>
        </div>

        <!-- Answers Section -->
        <div class="answers-section">
            <div class="answers-header">
                <h2 class="answers-title">Answers & Comments</h2>
                <span>${answers.size()} ${answers.size() == 1 ? 'answer' : 'answers'}</span>
            </div>

            <c:choose>
                <c:when test="${not empty answers}">
                    <c:forEach var="answer" items="${answers}">
                        <div class="answer-card ${answer.authorRole == 'admin' ? 'admin-answer' : ''}">
                            <div class="answer-header">
                                <div>
                                    <div class="answer-author ${answer.authorRole == 'admin' ? 'admin' : ''}">
                                        ${answer.authorName}
                                        <c:if test="${answer.authorRole == 'admin'}">
                                            <span style="color: #dc3545; font-size: 0.8rem; margin-left: 8px;">[ADMIN]</span>
                                        </c:if>
                                    </div>
                                    <div class="answer-meta">
                                        ${answer.createdAt}
                                    </div>
                                </div>
                            </div>
                            <div class="answer-text">
                                ${answer.answerText}
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-answers">
                        <h3>No answers yet</h3>
                        <p>Be the first to answer this question!</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Answer Form -->
        <div class="answer-form">
            <h3 style="margin-bottom: 20px; color: #1a1a1a;">Add Your Answer</h3>
            <form action="<c:url value='/questions/${question.id}/answers'/>" method="post">
                <div class="form-group">
                    <label for="answerText">Your Answer</label>
                    <textarea id="answerText" name="answerText" required placeholder="Share your knowledge and help others..."></textarea>
                </div>

                <button type="submit" class="submit-btn">Post Answer</button>
            </form>
        </div>

        <a href="<c:url value='/questions'/>" class="back-link">
            &#8592; Back to Q&A Forum
        </a>
    </div>
</body>
</html>
