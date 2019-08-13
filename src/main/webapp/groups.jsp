<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}

th, td {
	padding: 15px;
	text-align: left;
}

table#t01 {
	width: 100%;
	background-color: #f1f1c1;
}
</style>

<title>Groups page</title>
</head>
<body>
    <h1>Groups page</h1>
    <br />
    <h2>${text}</h2>
    <br />
    <a href="history.back()">Click here to previous page</a>
    <br />

    <h6>Find group by id:</h6>
    <form action="${pageContext.request.contextPath}/group">
        <input type="text" name="id"> <input type="submit" value="search">
    </form>
    <br />

    <table id="t01">
        <tr>
            <th>id</th>
            <th>Title</th>
            <th>Year of entry</th>
        </tr>
        <c:forEach var="group" items="${groups}">
            <tr>
                <td>${group.id}</td>
                <td>${group.title}</td>
                <td>${group.yearEntry}</td>
            </tr>
        </c:forEach>
    </table>
    <br />
    <a href="${pageContext.request.contextPath}" class="btn btn-success"> Back to main page </a>
    <br />
</body>
</html>
