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

.. hlreq:: The system should allow Viewers to view the list of Organizational Units.
    :tags: organization_units
    :id: HLR_010

.. hlreq:: The system should allow Viewers to view the list of Class Groups.
    :tags: class_groups
    :id: HLR_015
    :requires: HLR_010

.. hlreq:: The system should allow Viewers to view the schedule of the selected Class Group.
    :tags: schedule; classes_viewing
    :id: HLR_020
    :requires: HLR_015

    Selection of a Class Group should consist of choosing one of many Class Groups of the selected Organizational Unit.

.. hlreq:: The system should allow Viewers to view the list of Teachers.
    :tags: teachers
    :id: HLR_023

.. hlreq:: The system should allow Viewers to view the schedule of the selected Teacher including his Consultation Dates.
    :tags: schedule; consultations; classes_viewing
    :id: HLR_030
    :requires: HLR_023


.. hlreq:: The system should allow Teachers and Administrators to manage Consultation Dates.
    :tags: consultations
    :id: HLR_110
    :requires: HLR_300

    Administrators can manage the Consultation Dates of all Teachers, and Teachers can manage only their Consultation Dates.

    .. hlreq:: The system should allow for adding Consultation Dates.
        :tags: consultations
        :id: HLR_111

    .. hlreq:: The system should allow for viewing Consultation Dates.
        :tags: consultations
        :id: HLR_112

    .. hlreq:: The system should allow for editing Consultation Dates.
        :tags: consultations
        :id: HLR_113

    .. hlreq:: The system should allow for the removal of Consultation Dates from the system.
        :tags: consultations
        :id: HLR_114

.. hlreq:: The system should provide Administrators with a management panel.
    :tags: admin_panel
    :id: HLR_200
    :requires: HLR_300

    .. hlreq:: The system should allow Administrators to manage Courses.
        :tags: courses; admin_panel
        :id: HLR_210

        .. hlreq:: The system should allow Administrators to add new Courses to the system.
            :tags: courses
            :id: HLR_211

        .. hlreq:: The system should allow Administrators to view existing Courses in the system.
            :tags: courses
            :id: HLR_212

        .. hlreq:: The system should allow Administrators to edit existing Courses in the system.
            :tags: courses
            :id: HLR_213

        .. hlreq:: The system should allow Administrators to remove Courses from the system.
            :tags: courses
            :id: HLR_214


    .. hlreq:: The system should allow Administrators to manage user Accounts.
        :tags: acccounts; admin_panel
        :id: HLR_220

        .. hlreq:: The system should allow Administrators to create new user Accounts.
            :tags: acccounts
            :id: HLR_221

        .. hlreq:: The system should allow Administrators to view existing user Accounts in the system.
            :tags: acccounts
            :id: HLR_222

        .. hlreq:: The system should allow Administrators to edit existing user Accounts in the system.
            :tags: acccounts
            :id: HLR_223

        .. hlreq:: The system should allow Administrators to delete user Accounts from the system.
            :tags: acccounts
            :id: HLR_224


    .. hlreq:: The system should allow Administrators to manage Organizational Units.
        :tags: organization_units; admin_panel
        :id: HLR_230

        .. hlreq:: The system should allow Administrators to add new Organizational Units to the system.
            :tags: organization_units
            :id: HLR_231

        .. hlreq:: The system should allow Administrators to view existing Organizational Units in the system.
            :tags: organization_units
            :id: HLR_232

        .. hlreq:: The system should allow Administrators to edit existing Organizational Units in the system.
            :tags: organization_units
            :id: HLR_233

        .. hlreq:: The system should allow Administrators to remove Organizational Units from the system.
            :tags: organization_units
            :id: HLR_234


    .. hlreq:: The system should allow Administrators to manage Class Groups.
        :tags: class_groups; admin_panel
        :id: HLR_240

        .. hlreq:: The system should allow Administrators to add new Class Groups to the system.
            :tags: class_groups
            :id: HLR_241

        .. hlreq:: The system should allow Administrators to view existing Class Groups in the system.
            :tags: class_groups
            :id: HLR_242

        .. hlreq:: The system should allow Administrators to edit existing Class Groups in the system.
            :tags: class_groups
            :id: HLR_243

        .. hlreq:: The system should allow Administrators to remove Class Groups from the system.
            :tags: class_groups
            :id: HLR_244


    .. hlreq:: The system should allow Administrators to manage the Class Group Timetable.
        :tags: classes_management; admin_panel
        :id: HLR_250

        .. hlreq:: The system should allow Administrators to add new Classes to the system.
            :tags: classes_management
            :id: HLR_251

        .. hlreq:: The system should allow Administrators to view existing Classes in the system.
            :tags: classes_management
            :id: HLR_252

        .. hlreq:: The system should allow Administrators to edit existing Classes in the system.
            :tags: classes_management
            :id: HLR_253

        .. hlreq:: The system should allow Administrators to delete Classes from the system.
            :tags: classes_management
            :id: HLR_254


    .. hlreq:: The system should allow Administrators to manage Teachers.
        :tags: teachers; admin_panel
        :id: HLR_260

        .. hlreq:: The system should allow Administrators to add new Teachers to the system.
            :tags: teachers
            :id: HLR_261

        .. hlreq:: The system should allow Administrators to view existing Teachers in the system.
            :tags: teachers
            :id: HLR_262

        .. hlreq:: The system should allow Administrators to edit existing Teachers in the system.
            :tags: teachers
            :id: HLR_263

        .. hlreq:: The system should allow Administrators to remove Teachers from the system.
            :tags: teachers
            :id: HLR_264

.. hlreq:: The system should allow Teachers and Administrators to log into the system.
    :tags: login; admin_panel
    :id: HLR_300


Low-level requirements
======================

.. llreq:: Requirement for all forms in the System
    :tags: login
    :id: LLR_000
    :specifies: LLR_410

    - All text fields of the form should have a limit on the number of characters. The system should inform the user when the character limit is exceeded.
    - All forms should include appropriate labels and prompts to help the user understand the purpose of each field.
    - The system should automatically validate the format of data such as email addresses, passwords, fields with time selection and inform the user of any errors.

.. llreq:: Requirement of viewing panel for Organizational Units and Class Groups.
    :tags: organization_units; class_groups
    :id: LLR_050
    :specifies: HLR_010; HLR_015

    - The system should display the list of Organizational Units in the form of a tree structure. It should be possible to display a list of subordinate units by expanding the parent unit.
    - The following Organizational Unit data should be displayed:
        - name.
    - Clicking on the Organizational Unit list record should expand the list of subordinate Organizational Units and its Class Groups.
    - Clicking on the Class Group list record should take you to the timetable view of that Class Group.
    - The following Class Group data should be displayed:
        - name.

.. llreq:: Teacher viewing panel requirement
    :tags: teachers
    :id: LLR_051
    :specifies: HLR_023

    - The system should display a list of Teachers.
    - The following Teacher data should be displayed:
        - academic degree,
        - first and last name.
    - Clicking on the Teacher's list record should take you to the timetable view of that Teacher.

.. llreq:: Requirement for Timetable display
    :tags: schedule; consultations; classes_viewing
    :id: LLR_100
    :specifies: HLR_020; HLR_030

    - The schedule should be displayed as a table.
    - The table should contain a column for cells with consecutive hours from 7:00 am to 11:00 pm.
    - The table should contain a column for Classes for each day from Monday to Friday.
    - The table should contain cells to reflect the given Class.
    - The cell with the Class should be placed at the level of the cell containing the start time of that Class, with the cell heights corresponding to the hourly range of that Class.
    - When displaying a Teacher's schedule, their Consultation Dates should also be displayed.
    - If a Class Group's schedule is displayed, the logged-in Administrator should additionally see a button for adding a Class.
    - When displaying a Teacher's schedule, the logged-in Administrator should additionally see buttons for adding Consultation Dates and Classes.
    - When viewing a Teacher's schedule, the logged-in Teacher should see a button to add their Consultation Dates.

    .. llreq:: Requirement for Class cell in table
        :tags: classes_viewing
        :id: LLR_110

        - Each cell with a given Class should include:
            - Course name
            - Type of class
            - Hourly time range of the class
            - The teachers of the given class
            - The place where the lesson is held
        - The logged-in Administrator, after clicking on the Class cell, should see the Class edit form.

    .. llreq:: Consultation Date cells requirement in the timetable
        :tags: consultations
        :id: LLR_120

        - Each cell with Consultation Dates should include:
            - The hourly range of the duration of the particular consultation
            - The place where the consultation is held
            - The full name of the Teacher teaching the consultation
        - Logged in Administrator after clicking on the cell with Consultation Date should see the form for editing Consultation Date.
        - A logged Teacher after clicking on the cell with their Consultation Date should see the form for editing Consultation Date.
    

.. llreq:: Requirement for managing Consultation Dates
    :tags: consultations
    :id: LLR_210
    :specifies: HLR_110
    :requires: LLR_100

    .. llreq:: Consultation Date form requirement
        :tags: consultations
        :id: LLR_211
        :specifies: LLR_212; LLR_214; LLR_215

        - The form should consist of the following fields:
            - day of the week (selection from the list)
            - start time (selection of the time through a mechanism that prevents incorrect selection)
            - end time (selection of the time through a mechanism that prevents incorrect selection)
            - place of holding the consultation (max 50 characters)
            - optional public description (max 500 characters)
        - The administrator should additionally be able to select the Teacher holding the consultation.

    .. llreq:: The requirement of the option to add Consultation Date
        :tags: consultations
        :id: LLR_212
        :specifies: HLR_111

        - After clicking the add Consultation Date button, the System should display the form for adding Consultation Date.
        - After receiving valid data, the System should create a new Consultation Date in the database.

    .. llreq:: The requirement of the option to view Consultation Dates
        :tags: consultations
        :id: LLR_213
        :specifies: HLR_112

        - Viewing Consultation Dates is the same as the requirement for displaying the Teacher's schedule.

    .. llreq:: The requirement of the option to edit Consultation Date
        :tags: consultations
        :id: LLR_214
        :specifies: HLR_113

        - After clicking on the cell with Consultation Date, the System should display the form for editing Consultation Date.
        - The form should initially be completed with the current Consultation Date data.
        - Once the correct data is received, the System should change the Consultation Date data in the database.

    .. llreq:: The requirement for the option to remove Consultation Date
        :tags: consultations
        :id: LLR_215
        :specifies: HLR_114

        - When you click on a cell with Consultation Date, the System should display the form for editing Consultation Date with a button for deleting the Consultation Date.
        - If this button is pressed, the Consultation Date should be removed from the database.


.. llreq:: Administrator management panel requirement
    :tags: admin_panel
    :id: LLR_300
    :specifies: HLR_200

    - The system should provide the Administrator with a link to the management views of Organizational Units, Courses, Accounts and Teachers.

    .. llreq:: Requirement for Course management view
        :tags: courses; admin_panel
        :id: LLR_310
        :specifies: HLR_210

        - The system should provide Course management view.
        - The system should provide a button to go to the form for adding Course.
        - The system should provide a text field to search for Course by name and Course code.

        .. llreq:: Course form requirement
            :tags: courses
            :id: LLR_311
            :specifies: LLR_312; LLR_314; LLR_315

            - The form should consist of the following fields:
                - unique Course code (2 to 50 characters),
                - Course name (3 to 100 characters),
                - optional Course description (500 characters max).
            - The form should not accept incorrect data such as:
                - Course code existing in the database.

        .. llreq:: The requirement of the option to add Courses
            :tags: courses
            :id: LLR_312
            :specifies: HLR_211

            - The System should provide a form for adding Course.
            - After receiving valid data, the System should create a new Course in the database.

        .. llreq:: The requirement of the Course viewing option
            :tags: courses
            :id: LLR_313
            :specifies: HLR_212

            - The system should display the Course list in the form of a table. The following data should be displayed:
                - Course name,
                - Course code,
                - description.
            - Selecting a particular record should make the System display the Course editing form.
            - Entering text in the search field should cause the System to display only those records that contain the entered phrase in the Course name or code.

        .. llreq:: The requirement of the Course editing option
            :tags: courses
            :id: LLR_314
            :specifies: HLR_213

            - The System should provide the Course editing form with the old data pre-entered.
            - After receiving the correct data, the System should change the Course data in the database.

        .. llreq:: The requirement of the Course deletion option 
            :tags: courses
            :id: LLR_315
            :specifies: HLR_214

            - After selecting a record with a Course, the System should display the Course editing form with a button to delete the Course.
            - If this button is pressed, the Course should be deleted from the database.
            - All Classes related to this Subject should also be deleted.

    .. llreq:: The requirement for the Accounts management view
        :tags: accounts; admin_panel
        :id: LLR_320
        :specifies: HLR_220

        - The system should provide a view to manage Accounts.
        - The system should provide a button to go to the form for adding an Account.
        - The system should provide a text field to search for Accounts by email address.

        .. llreq:: The requirement for the Account form
            :tags: accounts
            :id: LLR_321
            :specifies: LLR_322; LLR_324; LLR_325

            - The form should consist of the following fields:
                - valid email address,
                - password (from 8 to 128 characters),
                - roles (multiple selection from a list).
            - The form should not accept incorrect data such as:
                - e-mail address existing in the database.

        .. llreq:: The requirement of the option to add an Account
            :tags: accounts
            :id: LLR_322
            :specifies: HLR_221

            - The System should provide a form for adding an Account.
            - Upon receipt of valid data, the System should create a new Account in the database.

        .. llreq:: The requirement for the option to view Accounts
            :tags: accounts
            :id: LLR_323
            :specifies: HLR_222

            - The System should display the list of Accounts in the form of a table. The following data should be displayed:
                - e-mail address,
                - roles.
            - Selecting a particular record should make the System display the Account editing form.

        .. llreq:: The requirement of the Account editing option
            :tags: accounts
            :id: LLR_324
            :specifies: HLR_223

            - The System should provide an Account editing form with the old data pre-entered except for the password.
            - After receiving the correct data, the System should change the Account data in the database.

        .. llreq:: The requirement of the Account deletion option
            :tags: accounts
            :id: LLR_325
            :specifies: HLR_224

            - After selecting a record with an Account, the System should display an Account editing form with a button to delete the Account.
            - If this button is pressed, the Account should be deleted from the database.


    .. llreq:: The requirement for the Organizational Units and Class Groups management view.
        :tags: organization_units; class_groups; admin_panel
        :id: LLR_330
        :specifies: HLR_230; HLR_240

        - The system should provide a view for managing Organizational Units.
        - The system should provide buttons to go to the form for adding an Organizational Unit and Class Group.
        - The system should provide buttons to go to the form for editing Organizational Unit and Class Group.

        .. llreq:: The requirement for the Organizational Unit form
            :tags: organization_units
            :id: LLR_331
            :specifies: LLR_332; LLR_334; LLR_335

            - The form should consist of the following fields:
                - name (from 2 to 100 characters),
                - optional parent entity (selection from the list)
                - optional description (max 1000 characters).
            - The form should not accept erroneous data such as:
                - name of a unit existing at a given hierarchy level.
                - parent unit equal to itself.

        .. llreq:: The requirement of the option to add an Organizational Unit
            :tags: organization_units
            :id: LLR_332
            :specifies: HLR_231

            - The System should provide a form for adding an Organizational Unit.
            - After receiving the correct data, the System should add a new Organizational Unit.

        .. llreq:: The requirement of an option to view Organizational Units
            :tags: organization_units
            :id: LLR_333
            :specifies: HLR_232

            - The System should display the list of Organizational Units in the form of a table. The following data should be displayed:
                - name of the unit,
                - description.
            - Selecting a given record should cause the System to allow the Organizational Unit edit button to be pressed.

        .. llreq:: The requirement of the option to edit information about the Organizational Unit.
            :tags: organization_units
            :id: LLR_334
            :specifies: HLR_233

            - The System should provide a form for editing the Organizational Unit information with the old data pre-entered.
            - After receiving the correct data, the System should change the Organizational Unit information.

        .. llreq:: The requirement of the option to delete an Organizational Unit
            :tags: organization_units
            :id: LLR_335
            :specifies: HLR_234

            - After selecting a record with an Organizational Unit, the System should allow you to press the edit button, which should display the Organizational Unit edit form with a button to delete the Organizational Unit.
            - If this button is pressed, the Organizational Unit should be deleted from the database.
            - All Class Groups associated with that Organizational Unit should also be deleted.
            - All Sub Organizational Units should also be deleted.

        .. llreq:: The requirement for the Class Group form.
            :tags: class_groups
            :id: LLR_341
            :specifies: LLR_343; LLR_344; LLR_345

            - The form should consist of the following fields:
                - name (from 2 to 50 characters),
                - organizational unit (selection from the list)
                - optional description (max 500 characters).
            - The form should not accept incorrect data such as:
                - the name of the group existing in the Organizational Unit.

        .. llreq:: The requirement of the option to add a Class Group
            :tags: class_groups
            :id: LLR_342
            :specifies: HLR_241

            - The System should provide a form for adding a Class Group.
            - After receiving valid data, the System should add a new Class Group.

        .. llreq:: The requirement of the Class Group viewing option.
            :tags: class_groups
            :id: LLR_343
            :specifies: HLR_242

            - The system should display the list of Class Groups in the form of a table. The following data should be displayed:
                - class group name,
                - description.

        .. llreq:: The requirement of the option to edit Class Group information.
            :tags: class_groups
            :id: LLR_344
            :specifies: HLR_243

            - The System should provide a form for editing Class Group information with the old data pre-entered.
            - After receiving the correct data, the System should change the Class Group information.

        .. llreq:: The requirement of the Class Group removal option.
            :tags: class_groups
            :id: LLR_345
            :specifies: HLR_244

            - After selecting a record with a Class Group, the System should allow you to press the edit button, which should display the Class Group edit form with a button to delete the Class Group.
            - If this button is pressed, the Class Group should be deleted from the database.
            - All Classes Terms associated with that Class Group should also be deleted.

    .. llreq:: The requirement for the management of Classes'
        :tags: admin_panel; classes_management
        :id: LLR_350
        :specifies: HLR_250
        :requires: LLR_100

        .. llreq:: The requirement for the Classes form
            :tags: classes_management
            :id: LLR_351
            :specifies: LLR_352; LLR_353; LLR_354

            - The form should consist of the following fields:
                - Course (selection from the list),
                - Class Group (selection from the list),
                - day of the week (selection from the list)
                - start time (selection of the time through a mechanism that prevents erroneous selection)
                - end time (selection of the time through a mechanism that prevents erroneous selection)
                - class teachers (multiple selection from the list),
                - type of class (max 50 characters)
                - optional location of the class (max 50 characters)
                - optional description (max 500 characters)

        .. llreq:: The requirement for the option to add a Classes Term
            :tags: classes_management
            :id: LLR_352
            :specifies: HLR_251

            - When you click the Add Class button in the Class Group schedule view, the System should display the add Class form.
            - If you have pre-selected the cells corresponding to the start and end times of the class, the form should be pre-filled with this data (including the day of the week).
            - After receiving the correct data, the System should create a new Class in the database.

        .. llreq:: The requirement for the option to view Classes
            :tags: classes_management
            :id: LLR_353
            :specifies: HLR_252
            :requires: LLR_100

            - The requirement is the same as the requirement to display the Class Group schedule.

        .. llreq:: The requirement of the option to edit Class information.
            :tags: classes_management
            :id: LLR_354
            :specifies: HLR_253

            - The System should provide a form for editing Class information with the old data pre-entered.
            - After receiving the correct data, the System should change the Class information.

        .. llreq:: The requirement for the option to remove the Class.
            :tags: classes_management
            :id: LLR_355
            :specifies: HLR_254

            - When you click on the Class cell, the System should display the Class edit form with a delete Class button.
            - If this button is pressed, the Class should be deleted from the database.


    .. llreq:: The requirement for the Teachers management view
        :tags: teachers; admin_panel
        :id: LLR_360
        :specifies: HLR_260

        - The system should provide a view to manage Teachers.
        - The system should provide a button to go to the form for adding a Teacher.
        - The system should provide a text field to search for Teachers by academic degree, first name and last name.

        .. llreq:: The requirement for the Teacher form
            :tags: teachers
            :id: LLR_361
            :specifies: LLR_362; LLR_364; LLR_365

            - The form should consist of the following fields:
                - first name (from 3 to 50 characters),
                - last name (from 3 to 50 characters),
                - optional academic degree (max 20 characters),
                - optional phone number (max 15 characters),
                - optional biography (max 1000 characters).
                - optional user Account selection (select from list).
            - The form should not accept incorrect data such as:
                - the selected Account is already assigned to another Teacher.

        .. llreq:: The requirement of the option to add a Teacher
            :tags: teachers
            :id: LLR_362
            :specifies: HLR_261

            - The System should provide a form for adding a new Teacher.
            - Upon receipt of valid data, the System should add the new Teacher to the database.

        .. llreq:: The requirement of the option to view Teachers
            :tags: teachers
            :id: LLR_363
            :specifies: HLR_262

            - The System should display the list of Teachers in the form of a table. The following data should be displayed:
                - academic degree,
                - first and last name,
                - email address.
            - Selecting a particular record should cause the System to display the Teacher editing form.
            - Entering text in the search box should cause the System to display only those records that contain the entered phrase in the academic degree, first name or last name of the Teacher.

        .. llreq:: The requirement for the option to edit Teacher information
            :tags: teachers
            :id: LLR_364
            :specifies: HLR_263

            - The System should provide a form for editing the Teacher information with the old data pre-entered.
            - After receiving the correct data, the System should change the Teacher information.

        .. llreq:: The requirement of the Teacher removal option
            :tags: teachers
            :id: LLR_365
            :specifies: HLR_264

            - After selecting a record with a Teacher, the System should display the Teacher edit form with a button to delete the Teacher.
            - If this button is pressed, the Teacher should be removed from the database.
            - The user Account assigned to the Teacher should also be deleted.
            - All Consultation Dates of a given Teacher should also be deleted.



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


Non-functional requirements
===========================

.. nfreq:: The System should be accessible through Google Chrome and Mozilla Firefox web browsers.
    :id: NFR_001

.. nfreq:: The system should store user passwords in encrypted form. Passwords should be encrypted using a modern cryptographic algorithm.
    :id: NFR_002

.. nfreq:: It should not take more than 300 milliseconds to load the requested timetable.
    :id: NFR_003

.. nfreq:: Operation of the system should be intuitive and easy to use. The user should be able to use the system on his own after a short course.
    :id: NFR_004

.. nfreq:: User sessions should be properly managed, and users should be automatically logged out after a certain period of inactivity to prevent unauthorized access to the system.
    :id: NFR_005

.. nfreq:: The user interface should be dynamic, i.e. it should not require refreshing the page after performing an action.
    :id: NFR_006

.. nfreq:: The system should check the correctness of the data entered and inform the user of errors.
    :id: NFR_007

.. nfreq:: The system should give access to secured areas of functionality only to authorized users.
    :id: NFR_008


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
