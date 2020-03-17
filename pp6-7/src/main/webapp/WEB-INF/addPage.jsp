<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div style="margin: auto; padding: auto; width: 500px;">
    <c:url value="/registration" var="var"/>
    <form action="${var}" method="POST">
        Choose  username:<input type="text" name="username" id="username"><br>
        <p style="color: red" align="center">${error1}</p>
        Choose  password:<input type="password" name="password" id="password"><br>
        <p style="color: red" align="center">${error2}</p>
        Confirm password:<input type="password" name="password1" id="password1"><br><br>
        <p style="color: red" align="center">${error3}</p>
        <input type="submit" value="Register" style="margin: auto">
    </form>
</div>

</body>
</html>
