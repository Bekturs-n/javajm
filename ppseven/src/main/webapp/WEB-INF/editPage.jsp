<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Edit</title>
    <style>
        div.wrapper {
            width: 300px;
            height: 100px;
            margin: auto;
        }

        div.left_block {
            float: left;
            width: 40%;
            height: 30px;
        }

        div.right_block {
            float: right;
            width: 60%;
            height: 30px;
            text-align: center;
        }
    </style>
</head>
<body>
<div style="width: 300px; margin: auto; padding: auto">
    <c:url value="/admin/edit" var="var"/>
    <form action="${var}" method="POST">
        <div>
            <input type="hidden" name="id" value="${user.id}">
            <div>
                Username:
                <input type="text" name="username" id="username" value="${user.username}">
                <p align="center" style="color: red">${error1}</p>
            </div>
            <div>
                Password:
                <input type="password" name="password" id="password" value="">
                <p align="center" style="color: red">${error2}</p>
            </div>
        </div>
        <div>
            <%--            <c:forEach items="${roles}" var="role">--%>
            <div>
                <label>
                    <input type="radio" name="role" value="ADMIN"
                           <c:if test="${user_role}">checked</c:if>>
                    ADMIN
                </label>
                <label>
                    <input type="radio" name="role" value="USER"
                           <c:if test="${!user_role}">checked</c:if>>
                    USER
                </label>
            </div>
            <%--            </c:forEach>--%>
            <br>
            <input type="submit" value="Save changes">
        </div>
    </form>
</div>
</body>
</html>
