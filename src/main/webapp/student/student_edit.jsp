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
        <c:if test="${!empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
    </div>
    <br />

    <div class="container-fluid">
        <form class="form-horizontal" action="${pageContext.request.contextPath}/student/edit" method="post">
            <div class="form-group">
                <label for="text" class="col-sm-2 control-label">First name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="first_name" value="${student.firstName}" name="first_name" placeholder="Enter first name">
                </div>
                <label for="text" class="col-sm-2 control-label">Last name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="last_name" value="${student.lastName}" name="last_name" placeholder="Enter last name">
                </div>
                <label for="text" class="col-sm-2 control-label">Middle name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="middle_name" value="${student.middleName}" name="middle_name" placeholder="Enter middle name">
                </div>
                <label for="text" class="col-sm-2 control-label">Birthday</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="birthday" value="${student.birthday}" name="birthday" placeholder="YYYY-MM-DD">
                </div>
                <label for="text" class="col-sm-2 control-label">Id fees</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="idFees" value="${student.idFees}" name="idFees" placeholder="XXXXXXXXX - 9 number">
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
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">

                    <c:if test="${empty student}">
                        <button type="submit" class="btn btn-default">Create</button>
                    </c:if>
                    <c:if test="${!empty student}">
                        <input type="hidden" name="id" value="${student.id}" />
                        <button type="submit" class="btn btn-default">Update</button>
                    </c:if>
                </div>
            </div>
        </form>

    </div>

</body>
</html>
