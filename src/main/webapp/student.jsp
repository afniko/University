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


<title>Student page</title>
</head>
<body>

    <nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="#" class="navbar-brand">Student page</a>
        </div>
    </div>

    <div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="history.back()">previous page</a></li>
            <li><a href="${pageContext.request.contextPath}">main page</a></li>
            <li><a href="${pageContext.request.contextPath}/students">students page</a></li>
            <li><a href="${pageContext.request.contextPath}/groups">groups page</a></li>
        </ul>
    </div>

    </nav>

    <div class="container-fluid">
        <c:if test="${!empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
            <br />
        </c:if>
        <c:if test="${!empty student}">
            <c:if test="${!empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>
            <table class="table table-bordered">
                <tr class="success">
                    <th>First name</th>
                    <td>${student.firstName}</td>
                </tr>
                <tr>
                    <th>Last name</th>
                    <td>${student.lastName}</td>
                </tr>
                <tr>
                    <th>Middle name</th>
                    <td>${student.middleName}</td>
                </tr>
                <tr>
                    <th>Birthday</th>
                    <td>${student.birthday}</td>
                </tr>
                <tr>
                    <th>Id fees</th>
                    <td>${student.idFees}</td>
                </tr>
                <tr>
                    <th>Group title</th>
                    <td>${student.groupTitle}</td>
                </tr>
            </table>
        </c:if>
    </div>
    <br />

    <div class="container-fluid">
        <form class="form-horizontal" action="${pageContext.request.contextPath}/student_update">
            <input type="hidden" name="id" value="${student.id}" />
            <div class="form-group">
                <label for="text" class="col-sm-2 control-label">First name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="first_name" value="${student.firstName}" name="first_name">
                </div>
            </div>
            <div class="form-group">
                <label for="text" class="col-sm-2 control-label">Last name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="last_name" value="${student.lastName}" name="last_name">
                </div>
            </div>
            <div class="form-group">
                <label for="text" class="col-sm-2 control-label">Middle name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="middle_name" value="${student.middleName}" name="middle_name">
                </div>
            </div>
            <div class="form-group">
                <label for="text" class="col-sm-2 control-label">Birthday</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="birthday" value="${student.birthday}" name="birthday">
                </div>
            </div>
            <div class="form-group">
                <label for="text" class="col-sm-2 control-label">Id fees</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="idFees" value="${student.idFees}" name="idFees">
                </div>
            </div>
            <div class="form-group">
                <label for="text" class="col-sm-2 control-label">Group</label>
                <div class="col-sm-10">
                    <select class="selectpicker" data-live-search="true" name="id_group">
                        <c:if test="${!empty student.groupTitle}">
                            <option value="">${student.groupTitle}</option>
                            <option value="0">Remove group</option>
                        </c:if>
                        <c:if test="${empty student.groupTitle}">
                            <option value="">Choose group</option>
                        </c:if>
                        <c:forEach var="group" items="${groups}">
                            <option data-tokens="${group.title}" value="${group.id}">${group.title}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Update</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
