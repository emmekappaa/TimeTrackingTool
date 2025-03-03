[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/made-with-html_css.svg)](https://forthebadge.com)

# Time Tracking Tool

## System Description

The Time Tracking Tool is a platform designed to support researchers and scientific managers in managing and monitoring research projects.  
The system facilitates the reporting of working hours and project management.

## System Objectives

The Time Tracking Tool is designed to automate and simplify the process of managing working hours for research projects and assigning them to new researchers.  
The system must provide an intuitive and user-friendly interface.

The system must support the following main functionalities:

- Daily time log management
- Monthly report visualization
- Project assignment to researchers
- Work hour history tracking
- Monthly report signing for a project

## Glossary

- **Researcher**: A user who participates in projects and logs working hours.
- **Scientific Manager**: A user who participates in projects, assigns projects to researchers, and monitors their monthly hours.
- **Report**: A visualization of the hours worked on a project for a specific month.
- **Signature**: Approval via signature of the hours worked related to a specific project and month.
- **TimeLog**: A record of working hours related to a day and a project, logged by a researcher/scientific manager.

## Scenarios

### 1. System Authentication and TimeLog Registration (Researcher)

The system must allow the Researcher to authenticate and subsequently log their daily TimeLogs.

### 2. View and Sign Report (Researcher)

The system must allow a Researcher to view and sign the hours related to a project on a monthly basis.

### 3. View and Sign Report (Scientific Manager)

The system must allow the Scientific Manager to sign and thus approve the researchers' reports.

### 4. Handling Incorrect TimeLog Entries

The system must verify the registration of a TimeLog with a number of working hours exceeding 8.

### 5. Work Hours History Consultation

The system must provide a history of recorded working hours.

### 6. System Authentication and TimeLog Registration (Scientific Manager)

The system must allow the Scientific Manager to authenticate and subsequently log their daily TimeLogs.

### 7. Project Creation and Researcher Assignment

The system must allow the Scientific Manager to create a new project and assign it to selected Researchers.

### 8. Project Rejection

The system must allow Researchers to reject a project assigned by the Scientific Manager.

### 9. Project Acceptance

The system must allow Researchers to accept a project assigned by the Scientific Manager.

## Permissions

- **Project Creation**: Scientific Manager
- **Project Read**: Scientific Manager, Researcher
- **TimeLog Creation**: Scientific Manager, Researcher
- **TimeLog Read**: Scientific Manager, Researcher
- **Report Read**: Scientific Manager, Researcher

## Testing and Code Quality

To verify the system's functionality and component interaction, the following System Tests were conducted using the Selenium library:

### `testLoginAsResearcher`

A test that verifies the authentication of a Researcher user. It involves all scenarios related to the Researcher.

### `testAddTimeLog`

A test that verifies the addition of a valid TimeLog by a Researcher.  
**Involved scenarios**: 1.

### `testErrorOnExceedingMaxHours`

A test that verifies the appearance of an error message when attempting to add an invalid TimeLog.  
**Involved scenarios**: 4.

### `testMonthlyReport`

A test that verifies the functionality of the monthly report management system.  
**Involved scenarios**: 2, 3.

### `testManagerFlow`

A test that verifies the correct and incorrect addition of a TimeLog by a Scientific Manager.  
**Involved scenarios**: 6.

### `testResearcherAcceptsPendingProject`

A test that verifies the creation, assignment, and acceptance of a new project.  
**Involved scenarios**: 7, 8, 9.

### `testAddTwoHoursAndVerifyInHistory`

A test that verifies the correct display of the work hour history.  
**Involved scenarios**: 5.

Additionally, numerous unit tests were written using the JUnit library to verify and ensure the functionality of the methods and classes used within the application.  
Subsequently, to check the coverage of the written code, the built-in tool of the IntelliJ IDE was used, achieving the following results:

![Code Coverage](/images/coverage_intellij.png "Code Coverage")

## User Guide

To use the application and verify its functionality and scenarios, the following accounts are available:

- **Scientific Manager** Franco Verdi, `username = root`, `password = root`;
- **Researcher** Dario Rossi, `username = user`, `password = user`;
- **Researcher** Luigi Rossi, `username = user1`, `password = user1`;

The account **Dario Rossi** already has some pre-entered TimeLogs up to the date **26/01/2025**.  
The account **Luigi Rossi** has no TimeLogs and has been used to verify the functionality of the project assignment system.

## Authors

- **Alessandro Aldegheri** VR519407
- **Michele Cipriani** VR516307
