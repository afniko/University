<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="includes/header.jsp" />
</head>
<body>

    <div class="container-fluid">
        <c:if test="${!empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
            <br />
        </c:if>
        <c:if test="${!empty group}">
            <div class="alert alert-success">${successMessage}</div>
            <br />
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
        </c:if>
    </div>
    <br />

    <div class="container-fluid">
        <form class="form-horizontal" action="${pageContext.request.contextPath}/group_create" method="post">
            <div class="form-group">
                <label for="text" class="col-sm-3 control-label">Title</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="title" name="title" placeholder="Enter title">
                </div>

                <label for="text" class="col-sm-3 control-label">Year of entry</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="year_entry" name="year_entry" placeholder="YYYY-MM-DD">
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-9">
                    <button type="submit" class="btn btn-default">Create</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
