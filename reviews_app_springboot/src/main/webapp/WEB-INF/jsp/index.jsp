<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Feedback Portal</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 50px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 500px;
            width: 100%;
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .logo {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, #667eea, #764ba2);
            border-radius: 50%;
            margin: 0 auto 25px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 2rem;
        }

        h1 {
            color: #2d3748;
            font-size: 2.2rem;
            font-weight: 700;
            margin-bottom: 15px;
            line-height: 1.3;
        }

        .subtitle {
            color: #718096;
            font-size: 1.1rem;
            margin-bottom: 40px;
            line-height: 1.6;
        }

        .button-group {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-bottom: 30px;
        }

        .btn {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 12px;
            padding: 16px 24px;
            border: none;
            border-radius: 12px;
            font-size: 1rem;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(102, 126, 234, 0.3);
        }

        .btn-secondary {
            background: #f7fafc;
            color: #4a5568;
            border: 2px solid #e2e8f0;
        }

        .btn-secondary:hover {
            background: #edf2f7;
            border-color: #cbd5e0;
            transform: translateY(-2px);
        }

        .features {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-top: 40px;
            padding-top: 30px;
            border-top: 1px solid #e2e8f0;
        }

        .feature {
            text-align: center;
        }

        .feature-icon {
            width: 50px;
            height: 50px;
            background: #f7fafc;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 10px;
            color: #667eea;
            font-size: 1.2rem;
        }

        .feature-text {
            color: #718096;
            font-size: 0.85rem;
            font-weight: 500;
        }

        @media (max-width: 480px) {
            .container {
                padding: 30px 20px;
            }

            h1 {
                font-size: 1.8rem;
            }

            .features {
                grid-template-columns: 1fr;
                gap: 15px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="logo">
        <i class="fas fa-comments"></i>
    </div>

    <h1>Customer Feedback Management</h1>
    <p class="subtitle">
        Share your experience and help us improve our services. Your feedback matters to us.
    </p>

    <div class="button-group">
        <a href="<c:url value='/select-user'/>" class="btn btn-primary">
            <i class="fas fa-user-circle"></i>
            Select Existing User
        </a>
        <a href="<c:url value='/register'/>" class="btn btn-secondary">
            <i class="fas fa-user-plus"></i>
            Register New Account
        </a>
    </div>

    <div class="features">
        <div class="feature">
            <div class="feature-icon">
                <i class="fas fa-shield-alt"></i>
            </div>
            <div class="feature-text">Secure & Private</div>
        </div>
        <div class="feature">
            <div class="feature-icon">
                <i class="fas fa-clock"></i>
            </div>
            <div class="feature-text">24/7 Available</div>
        </div>
        <div class="feature">
            <div class="feature-icon">
                <i class="fas fa-headset"></i>
            </div>
            <div class="feature-text">Quick Response</div>
        </div>
    </div>
</div>
</body>
</html>