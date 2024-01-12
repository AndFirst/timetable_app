Requirements analysis
*********************

Project goal
============

The primary goal of this project is to develop a comprehensive academic management system that empowers administrators, teachers, and viewers to efficiently manage and access information related to subjects, lecturers, organization units, groups, and class schedules. The system aims to streamline administrative tasks for system administrators, provide academic teachers with the ability to manage their consultation appointments seamlessly, and offer viewers an intuitive interface to view schedules for individual groups and teachers.

Actors of the system
====================
1. **Administrators** - system administrators who add, delete, edit information about subjects, lecturers, organization units and groups. They add and edit class schedules, and create accounts for Academic Teachers.
2. **Teachers** - add, delete and edit their consultation appointments.
3. **Viewers** - view the timetables of individual organization units.


High-level requirements
=======================

.. hlreq:: System powinien umożliwiać Przeglądającym przeglądanie listy Jednostek Organizacyjnych.
    :tags: organization_units
    :id: HLR_010

.. hlreq:: System powinien umożliwiać Przeglądającym przeglądanie listy Grup Zajęciowych.
    :tags: class_groups
    :id: HLR_015
    :requires: HLR_010

.. hlreq:: System powinien umożliwiać Przeglądającym wyświetlanie planu zajęć wybranej Grupy Zajęciowej.
    :tags: schedule; classes_viewing
    :id: HLR_020
    :requires: HLR_015

    Wybór Grupy Zajęciowej powinien polegać na wyborze jednej z możliwie wielu Grup Zajęciowych wybranej Jednostki Organizacyjnej.

.. hlreq:: System powinien umożliwiać Przeglądającym przeglądanie listy Nauczycieli.
    :tags: teachers
    :id: HLR_023

.. hlreq:: System powinien umożliwiać Przeglądającym wyświetlanie planu zajęć wybranego Nauczyciela w tym jego Terminów Konsultacji.
    :tags: schedule; consultations; classes_viewing
    :id: HLR_030
    :requires: HLR_023


.. hlreq:: System powinien umożliwiać Nauczycielom i Administratorom zarządzanie Terminami Konsultacji.
    :tags: consultations
    :id: HLR_110
    :requires: HLR_300

    Administratorzy mogą zarządzać Terminami Konsultacji wszystkich Nauczycieli, a Nauczyciele tylko swoich Terminów Konsultacji.

    .. hlreq:: System powinien umożliwiać dodawanie Terminów Konsultacji.
        :tags: consultations
        :id: HLR_111

    .. hlreq:: System powinien umożliwiać przeglądanie Terminów Konsultacji.
        :tags: consultations
        :id: HLR_112

    .. hlreq:: System powinien umożliwiać edytowanie Terminów Konsultacji.
        :tags: consultations
        :id: HLR_113

    .. hlreq:: System powinien umożliwiać usuwanie z systemu Terminów Konsultacji.
        :tags: consultations
        :id: HLR_114

.. hlreq:: System powinien dostarczać Administratorom panel zarządzania.
    :tags: admin_panel
    :id: HLR_200
    :requires: HLR_300

    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie Przedmiotami.
        :tags: courses; admin_panel
        :id: HLR_210

        .. hlreq:: System powinien umożliwiać Administratorom dodawanie do systemu nowych Przedmiotów.
            :tags: courses
            :id: HLR_211

        .. hlreq:: System powinien umożliwiać Administratorom przeglądanie istniejących w systemie Przedmiotów.
            :tags: courses
            :id: HLR_212

        .. hlreq:: System powinien umożliwiać Administratorom edytowanie istniejących w systemie Przedmiotów.
            :tags: courses
            :id: HLR_213

        .. hlreq:: System powinien umożliwiać Administratorom usuwanie z systemu Przedmiotów.
            :tags: courses
            :id: HLR_214


    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie Kontami użytkowników.
        :tags: acccounts; admin_panel
        :id: HLR_220

        .. hlreq:: System powinien umożliwiać Administratorom zakładanie nowych Kont użytkowników.
            :tags: acccounts
            :id: HLR_221

        .. hlreq:: System powinien umożliwiać Administratorom przeglądanie istniejących w systemie Kont użytkowników.
            :tags: acccounts
            :id: HLR_222

        .. hlreq:: System powinien umożliwiać Administratorom edytowanie istniejących w systemie Kont użytkowników.
            :tags: acccounts
            :id: HLR_223

        .. hlreq:: System powinien umożliwiać Administratorom usuwanie z systemu Kont użytkowników.
            :tags: acccounts
            :id: HLR_224


    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie Jednostkami Organizacyjnymi.
        :tags: organization_units; admin_panel
        :id: HLR_230

        .. hlreq:: System powinien umożliwiać Administratorom dodawanie do systemu nowych Jednostek Organizacyjnych.
            :tags: organization_units
            :id: HLR_231

        .. hlreq:: System powinien umożliwiać Administratorom przeglądanie istniejących w systemie Jednostek Organizacyjnych.
            :tags: organization_units
            :id: HLR_232

        .. hlreq:: System powinien umożliwiać Administratorom edytowanie istniejących w systemie Jednostek Organizacyjnych.
            :tags: organization_units
            :id: HLR_233

        .. hlreq:: System powinien umożliwiać Administratorom usuwanie z systemu Jednostek Organizacyjnych.
            :tags: organization_units
            :id: HLR_234


    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie Grupami Zajęciowymi.
        :tags: class_groups; admin_panel
        :id: HLR_240

        .. hlreq:: System powinien umożliwiać Administratorom dodawanie do systemu nowych Grup Zajęciowych.
            :tags: class_groups
            :id: HLR_241

        .. hlreq:: System powinien umożliwiać Administratorom przeglądanie istniejących w systemie Grup Zajęciowych.
            :tags: class_groups
            :id: HLR_242

        .. hlreq:: System powinien umożliwiać Administratorom edytowanie istniejących w systemie Grup Zajęciowych.
            :tags: class_groups
            :id: HLR_243

        .. hlreq:: System powinien umożliwiać Administratorom usuwanie z systemu Grup Zajęciowych.
            :tags: class_groups
            :id: HLR_244


    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie planem zajęć Grup Zajęciowej.
        :tags: classes_management; admin_panel
        :id: HLR_250

        .. hlreq:: System powinien umożliwiać Administratorom dodawanie do systemu nowych Terminów Zajęć.
            :tags: classes_management
            :id: HLR_251

        .. hlreq:: System powinien umożliwiać Administratorom przeglądanie istniejących w systemie Terminów Zajęć.
            :tags: classes_management
            :id: HLR_252

        .. hlreq:: System powinien umożliwiać Administratorom edytowanie istniejących w systemie Terminów Zajęć.
            :tags: classes_management
            :id: HLR_253

        .. hlreq:: System powinien umożliwiać Administratorom usuwanie z systemu Terminów Zajęć.
            :tags: classes_management
            :id: HLR_254


    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie Nauczycielami
        :tags: teachers; admin_panel
        :id: HLR_260

        .. hlreq:: System powinien umożliwiać Administratorom dodawanie do systemu nowych Nauczycieli.
            :tags: teachers
            :id: HLR_261

        .. hlreq:: System powinien umożliwiać Administratorom przeglądanie istniejących w systemie Nauczycieli.
            :tags: teachers
            :id: HLR_262

        .. hlreq:: System powinien umożliwiać Administratorom edytowanie istniejących w systemie Nauczycieli.
            :tags: teachers
            :id: HLR_263

        .. hlreq:: System powinien umożliwiać Administratorom usuwanie z systemu Nauczycieli.
            :tags: teachers
            :id: HLR_264

.. hlreq:: System powinien umożliwiać Nauczycielom i Administratorom logowanie się do Systemu.
    :tags: login; admin_panel
    :id: HLR_300


Low-level requirements
======================

.. llreq:: Wymaganie dotyczące wszystkich formularzy w Systemie
    :tags: login
    :id: LLR_000
    :specifies: LLR_410

    - Wszystkie tekstowe pola formularza powinny mieć ograniczenie na liczbę znaków. System powinien informować użytkownika o przekroczeniu limitu znaków.
    - Wszystkie formularze powinny zawierać odpowiednie etykiety i podpowiedzi, aby ułatwić użytkownikowi zrozumienie przeznaczenia każdego pola.
    - System powinien automatycznie sprawdzać poprawność formatu danych, takich jak adresy e-mail, hasła, pola z wyborem godzin i informować użytkownika o ewentualnych błędach.

.. llreq:: Wymaganie panelu przeglądania Jednostek Organizacyjnych i Grup Zajęciowych
    :tags: organization_units; class_groups
    :id: LLR_050
    :specifies: HLR_010; HLR_015

    - System powinien wyświetlać listę Jednostek Organizacyjnych w formie struktury drzewiastej. Powinna być możliwość wyświetlenia listy podrzędnych jednostek poprzez rozwinięcie jednostki nadrzędnej.
    - Wyświetlane powinny być następujące dane Jednostki Organizacyjnej:
        - nazwa.
    - Kliknięcie na rekord listy Jednostek Organizacyjnych powinno rozwinąć listę Jednostek Organizacyjnych podrzędnych i jej Grup Zajęciowych.
    - Kliknięcie na rekord listy Grupy Zajęciowej powinno przenosić do widoku planu zajęć danej Grupy Zajęciowej.
    - Wyświetlane powinny być następujące dane Grupy Zajęciowej:
        - nazwa.

.. llreq:: Wymaganie panelu przeglądania Nauczycieli
    :tags: teachers
    :id: LLR_051
    :specifies: HLR_023

    - System powinien wyświetlać listę Nauczycieli.
    - Wyświetlane powinny być następujące dane Nauczyciela:
        - tytuł naukowy,
        - imię i nazwisko.
    - Kliknięcie na rekord listy Nauczycieli powinno przenosić do widoku planu zajęć danego Nauczyciela.

.. llreq:: Wymaganie dotyczące wyświetlania planu zajęć
    :tags: schedule; consultations; classes_viewing
    :id: LLR_100
    :specifies: HLR_020; HLR_030

    - Plan zajęć powinien być wyświetlany w formie tabeli.
    - Tabela powinna zawierać kolumnę zawierającą komórki z kolejnymi godzinami z zakresu 7:00 - 23:00.
    - Tabela powinna zawierać kolumnę na Terminy Zajęć na każdy dzień od poniedziałku do piątku.
    - Tabela powinna zawierać komórki, które będą odzwierciedlały dany Termin Zajęciowy.
    - Komórka z Terminem Zajęć powinna być umieszczona na poziomie komórki zawierającej godzinę rozpoczęcia danego Terminu Zajęć, przy zachowaniu wysokości komórek odpowiadających godzinowemu zakresowi trwania tego Terminu Zajęć.
    - W przypadku wyświetlenia planu zajęć Nauczyciela powinny być również wyświetlone jego Terminy Konsultacji.
    - W przypadku wyświetlenia planu zajęć Grupy Zajęciowej zalogowany Administrator dodatkowo powinien widzieć przycisk dodawania Terminu Zajęć.
    - W przypadku wyświetlenia planu zajęć Nauczyciela zalogowany Administrator dodatkowo powinien widzieć przyciski dodawania Terminu Konsultacji i Terminu Zajęć.
    - Zalogowany Nauczyciel podczas przeglądania swojego planu zajęć powinien widzieć przycisk dodawania swojego Terminu Konsultacji.

    .. llreq:: Wymaganie dotyczące komórki Terminu Zajęć w tabeli
        :tags: classes_viewing
        :id: LLR_110

        - Każda komórka z danym Terminem Zajęć powinna zawierać:
            - Nazwę przedmiotu
            - Typ zajęć
            - Godzinowy zakres czasu trwania danej lekcji
            - Prowadzących daną lekcję
            - Miejsce odbywania zajęć
        - Zalogowany Administrator po kliknięciu na komórkę z Terminem Zajęć powinien zobaczyć formularz edycji Terminu Zajęć.

    .. llreq:: Wymaganie dotyczące komórki Terminu Konsultacji w tabeli
        :tags: consultations
        :id: LLR_120

        - Każda komórka z Terminem Konsultacji powinna zawierać:
            - Godzinowy zakres czasu trwania danej konsultacji
            - Miejsce odbywania konsultacji
            - Pełną nazwę Nauczyciela prowadzącego konsultację
        - Zalogowany Administrator po kliknięciu na komórkę z Terminem Konsultacji powinien zobaczyć formularz edycji Terminu Konsultacji.
        - Zalogowany Nauczyciel po kliknięciu na komórkę ze swoim Terminem Konsultacji powinien zobaczyć formularz edycji Terminu Konsultacji.
    

.. llreq:: Wymaganie dotyczące zarządzania Terminami Konsultacji
    :tags: consultations
    :id: LLR_210
    :specifies: HLR_110
    :requires: LLR_100

    .. llreq:: Wymaganie dotyczące formularza Terminu Konsultacji
        :tags: consultations
        :id: LLR_211
        :specifies: LLR_212; LLR_214; LLR_215

        - Formularz powinien składać się z następujących pól:
            - dzień tygodnia (wybór z listy)
            - godzina rozpoczęcia (wybór godziny poprzez mechanizm uniemożlwiający błędny wybór)
            - godzina zakończenia (wybór godziny poprzez mechanizm uniemożlwiający błędny wybór)
            - miejsce odbywania konsultacji (maks 50 znaków)
            - opcjonalny publiczny opis (maks 500 znaków)
        - Administrator dodatkowo powinien mieć możliwość wyboru Nauczyciela prowadzącego konsultacje.

    .. llreq:: Wymaganie dotyczące opcji dodawania Terminu Konsultacji
        :tags: consultations
        :id: LLR_212
        :specifies: HLR_111

        - Po kliknięciu przycisku dodawania Terminu Konsultacji System powinien wyświetlić formularz dodania Terminu Konsultacji.
        - Po otrzymaniu prawidłowych danych System powinien utworzyć nowy Termin Konsultacji w bazie danych.

    .. llreq:: Wymaganie opcji przeglądania Terminów Konsultacji
        :tags: consultations
        :id: LLR_213
        :specifies: HLR_112

        - Przeglądanie Terminów Konsultacji jest tożsame z wymaganiem dotyczącym wyświetlania planu zajęć Nauczyciela.

    .. llreq:: Wymaganie opcji edytowania Terminu Konsultacji
        :tags: consultations
        :id: LLR_214
        :specifies: HLR_113

        - Po kliknięciu na komórkę z Terminem Konsultacji System powinien wyświetlić formularz edycji Terminu Konsultacji.
        - Formularz wstępnie powinien być uzupełniony o aktualne dane Terminu Konsultacji.
        - Po otrzymaniu prawidłowych danych System powinien zmienić dane Terminu Konsultacji w bazie danych.

    .. llreq:: Wymaganie dotyczące opcji usuwania Terminu Konsultacji
        :tags: consultations
        :id: LLR_215
        :specifies: HLR_114

        - Po kliknięciu na komórkę z Terminem Konsultacji System powinien wyświetlić formularz edycji Terminu Konsultacji z przyciskiem usunięcia Terminu Konsultacji.
        - W przypadku wciśnięcia tego przycisku Termin Konsultacji powinien zostać usunięty z bazy danych.


.. llreq:: Wymaganie panelu zarządzania Administratora
    :tags: admin_panel
    :id: LLR_300
    :specifies: HLR_200

    - System powinien dostarczać Administratorowi odnośnik do widoków zarządzania: Jednostkami Organizacyjnymi, Przedmiotami, Kontami i Nauczycielami.

    .. llreq:: Wymaganie dotyczące widoku zarządzania Przedmiotami
        :tags: courses; admin_panel
        :id: LLR_310
        :specifies: HLR_210

        - System powinien dostarczyć widok zarządzania Przedmiotami.
        - System powinien dostarczyć przycisk umożliwiający przejście do formularza dodawania Przedmiotu.
        - System powinien dostarczyć pole tekstowe umożliwiające wyszukiwanie Przedmiotów po nazwie i kodzie przedmiotu.

        .. llreq:: Wymaganie dotyczące formularza Przedmiotu
            :tags: courses
            :id: LLR_311
            :specifies: LLR_312; LLR_314; LLR_315

            - Formularz powinien składać się z następujących pól:
                - unikalny kod przedmiotu (2 do 50 znaków),
                - nazwa przedmiotu (3 do 100 znaków),
                - opcjonalny opis przedmiotu (maks 500 znaków).
            - Formularz powinien nie przyjąć błędnych danych takich jak:
                - kod przedmiotu istniejący w bazie.

        .. llreq:: Wymaganie opcji dodawania Przedmiotów
            :tags: courses
            :id: LLR_312
            :specifies: HLR_211

            - System powinien dostarczać formularz dodawania Przedmiotu.
            - Po otrzymaniu prawidłowych danych System powinien utworzyć nowy Przedmiot w bazie danych.

        .. llreq:: Wymaganie opcji przeglądania Przedmiotów
            :tags: courses
            :id: LLR_313
            :specifies: HLR_212

            - System powinien wyświetlać listę Przedmiotów w formie tabeli. Wyświetlane powinny być następujące dane:
                - nazwa przedmiotu,
                - kod przedmiotu,
                - opis.
            - Zaznaczenie danego rekordu powinno sprawić, że System wyświetli formularz edycji Przedmiotu.
            - Wpisanie tekstu w pole wyszukiwania powinno spowodować wyświetlenie tylko tych rekordów, które zawierają wpisaną frazę w nazwie lub kodzie Przedmiotu.

        .. llreq:: Wymaganie opcji edytowania Przedmiotów
            :tags: courses
            :id: LLR_314
            :specifies: HLR_213

            - System powinien dostarczać formularz edytowania Przedmiotu z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić dane Przedmiotu w bazie danych.

        .. llreq:: Wymaganie opcji usuwania Przedmiotów
            :tags: courses
            :id: LLR_315
            :specifies: HLR_214

            - Po zaznaczeniu rekordu z Przedmiotem System powinien wyświetlić formularz edycji Przedmiotu z przyciskiem usunięcia Przedmiotu.
            - W przypadku wciśnięcia tego przycisku Przedmiot powinien zostać usunięty z bazy danych.
            - Usunięte powinny zostać również wszystkie Terminy Zajęć związane z tym Przedmiotem.

    .. llreq:: Wymaganie dotyczące widoku zarządzania Kontami
        :tags: accounts; admin_panel
        :id: LLR_320
        :specifies: HLR_220

        - System powinien dostarczyć widok zarządzania Kontami.
        - System powinien dostarczyć przycisk umożliwiający przejście do formularza dodawania Konta.
        - System powinien dostarczyć pole tekstowe umożliwiające wyszukiwanie Kont po adresie e-mail.

        .. llreq:: Wymaganie dotyczące formularza Konta
            :tags: accounts
            :id: LLR_321
            :specifies: LLR_322; LLR_324; LLR_325

            - Formularz powinien składać się z następujących pól:
                - prawidłowy adres e-mail,
                - hasło (od 8 do 128 znaków),
                - role (wielokrotny wybór z listy).
            - Formularz powinien nie przyjąć błędnych danych takich jak:
                - adres e-mail istniejący w bazie.

        .. llreq:: Wymaganie opcji dodawania Konta
            :tags: accounts
            :id: LLR_322
            :specifies: HLR_221

            - System powinien dostarczać formularz dodawania Konta.
            - Po otrzymaniu prawidłowych danych System powinien utworzyć nowe Konto w bazie danych.

        .. llreq:: Wymaganie opcji przeglądania Kont
            :tags: accounts
            :id: LLR_323
            :specifies: HLR_222

            - System powinien wyświetlać listę Kont w formie tabeli. Wyświetlane powinny być następujące dane:
                - adres e-mail,
                - role.
            - Zaznaczenie danego rekordu powinno sprawić, że System wyświetli formularz edycji Konta.

        .. llreq:: Wymaganie opcji edytowania Konta
            :tags: accounts
            :id: LLR_324
            :specifies: HLR_223

            - System powinien dostarczać formularz edytowania Konta z wstępnie wpisanymi starymi danymi z wyjątkiem hasła.
            - Po otrzymaniu prawidłowych danych System powinien zmienić dane Konta w bazie danych.

        .. llreq:: Wymaganie opcji usuwania Konta
            :tags: accounts
            :id: LLR_325
            :specifies: HLR_224

            - Po zaznaczeniu rekordu z Kontem System powinien wyświetlić formularz edycji Konta z przyciskiem usunięcia Konta.
            - W przypadku wciśnięcia tego przycisku Konto powinno zostać usunięte z bazy danych.


    .. llreq:: Wymaganie dotyczące widoku zarządzania Jednostkami Organizacyjnymi i Grupami Zajęciowymi
        :tags: organization_units; class_groups; admin_panel
        :id: LLR_330
        :specifies: HLR_230; HLR_240

        - System powinien dostarczyć widok zarządzania Jednostkami Organizacyjnymi.
        - System powinien dostarczyć przyciski umożliwiające przejście do formularza dodawania Jednostki Organizacyjnej i Grupy Zajęciowej.
        - System powinien dostarczyć przyciski umożliwiające przejście do formularza edycji Jednostki Organizacyjnej i Grupy Zajęciowej.

        .. llreq:: Wymaganie dotyczące formularza Jednostki Organizacyjnej
            :tags: organization_units
            :id: LLR_331
            :specifies: LLR_332; LLR_334; LLR_335

            - Formularz powinien składać się z następujących pól:
                - nazwa (od 2 do 100 znaków),
                - opcjonalna jednostka nadrzędna (wybór z listy)
                - opcjonalny opis (maks 1000 znaków).
            - Formularz powinien nie przyjąć błędnych danych takich jak:
                - nazwa jednostki istniejąca na danym poziomie hierarchii.
                - jednostka nadrzędna równa sobie.

        .. llreq:: Wymaganie opcji dodawania Jednostki Organizacyjnej
            :tags: organization_units
            :id: LLR_332
            :specifies: HLR_231

            - System powinien dostarczać formularz dodawania Jednostki Organizacyjnej.
            - Po otrzymaniu prawidłowych danych System powinien dodać nową Jednostkę Organizacyjną.

        .. llreq:: Wymaganie opcji przeglądania Jednostek Organizacyjnych
            :tags: organization_units
            :id: LLR_333
            :specifies: HLR_232

            - System powinien wyświetlać listę Jednostek Organizacyjnych w formie tabeli. Wyświetlane powinny być następujące dane:
                - nazwa jednostki,
                - opis.
            - Zaznaczenie danego rekordu powinno sprawić, że System umożliwi wciśnięcie przycisku edycji Jednostki Organizacyjnej.

        .. llreq:: Wymaganie opcji edytowania informacji o Jednostce Organizacyjnej
            :tags: organization_units
            :id: LLR_334
            :specifies: HLR_233

            - System powinien dostarczać formularz edytowania informacji o Jednostce Organizacyjnej z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić informacje o Jednostce Organizacyjnej.

        .. llreq:: Wymaganie opcji usuwania Jednostki Organizacyjnej
            :tags: organization_units
            :id: LLR_335
            :specifies: HLR_234

            - Po zaznaczeniu rekordu z Jednostką Organizacyjną System powinien umożliwić wciśnięcie przycisku edytowania, który powinien wyświetlić formularz edycji Jednostki Organizacyjnej z przyciskiem usunięcia Jednostki Organizacyjnej.
            - W przypadku wciśnięcia tego przycisku Jednostka Organizacyjna powinna zostać usunięta z bazy danych.
            - Usunięte powinny zostać również wszystkie Grupy Zajęciowe związane z tą Jednostką Organizacyjną.
            - Usunięte powinny zostać również wszystkie Jednostki Organizacyjne podrzędne.


        .. llreq:: Wymaganie dotyczące formularza Grupy Zajęciowej
            :tags: class_groups
            :id: LLR_341
            :specifies: LLR_343; LLR_344; LLR_345

            - Formularz powinien składać się z następujących pól:
                - nazwa (od 2 do 50 znaków),
                - jednostka organizacyjna (wybór z listy)
                - opcjonalny opis (maks 500 znaków).
            - Formularz powinien nie przyjąć błędnych danych takich jak:
                - nazwa grupy istniejąca w danej Jednostce Organizacyjnej.

        .. llreq:: Wymaganie opcji dodawania Grupy Zajęciowej
            :tags: class_groups
            :id: LLR_342
            :specifies: HLR_241

            - System powinien dostarczać formularz dodawania Grupy Zajęciowej.
            - Po otrzymaniu prawidłowych danych System powinien dodać nową Grupę Zajęciową.

        .. llreq:: Wymaganie opcji przeglądania Grup Zajęciowych
            :tags: class_groups
            :id: LLR_343
            :specifies: HLR_242

            - System powinien wyświetlać listę Grup Zajęciowych w formie tabeli. Wyświetlane powinny być następujące dane:
                - nazwa grupy,
                - opis.

        .. llreq:: Wymaganie opcji edytowania informacji o Grupie Zajęciowej
            :tags: class_groups
            :id: LLR_344
            :specifies: HLR_243

            - System powinien dostarczać formularz edytowania informacji o Grupie Zajęciowej z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić informacje o Grupie Zajęciowej.

        .. llreq:: Wymaganie opcji usuwania Grupy Zajęciowej
            :tags: class_groups
            :id: LLR_345
            :specifies: HLR_244

            - Po zaznaczeniu rekordu z Grupą Zajęciową System powinien umożliwić wciśnięcie przycisku edytowania, który powinien wyświetlić formularz edycji Grupy Zajęciowej z przyciskiem usunięcia Grupy Zajęciowej.
            - W przypadku wciśnięcia tego przycisku Grupa Zajęciowa powinna zostać usunięta z bazy danych.
            - Usunięte powinny zostać również wszystkie Terminy Zajęć związane z tą Grupą Zajęciową.

    .. llreq:: Wymaganie dotyczące zarządzania Terminami Zajęć
        :tags: admin_panel; classes_management
        :id: LLR_350
        :specifies: HLR_250
        :requires: LLR_100

        .. llreq:: Wymaganie dotyczące formularza Terminu Zajęć
            :tags: classes_management
            :id: LLR_351
            :specifies: LLR_352; LLR_353; LLR_354

            - Formularz powinien składać się z następujących pól:
                - przedmiotu (wybór z listy),
                - grupy zajęciowej (wybór z listy),
                - dzień tygodnia (wybór z listy)
                - godzina rozpoczęcia (wybór godziny poprzez mechanizm uniemożlwiający błędny wybór)
                - godzina zakończenia (wybór godziny poprzez mechanizm uniemożlwiający błędny wybór)
                - prowadzący zajęcia (wielokrotny wybór z listy),
                - typ zajęć (maks 50 znaków)
                - opcjonalne miejsce odbywania zajęć (maks 50 znaków)
                - opcjonalny opis (maks 500 znaków) 

        .. llreq:: Wymaganie dotyczące opcji dodawania Terminu Zajęć
            :tags: classes_management
            :id: LLR_352
            :specifies: HLR_251

            - Po kliknięciu przycisku dodawania Terminu Zajęć w widoku planu zajęć Grupy Zajęciowej System powinien wyświetlić formularz dodania Przedmiotu.
            - W przypadku wcześniejszego zaznaczenia komórek odpowiadających godzinom rozpoczęcia i zakończenia zajęć, formularz powinien być wstępnie wypełniony tymi danymi (również dzień tygodnia).
            - Po otrzymaniu prawidłowych danych System powinien utworzyć nowy Termin Zajęć w bazie danych.

        .. llreq:: Wymaganie opcji przeglądania Terminów Zajęć
            :tags: classes_management
            :id: LLR_353
            :specifies: HLR_252
            :requires: LLR_100

            - Wyamaganie jest tożsame z wymaganiem dotyczącym wyświetlania planu zajęć Grupy Zajęciowej.

        .. llreq:: Wymaganie opcji edytowania informacji o Terminie Zajęć
            :tags: classes_management
            :id: LLR_354
            :specifies: HLR_253

            - System powinien dostarczać formularz edytowania informacji o Terminie Zajęć z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić informacje o Terminie Zajęć.

        .. llreq:: Wymaganie dotyczące opcji usuwania Terminu Zajęć
            :tags: classes_management
            :id: LLR_355
            :specifies: HLR_254

            - Po kliknięciu na komórkę z Terminem Zajęć System powinien wyświetlić formularz edycji Terminu Zajęć z przyciskiem usunięcia Terminu Zajęć.
            - W przypadku wciśnięcia tego przycisku Termin Zajęć powinien zostać usunięty z bazy danych.


    .. llreq:: Wymaganie dotyczące widoku zarządzania Nauczycielami
        :tags: teachers; admin_panel
        :id: LLR_360
        :specifies: HLR_260

        - System powinien dostarczyć widok zarządzania Nauczycielimi.
        - System powinien dostarczyć przycisk umożliwiający przejście do formularza dodawania Nauczyciela.
        - System powinien dostarczyć pole tekstowe umożliwiające wyszukiwanie Nauczycieli po tytule naukowym, imieniu i nazwisku.

        .. llreq:: Wymaganie dotyczące formularza Nauczyciela
            :tags: teachers
            :id: LLR_361
            :specifies: LLR_362; LLR_364; LLR_365

            - Formularz powinien składać się z następujących pól:
                - imię (od 3 do 50 znaków),
                - nazwisko (od 3 do 50 znaków),
                - opcjonalny tytuł naukowy (maks 20 znaków),
                - opcjonalny numer telefonu (maks 15 znaków),
                - opcjonalna biografia (maks 1000 znaków).
                - opcjonalne wybór konta użytkownika (wybór z listy)
            - Formularz powinien nie przyjąć błędnych danych takich jak:
                - wybrane konto jest już przypisane do innego Nauczyciela.

        .. llreq:: Wymaganie opcji dodawania Nauczyciela
            :tags: teachers
            :id: LLR_362
            :specifies: HLR_261

            - System powinien dostarczać formularz dodawania nowego Nauczyciela.
            - Po otrzymaniu prawidłowych danych System powinien dodać nowego Nauczyciela do bazy danych.

        .. llreq:: Wymaganie opcji przeglądania Nauczycieli
            :tags: teachers
            :id: LLR_363
            :specifies: HLR_262

            - System powinien wyświetlać listę Nauczycieli w formie tabeli. Wyświetlane powinny być następujące dane:
                - tytuł naukowy,
                - imię i nazwisko,
                - adres email.
            - Zaznaczenie danego rekordu powinno sprawić, że System wyświetli formularz edycji Nauczyciela.
            - Wpisanie tekstu w pole wyszukiwania powinno spowodować wyświetlenie tylko tych rekordów, które zawierają wpisaną frazę w tytule naukowym, imieniu lub nazwisku Nauczyciela.

        .. llreq:: Wymaganie opcji edytowania informacji o Nauczycielu
            :tags: teachers
            :id: LLR_364
            :specifies: HLR_263

            - System powinien dostarczać formularz edytowania informacji o Nauczycielu z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić informacje o Nauczycielu.

        .. llreq:: Wymaganie opcji usuwania Nauczyciela
            :tags: teachers
            :id: LLR_365
            :specifies: HLR_264

            - Po zaznaczeniu rekordu z Nauczycielem System powinien wyświetlić formularz edycji Nauczyciela z przyciskiem usunięcia Nauczyciela.
            - W przypadku wciśnięcia tego przycisku Nauczyciel powinien zostać usunięty z bazy danych.
            - Usunięte powinno zostać również konto użytkownika przypisane do Nauczyciela.
            - Usunięte powinny zostać również wszystkie Terminy Konsultacji danego Nauczyciela.




.. llreq:: Wymagania dotyczące logowania
    :tags: login
    :id: LLR_400
    :specifies: HLR_300

    .. llreq:: Wymagania dotyczące interfejsu logowania
        :tags: login
        :id: LLR_410

        - System powinien wyświetlać panel logowania.
        - Panel logowania powinien składać się z pól do wpisania adresu email i hasła.
        - Panel logowania powinien zawierać przycisk "Zaloguj się", po którego wciśnięciu System zweryfikuje wprowadzone dane i zaloguje do panelu zarządzania.


    .. llreq:: Wymagania dotyczące uwierzytelniania
        :tags: login
        :id: LLR_420

        - Po wprowadzeniu danych logowania system powinien zweryfikować dane w bazie danych.
        - System powinien dawać dostęp do edycji własnych Terminów Konsultacji Nauczycielom.
        - System powinien dawać dostęp do panelu zarządzania Administratorom.
        - W przypadku nieprawidłowych danych logowania system powinien wyświetlić odpowiedni komunikat o błędzie i nie zezwolić na dostęp do panelu.


    .. llreq:: Wymagania dotyczące bezpieczeństwa
        :tags: login
        :id: LLR_430

        - System powinien przechowywać hasła użytkowników w bezpieczny sposób, np. poprzez haszowanie.
        - Sesje użytkowników powinny być odpowiednio zarządzane, a użytkownicy powinni być automatycznie wylogowywani po pewnym okresie bezczynności, aby zapobiec nieautoryzowanemu dostępowi do systemu.

Flows
=====

Login flow
^^^^^^^^^^

.. needflow::
    :tags: login
    :show_link_names:

Administrator panel flow
^^^^^^^^^^^^^^^^^^^^^^^^

.. needflow::
    :tags: admin_panel
    :show_link_names:


Organization units flow
^^^^^^^^^^^^^^^^^^^^^^^

.. needflow::
    :tags: organization_units
    :show_link_names:

Class groups flow
^^^^^^^^^^^^^^^^^

.. needflow::
    :tags: class_groups
    :show_link_names:

Courses flow
^^^^^^^^^^^^

.. needflow::
    :tags: courses
    :show_link_names:

Classes viewing flow
^^^^^^^^^^^^^^^^^^^^

.. needflow::
    :tags: classes_viewing
    :show_link_names:

Classes management flow
^^^^^^^^^^^^^^^^^^^^^^^

.. needflow::
    :tags: classes_management
    :show_link_names:

Teachers flow
^^^^^^^^^^^^^

.. needflow::
    :tags: teachers
    :show_link_names:

Schedule flow
^^^^^^^^^^^^^

.. needflow::
    :tags: schedule
    :show_link_names:

Consultations flow
^^^^^^^^^^^^^^^^^^

.. needflow::
    :tags: consultations
    :show_link_names:

Nonfunctional requirements
==========================

1. The system should feature an intuitive user interface.
2. The system should feature data and access security.
3. The system should be scalable according to the number of classes and class groups.
4. The system should be accessible to the visually impaired.

..
    Biznesowe przypadki użycia
    ==========================

    1. The Viewer selects the top-level organization unit.
    2. The Viewer selects subsequent subordinate organization units to the selected one.

    1. The Viewer selects the organization unit whose schedule he wants to view.
    2. The Viewer views the schedule of the selected organization unit.



    PB2. Dodanie Jednostki Organizacyjnej
    -------------------------------------

    **Aktorzy**: Administrator

    **Scenariusz główny**:

    1. System sprawdza tożsamość administratora.
    2. Administrator wybiera opcję dodania Jednostki Organizacyjnej.
    3. Administrator wybiera nazwę Jednostki Organizacyjnej i jej Jednostkę nadrzędną.
    4. System zapisuje nową Jednostkę Organizacyjną.

    PB3. Dodanie Nauczyciela akademickiego
    --------------------------------------

    **Aktorzy**: Administrator

    **Scenariusz główny**:

    1. System sprawdza tożsamość administratora.
    2. Administrator wybiera opcję dodania Nauczyciela akademickiego.
    3. Administrator uzupełnia pola z jego danymi.
    4. System potwierdza utworzenie nowego Nauczyciela
    5. System wysyła email nowo utworzonemu Nauczycielowi z jego danymi logowania.

    PB4. Dodanie Przedmiotu
    -----------------------

    **Aktorzy**: Administrator

    **Scenariusz główny**:

    1. System sprawdza tożsamość administratora.
    2. Administrator wybiera opcję dodania Przedmiotu.
    3. Administrator wpisuje nazwę nowego Przedmiotu.
    4. System potwierdza utworzenie nowego Przedmiotu.

    PB5. Dodanie terminu zajęć do planu
    -----------------------------------

    **Aktorzy**: Administrator

    **Scenariusz główny**:

    1. System sprawdza tożsamość administratora.
    2. Administrator wybiera opcję dodania terminu zajęć.
    3. Administrator wybiera Jednostkę Organizacyjną, Nauczyciela i Przedmiot zajęć.
    4. Administrator uzupełnia termin, lokalizację i typ zajęć.
    5. System potwierdza zapisanie nowego terminu do planu zajęć.


    Systemowe przypadki użycia
    ==========================

    FU1. Przeglądanie katalogu Jednostek Organizacyjnych
    ----------------------------------------------------

    **Aktorzy**: Przeglądający

    **Scenariusz główny**:

    1. System prezentuje Jednostki Organizacyjne w strukturze drzewiastej
    2. Przeglądający kolejno wybiera interesujące go Jednostki Organizacyjne

    FU2. Logowanie do systemu
    ----------------------------------------------------

    **Aktorzy**: Administratorzy, Nauczyciele

    **Scenariusz główny**:

    1. Użytkownik systemu wybiera opcję logowania do systemu.
    2. System wyświetla pola logowania -- nazwa użytkownika i hasło.
    3. Użytkownik systemu wpisuje swoje dane logowania.
    4. System przekierowuje użytkownika systemu do jego panelu zarządzania.

    **Scenariusz alternatywny 1 - użytkownik podał nieprawidłowe dane logowania**:

    1. Kroki 1-3 scenariusza głównego.
    2. System wyświetla informację o nieprawidłowych danych logowania i prosi użytkownika o ponowne podanie danych.
    3. Powrót do kroku 3 scenariusza głównego.


..
    Diagram komponentów
    -------------------

    .. needuml::

        database {
            [Baza danych] as bd
        }

        package "Interfejs użytkownika"{
            [Interfejs panelu administratora] as ipa
            [Interfejs panelu wykładowcy] as ipw
            [Interfejs przeglądu planów] as ipp
        }

        package "Zarządzanie informacją" {
            [Zarządzanie salami] as zs
            [Zarządzanie wykładowcami] as zw
            [Zarządzanie Jednostkami Organizacyjnymi] as zjo
        }

        package "Tworzenie planów zajęć" {
            [Planowanie zajęć] as pz
        }

        bd --> pz
        bd --> zs
        bd --> zw
        bd --> zjo
        bd --> ipa
        bd --> ipw
        bd --> ipp

        ipa --> zs
        ipa --> zw
        ipa --> zjo

        ipw --> pz

