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
    wyswietlajacy --> wyswietlanie_konsultacje

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

    abstract class User {
        emailAddress: String
        name: String
        surname: String
    }

    class Teacher {
        degree: String
        phoneNumber: String
        biography: String
    }

    class Administrator {

    }
    
    class OrganizationUnit {
        name: String
        description: String
    }

    class TeacherOrganizationUnit {
        name: String
        description: String
    }

    class ClassGroup {
        name: String
        description: String
    }

    class Course {
        code: String
        name: String
        description: String
    }

    enum ClassFrequency {
        ODD_WEEKS
        EVEN_WEEKS
        ALL_WEEKS
    }

    abstract class Event {
        startTime: LocalTime
        endTime: LocalTime
        dayOfWeek: DayOfWeek
        location: String
        description: String
    }

    class Class {
        type: String
        frequency: ClassFrequency
    }

    class Consultation {

    }

    Event <|.. Class
    Event <|.. Consultation

    User <|.. Teacher
    User <|.. Administrator
    
    TeacherOrganizationUnit "1" *-- "*" TeacherOrganizationUnit
    TeacherOrganizationUnit "1" *-- "*" Teacher

    OrganizationUnit "1" *-- "*" OrganizationUnit
    OrganizationUnit "1" *-- "*" ClassGroup

    Class "0..*" o-- "1" Course
    Class "0..*" o-- "1..*" Teacher

    Teacher "1" *-- "*" Consultation

    ClassGroup "1" *-- "*" Class

Architecture Diagram
********************

.. needuml::

    node "Client - Web browser" {
        node "User interface" {
            [Panel przeglądania\nPlanów Zajęć]
            [Panel zarządzania\nAdministratora]
            [Panel zarządzania\nNauczyciela]
        }
    }


    node "Server" {
        node "Web Application Server" {
            [Obsługa Nauczycieli]
            [Obsługa Jednostek\nOrganizacyjnych]
            [Obsługa Grup\nZajęciowych]
            [Obsługa Terminów Zajęć\ni Konsultacji]
            [Obsługa Przedmiotów]
            [Obsługa logowania]
        }

        database "Database" {

        }
    }


    "Client - Web browser" "*" -- "1" "Server"
    "Web Application Server" -- Database