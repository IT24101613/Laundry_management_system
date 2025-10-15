<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register New User</title>
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
            background: white;
            border-radius: 16px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            padding: 50px;
            max-width: 480px;
            width: 100%;
            border: 1px solid #e9ecef;
        }

        .header {
            text-align: center;
            margin-bottom: 40px;
        }

        .logo {
            width: 60px;
            height: 60px;
            background: linear-gradient(135deg, #1a1a1a, #333);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 20px;
            color: white;
            font-size: 1.5rem;
        }

        h1 {
            color: #1a1a1a;
            font-size: 2rem;
            font-weight: 700;
            margin-bottom: 8px;
        }

        .subtitle {
            color: #6c757d;
            font-size: 1rem;
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

        .input-wrapper {
            position: relative;
        }

        input {
            width: 100%;
            padding: 14px 16px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s ease;
            background-color: #f8f9fa;
            color: #1a1a1a;
        }

        input:focus {
            outline: none;
            border-color: #1a1a1a;
            background-color: white;
            box-shadow: 0 0 0 3px rgba(26, 26, 26, 0.1);
        }

        input:valid {
            border-color: #28a745;
        }

        .input-icon {
            position: absolute;
            right: 16px;
            top: 50%;
            transform: translateY(-50%);
            color: #6c757d;
        }

        .btn-primary {
            width: 100%;
            background: #1a1a1a;
            color: white;
            border: none;
            padding: 16px;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 10px;
        }

        .btn-primary:hover {
            background: #333;
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
        }

        .btn-primary:active {
            transform: translateY(0);
        }

        .back-link {
            text-align: center;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #e9ecef;
        }

        .back-link a {
            color: #6c757d;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .back-link a:hover {
            color: #1a1a1a;
        }

        .form-footer {
            text-align: center;
            margin-top: 30px;
            color: #6c757d;
            font-size: 0.9rem;
        }

        .password-requirements {
            margin-top: 8px;
            font-size: 0.85rem;
            color: #6c757d;
        }

        @media (max-width: 480px) {
            .container {
                padding: 30px 20px;
            }

            h1 {
                font-size: 1.75rem;
            }

            body {
                padding: 15px;
            }
        }

        /* Validation styles */
        input:invalid:not(:focus):not(:placeholder-shown) {
            border-color: #dc3545;
        }

        input:valid:not(:focus):not(:placeholder-shown) {
            border-color: #28a745;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <div class="logo">
            <i class="fas fa-user-plus"></i>
        </div>
        <h1>Create Account</h1>
        <p class="subtitle">Join our community today</p>
    </div>

    <form action="<c:url value='/register'/>" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <div class="input-wrapper">
                <input type="text" id="username" name="username" required placeholder="Enter your username">
                <i class="fas fa-user input-icon"></i>
            </div>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <div class="input-wrapper">
                <input type="password" id="password" name="password" required placeholder="Create a password">
                <i class="fas fa-lock input-icon"></i>
            </div>
            <div class="password-requirements">
                Use 8+ characters with a mix of letters, numbers & symbols
            </div>
        </div>

        <div class="form-group">
            <label for="email">Email Address</label>
            <div class="input-wrapper">
                <input type="email" id="email" name="email" required placeholder="your.email@example.com">
                <i class="fas fa-envelope input-icon"></i>
            </div>
        </div>

        <button type="submit" class="btn-primary">
            <i class="fas fa-user-plus"></i>
            Create Account
        </button>
    </form>

    <div class="back-link">
        <a href="<c:url value='/select-user'/>">
            <i class="fas fa-arrow-left"></i>
            Back to User Selection
        </a>
    </div>

    <div class="form-footer">
        <p>Already have an account? <a href="<c:url value='/select-user'/>" style="color: #1a1a1a; font-weight: 600;">Sign in here</a></p>
    </div>
</div>

<script>
    // Add simple form validation and enhancements
    document.addEventListener('DOMContentLoaded', function() {
        const inputs = document.querySelectorAll('input');

        inputs.forEach(input => {
            // Add focus effects
            input.addEventListener('focus', function() {
                this.parentElement.style.transform = 'scale(1.02)';
            });

            input.addEventListener('blur', function() {
                this.parentElement.style.transform = 'scale(1)';
            });

            // Real-time validation feedback
            input.addEventListener('input', function() {
                if (this.value.length > 0) {
                    this.style.backgroundColor = '#f8fff9';
                } else {
                    this.style.backgroundColor = '#f8f9fa';
                }
            });
        });

        // Form submission enhancement
        const form = document.querySelector('form');
        form.addEventListener('submit', function(e) {
            const submitBtn = this.querySelector('button[type="submit"]');
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Creating Account...';
            submitBtn.disabled = true;
        });
    });
</script>
</body>
</html>