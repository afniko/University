<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="//WEB-INF/includes/header.jsp" />
</head>
<body>

    <div class="container-fluid">
        <c:if test="${!empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
            <br />
        </c:if>
        <c:if test="${!empty students}">
            <table class="table table-hover">
                <tr>
                    <th>First name</th>
                    <th>Middle name</th>
                    <th>Birthday</th>
                    <th>Id fees</th>
                    <th>Group title</th>
                </tr>
                <c:forEach var="student" items="${students}">
                    <tr onclick="window.location='${pageContext.request.contextPath}/student?id=${student.id}'">
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
