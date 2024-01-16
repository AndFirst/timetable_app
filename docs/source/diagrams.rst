Use cases diagram
*****************

.. needuml::

    left to right direction

    "Viewer" as viewer
    "Teacher" as teacher
    "Administrator" as administrator

    package "Administrator Panel" {
        "Managing Classes" as (manage_classes)
        "Managing Organizational Units" as (manage_org_units)
        "Managing Class Groups" as (manage_class_groups)
        "Managing Accounts" as (manage_accounts)
        "Managing Courses" as (manage_courses)
    }
    package "Teacher Panel" {
        "Managing Consultation Dates" as (manage_consultations)
    }
    package "Displaying Timetable" {
        "Displaying Classes" as (display_classes)
        "Displaying Consultations Dates" as (display_consultations)
    }
    package "Browsing Available Class schedules" {
        "Browsing Organizational Units" as (browse_org_units)
        "Browsing Class Groups" as (browse_class_groups)
        "Browsing Teacher Schedules" as (browse_teacher_schedules)
    }
    package "Login" {
        "Login" as (login)
    }

    viewer --> display_classes
    viewer --> display_consultations

    teacher --> manage_consultations
    
    administrator --> manage_consultations
    administrator --> manage_classes
    administrator --> manage_org_units
    administrator --> manage_class_groups
    administrator --> manage_accounts
    administrator --> manage_courses
    

    manage_classes ..> login : <<Include>>
    manage_org_units ..> login : <<Include>>
    manage_class_groups ..> login : <<Include>>
    manage_accounts ..> login : <<Include>>
    manage_courses ..> login : <<Include>>
    
    manage_classes ..> display_classes : <<Include>>
    manage_consultations ..> display_consultations : <<Include>>

    display_classes ..> browse_class_groups : <<Include>>
    display_consultations ..> browse_teacher_schedules : <<Include>>

    browse_org_units <.. browse_class_groups : <<Include>>

Class diagram
*************

.. needuml::

    abstract class Account {
        emailAddress: String
        password: String
    }

    class TeacherInfo {
        name: String
        surname: String
        degree: String
        phoneNumber: String
        biography: String
    }
    
    class OrganizationalUnit {
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

    class Role {
        name: String
    }

    Event <|.. Class
    Event <|.. Consultation

    TeacherInfo o-- Account
    Account o- Role

    OrganizationalUnit *-- OrganizationalUnit
    OrganizationalUnit *-- ClassGroup

    Class o-- Course 
    Class o- TeacherInfo

    TeacherInfo *-- Consultation

    ClassGroup *-- Class

Architecture Diagram
********************

