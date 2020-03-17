<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/logout" method="post">
    <input type="submit" value="Sing out"/>
</form>
<div style="margin: auto; padding: auto; width: 500px;">
    <h2> Hello ${user.username}</h2>
    <c:if test="${fn:contains(user.roles, 'ROLE_ADMIN')}">
        <form action="/admin" method="get">
            <input type="submit" value="Go to admin page"/>
        </form>
    </c:if>
</div>
</body>
</html>
