<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="//WEB-INF/includes/header.jsp">
    <jsp:param value="Groups page" name="title" />
    <jsp:param value="Groups page" name="title_header" />
</jsp:include>
</head>
<body>

    <div class="container-fluid">
        <c:if test="${!empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
            <br />
        </c:if>
        <c:if test="${!empty groups}">
            <table class="table table-hover">
                <tr>
                    <th>Title</th>
                    <th>Year of entry</th>
                </tr>
                <c:forEach var="group" items="${groups}">
                    <tr onclick="window.location='${pageContext.request.contextPath}/group?id=${group.id}'">
                        <td>${group.title}</td>
                        <td>${group.yearEntry}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
    <br />
</body>
</html>
