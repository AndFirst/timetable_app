Requirements analysis
=====================

Actors of the system
^^^^^^^^^^^^^^^^^^^^
1. **Administrators** - system administrators who add, delete, edit information about subjects, lecturers and organization units. They add and edit class schedules, and create accounts for Academic Teachers.
2. **Teachers** - add, delete and edit their consultation appointments.
3. **Viewers** - view the timetables of individual organization units.


High-level requirements
^^^^^^^^^^^^^^^^^^^^^^^

.. hlreq:: System powinien wyświetlać Przeglądającym plan zajęć wybranej Grupy Zajęciowej.

    .. hlreq:: System powinien umożliwiać Przeglądającym wyświetlanie i wybieranie Jednostek Organizacyjnych.

    .. hlreq:: System powinien umożliwiać Przeglądającym wyświetlanie planu zajęć wybranej Grupy Zajęciowej.

        Wybór Grupy Zajęciowej powinien polegać na wyborze jednej z możliwie wielu Grup Zajęciowych wybranej Jednostki Organizacyjnej. [XXX do wybierania JO]

    .. hlreq:: System powinien umożliwiać Przeglądającym wyświetlanie planu zajęć wybranego Nauczyciela w tym jego terminów konsultacji.

        Wybór Nauczyciela powinien polegać na wyborze jednego z możliwie wielu Nauczycieli wybranej Jednostki Organizacyjnej. [XXX do wybierania JO]

.. hlreq:: System powinien dostarczać Nauczycielom panel zarządzania.

    .. hlreq:: System powinien umożliwiać Nauczycielom zarządzanie ich terminami konsultacji.
        :tags: consultations

        .. hlreq:: Nauczyciele powinni móc dodać swój termin konsultacji.
            :tags: consultations
        .. hlreq:: Nauczyciele powinni móc przeglądać swoje terminy konsultacji.
            :tags: consultations
        .. hlreq:: Nauczyciele powinni móc edytować swoje terminy konsultacji.
            :tags: consultations
        .. hlreq:: Nauczyciele powinni móc usuwać z systemu swoje terminy konsultacji.
            :tags: consultations

.. hlreq:: System powinien dostarczać Administratorom panel zarządzania.

    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie Przedmiotami.
        :tags: courses

        .. hlreq:: Administratorzy powinni móc dodawać nowe Przedmioty do systemu.
            :tags: courses
        .. hlreq:: Administratorzy powinni móc przeglądać istniejące Przedmioty w systemie.
            :tags: courses
        .. hlreq:: Administratorzy powinni móc edytować istniejące w systemie Przedmioty.
            :tags: courses
        .. hlreq:: Administratorzy powinni móc usuwać z systemu Przedmioty, które nie są już potrzebne.
            :tags: courses

    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie kontami Nauczycieli.
        :tags: teachers

        .. hlreq:: Administratorzy powinni móc zakładać nowe konta Nauczycielom.
            :tags: teachers
        .. hlreq:: Administratorzy powinni móc przeglądać listę kont Nauczycieli i informacje o nich.
            :tags: teachers
        .. hlreq:: Administratorzy powinni móc edytować dane istniejących w systemie Nauczycieli.
            :tags: teachers
        .. hlreq:: Administratorzy powinni móc usuwać z systemu konta Nauczycieli, które nie są już potrzebne.
            :tags: teachers

    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie Jednostkami Organizacyjnymi.
        :tags: organization_units

        .. hlreq:: Administratorzy powinni móc dodawać nowe Jednostki Organizacyjne do systemu.
            :tags: organization_units
        .. hlreq:: Administratorzy powinni móc przeglądać istniejące Jednostki Organizacyjne w systemie.
            :tags: organization_units
        .. hlreq:: Administratorzy powinni móc edytować istniejące w systemie Jednostki Organizacyjne.
            :tags: organization_units
        .. hlreq:: Administratorzy powinni móc usuwać z systemu Jednostki Organizacyjne, które nie są już potrzebne.
            :tags: organization_units

    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie Grupami Zajęciowymi.
        :tags: groups

        .. hlreq:: Administratorzy powinni móc dodawać nowe Grupy Zajęciowe do systemu.
            :tags: groups
        .. hlreq:: Administratorzy powinni móc przeglądać istniejące Grupy Zajęciowe w systemie.
            :tags: groups
        .. hlreq:: Administratorzy powinni móc edytować istniejące w systemie Grupy Zajęciowe.
            :tags: groups
        .. hlreq:: Administratorzy powinni móc usuwać z systemu Grupy Zajęciowe, które nie są już potrzebne.
            :tags: groups

    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie planem zajęć Grup Zajęciowej.
        :tags: classes

        .. hlreq:: Administratorzy powinni móc dodawać nowy termin zajęć.
            :tags: classes
        .. hlreq:: Administratorzy powinni móc usuwać termin zajęć.
            :tags: classes
        .. hlreq:: Administratorzy powinni móc edytować termin zajęć.
            :tags: classes

.. hlreq:: System powinien umożliwiać Nauczycielom i Administratorom logowanie się do ich paneli zarządzania.



Low-level requirements
^^^^^^^^^^^^^^^^^^^^^^

.. llreq:: Wymaganie dotyczące formularzy w Systemie

    - Wszystkie pola powinny mieć ograniczenie na liczbę znaków.
    - Formularz nie powinien umożliwiać wpisania w polach więcej znaków niż System przyjmie. Przy normalnym używaniu Systemu niemożliwe powinno być uzyskanie komunikatu o zbyt wielkiej liczbie wprowadzonych znaków.
    - Po otrzymaniu błędnych danych System powinien zwrócić stosowny komunikat o błędzie. Tak aby użytkownik wiedział co należy poprawić.


.. llreq:: Wymaganie panelu zarządzania Nauczyciela

    - System powinien dostarczać Nauczycielowi odnośnik do widoku zarządzania jego terminami konsultacji.


.. llreq:: Wymaganie panelu zarządzania Administratora

    - System powinien dostarczać Administratorowi odnośnik do widoku zarządzania Przedmiotami.
    - System powinien dostarczać Administratorowi odnośnik do widoku zarządzania kontami Nauczycieli.
    - System powinien dostarczać Administratorowi odnośnik do widoku zarządzania Jednostkami Organizacyjnymi.

    .. llreq:: Wymaganie dotyczące widoku zarządzania Przedmiotami

        - System powinien dostarczyć widok zarządzania Przedmiotami.
        - Domyślnie powinna być wyświetlana opcja przeglądania Przedmiotów.

        .. llreq:: Wymaganie dotyczące formularza Przedmiotu

            - Formularz powinien składać się z następujących pól:
                - nazwa przedmiotu (maks 100 znaków),
                - unikalny kod przedmiotu (maks 20 znaków),
                - opcjonalny opis przedmiotu (maks 500 znaków).
            - Formularz powinien nie przyjąć błędnych danych takich jak:
                - kod przedmiotu istniejący w bazie.

        .. llreq:: Wymaganie opcji przeglądania Przedmiotów

            - System powinien wyświetlać przycisk umożliwiający przejście do formularza dodawania Przedmiotu.
            - System powinien wyświetlać listę Przedmiotów w formie tabeli. Wyświetlane powinny być następujące dane:
                - nazwa przedmiotu,
                - kod przedmiotu.
            - Każdy rekord tabeli powinien zawierać:
                - przycisk pozwalający na przejście do opcji edycji Przedmiotu
                - przycisk pozwalający na usunięcie Przedmiotu.

        .. llreq:: Wymaganie opcji dodawania Przedmiotów

            - System powinien dostarczać formularz dodania Przedmiotu.
            - Po otrzymaniu prawidłowych danych System powinien utworzyć nowy Przedmiot w bazie danych.

        .. llreq:: Wymaganie opcji edytowania Przedmiotów

            - System powinien dostarczać formularz edytowania Przedmiotu z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić dane Przedmiotu w bazie danych.

        .. llreq:: Wymaganie opcji usuwania Przedmiotów

            - Po kliknięciu przycisku usuwania Przedmiotu przy danym rekordzie System powinien wyświetlić potwierdzenie wykonania czynności.
            - W przypadku potwierdzeniu wykonania czynności Przedmiot powinien zostać usunięty z bazy danych wraz z dotyczącymi go informacjami.
            - W przypadku odrzucenia wykonania czynności nic się nie powinno stać.


    .. llreq:: Wymaganie dotyczące widoku zarządzania kontami Nauczycieli

        - System powinien dostarczyć widok zarządzania kontami Nauczycieli.
        - Domyślnie powinna być wyświetlana opcja przeglądania kont Nauczycieli.

        .. llreq:: Wymaganie dotyczące formularza konta Nauczyciela

            - Formularz powinien składać się z następujących pól:
                - tytuł naukowy (maks 20 znaków),
                - imię (maks 50 znaków),
                - nazwisko (maks 50 znaków),
                - unikalny adres email (maks 100 znaków),
                - opcjonalny numer telefonu (maks 15 znaków),
                - opcjonalna biografia (maks 1000 znaków).
            - Formularz powinien nie przyjąć błędnych danych takich jak:
                - błędny lub istniejący w bazie adres email.

        .. llreq:: Wymaganie opcji przeglądania kont Nauczycieli

            - System powinien wyświetlać przycisk umożliwiający przejście do formularza zakładania konta Nauczyciela.
            - System powinien wyświetlać listę Nauczycieli w formie tabeli. Wyświetlane powinny być następujące dane:
                - tytuł naukowy,
                - imię i nazwisko,
                - adres email.
            - Każdy rekord tabeli powinien zawierać:
                - przycisk pozwalający na przejście do opcji edycji informacji o Nauczycielu
                - przycisk pozwalający na usunięcie konta Nauczyciela.

        .. llreq:: Wymaganie opcji zakładania konta Nauczyciela

            - System powinien dostarczać formularz zakładania konta Nauczyciela.
            - Po otrzymaniu prawidłowych danych System powinien założyć nowe konto Nauczyciela w bazie danych.
            - System powinien wysłać adres email Nauczycielowi z wygenerowanym dla niego hasłem.

        .. llreq:: Wymaganie opcji edytowania informacji o Nauczycielu

            - System powinien dostarczać formularz edytowania informacji o Nauczycielu z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić informacje o Nauczycielu.

        .. llreq:: Wymaganie opcji usuwania Nauczycieli

            - Po kliknięciu przycisku usuwania konta Nauczyciela przy danym rekordzie System powinien wyświetlić potwierdzenie wykonania czynności.
            - Po potwierdzeniu wykonania czynności konto Nauczyciela powinno zostać usunięte z bazy danych wraz z dotyczącymi go informacjami.
            - Po odrzuceniu wykonania czynności nic się nie powinno stać.
            - Terminy konsultacji Nauczyciela również powinny zostać usunięte.


    .. llreq:: Wymaganie dotyczące widoku zarządzania Jednostkami Organizacyjnymi

        - System powinien dostarczyć widok zarządzania Jednostkami Organizacyjnymi.
        - Domyślnie powinna być wyświetlana opcja przeglądania Jednostek Organizacyjnych.

        .. llreq:: Wymaganie dotyczące formularza Jednostki Organizacyjnej

            - Formularz powinien składać się z następujących pól:
                - nazwa (maks 20 znaków),
                - opis (maks 100 znaków).
                - opcjonalna jednostka nadrzędna (wybór z listy)

        .. llreq:: Wymaganie opcji przeglądania Jednostek Organizacyjnych

            - System powinien wyświetlać przycisk umożliwiający przejście do formularza dodawania nowej Jednostki Organizacyjnej.
            - System powinien wyświetlać listę Jednostek Organizacyjnych w formie listy o strukturze drzewiastej. Powinna być możliwość wyświetlenia podrzędnych jednostek poprzez rozwinięcie jednostki nadrzędnej.
            - Wyświetlane powinny być następujące dane:
                - nazwa,
                - opis.
            - Każdy rekord listy powinien zawierać:
                - przycisk pozwalający na przejście do opcji edycji informacji o Jednostce Organizacyjnej,
                - przycisk pozwalający na usunięcie Jednostki Organizacyjnej.
            - Każda rekord listy Jednostki Organizacyjnej najbardziej podrzędnej powinien zawierać:
                - przycisk przeglądania jej Grup Zajęciowych. 

        .. llreq:: Wymaganie opcji dodawania Jednostki Organizacyjnej

            - System powinien dostarczać formularz dodawania Jednostki Organizacyjnej.
            - Po otrzymaniu prawidłowych danych System powinien dodać nową Jednostkę Organizacyjną.

        .. llreq:: Wymaganie opcji edytowania informacji o Jednostce Organizacyjnej

            - System powinien dostarczać formularz edytowania informacji o Jednostce Organizacyjnej z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić informacje o Jednostce Organizacyjnej.

        .. llreq:: Wymaganie opcji usuwania Jednostki Organizacyjnej

            - Po kliknięciu przycisku usuwania Jednostki Organizacyjnej przy danym rekordzie System powinien wyświetlić potwierdzenie wykonania czynności.
            - Po potwierdzeniu wykonania czynności Jednostka Organizacyjna powinna zostać usunięta z bazy danych wraz z dotyczącymi jej informacjami.
            - Po odrzuceniu wykonania czynności nic się nie powinno stać.
            - Wraz z usunięciem Jednostki Organizacyjnej powinny zostać usunięte jej Grupy Zajęciowe.


    .. llreq:: Wymaganie dotyczące widoku zarządzania Grupami Zajęciowymi

        - Przejście do tego widoku za pomocą przycisku. [XXX]

        .. llreq:: Wymaganie dotyczące formularza Grupy Zajęciowej

            - Formularz powinien składać się z następujących pól:
                - nazwa (maks 20 znaków),
                - opis (maks 100 znaków).
                - jednostka organizacyjna (wybór z listy)
            - Formularz powinien akceptować wybór jedynie najbardziej podrzędnej Jednostki Organizacyjnej.


        .. llreq:: Wymaganie opcji przeglądania Grup Zajęciowych

            - System powinien wyświetlać przycisk umożliwiający przejście do formularza dodawania nowej Grupy Zajęciowej.
            - System powinien wyświetlać listę Grup Zajęciowych w formie tabeli.
            - Wyświetlane powinny być następujące dane:
                - nazwa,
                - opis.
            - Każdy rekord listy powinien zawierać:
                -  przycisk pozwalający na przejście do opcji edycji informacji o Grupie Zajęciowej,
                -  przycisk usuwania Grupy Zajęciowej,
                -  przycisk przejścia do widoku planu zajęć danej Grupy Zajęciowej,
                -  przycisk dodania nowego terminu zajęć dla tej Grupy Zajęciowej.

        .. llreq:: Wymaganie opcji dodawania Grupy Zajęciowej

            - System powinien dostarczać formularz dodawania Grupy Zajęciowej.
            - Po otrzymaniu prawidłowych danych System powinien dodać nową Grupę Zajęciową.

        .. llreq:: Wymaganie opcji edytowania informacji o Grupie Zajęciowej

            - System powinien dostarczać formularz edytowania informacji o Grupie Zajęciowej z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić informacje o Grupie Zajęciowej.

        .. llreq:: Wymaganie opcji usuwania Grupy Zajęciowej

            - Po kliknięciu przycisku usuwania Grupy Zajęciowej przy danym rekordzie System powinien wyświetlić potwierdzenie wykonania czynności.
            - Po potwierdzeniu wykonania czynności Grupa Zajęciowa powinna zostać usunięta z bazy danych wraz z dotyczącymi jej informacjami.
            - Po odrzuceniu wykonania czynności nic się nie powinno stać.
            - Wraz z usunięciem Grupy Zajęciowej powinny zostać usunięte jej terminy zajęć.


.. llreq:: Wymagania dotyczące interfejsu logowania

    - System powinien wyświetlać panel logowania.
    - Panel logowania powinien składać się z pól do wpisania nazwy użytkownika oraz hasła.
    - Panel logowania powinien zawierać przycisk "Zaloguj się", po którego wciśnięciu System zweryfikuje wprowadzone dane i zaloguje do panelu zarządzania.


.. llreq:: Wymagania dotyczące uwierzytelniania

    - Po wprowadzeniu danych logowania system powinien zweryfikować dane w bazie danych.
    - System powinien dawać dostęp do panelu Nauczyciela tylko Nauczycielom, a do panelu administratora tylko administratorom.
    - W przypadku nieprawidłowych danych logowania system powinien wyświetlić odpowiedni komunikat o błędzie i nie zezwolić na dostęp do panelu.


.. llreq:: Wymagania dotyczące bezpieczeństwa

    - System powinien przechowywać hasła użytkowników w bezpieczny sposób, np. poprzez haszowanie.
    - Sesje użytkowników powinny być odpowiednio zarządzane, a użytkownicy powinni być automatycznie wylogowywani po pewnym okresie bezczynności, aby zapobiec nieautoryzowanemu dostępowi do systemu.


Nonfunctional requirements
^^^^^^^^^^^^^^^^^^^^^^^^^^

1. The system should feature an intuitive user interface.
2. The system should feature data and access security.
3. The system should be scalable according to the number of classes and class groups.
4. The system should be accessible to the visually impaired.

Biznesowe przypadki użycia
^^^^^^^^^^^^^^^^^^^^^^^^^^

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
^^^^^^^^^^^^^^^^^^^^^^^^^^

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
