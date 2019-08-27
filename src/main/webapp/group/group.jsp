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
        <c:if test="${!empty group}">
            <c:if test="${!empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>
            <table class="table table-bordered">
                <tr class="success">
                    <th>Title</th>
                    <td>${group.title}</td>
                </tr>
                <tr>
                    <th>Year of entry</th>
                    <td>${group.yearEntry}</td>
                </tr>
            </table>
            <button type="button" class="btn btn-info" onclick="window.location='${pageContext.request.contextPath}/group/edit?id=${group.id}'">
            Edit record
            </button>
        </c:if>
    </div>
    <br />
</body>
</html>
