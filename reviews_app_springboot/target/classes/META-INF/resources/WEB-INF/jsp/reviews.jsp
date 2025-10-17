<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Reviews</title>
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

        .subtitle {
            color: #6c757d;
            font-size: 1.1rem;
            font-weight: 400;
        }

        .add-review-btn {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            background-color: #1a1a1a;
            color: white;
            text-decoration: none;
            padding: 12px 24px;
            border-radius: 8px;
            font-weight: 600;
            transition: all 0.3s ease;
            margin-bottom: 30px;
        }

        .add-review-btn:hover {
            background-color: #333;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .reviews-list {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        .review-card {
            background: white;
            border-radius: 12px;
            padding: 24px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #e9ecef;
            transition: all 0.3s ease;
            position: relative;
        }

        .review-card:hover {
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
            transform: translateY(-2px);
        }

        .review-header {
            display: flex;
            justify-content: between;
            align-items: flex-start;
            margin-bottom: 12px;
        }

        .review-customer {
            font-size: 1.1rem;
            font-weight: 600;
            color: #1a1a1a;
        }

        .review-stars {
            color: #1a1a1a;
            font-size: 1.1rem;
            letter-spacing: 2px;
            margin: 8px 0;
        }

        .star {
            font-size: 1.2rem;
            margin-right: 2px;
        }

        .star.filled {
            color: #ffc107;
        }

        .star.empty {
            color: #dee2e6;
        }

        .review-text {
            color: #495057;
            font-size: 1rem;
            line-height: 1.7;
            margin: 16px 0;
            padding: 16px;
            background-color: #f8f9fa;
            border-radius: 8px;
            border-left: 4px solid #1a1a1a;
        }

        .review-actions {
            display: flex;
            gap: 16px;
            margin-top: 20px;
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

        .edit-btn {
            color: #1a1a1a;
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
        }

        .edit-btn:hover {
            background-color: #e9ecef;
            border-color: #adb5bd;
        }

        .delete-btn {
            color: #dc3545;
            background-color: #fff5f5;
            border: 1px solid #f5c6cb;
        }

        .delete-btn:hover {
            background-color: #f8d7da;
            border-color: #dc3545;
        }

        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #6c757d;
        }

        .empty-state i {
            font-size: 3rem;
            margin-bottom: 20px;
            opacity: 0.5;
        }

        .stats-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding: 16px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
        }

        .total-reviews {
            font-weight: 600;
            color: #1a1a1a;
        }

        @media (max-width: 768px) {
            body {
                padding: 20px 15px;
            }

            h1 {
                font-size: 2rem;
            }

            .review-header {
                flex-direction: column;
                gap: 10px;
            }

            .review-actions {
                flex-direction: column;
                gap: 10px;
            }

            .action-btn {
                justify-content: center;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Customer Reviews</h1>
        <p class="subtitle">What our customers are saying about us</p>
    </div>

    <div class="stats-bar">
        <div class="total-reviews">Total Reviews: ${reviews.size()}</div>
        <a href="<c:url value='/reviews/new'/>" class="add-review-btn">
            <i class="fas fa-plus"></i>
            Add New Review
        </a>
    </div>

    <div class="reviews-list">
        <c:choose>
            <c:when test="${not empty reviews}">
                <c:forEach var="review" items="${reviews}">
                    <div class="review-card">
                        <div class="review-header">
                            <div class="review-customer">${review.customerName}</div>
                        </div>
                        <div class="review-stars">
                            <c:forEach begin="1" end="${review.stars}">
                                <span class="star filled">&#9733;</span>
                            </c:forEach>
                            <c:forEach begin="${review.stars + 1}" end="5">
                                <span class="star empty">&#9734;</span>
                            </c:forEach>
                            <span style="margin-left: 8px; color: #6c757d; font-size: 0.9rem;">(${review.stars}/5)</span>
                        </div>
                        <div class="review-text">${review.reviewText}</div>
                        <div class="review-actions">
                            <a href="<c:url value='/reviews/edit/${review.id}'/>" class="action-btn edit-btn">
                                <i class="fas fa-edit"></i>
                                Edit Review
                            </a>
                            <a href="<c:url value='/reviews/delete/${review.id}'/>"
                               class="action-btn delete-btn"
                               onclick="return confirm('Are you sure you want to delete this review?');">
                                <i class="fas fa-trash"></i>
                                Delete Review
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <i class="fas fa-comments"></i>
                    <h3>No Reviews Yet</h3>
                    <p>Be the first to share your experience!</p>
                    <a href="<c:url value='/reviews/new'/>" class="add-review-btn" style="margin-top: 20px;">
                        <i class="fas fa-plus"></i>
                        Write First Review
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- Font Awesome for icons -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
</body>
</html>