curl -X GET "localhost:8080/screening?fromDate=2021-01-01T11:00:00&toDate=2021-08-13T16:00:00"
curl localhost:8080/screening/1

curl --header "Content-Type: application/json" -X POST --data "{\"seatId\": [ 2 , 7 ],\"screeningId\":1,\"name\": \"Jan\",\"surname\": \"Kowalski-Nowak\",\"reservationTickets\": [{\"number\": 1,\"name\": \"Adult\"},{\"number\": 1, \"name\": \"Student\"}]}" localhost:8080/reservation