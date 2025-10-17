<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Select User</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>">
    <style>
        .user-card__header h1 {
            color: #ffffff !important;
        }
        
        .password-field {
            margin: 15px 0;
            padding: 0 15px;
        }
        
        .password-input {
            width: 100%;
            padding: 10px 12px;
            border: 2px solid #e9ecef;
            border-radius: 6px;
            font-size: 14px;
            font-family: 'Inter', sans-serif;
            transition: all 0.3s ease;
            background-color: #ffffff;
        }
        
        .password-input:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.1);
        }
        
        .password-input::placeholder {
            color: #6c757d;
            font-style: italic;
        }
    </style>
</head>
<body class="gradient-bg">
<div class="user-card">
    <div class="user-card__header">
        <h1>Select a User to Continue</h1>
        <p>Choose your profile to access your account</p>
        <c:if test="${param.error == 'invalid_password'}">
            <div style="color: #dc3545; background-color: #f8d7da; border: 1px solid #f5c6cb; padding: 10px; border-radius: 4px; margin: 10px 0;">
                Invalid password. Please try again.
            </div>
        </c:if>
    </div>

    <div class="user-list">
        <c:choose>
            <c:when test="${not empty users}">
                <c:forEach var="user" items="${users}">
                    <form class="user-item" action="<c:url value='/select-user'/>" method="post">
                        <div class="user-item__meta">
                            <div class="avatar">
                                <span>${fn:substring(user.username, 0, 1)}</span>
                            </div>
                            <div class="user-item__text">
                                <div class="user-name">${user.username}</div>
                                <div class="user-role">
                                    <c:out value="${user.role}"/>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <div class="password-field">
                            <input type="password" name="password" placeholder="Enter password" required class="password-input"/>
                        </div>
                        <div class="user-actions">
                            <button type="submit" class="btn-select">Select</button>
                            <a href="<c:url value='/users/delete/${user.id}'/>" class="btn-delete" onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
                        </div>
                    </form>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <form class="user-item" action="#" method="get">
                    <div class="user-item__meta">
                        <div class="avatar"><span>C</span></div>
                        <div class="user-item__text">
                            <div class="user-name">customer1</div>
                            <div class="user-role">Customer</div>
                        </div>
                    </div>
                    <button type="button" class="btn-select" onclick="location.href='<c:url value='/reviews'/>'">Select</button>
                </form>
                <form class="user-item" action="#" method="get">
                    <div class="user-item__meta">
                        <div class="avatar"><span>A</span></div>
                        <div class="user-item__text">
                            <div class="user-name">admin</div>
                            <div class="user-role">Administrator</div>
                        </div>
                    </div>
                    <button type="button" class="btn-select" onclick="location.href='<c:url value='/admin/reviews'/>'">Select</button>
                </form>
                <form class="user-item" action="#" method="get">
                    <div class="user-item__meta">
                        <div class="avatar"><span>G</span></div>
                        <div class="user-item__text">
                            <div class="user-name">guest</div>
                            <div class="user-role">Guest Account</div>
                        </div>
                    </div>
                    <button type="button" class="btn-select" onclick="location.href='<c:url value='/reviews'/>'">Select</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="user-card__footer">
        <a class="btn-register" href="<c:url value='/register'/>">
            <span class="plus">+</span>
            <span>Register New User</span>
        </a>
    </div>
</div>
</body>
</html>