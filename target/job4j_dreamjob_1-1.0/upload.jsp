<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.MemStore" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %><%--
  Created by IntelliJ IDEA.
  User: SlartiBartFast-art
  Date: 14.10.2021
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "Name NEMO");
    if (id != null) {
        candidate = PsqlStore.instOf().findByIdCandidate(Integer.parseInt(id));
    }
%>
<div class="container pt-3">
    <div class="card-body">
        <table class="table">
            <thead>
            <tr>
                <th>URL</th>
                <th>View</th>
            </tr>
            </thead>
            <tbody>
            <table class="container">
                <%--   <c:forEach items="${candidates}" var="candidate"> --%>
                <h2>Upload image</h2>
                    <%--  <jsp:text> <% request.getParameter("id");%></jsp:text>--%>
                 <form action="<%=request.getContextPath()%>/upload?id=<%=candidate.getId()%>" method="post" enctype="multipart/form-data">
                       <%--? --%>
                    <%--   <form action="<c:url value='/upload?id=${candidate.id}'/>" method="post" enctype="multipart/form-data"> --%>
                    <div class="checkbox">
                        <input type="file" name="file">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>
                <%--  </c:forEach> --%>
            </table>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
