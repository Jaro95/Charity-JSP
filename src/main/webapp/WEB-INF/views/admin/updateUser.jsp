<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 13.06.2024
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>

<div class="w3-display-container w3-light-grey contact" id="admin">
    <p class="w3-center w3-jumbo cantact-info">Edit user</p>
    <form:form modelAttribute="user">
        <table id="adminTable" class="display">
            <thead>
            <tr>
                <th>Id</th>
                <th>Email</th>
                <th>Enabled</th>
                <th>Role</th>
                <th>Password</th>
                <th>Full name</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>

            <tr>
                <td>${userId}</td>
                <td>${userEmail}</td>
                <form:input path="id" type="hidden"/>
                <form:input path="email" type="hidden"/>
                <form:input path="token" type="hidden"/>
                <form:input path="createdAccount" type="hidden"/>

                <td>
                    <form:select class="w3-border" path="enabled">
                        <form:option value="true">Tak</form:option>
                        <form:option value="false">Nie</form:option>
                    </form:select>
                </td>
                <sec:authorize access="hasRole('SUPER_ADMIN')">
                    <td>
                        <c:forEach items="${roles}" var = "role" >
                           <p>${role.name}: <form:checkbox path="roles"
                                             value="${role}"/></p>
                        </c:forEach>

                    </td>
                </sec:authorize>
                <sec:authorize access="!hasRole('SUPER_ADMIN')">
                    <td>
                        <c:forEach items="${userRole}" var="role">
                        <p>${role.name}</p>
                        </c:forEach>
                        <form:input path="roles" type="hidden"/>
                    </td>
                </sec:authorize>
                <sec:authorize access="hasRole('SUPER_ADMIN')">
                    <td><input name="password" type="text" value="${userPassword}"/></td>
                </sec:authorize>
                <sec:authorize access="!hasRole('SUPER_ADMIN')">
                    <td>${userPassword}</td>
                    <form:input path="password" type="hidden"/>
                </sec:authorize>
                <td><form:input path="name"/> <form:input path="lastName"/>
                <p><form:errors path="name" cssClass="alert alert-error"/>
                    <form:errors path="lastName" cssClass="alert alert-error"/></p>
                </td>
                <td>
                    <button class="w3-button w3-black" type="submit">
                        <i class="fa fa-save"></i> Zapisz
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </form:form>
</div>


<jsp:include page="footer.jsp"/>

