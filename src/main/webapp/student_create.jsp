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
        <c:if test="${!empty student}">
            <div class="alert alert-success">${successMessage}</div>
            <br />
            <table class="table table-bordered">
                <tr class="success">
                    <th>First name</th>
                    <td>${student.firstName}</td>
                </tr>
                <tr>
                    <th>Middle name</th>
                    <td>${student.middleName}</td>
                </tr>
                <tr>
                    <th>Last name</th>
                    <td>${student.lastName}</td>
                </tr>
                <tr>
                    <th>Date of Birthday</th>
                    <td>${student.birthday}</td>
                </tr>
                <tr>
                    <th>Id fees</th>
                    <td>${student.idFees}</td>
                </tr>
            </table>
        </c:if>
    </div>
    <br />

    <div class="container-fluid">
        <form class="form-horizontal" action="${pageContext.request.contextPath}/student_create">
            <div class="form-group">
                <label for="text" class="col-sm-3 control-label">First name</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="first_name" name="first_name" placeholder="Enter first name">
                </div>
                <label for="text" class="col-sm-3 control-label">Middle name</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="middle_name" name="middle_name" placeholder="Enter middle name">
                </div>
                <label for="text" class="col-sm-3 control-label">Last name</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="last_name" name="last_name" placeholder="Enter last name">
                </div>
                <label for="text" class="col-sm-3 control-label">Date of Birthday</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="birthday" name="birthday" placeholder="YYYY-MM-DD">
                </div>
                <label for="text" class="col-sm-3 control-label">Id Fees</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="idFees" name="idFees" placeholder="XXXXXXXXX - 9 number">
                </div>
                <div class="col-sm-offset-3 col-sm-9">
                    <button type="submit" class="btn btn-default">Create</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
