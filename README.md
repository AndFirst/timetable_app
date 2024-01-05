# Uruchamianie projektu

## Wymagania:
- docker
- java (testowane na wersji jdk 21), zmienna JAVA_HOME musi być ustawiona
- node.js (testowane na wersji v20.10.0)

## Instrukcja uruchomienia:
1. W katalogu projektu uruchomić komendę `./build.sh` na systemach unixowych lub `.\build.cmd` na systemach windowsowych, w celu pominiecia testów należy dodać parametr `-nt`
2. Uruchomić komendę `docker-compose up -d` w celu uruchomienia kontenerów

## Logowanie do aplikacji

Dane logowania administratora:
- login: admin@email.com
- hasło: admin

Dane logowania nauczyciela:
- login: teacher@email.com
- hasło: teacher
