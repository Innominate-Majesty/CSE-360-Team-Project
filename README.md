*** CSE 360 - Intro to Software Engineering ***

Semester: Fall 2026

Last updated: September 17, 2026

Team Members:

1. Caleb Beaven
2. Krithik Duraisamy
3. Venus Ho
4. Alex Murray
5. Alan Shimp

-- Project Details --

Phase 1:

   1. Extended an existing Java/JavaFX application rather than building the system from scratch.
   2. Established the foundation for the application's secure identity and login system.
   3. Implemented validation for existing and newly added input fields.
   4. Used finite-state-machine concepts for validating user input and generating meaningful error messages.
   5. Established a consistent user experience for newly implemented roles and screens.
   6. Created and prioritized a sprint backlog containing required user stories.
   7. Developed test cases, architecture/design documentation, and code documentation.
   8. Conducted recurring team standups and tracked user stories through Backlog, In Progress, and Completed states.
   9. Produced demonstrations connecting requirements → architecture → design → implementation → testing.

Phase 2:

Phase 3:


-- Group Norms --

1. Team Project Work will be divided as equally as possible, if there are issues with getting the work done (Such as not understanding the assigned subject, or not being able to do it in a timely manner) then the group will be notified quickly (Within 24 hours).

2. All assigned Team Project Work should be completed 48 hours ahead of due date to allow Quality Control and or last minute stuff breaking having time to be fixed.

3. If 2 Meetings in a row are missed then a considerable update should be made by the person missing, and 3 Meetings in a row will never be missed (Without extenuating circumstance).

4. Disagreements in planning of the project should be handled in front of the team with all members input, and should be handled in a well meaning and friendly demeanor.

5. Don't Cheat, if you're struggling with material reach out to the group for help or swapping of workload.

6. All meetings require some sort of input, so if you're going to miss one please write a short update on what you accomplished since the previous standup, what you plan to accomplish before the next standup, and any issues that are blocking progress.

7. If the team attempts to communicate multiple times with an individual member and receives no reply for 7 days (Period of 2 meetings), then the team will have no choice but to assume the member has dropped out and say as such to the Professor.


-- Project Directory and Details -- 

# TP1

The TP1 folder should contain the combined main project and all required implementations. Its
planned directory structure is:

1. Foundations-F26/

   - .classpath
   - .project
   - build.fxbuild

   1. .settings/
     - org.eclipse.core.resources.prefs
     - org.eclipse.jdt.core.prefs
   2. src/

     - module-info.java

     1. applicationMain/
        - FoundationsMain.java
        - application.css
        - package-info.java
     2. database/
        - Database.java
     3. entityClasses/
        - User.java
     4. guiAddRemoveRoles/
        - ControllerAddRemoveRoles.java
        - ModelAddRemoveRoles.java
        - ViewAddRemoveRoles.java
     5. guiAdminHome/
        - ControllerAdminHome.java
        - ModelAdminHome.java
        - ViewAdminHome.java
     6. guiDeleteUser/
        - ControllerDeleteUser.java
        - ModelDeleteUser.java
        - ViewDeleteUser.java
     7. guiFirstAdmin/
        - ControllerFirstAdmin.java
        - ModelFirstAdmin.java
        - ViewFirstAdmin.java
     8. guiMultipleRoleDispatch/
        - ControllerMultipleRoleDispatch.java
        - ModelMultipleRoleDispatch.java
        - ViewMultipleRoleDispatch.java
     9. guiNewAccount/
        - ControllerNewAccount.java
        - ModelNewAccount.java
        - ViewNewAccount.java
     10. guiRole1/
         - ControllerRole1Home.java
         - ModelRole1Home.java
         - ViewRole1Home.java
     11. guiRole2/
         - ControllerRole2Home.java
         - ModelRole2Home.java
         - ViewRole2Home.java
     12. guiTools/
         - GUISingleRoleDispatch.java
     13. guiUserLogin/
         - ControllerUserLogin.java
         - Model.java
         - ViewUserLogin.java
     14. guiUserUpdate/
         - ControllerUserUpdate.java
         - Model.java
         - ViewUserUpdate.java
     15. passwordValidator/
         - PasswordValidator.java
     16. userNameRecognizer/
         - UserNameRecognizer.java

The generated bin/ directory is not part of the submitted source structure.


# All other folders

# Maven

Maven has been added to the project directory as a separate folder. It is not located inside
TP1, so a ZIP file containing only the TP1 folder will not include the Maven
files.

Maven is a build and dependency-management tool for Java projects. It manages the libraries and
configuration needed by the application and makes the project easier to build and run from Visual
Studio Code without manually configuring every dependency.

Commands to run each implementation from the Visual Studio Code terminal:

1. Command for implementation 1 (to be added)
2. Command for implementation 2 (to be added)
3. Command for implementation 3 (to be added)

-- Meeting Details --

