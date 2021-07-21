# TicketBookingApp

### Cel Zadania
Stworzenie systemu rezerwacji miejsc w pojedyńczym kinie z wieloma salami.

### Założenia
- System pokrywa jedno kino z wieloma salami.
- Rezerwacjii można dokonać maksymalnie 15 minut przed rozpoczęciem seansu.
- Rezerwacja wygasa 10 minut przed rozpoczęciem seanus.
- Są trzy typy biletów
- W rzędzie nie może być wolnego pojedyńczego miejsca pomiędzy zarezerwowanymi miejscami.

### Instrukcja Uruchomienia
1.<b>Maven</b>: Wymagany jest zainstalowany <a href="https://maven.apache.org/index.html">maven</a>. W konsoli wpisujemy:

```
git clone https://github.com/mdabrow9/TicketBookingApp.git
cd TicketBookingApp
mvn spring-boot:run
```

2. <b>konsola</b>: Uruchamiamy poleceniami:
```
git clone https://github.com/mdabrow9/TicketBookingApp.git
cd TicketBookingApp
mvnw spring-boot:run
```

### Demo

Baza danych inicjalizowana jest podczas startu aplikacji.  
Przykładowy sposób użycia jest zawarty w pliku demo.sh

### Działanie
Dostępne endpointy:
* GET `/screening` : Posiada dwa wymagane parametry: `fromDate` i `toDate` w które przyjmują timestamp odpowidnio początku i końca czasu rozpoczęcia seansu. Zwraca seanse dostępne w danym przedziale czasu.
* GET `/screening/{id}` : Zwraca informację o sali i dostępne miejsca dla seansu o zadanym id: {id}.
* POST `/reservation` : tworzy nową rezerwację w oparciu o ciało zapytania. Zwraca sumę do zapłaty i termin wygaśnięcia rezerwacji. Przykładowe ciało:
```
{
    "seatId": [
        2,
        1
    ],
    "screeningId": 1,
    "name": "Jan",
    "surname": "Kowalski-Nowak",
    "reservationTickets": [
        {
            "number": 1,
            "name": "Adult"
        },
        {
            "number": 1,
            "name": "Student"}
        ]
    }
```
##### Oznaczenia
- `seatId`: lista id miejsc które chcemy zarezerwować.
- `screeningId`: id seansu.
- `name`: Imię osoby rezerwującej.
- `surname`: Nazwisko osoby rezerwującej.
- `reservationTickets`: lista obiektów reprezentujących rodzaj biletu(`name`) oraz rezerwowaną liczbę (`number`).
  
