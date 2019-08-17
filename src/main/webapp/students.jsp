<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu"
    crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ"
    crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>

<title>Students page</title>
</head>
<body>


    <nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="#" class="navbar-brand">Students page</a>
        </div>
    </div>

    <div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="history.back()">previous page</a></li>
            <li><a href="${pageContext.request.contextPath}">main page</a></li>
            <li><a href="${pageContext.request.contextPath}/groups">groups page</a></li>
        </ul>
    </div>

    </nav>

    <div class="container-fluid">
        <c:if test="${!empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
            <br />
        </c:if>
        <c:if test="${!empty students}">
            <form class="form-inline" action="${pageContext.request.contextPath}/student">
                <div class="form-group">
                    <label for="idGroup">id#</label> <input type="text" class="form-control" id="idGroup" placeholder="Enter Id" name="id">
                </div>
                <button type="submit" class="btn btn-default">Search</button>
            </form>

            <br />

            <table class="table table-hover">
                <tr>
                    <th>First name</th>
                    <th>Middle name</th>
                    <th>Birthday</th>
                    <th>Id fees</th>
                    <th>Group title</th>
                </tr>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td>${student.firstName}</td>
                        <td>${student.middleName}</td>
                        <td>${student.birthday}</td>
                        <td>${student.idFees}</td>
                        <td>${student.groupTitle}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</body>
</html>
