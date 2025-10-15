<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Review Form</title>
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

        .form-card {
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

        input[type="text"], 
        textarea, 
        input[type="number"] {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            background-color: #ffffff;
            color: #2c3e50;
            font-size: 1rem;
            font-family: 'Inter', sans-serif;
            transition: all 0.3s ease;
        }

        input[type="text"]:focus, 
        textarea:focus, 
        input[type="number"]:focus {
            outline: none;
            border-color: #1a1a1a;
            box-shadow: 0 0 0 3px rgba(26, 26, 26, 0.1);
        }

        textarea {
            resize: vertical;
            min-height: 120px;
            line-height: 1.6;
        }

        input[type="number"] {
            width: 100px;
        }

        .submit-btn {
            background-color: #1a1a1a;
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
            background-color: #333;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
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

        .stars-preview {
            margin-top: 10px;
            font-size: 1.2rem;
        }

        .star {
            color: #ffc107;
            margin-right: 4px;
        }

        .star.filled {
            color: #ffc107;
        }

        .star.empty {
            color: #dee2e6;
        }

        @media (max-width: 768px) {
            body {
                padding: 20px 15px;
            }

            h1 {
                font-size: 2rem;
            }

            .form-card {
                padding: 24px 20px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><c:if test="${review.id == null}">Add New Review</c:if><c:if test="${review.id != null}">Edit Review</c:if></h1>
            <p class="subtitle">Share your experience with us</p>
        </div>

        <div class="form-card">
            <form action="<c:url value='/reviews'/>" method="post">
                <c:if test="${review.id != null}">
                    <input type="hidden" name="id" value="${review.id}" />
                </c:if>
                
                <div class="form-group">
                    <label for="customerName">Customer Name</label>
                    <input type="text" id="customerName" name="customerName" value="${review.customerName}" required />
                </div>

                <div class="form-group">
                    <label for="reviewText">Review Text</label>
                    <textarea id="reviewText" name="reviewText" required>${review.reviewText}</textarea>
                </div>

                <div class="form-group">
                    <label for="stars">Rating (1-5 Stars)</label>
                    <input type="number" id="stars" name="stars" min="1" max="5" value="${review.stars}" required />
                    <div class="stars-preview" id="starsPreview"></div>
                </div>

                <button type="submit" class="submit-btn">Save Review</button>
            </form>
        </div>

        <a href="<c:url value='/reviews'/>" class="back-link">
            &#8592; Back to Reviews
        </a>
    </div>

    <script>
        // Live star preview
        document.getElementById('stars').addEventListener('input', function() {
            const stars = parseInt(this.value) || 0;
            const preview = document.getElementById('starsPreview');
            let html = '';
            
            for (let i = 1; i <= 5; i++) {
                if (i <= stars) {
                    html += '<span class="star filled">&#9733;</span>';
                } else {
                    html += '<span class="star empty">&#9734;</span>';
                }
            }
            
            preview.innerHTML = html;
        });
        
        // Initialize preview on page load
        document.getElementById('stars').dispatchEvent(new Event('input'));
    </script>
</body>
</html>