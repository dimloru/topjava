REST commands
=============


GET<br> 
DELETE<br>
UPDATE (body app/json,<br>
example:
{
   "id": 100005,
   "dateTime": "2015-05-31T10:00:00",
   "description": "Русский",
   "calories": 503
})<br>
**/topjava/rest/meals/foruser/{uid}/id/{id}**
-----------------------------------------------

GET
/topjava/rest/meals/foruser/100000
All meals for user 100000

------------------------------

POST
/topjava/rest/meals/foruser/100000

body: app/json
{      
      "dateTime": "2018-09-05T12:00:00",
      "description": "NEWWWW",
      "calories": 1200
}
Create new meal for user

----------------

GET
/topjava/rest/meals/foruser/100000/between
?startDate=2015-05-31&endTime=17:00:00

startDate
endDat
startTime
endTime

ISO format