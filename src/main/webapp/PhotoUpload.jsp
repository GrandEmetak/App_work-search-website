<%--
  Created by IntelliJ IDEA.
  User: AdminTH
  Date: 15.10.2021
  Time: 13:04
  To change this template use File | Settings | File Templates.
  1. В таблице кандидатов добавьте колонку фото и кнопки (добавить, удалить).
2. Если нажали на кнопку добавить, то переходить на страницу PhotoUpload.jsp с формой загрузки файла.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ФОТО ЗАГРУЗКА</title>
</head>
<body>
<div class="row">
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">ФОТО</a>
        </li>
    </ul>
</div>
</body>
</html>
