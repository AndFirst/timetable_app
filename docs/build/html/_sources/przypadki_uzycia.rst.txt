Use cases
*********

.. usecase:: Logowanie administratora do panelu administracyjnego

    Aktorzy
    -------

    - Administrator

    Cel
    ---

    Zalogowanie się do panelu administracyjnego w celu zarządzania systemem.

    Warunki wstępne
    ---------------

    1. Administrator ma dostęp do przeglądarki internetowej.
    2. Panel administracyjny jest dostępny pod określonym adresem URL.
    3. Administrator dysponuje poprawnymi danymi uwierzytelniającymi (login i hasło).

    Kroki przypadku użycia
    ----------------------

    1. Otwarcie przeglądarki
        - Administrator uruchamia przeglądarkę internetową na swoim urządzeniu.

    2. Wpisanie adresu URL panelu administracyjnego
        - Administrator wpisuje adres URL panelu administracyjnego w pasek adresu przeglądarki.

    3. Przejście do strony logowania
        - System wyświetla stronę logowania, na której Administrator wprowadza swoje dane uwierzytelniające.

    4. Wpisanie danych uwierzytelniających
        - Administrator wpisuje swój login i hasło do odpowiednich pól na formularzu logowania.

    5. Weryfikacja danych
        - System sprawdza wprowadzone dane uwierzytelniające w bazie danych, aby potwierdzić tożsamość Administratora.

    6. Logowanie
        - Jeśli dane są poprawne, system autoryzuje Administratora i przekierowuje go do panelu administracyjnego.

    7. Eksploracja panelu administracyjnego
        - Administrator ma teraz dostęp do funkcji panelu administracyjnego.

    8. Wylogowanie
        - Po zakończeniu pracy Administrator ma możliwość wylogowania się z panelu administracyjnego.

    Warunki końcowe
    ---------------

    Administrator jest zalogowany do panelu administracyjnego i może efektywnie zarządzać systemem.

    Wyjątki
    -------

    - Jeśli podane dane uwierzytelniające są niepoprawne, system informuje Administratora o błędzie i prosi o ponowne wprowadzenie danych.

.. usecase:: Wybór Jednostki Organizacyjnej i Grupy Zajęciowej

    Aktorzy
    -------

    - Administrator, Nauczyciel, Przeglądający

    Cel
    ---

    Wybór Jednostki Organizacyjnej i Grupy Zajęciowej np. w celu wyświetlenia jej planu zajęć.

    Warunki wstępne
    ---------------

    1. Użytkownik ma dostęp do przeglądarki internetowej.
    2. Serwis jest dostępny pod określonym adresem URL.

    Kroki przypadku użycia
    ----------------------

    1. Otwarcie przeglądarki
        - Użytkownik uruchamia przeglądarkę internetową na swoim urządzeniu.

    2. Wpisanie adresu URL serwisu
        - Użytkownik wpisuje adres URL serwisu w pasek adresu przeglądarki.

    3. Wybranie Jednostki Organizacyjnej
        - System wyświetla główne Jednostki Organizacyjne. Użytkownik wybiera główną Jednostkę Organizacyjną. System rozwija listę podrzędnych Jednostek Organizacyjnych do wybranej. System rozwija listę podrzędnych Jednostek Organizacyjnych do wybranej. Cały proces powtarza się aż do wyczerpania możliwości rozwinięcia. Wtedy System prezentuje Grupy Zajęciowe wybranej Jednostki Organizacyjnej. 

    4. Wybranie Grupy Zajęciowej
        - Użytkownik wybiera Grupę Zajęciową z listy wyświetlonej w punkcie 3.
    

    Warunki końcowe
    ---------------

    Użytkownik wybrał Grupę Zajęciowej i wyświetlany jest mu widok z tym związany. Dostępne są wtedy również odnośniki umożliwiające zarządzanie.

    Wyjątki
    -------


.. usecase:: Dodawanie terminu zajęć do systemu

    Aktorzy
    -------

    - Administrator

    Cel
    ---

    Dodanie nowego terminu zajęć do systemu.

    Warunki wstępne
    ---------------

    1. Administrator jest zalogowany do systemu.
    2. Administrator wybrał Grupę Zajęciową.

    Kroki przypadku użycia
    ----------------------
    1. Wciśnięcie przycisku przekierowującego do formularza dodawania terminu zajęć.

    2. Wypełnienie formularza

    3. Zatwierdzenie formularza przyciskiem "Dodaj"

    4. Utworzenie terminu zajęć w bazie danych
        - System tworzy nowy termin zajęć w bazie danych na podstawie otrzymanych danych.

    Warunki końcowe
    ---------------

    Nowy termin zajęć zostaje dodany do systemu.

    Wyjątki
    -------

    - Jeśli dane w formularzu są nieprawidłowe, system informuje użytkownika o błędach i prosi o ponowne wprowadzenie danych.
