# Uruchamianie projektu

## Wymagania:
- docker
- java (testowane na wersji openjdk 21.0.1), zmienna JAVA_HOME musi być ustawiona
- node.js (testowane na wersji v20.10.0)

## Instrukcja uruchomienia:
1. W systemach unixowych nadać plikom `./mvnw` i `./build.sh` uprawnienia do wykonywania komendą `chmod +x ./mvnw ./build.sh`.
2. W katalogu projektu uruchomić komendę `./build.sh` na systemach unixowych lub `.\build.cmd` na systemach windowsowych, w celu pominięcia testów należy dodać parametr `-nt`.
3. Uruchomić komendę `docker-compose up -d` w celu uruchomienia kontenerów.
4. Aplikacja powinna być dostępna pod adresem `http://localhost:8080`.

## Logowanie do aplikacji

Dane logowania administratora:
- login: admin@email.com
- hasło: admin

Dane logowania nauczyciela:
- login: teacher@email.com
- hasło: teacher
