<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 03.07.2018
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update</title>
</head>
<body>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal" />

<h2>You are updating our meal.</h2>
<p>Please change the data and hit the "Submit" button. If you want to cancel editing
    and go back to your meals list click <a href="meals">here</a></p>
    <form action="put" method="post">
        <input type="number" value="${meal.id}" name="id" hidden>
        <p>DateTime: <input type="datetime-local" value="${meal.dateTime}" name="datetime" /></p>
        <p>Description: <input type="text" value="${meal.description}" name="description" /></p>
        <p>Calories: <input type="number" value="${meal.calories}" name="calories" /></p>
        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></p>
    </form>

</body>
</html>
