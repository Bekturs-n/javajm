<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <form action="/logout" method="post">
        <input type="submit" value="Sing out"/>
    </form>
</div>
<div style="margin: auto; padding: auto; width: 500px;">
    <h1>Welcome to admin page ${user.username}</h1>
    <table>
        <tr>
            <th><h3>id&ensp;</h3></th>
            <th><h3>login&ensp;</h3></th>
            <th><h3>Roles&ensp;</h3></th>
            <th><h3>Action&ensp;</h3></th>
        </tr>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td>${user.id}&ensp;&ensp;&ensp;</td>
                <td>${user.username}&ensp;&ensp;&ensp;</td>
                <td><c:forEach var="role" items="${user.roles}">
                    <c:out value="${role.authority.contains('ADMIN')?'ADMIN':'USER'}"></c:out>&ensp;
                </c:forEach></td>
                <td>
                    <a href="/admin/edit/${user.id}">edit</a>&ensp;&ensp;
                    <a href="/admin/delete/${user.id}">delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <div>
        <form action="/registration" method="get">
            <input type="submit" value="Add user"/>
        </form>
    </div>
</div>
</body>
</html>
