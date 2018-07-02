<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>

<html>
<head>
    <title>Meals</title>
    <style>
        table {
            width: 100%; /* Ширина таблицы */
            border-collapse: collapse; /*  Убираем двойные линии между ячейками */
            background: #ebebeb;
        }
        td, th {
            padding: 4px; /* Поля в ячейках */
            border: 1px solid #000080; /* Граница между ячейками */
        }
        th {
            background: dimgray; /* Цвет фона строки заголовка */
            color: #ffe; /* Цвет текста */
            text-align: left; /* Выравнивание по левому краю */
            font-family: Arial, Helvetica, sans-serif; /* Выбор гарнитуры */
            font-size: 0.9em; /* Размер текста */
        }
        tr.true { color: red}
        tr.false { color: green}
    </style>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <table border="1">
        <tr>
            <th>Time</th>
            <th>Desc</th>
            <th>Calories</th>
        </tr>

    <c:forEach var="meal" items="${meals}">
        <tr class="${meal.exceed}">
            <td>${meal.dateTime.format(formatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
    </table>

        <h2>Add</h2>
        <form action="add" method="post">
            <p>DateTime: <input type="datetime-local" name="datetime" /></p>
            <p>Description: <input type="text" name="description" /></p>
            <p>Calories: <input type="text" name="calories" /></p>
            <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></p>
        </form>
</body>
</html>
