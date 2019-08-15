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

<title>Student page</title>
</head>
<body>
    <h1>Student page</h1>
    <br />
    <h2>${text}</h2>
    <br />
    <a href="${pageContext.request.contextPath}/students"> Back to students page </a>
    <br />

    <table id="t01">
        <tr>
            <th>id</th>
            <th>First name</th>
            <th>Middle name</th>
            <th>Birthday</th>
            <th>Id fees</th>
            <th>Group title</th>
        </tr>
        <tr>
            <td>${student.id}</td>
            <td>${student.firstName}</td>
            <td>${student.middleName}</td>
            <td>${student.birthday}</td>
            <td>${student.idFees}</td>
            <td>${student.group.title}</td>
        </tr>
    </table>
    <br />
    <a href="${pageContext.request.contextPath}"> Back to main page </a>
    <br />
</body>
</html>
