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
        <c:if test="${!empty student}">
            <form class="form-horizontal" action="${pageContext.request.contextPath}/student" method="post">
                <input type="hidden" name="id" value="${student.id}" />
                <div class="form-group">
                    <label for="text" class="col-sm-2 control-label">First name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="first_name" value="${student.firstName}" name="first_name">
                    </div>
                    <label for="text" class="col-sm-2 control-label">Last name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="last_name" value="${student.lastName}" name="last_name">
                    </div>
                    <label for="text" class="col-sm-2 control-label">Middle name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="middle_name" value="${student.middleName}" name="middle_name">
                    </div>
                    <label for="text" class="col-sm-2 control-label">Birthday</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="birthday" value="${student.birthday}" name="birthday">
                    </div>
                    <label for="text" class="col-sm-2 control-label">Id fees</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="idFees" value="${student.idFees}" name="idFees">
                    </div>
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
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Update</button>
                    </div>
                </div>
            </form>
        </c:if>
    </div>

</body>
</html>
