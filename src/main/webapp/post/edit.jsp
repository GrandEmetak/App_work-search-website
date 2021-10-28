<%--
  Created by IntelliJ IDEA.
  User: SlartiBartFast-art
  Date: 22.09.2021
  Time: 15:19
  To change this template use File | Settings | File Templates.
  Создадим страницу для создания новой вакансии.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.store.MemStore" %>
<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>


    <title>Работа мечты</title>
    <script>
        function validate() {
            let x = Boolean(true);
            if ($('#firstName').val() === '') {
                x = false;
            }
            if ($('#description').val() === '') {
                x = false;
            }
            if ($('#city').val() === '') {
                x = false;
            }
            return x;
        }
    </script>
    <script>
        function addRow() {
            if (validate()) {
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/dreamjob_1/posts.do',
                    data: 'id' + $('#id').val(),
                    'name=': +$('#firstName').val(),
                    'description=': +$('#description').val(),
                    'city=': +$('#city').val(),
                    dataType: 'text'
                }).done(function (data) {
                    alert(data);
                }).fail(function (err) {
                    alert(err);
                });
            }
        }
    </script>
</head>
<body>
<%
    String id = request.getParameter("id");
    Post post = new Post(0, "", "", "");
    if (id != null) {
        post = PsqlStore.instOf().findById(Integer.parseInt(id));
    }
%>
<div class="needs-validation" novalidate>
    <div class="form-auto">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                <h5>New Post</h5>
                <% } else { %>
                <h5>you are in the Post edit field</h5>
                <% } %>
            </div>
            <div class="card-body">
                <%-- ниже адрес сервлета в web.xml длЯ загрузки указна метод ПОСТ
                 <form action="<%=request.getContextPath()%>/post/save?id=<%=post.getId()%>" method="post"> был это но поменяли на  post.do
                 --%>
                <form action="<%=request.getContextPath()%>/posts.do?id=<%=post.getId()%>" method="post" ><%--id="id" --%>
                    <div class="form-group">
                        <div class="col-md-6 mb-3">
                            <label>Name</label>
                            <input type="text" class="form-control" name="name" value="<%=post.getName()%>"
                                   id="firstName"
                                   placeholder="Pleas enter First name" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label>Description</label>
                            <input type="text" class="form-control" name="description"
                                   value="<%=post.getDescription()%>"
                                   id="description"
                                   placeholder="Please enter description Post" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label>City</label>
                            <input type="text" class="form-control" name="city" value="<%=post.getCityId()%>"
                                   id="city"
                                   placeholder="Please enter your city" required>
                        </div>
                    </div>
                    <%-- <button type="submit" class="btn btn-primary">Сохранить</button> addRow(--%>
                    <button type="submit" class="btn btn-primary" onclick="validate()">Save</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
