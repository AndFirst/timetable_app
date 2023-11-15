Use cases diagram
*****************

.. needuml::

    left to right direction

    "Wyświetlający" as wyswietlajacy
    "Nauczyciel" as nauczyciel
    "Administrator" as administrator

    package "Panel Administratora" {
        "Zarządzanie Terminami Zajęć" as (zarzadzanie_zajecia)
        "Zarządzanie Jednostkami Organizacyjnymi" as (zarzadzanie_jo)
        "Zarządzanie Grupami Zajęciowymi" as (zarzadzanie_gz)
        "Zarządzanie Kontami Nauczycieli" as (zarzadzanie_nauczyciele)
    }
    package "Panel Nauczyciela" {
        "Zarządzanie Terminami Konsultacji" as (zarzadzanie_konsultacje)
    }
    package "Przeglądanie Planów Zajęć" {
        "Wyświetlanie Terminów Zajęć" as (wyswietlanie_zajecia)
        "Wyświetlanie Terminów Konsultacji" as (wyswietlanie_konsultacje)
    }
    package "Przeglądanie Jednostek Organizacyjnych" {
        "Przeglądanie Jednostek Organizacyjnych" as (przegladanie_jo)
        "Przeglądanie Nauczycieli" as (przegladanie_nauczycieli)
        "Przeglądanie Grup Zajęciowych" as (przegladanie_gz)
    }
    package "Logowanie" {
        "Logowanie" as (login)
    }

    wyswietlajacy --> wyswietlanie_zajecia

    nauczyciel --> zarzadzanie_konsultacje
    
    administrator --> zarzadzanie_zajecia
    administrator --> zarzadzanie_jo
    administrator --> zarzadzanie_gz
    administrator --> zarzadzanie_nauczyciele

    zarzadzanie_zajecia ..> login : <<Include>>
    zarzadzanie_jo ..> login : <<Include>>
    zarzadzanie_gz ..> login : <<Include>>
    zarzadzanie_nauczyciele ..> login : <<Include>>
    
    zarzadzanie_zajecia ..> wyswietlanie_zajecia : <<Include>>
    zarzadzanie_konsultacje ..> wyswietlanie_konsultacje : <<Include>>

    wyswietlanie_zajecia ..> przegladanie_gz : <<Include>>
    wyswietlanie_konsultacje ..> przegladanie_nauczycieli : <<Include>>

    przegladanie_jo <.. przegladanie_nauczycieli : <<Include>>
    przegladanie_jo <.. przegladanie_gz : <<Include>>


Class diagram
*************

.. needuml::

    class A
    class B
