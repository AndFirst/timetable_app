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

.. hlreq:: System powinien umożliwiać Przeglądającym przeglądanie Jednostek Organizacyjnych.
    :tags: organization_units
    :id: HLR_010

    W tym Jednostek Organizacyjnych Grup Zajęciowych i oddzielnie Nauczycieli.

.. hlreq:: System powinien umożliwiać Przeglądającym przeglądanie Grup Zajęciowych.
    :tags: groups
    :id: HLR_015
    :requires: HLR_010

.. hlreq:: System powinien umożliwiać Przeglądającym wyświetlanie planu zajęć wybranej Grupy Zajęciowej.
    :tags: schedule; classes_viewing
    :id: HLR_020
    :requires: HLR_015

    Wybór Grupy Zajęciowej powinien polegać na wyborze jednej z możliwie wielu Grup Zajęciowych wybranej Jednostki Organizacyjnej.

.. hlreq:: System powinien umożliwiać Przeglądającym przeglądanie Nauczycieli.
    :tags: teachers
    :id: HLR_023
    :requires: HLR_010

.. hlreq:: System powinien umożliwiać Przeglądającym wyświetlanie planu zajęć wybranego Nauczyciela w tym jego terminów konsultacji.
    :tags: schedule; consultations; classes_viewing
    :id: HLR_030
    :requires: HLR_023

    Wybór Nauczyciela powinien polegać na wyborze jednego z możliwie wielu Nauczycieli wybranej Jednostki Organizacyjnej. [XXX do wybierania JO]

.. hlreq:: System powinien dostarczać Nauczycielom panel zarządzania.
    :tags: teacher_panel
    :id: HLR_100

    .. hlreq:: System powinien umożliwiać Nauczycielom zarządzanie ich terminami konsultacji.
        :tags: consultations; teacher_panel
        :id: HLR_110
        :requires: HLR_300

        .. hlreq:: System powinien umożliwiać Nauczycielom dodawanie swoich terminów konsultacji.
            :tags: consultations
            :id: HLR_111

        .. hlreq:: System powinien umożliwiać Nauczycielom przeglądanie swoich terminów konsultacji.
            :tags: consultations
            :id: HLR_112

        .. hlreq:: System powinien umożliwiać Nauczycielom edytowanie swoich terminów konsultacji.
            :tags: consultations
            :id: HLR_113

        .. hlreq:: System powinien umożliwiać Nauczycielom usuwanie z systemu swoich terminów konsultacji.
            :tags: consultations
            :id: HLR_114

.. hlreq:: System powinien dostarczać Administratorom panel zarządzania.
    :tags: admin_panel
    :id: HLR_200
    :requires: HLR_300

    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie Przedmiotami.
        :tags: courses; admin_panel
        :id: HLR_210

        .. hlreq:: System powinien umożliwiać Administratorom dodawanie nowych Przedmiotów do systemu.
            :tags: courses
            :id: HLR_211

        .. hlreq:: System powinien umożliwiać Administratorom przeglądanie istniejących w systemie Przedmiotów.
            :tags: courses
            :id: HLR_212

        .. hlreq:: System powinien umożliwiać Administratorom edytowanie istniejących w systemie Przedmiotów.
            :tags: courses
            :id: HLR_213

        .. hlreq:: System powinien umożliwiać Administratorom usuwanie z systemu Przedmiotów, które nie są już potrzebne.
            :tags: courses
            :id: HLR_214


    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie kontami Nauczycieli.
        :tags: teachers; admin_panel
        :id: HLR_220

        .. hlreq:: System powinien umożliwiać Administratorom zakładanie nowych kont Nauczycielom.
            :tags: teachers
            :id: HLR_221

        .. hlreq:: System powinien umożliwiać Administratorom przeglądanie istniejących w systemie kont Nauczycieli.
            :tags: teachers
            :id: HLR_222

        .. hlreq:: System powinien umożliwiać Administratorom edytowanie istniejących w systemie kont Nauczycieli.
            :tags: teachers
            :id: HLR_223

        .. hlreq:: System powinien umożliwiać Administratorom usuwanie z systemu kont Nauczycieli, które nie są już potrzebne.
            :tags: teachers
            :id: HLR_224


    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie Jednostkami Organizacyjnymi.
        :tags: organization_units; admin_panel
        :id: HLR_230

        W tym Jednostkami Organizacyjnymi Grup Zajęciowych i oddzielnie Nauczycieli.

        .. hlreq:: System powinien umożliwiać Administratorom dodawanie nowych Jednostek Organizacyjnych do systemu.
            :tags: organization_units
            :id: HLR_231

        .. hlreq:: System powinien umożliwiać Administratorom przeglądanie istniejących Jednostek Organizacyjnych w systemie.
            :tags: organization_units
            :id: HLR_232

        .. hlreq:: System powinien umożliwiać Administratorom edytowanie istniejących w systemie Jednostek Organizacyjnych.
            :tags: organization_units
            :id: HLR_233

        .. hlreq:: System powinien umożliwiać Administratorom usuwanie z systemu Jednostek Organizacyjnych, które nie są już potrzebne.
            :tags: organization_units
            :id: HLR_234


    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie Grupami Zajęciowymi.
        :tags: groups; admin_panel
        :id: HLR_240

        .. hlreq:: System powinien umożliwiać Administratorom dodawanie nowch Grup Zajęciowych do systemu.
            :tags: groups
            :id: HLR_241

        .. hlreq:: System powinien umożliwiać Administratorom przeglądanie istniejących Grup Zajęciowych w systemie.
            :tags: groups
            :id: HLR_242

        .. hlreq:: System powinien umożliwiać Administratorom edytowanie istniejących w systemie Grup Zajęciowych.
            :tags: groups
            :id: HLR_243

        .. hlreq:: System powinien umożliwiać Administratorom usuwanie z systemu Grup Zajęciowych, które nie są już potrzebne.
            :tags: groups
            :id: HLR_244


    .. hlreq:: System powinien umożliwiać Administratorom zarządzanie planem zajęć Grup Zajęciowej.
        :tags: classes_management; admin_panel
        :id: HLR_250

        .. hlreq:: System powinien umożliwiać Administratorom dodawanie nowych Terminów Zajęć.
            :tags: classes_management
            :id: HLR_251

        .. hlreq:: System powinien umożliwiać Administratorom usuwanie nowych Terminów Zajęć.
            :tags: classes_management
            :id: HLR_252

        .. hlreq:: System powinien umożliwiać Administratorom edytowanie Terminów Zajęć.
            :tags: classes_management
            :id: HLR_253


.. hlreq:: System powinien umożliwiać Nauczycielom i Administratorom logowanie się do ich paneli zarządzania.
    :tags: login; admin_panel; teacher_panel
    :id: HLR_300


Low-level requirements
======================

.. llreq:: Wymaganie dotyczące wszystkich formularzy w Systemie
    :tags: login
    :id: LLR_000
    :specifies: LLR_410

    - Wszystkie tekstowe pola formularza powinny mieć ograniczenie na liczbę znaków.
    - Formularz nie powinien umożliwiać wpisania w polach więcej znaków niż System przyjmie. Przy normalnym używaniu Systemu niemożliwe powinno być uzyskanie komunikatu o zbyt wielkiej liczbie wprowadzonych znaków.
    - Wszystkie formularze powinny zawierać odpowiednie etykiety i podpowiedzi, aby ułatwić użytkownikowi zrozumienie przeznaczenia każdego pola.
    - Formularze powinny być responsywne i dostosowywać się do różnych rozmiarów ekranów, zapewniając jednolite doświadczenie użytkownika niezależnie od urządzenia.
    - System powinien automatycznie sprawdzać poprawność formatu danych, takich jak adresy e-mail czy numery telefonów, i informować użytkownika o ewentualnych błędach.


.. llreq:: Wymaganie dotyczące wyświetlania planu zajęć
    :tags: schedule; consultations; classes_viewing
    :id: LLR_100
    :specifies: HLR_020; HLR_030

    - Plan zajęć powinien być wyświetlany w formie tabeli.
    - Tabela powinna zawierać kolumnę na każdy dzień tygodnia
    - Tabela powinna zawierać kolumnę z godzinami w ciągu dnia.
    - Tabela powinna zawierać komórki, które będą odzwierciedlały dany Termin Zajęciowy.
    - Komórka z Terminem Zajęć powinna być na poziomie i mieć wysokość komórek odpowiadających godzinowemu zakresowi trwania Terminu Zajęć.
    - W przypadku wyświetlenia planu zajęć Nauczyciela powinny być rónież wyświetlone jego Terminy Konsultacji.
    - W przypadku wyświetlenia planu zajęć Grupy Zajęciowej zalogowany Administrator dodatkowo powinien widzieć przycisk dodawania Terminu Zajęć.

    .. llreq:: Wymaganie dotyczące komórki Terminu Zajęć w tabeli
        :tags: classes_viewing
        :id: LLR_110

        - Każda komórka z danym Terminem Zajęć powinna zawierać:
            - Nazwę przedmiotu
            - Godzinowy zakres czasu trwania danej lekcji
            - Prowadzącego daną lekcję
            - Miejsce odbywania zajęć
        - Zalogowany Administrator dodatkowo w każdej komórce Terminu Zajęć powinien widzieć:
            - Przycisk przenoszący do edycji danego Terminu Zajęć
            - Przycisk usuwający dany Termin Zajęć

.. llreq:: Wymaganie opcji przeglądania Jednostek Organizacyjnych, Grup Zajęciowych i Nauczycieli
    :tags: organization_units; groups; teachers
    :id: LLR_332
    :specifies: HLR_010; HLR_015; HLR_023; HLR_232; HLR_242; HLR_222

    - System powinien wyświetlać listę Jednostek Organizacyjnych w formie struktury drzewiastej. Powinna być możliwość wyświetlenia listy podrzędnych jednostek poprzez rozwinięcie jednostki nadrzędnej.
    - Wyświetlane powinny być następujące dane Jednostki Organizacyjnej:
        - nazwa.
    - Kliknięcie na rekord listy Jednostek Organizacyjnych powinno rozwinąć listę Jednostek Organizacyjnych podrzędnych.
    - Rozwinięcie Jednostki Organizacyjnej, która nie ma podrzędnej Jednostki Organizacyjne, powinno powodować pokazanie w formie listy w zależności od trybu jej Grupy Zajęciowe lub Nauczycieli.
    - Kliknięcie na rekord listy Grupy Zajęciowej powinno przenosić do widoku planu zajęć danej Grupy Zajęciowej.
    - Kliknięcie na rekord listy Nauczyciela powinno przenosić do widoku planu zajęć danego Nauczyciela w tym jego Terminów Konsultacji.
    - Wyświetlane powinny być następujące dane Grupy Zajęciowej:
        - nazwa.
    - Wyświetlane powinny być następujące dane Nauczyciela:
        - tytuł naukowy,
        - imię i nazwisko. :
    - Każdy rekord listy Jednostek Organizacyjnych powinien zawierać widoczny tylko dla Administratora:
        - przycisk pozwalający na przejście do opcji edycji informacji o Jednostce Organizacyjnej,
        - przycisk pozwalający na usunięcie Jednostki Organizacyjnej.
        - w trybie Grup Zajęciowych: przycisk pozwalający na dodanie podrzędnej Jednostki Organizacyjnej, chyba że zawiera ona Grupy Zajęciowe.
        - w trybie Grup Zajęciowych: przycisk pozwalający na dodanie Grupy Zajęciowej, chyba że ma Jednostki Organizacyjne podrzędne.
        - w trybie Nauczycieli: przycisk pozwalający na dodanie podrzędnej Jednostki Organizacyjnej, chyba że zawiera ona Nauczycieli.
        - w trybie Nauczycieli: przycisk pozwalający na dodanie Nauczyciela, chyba że ma Jednostki Organizacyjne podrzędne.
    - Każdy rekord listy Grupy Zajęciowej powinien zawierać widoczny tylko dla Administratora:
        - przycisk pozwalający na przejście do opcji edycji informacji o Grupie Zajęciowej,
        - przycisk pozwalający na usunięcie Grupy Zajęciowej.
    - Każdy rekord listy Nauczycieli powinien zawierać widoczny tylko dla Administratora:
        - przycisk pozwalający na przejście do opcji edycji informacji o Nauczycielu,
        - przycisk pozwalający na usunięcie Nauczyciela.
    - System powinien wyświetlać Administratorowi przycisk umożliwiający przejście do formularza dodawania głównej Jednostki Organizacyjnej.

.. llreq:: Wymaganie panelu zarządzania Nauczyciela
    :tags: teacher_panel
    :id: LLR_200
    :specifies: HLR_100

    - System powinien dostarczać Nauczycielowi odnośnik do widoku zarządzania jego Terminami Konsultacji.

    .. llreq:: Wymaganie dotyczące zarządzania Terminami Konsultacji
        :tags: teacher_panel; consultations
        :id: LLR_210
        :specifies: HLR_110

        .. llreq:: Wymaganie dotyczące formularza Terminu Konsultacji
            :tags: consultations
            :id: LLR_211
            :specifies: LLR_213; LLR_215

            - Formularz powinien składać się z następujących pól:
                - miejsce odbywania konsultacji (maks 50 znaków)
                - dzień tygodnia (wybór z listy)
                - godzina rozpoczęcia (wybór godziny poprzez mechanizm uniemożlwiający błędny wybór)
                - godzina zakończenia (wybór godziny poprzez mechanizm uniemożlwiający błędny wybór)
                - opcjonalny publiczny opis (maks 500 znaków)

        .. llreq:: Wymaganie opcji przeglądania Terminów Konsultacji
            :tags: consultations
            :id: LLR_212
            :specifies: HLR_112

            - System powinien wyświetlać przycisk umożliwiający przejście do formularza dodawania Terminu Konsultacji.
            - System powinien wyświetlać listę Terminów Konsultacji w formie tabeli. Wyświetlane powinny być następujące dane:
                - dzień tygodnia,
                - godzina rozpoczęcia,
                - godzina zakończenia,
                - miejsce odbywania konsultacji.
            - Każdy rekord tabeli powinien zawierać:
                - przycisk pozwalający na przejście do opcji edycji Terminu Konsultacji
                - przycisk pozwalający na usunięcie Terminów Konsultacji.

        .. llreq:: Wymaganie dotyczące opcji dodawania Terminu Konsultacji
            :tags: consultations
            :id: LLR_213
            :specifies: HLR_111

            - Po kliknięciu przycisku dodawania Terminu Konsultacji w widoku zarządzania Terminami Konsultacji System powinien wyświetlić formularz dodania Terminu Konsultacji.
            - Po otrzymaniu prawidłowych danych System powinien utworzyć nowy Termin Konsultacji w bazie danych.

        .. llreq:: Wymaganie opcji edytowania Terminu Konsultacji
            :tags: consultations
            :id: LLR_215
            :specifies: HLR_113

            - System powinien dostarczać formularz edytowania Terminu Konsultacji z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić dane Terminu Konsultacji w bazie danych.

        .. llreq:: Wymaganie dotyczące opcji usuwania Terminu Konsultacji
            :tags: consultations
            :id: LLR_214
            :specifies: HLR_114

            - Po kliknięciu przycisku usuwania Terminu Konsultacji przy danym rekordzie System powinien wyświetlić potwierdzenie wykonania czynności.
            - W przypadku potwierdzeniu wykonania czynności Termin Konsultacji powinien zostać usunięty z bazy danych.
            - W przypadku odrzucenia wykonania czynności nic się nie powinno stać.



.. llreq:: Wymaganie panelu zarządzania Administratora
    :tags: admin_panel
    :id: LLR_300
    :specifies: HLR_200

    - System powinien dostarczać Administratorowi odnośnik do widoku zarządzania Przedmiotami.
    - System powinien dostarczać Administratorowi odnośnik do widoku zarządzania kontami Nauczycieli.
    - System powinien dostarczać Administratorowi odnośnik do widoku zarządzania Jednostkami Organizacyjnymi.

    .. llreq:: Wymaganie dotyczące widoku zarządzania Przedmiotami
        :tags: courses; admin_panel
        :id: LLR_310
        :specifies: HLR_210

        - System powinien dostarczyć widok zarządzania Przedmiotami.
        - Domyślnie powinna być wyświetlana opcja przeglądania Przedmiotów.

        .. llreq:: Wymaganie dotyczące formularza Przedmiotu
            :tags: courses
            :id: LLR_311
            :specifies: LLR_313; LLR_314

            - Formularz powinien składać się z następujących pól:
                - nazwa przedmiotu (maks 100 znaków),
                - unikalny kod przedmiotu (maks 20 znaków),
                - opcjonalny opis przedmiotu (maks 500 znaków).
            - Formularz powinien nie przyjąć błędnych danych takich jak:
                - kod przedmiotu istniejący w bazie.

        .. llreq:: Wymaganie opcji przeglądania Przedmiotów
            :tags: courses
            :id: LLR_312
            :specifies: HLR_212

            - System powinien wyświetlać przycisk umożliwiający przejście do formularza dodawania Przedmiotu.
            - System powinien wyświetlać listę Przedmiotów w formie tabeli. Wyświetlane powinny być następujące dane:
                - nazwa przedmiotu,
                - kod przedmiotu.
            - Każdy rekord tabeli powinien zawierać:
                - przycisk pozwalający na przejście do opcji edycji Przedmiotu
                - przycisk pozwalający na usunięcie Przedmiotu.

        .. llreq:: Wymaganie opcji dodawania Przedmiotów
            :tags: courses
            :id: LLR_313
            :specifies: HLR_211

            - System powinien dostarczać formularz dodania Przedmiotu.
            - Po otrzymaniu prawidłowych danych System powinien utworzyć nowy Przedmiot w bazie danych.

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

            - Po kliknięciu przycisku usuwania Przedmiotu przy danym rekordzie System powinien wyświetlić potwierdzenie wykonania czynności.
            - W przypadku potwierdzeniu wykonania czynności Przedmiot powinien zostać usunięty z bazy danych wraz z dotyczącymi go informacjami.
            - W przypadku odrzucenia wykonania czynności nic się nie powinno stać.


    .. llreq:: Wymaganie dotyczące widoku zarządzania kontami Nauczycieli
        :tags: teachers; admin_panel
        :id: LLR_320
        :specifies: HLR_220

        - System powinien dostarczyć widok zarządzania kontami Nauczycieli.
        - Domyślnie powinna być wyświetlana opcja przeglądania kont Nauczycieli.

        .. llreq:: Wymaganie dotyczące formularza konta Nauczyciela
            :tags: teachers
            :id: LLR_321
            :specifies: LLR_323; LLR_324

            - Formularz powinien składać się z następujących pól:
                - imię (maks 50 znaków),
                - nazwisko (maks 50 znaków),
                - unikalny adres email (maks 100 znaków),
                - opcjonalny tytuł naukowy (maks 20 znaków),
                - opcjonalny numer telefonu (maks 15 znaków),
                - opcjonalna biografia (maks 1000 znaków).
            - Formularz powinien nie przyjąć błędnych danych takich jak:
                - błędny lub istniejący w bazie adres email.

        .. llreq:: Wymaganie opcji przeglądania kont Nauczycieli
            :tags: teachers
            :id: LLR_322
            :specifies: HLR_222

            - System powinien wyświetlać przycisk umożliwiający przejście do formularza zakładania konta Nauczyciela.
            - System powinien wyświetlać listę Nauczycieli w formie tabeli. Wyświetlane powinny być następujące dane:
                - tytuł naukowy,
                - imię i nazwisko,
                - adres email.
            - Każdy rekord tabeli powinien zawierać:
                - przycisk pozwalający na przejście do opcji edycji informacji o Nauczycielu
                - przycisk pozwalający na usunięcie konta Nauczyciela.

        .. llreq:: Wymaganie opcji zakładania konta Nauczyciela
            :tags: teachers
            :id: LLR_323
            :specifies: HLR_221

            - System powinien dostarczać formularz zakładania konta Nauczyciela.
            - Po otrzymaniu prawidłowych danych System powinien założyć nowe konto Nauczyciela w bazie danych.
            - System powinien wysłać adres email Nauczycielowi z wygenerowanym dla niego hasłem.

        .. llreq:: Wymaganie opcji edytowania informacji o Nauczycielu
            :tags: teachers
            :id: LLR_324
            :specifies: HLR_223

            - System powinien dostarczać formularz edytowania informacji o Nauczycielu z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić informacje o Nauczycielu.

        .. llreq:: Wymaganie opcji usuwania Nauczycieli XXX
            :tags: teachers
            :id: LLR_325
            :specifies: HLR_224

            - Po kliknięciu przycisku usuwania konta Nauczyciela przy danym rekordzie System powinien wyświetlić potwierdzenie wykonania czynności.
            - Po potwierdzeniu wykonania czynności konto Nauczyciela powinno zostać usunięte z bazy danych wraz z dotyczącymi go informacjami.
            - Po odrzuceniu wykonania czynności nic się nie powinno stać.
            - Terminy konsultacji Nauczyciela również powinny zostać usunięte.


    .. llreq:: Wymaganie dotyczące widoku zarządzania Jednostkami Organizacyjnymi
        :tags: organization_units; admin_panel
        :id: LLR_330
        :specifies: HLR_230

        - System powinien dostarczyć widok zarządzania Jednostkami Organizacyjnymi.
        - Domyślnie powinna być wyświetlana opcja przeglądania Jednostek Organizacyjnych.

        .. llreq:: Wymaganie dotyczące formularza Jednostki Organizacyjnej
            :tags: organization_units
            :id: LLR_331
            :specifies: LLR_333; LLR_334

            - Formularz powinien składać się z następujących pól:
                - nazwa (maks 20 znaków),
                - opis (maks 100 znaków).
                - opcjonalna jednostka nadrzędna (wybór z listy)

        .. llreq:: Wymaganie opcji dodawania Jednostki Organizacyjnej
            :tags: organization_units
            :id: LLR_333
            :specifies: HLR_231

            - System powinien dostarczać formularz dodawania Jednostki Organizacyjnej.
            - Po otrzymaniu prawidłowych danych System powinien dodać nową Jednostkę Organizacyjną.

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

            - Po kliknięciu przycisku usuwania Jednostki Organizacyjnej przy danym rekordzie System powinien wyświetlić potwierdzenie wykonania czynności.
            - Po potwierdzeniu wykonania czynności Jednostka Organizacyjna powinna zostać usunięta z bazy danych wraz z dotyczącymi jej informacjami.
            - Po odrzuceniu wykonania czynności nic się nie powinno stać.
            - Wraz z usunięciem Jednostki Organizacyjnej powinny zostać usunięte jej Grupy Zajęciowe.


    .. llreq:: Wymaganie dotyczące widoku zarządzania Grupami Zajęciowymi
        :tags: groups; admin_panel
        :id: LLR_340
        :specifies: HLR_240

        - Przejście do tego widoku za pomocą przycisku. [XXX]

        .. llreq:: Wymaganie dotyczące formularza Grupy Zajęciowej
            :tags: groups
            :id: LLR_341
            :specifies: LLR_343; LLR_344

            - Formularz powinien składać się z następujących pól:
                - nazwa (maks 20 znaków),
                - opis (maks 100 znaków).
                - jednostka organizacyjna (wybór z listy)
            - Formularz powinien akceptować wybór jedynie najbardziej podrzędnej Jednostki Organizacyjnej.

        .. llreq:: Wymaganie opcji dodawania Grupy Zajęciowej
            :tags: groups
            :id: LLR_343
            :specifies: HLR_241

            - System powinien dostarczać formularz dodawania Grupy Zajęciowej.
            - Po otrzymaniu prawidłowych danych System powinien dodać nową Grupę Zajęciową.

        .. llreq:: Wymaganie opcji edytowania informacji o Grupie Zajęciowej
            :tags: groups
            :id: LLR_344
            :specifies: HLR_243

            - System powinien dostarczać formularz edytowania informacji o Grupie Zajęciowej z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić informacje o Grupie Zajęciowej.

        .. llreq:: Wymaganie opcji usuwania Grupy Zajęciowej
            :tags: groups
            :id: LLR_345
            :specifies: HLR_244

            - Po kliknięciu przycisku usuwania Grupy Zajęciowej przy danym rekordzie System powinien wyświetlić potwierdzenie wykonania czynności.
            - Po potwierdzeniu wykonania czynności Grupa Zajęciowa powinna zostać usunięta z bazy danych wraz z dotyczącymi jej informacjami.
            - Po odrzuceniu wykonania czynności nic się nie powinno stać.
            - Wraz z usunięciem Grupy Zajęciowej powinny zostać usunięte jej Terminy Zajęć.

    .. llreq:: Wymaganie dotyczące zarządzania Terminami Zajęć
        :tags: admin_panel; classes_management
        :id: LLR_350
        :specifies: HLR_250
        :requires: LLR_100

        .. llreq:: Wymaganie dotyczące formularza Terminu Zajęć
            :tags: classes_management
            :id: LLR_351
            :specifies: LLR_352; LLR_354

            - Formularz powinien składać się z następujących pól:
                - przedmiotu (wybór z listy),
                - prowadzący zajęcia (wybór z listy),
                - dzień tygodnia (wybór z listy)
                - godzina rozpoczęcia (wybór godziny poprzez mechanizm uniemożlwiający błędny wybór)
                - godzina zakończenia (wybór godziny poprzez mechanizm uniemożlwiający błędny wybór)
                - typ zajęć (maks 20 znaków)
                - opcjonalne miejsce odbywania zajęć (maks 50 znaków)
                - opcjonalny opis (maks 500 znaków) 

        .. llreq:: Wymaganie dotyczące opcji dodawania Terminu Zajęć
            :tags: classes_management
            :id: LLR_352
            :specifies: HLR_251

            - Po kliknięciu przycisku dodawania Terminu Zajęć w widoku planu zajęć Grupy Zajęciowej System powinien wyświetlić formularz dodania Przedmiotu.
            - Po otrzymaniu prawidłowych danych System powinien utworzyć nowy Przedmiot w bazie danych.

        .. llreq:: Wymaganie opcji edytowania informacji o Terminie Zajęć
            :tags: classes_management
            :id: LLR_354
            :specifies: HLR_253

            - System powinien dostarczać formularz edytowania informacji o Terminie Zajęć z wstępnie wpisanymi starymi danymi.
            - Po otrzymaniu prawidłowych danych System powinien zmienić informacje o Terminie Zajęć.

        .. llreq:: Wymaganie dotyczące opcji usuwania Terminu Zajęć
            :tags: classes_management
            :id: LLR_353
            :specifies: HLR_252

            - Po kliknięciu przycisku usuwania Terminu Zajęć przy danej komórce w tabeli planu zajęć System powinien wyświetlić potwierdzenie wykonania czynności.
            - W przypadku potwierdzeniu wykonania czynności Termin Zajęć powinien zostać usunięty z bazy danych.
            - W przypadku odrzucenia wykonania czynności nic się nie powinno stać.



.. llreq:: Wymagania dotyczące logowania
    :tags: login
    :id: LLR_400
    :specifies: HLR_300

    .. llreq:: Wymagania dotyczące interfejsu logowania
        :tags: login
        :id: LLR_410

        - System powinien wyświetlać panel logowania.
        - Panel logowania powinien składać się z pól do wpisania nazwy użytkownika oraz hasła.
        - Panel logowania powinien zawierać przycisk "Zaloguj się", po którego wciśnięciu System zweryfikuje wprowadzone dane i zaloguje do panelu zarządzania.


    .. llreq:: Wymagania dotyczące uwierzytelniania
        :tags: login
        :id: LLR_420

        - Po wprowadzeniu danych logowania system powinien zweryfikować dane w bazie danych.
        - System powinien dawać dostęp do panelu Nauczyciela tylko Nauczycielom, a do panelu administratora tylko administratorom.
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

Teacher panel flow
^^^^^^^^^^^^^^^^^^

.. needflow::
    :tags: teacher_panel
    :show_link_names:

Organization units flow
^^^^^^^^^^^^^^^^^^^^^^^

.. needflow::
    :tags: organization_units
    :show_link_names:

Groups flow
^^^^^^^^^^^

.. needflow::
    :tags: groups
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

