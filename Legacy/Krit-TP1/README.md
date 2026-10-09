Team Project 1

-- Summary --

1. Required files in the final zip folder

2. Required Story Assignments

    Each story will be implemented by one team member. That leaves an extra team member, but the one-time password story seems more complicated than the others, so that one has been split into two tasks.

       1. Password validation: Alex
       2. Admin’s ability to set a one-time password: Krithik
       3. Admin’s ability to delete a user account: Alan
       4. Admin’s ability to list all user accounts and display information: Venus
       5. Admin’s ability to add or remove roles to and from users: Caleb

3. TLDR
    - Extend the provided foundational Java/JavaFX application rather than building a new application from scratch.
    - Select one or more professionally produced applications/websites as inspiration for the project's user experience and visual design.
    - Establish a consistent user interface for newly implemented roles while keeping the existing Admin interface if desired.
    - Review the team's HW1 solutions and agree on a single approach for username validation and its user interface.
    - Expand input validation beyond HW1 so that every input field used in TP1 is checked before its value is processed.
    - Incorporate the provided password-validation approach from `PasswordEvaluationTestbed-F26`, including protection against excessively long passwords and textual inputs.
    - Implement all required New User and Admin user stories that are not already implemented by the foundational code.
    - Integrate all new functionality into one cohesive, working application.
    - Create automated and/or manual tests for the project's requirements and resolve defects discovered during testing.
    - Maintain alignment between requirements, architecture, detailed design, implementation, documentation, and testing.
    - Update UML architecture and detailed-design documentation as the implementation changes.
    - Keep new code, Javadoc, internal comments, and other documentation consistent with the style of the provided foundational code.
    - Use a Sprint Backlog and project plan to assign work, identify risks, and track implementation progress.
    - Hold team standups at least twice per week to communicate progress, upcoming work, blockers, and integration risks.
    - Produce demonstrations showing that the implementation works and that the requirements flow through design, code, and testing.

-- Full Details --

(Copy and Pasted from Canvas)

Introduction

The goal for this team project is to help you move beyond small, throwaway, class-sized programs toward those society needs and will use. You have been introduced to numerous principles and concepts during your education.  The challenge now is determining which are relevant for the task at hand and how to choose between potential solutions.  In computing, there is seldom one right way to satisfy the needs of a set of stakeholders.  The key reasons for this are the differing stakeholder goals and objectives, the accelerating rate of change in technology and user needs, and the competitive market for the work.

Providing the right solution to the right people at the right time in a form they can use effectively today and in the future is critical.  Success also demands that the solution protect the user from improper or malevolent use without onerous burdens on legitimate users.  Up to now, most of the programs you have written have been small and focused on satisfying various functional requirements.  As a professional, you will be challenged to create and support much larger systems that deliver significant value to those who use, produce, support, and operate them.

A serious challenge we face when producing systems of real value is the fact that people will become reliant on them.  When that happens, these systems become targets for cybercrime.  It's hard enough to keep systems effective and useful, but we must also strive to make it as difficult as possible for cybercriminals to cause havoc and steal.

High-performance teams bring numerous benefits to all aspects of software-intensive systems.  Each member brings unique experiences, perspectives, and capabilities to the project.  This can be a determining factor between producing a good tactical product and a successful one that lasts. Long-term project success requires more than the testable knowledge common in many courses you have taken.   The team must find common ground to implement the solution effectively within the given resource constraints (e.g., time, cost).  A set of Team Norms can be a powerful tool to help teams work through many of the common challenges they face.  How do you handle a teammate who is not participating? Doing nothing rewards that individual, and there is little hope for improvement. To learn more about team norms, consider reading this article.

          10 Steps for Establishing Team Norms from Center for Creative Leadership.pdf

The work by Tuckman is commonly used to help people appreciate and move more quickly to high performance.  The following Wikipedia article is a good place to start to learn about team development.

          https://en.wikipedia.org/wiki/Tuckman%27s_stages_of_group_developmentLinks to an external site.

High-performance teams don’t just partition the work, independently produce their portion of the solution, and then collaborate to integrate the parts into a working whole.  Students try this all the time because it often works for small, simple projects.  Sadly, very few real-world projects that professionals are asked to produce are small and simple.  When do you plan to learn and then practice the important proven best-practice methods?  Why not now and in this course? 

The problem with choosing the simple way is that the work seldom fits nicely with the work of others into an effective, trustworthy whole.  Each individual's part usually needs to fit perfectly with the solution elements produced by the other team members.  Integration can be a nightmare if these elements are not carefully designed and aligned to work with the others.  Given that most of this work differs from what the team members have done in the past, new lessons must be learned, and changes made.  The complex relationships between the system's parts mean that any change in one part often requires adjustments in the others.  To handle this, the team must work collaboratively.

Members in each Standup Meeting share key accomplishments since the last Standup, the planned work before the next, and identify issues.  The goal is not to provide status to management.  Rather, the goal is to tell your team what they might need to know when they start working after the meeting. Don't waste their time or yours on things they don't need to hear. Daily Standup Meetings are not required for this course.  Hold at least two each week, with enough time between them to accomplish useful work, integration testing, and refinements. 

Standup Meetings are a critical collaboration and communication tool.  They help team members identify previously unrecognized interactions, realize that previous experience might help resolve another’s current issue, and facilitate collaboration.  (If you don't know what issues a teammate is having, how can you help?)  Putting off work until just before the deadline is not appropriate for this course and regularly leads to painful integrations and poor results.  The likelihood of superior results is low if your team works individually until just before the due date!

Overview
The team must work together to find a current professionally produced application or webpage that requires users to log in to use the application and allows users to update their account information (e.g., name, email address, and password).  Using it as a user interface design inspiration, the team must update the provided foundational code so a third party would believe that a successful application or webpage influenced the team’s work. You may use the provided GUI design for the Admin functions as is, but the result of this team activity should influence the look and feel of the other roles.  Start this search for your team's design inspiration now!

Foundational code with supporting documentation has been provided.  IN this course, you are expected to build upon this foundational code and enhance the supporting documentation (e.g., UML-based architecture and detailed design diagrams).  The code and documentation must align and employ what professionals are actually using today.  Producing these documents and keeping them in sync with each other and the code is crucial on team projects.  If you don't write it down, you will be forced to explain it to others, taking you away from what you are supposed to be doing now.  The goal is not to please the instructional team.  Rather, it is to excite you and provide concrete proof of what you can do and how well you can do it in a team context.  Concrete examples and your obvious excitement are what prospective employers love to see.  Our goal is to help you accomplish that.

Your team is expected to implement input validation for all input fields.  As a team, review the HW1 username validation submissions each member has made and reach an agreement on the code and user interface to use for the user stories for this phase.

In addition to the username validation from HW1, you are expected to leverage the standalone application "PasswordEvaluationTestbed-F26".  The following is the UML state diagram for the password recognizer at the heart of that application.  In addition to the functionality, you also need to define and then have your code detect passwords that are too long.  (All textual input fields should be checked for input exceeding a reasonable size limit before doing anything with it.  Beyond that, not all input fields need to be validated.  Can you explain why?)   One standard hacking tactic is to drop a long string of text into each input field to see if the application crashes.  If it does, they know the application has a potentially exploitable weakness.  Once they find a weakness, they are much more likely to invest more time and energy experimenting to see whether they can leverage it to take over the application.  Therefore, you don't want your applications to exhibit an easy-to-find weakness!

This Astah UML diagram describes how to determine if a password satisfies a set of requirements.  Given what has been described before, what important requirement is not explicitly being tested?  Is it tested before the code described here is used, or does this diagram need to be enhanced to address this other requirement?

Directed Graph - Password Evaluator.png

Your team must determine the best user interface to use when a user specifies a new password.  Please experiment with the standalone application to better understand how this dynamic interface helps users create a valid password.  This is quite different from the username validator's function.

Use the following link to download the PasswordRecognizer standalone application.

PasswordEvaluationTestbed-F26.zipDownload PasswordEvaluationTestbed-F26.zip

The team must ensure that the required set of new user stories specified below (Task 5) has been implemented and integrated into a well-functioning application.  Remember, these user stories may be incomplete, may conflict with one another, and may contain errors, just like in the real world outside of the university.  Address and resolve these issues as a team by engaging with the stakeholders’ representative, the instructional team. The new code and documentation must be like what was provided so that outsiders would assume it was the work of a single author.

Three screencasts must be produced:

1.    The first demonstrates that the new functionality works properly and the validation using the semiautomated method used in the PasswordEvaluationTestingAutomation Class and other testing methods.  The intent is to convince the observer (and the grader) that the implementation satisfies the requirements.

2.    The second shows how all the required deliverables align with one another and the provided course examples (some of the provided code may need to be altered).  Each team member must be a screencast presenter.  The duration of each presenter must be roughly the same.

3.    The third screencast is a set of recordings that summarize the team's standup meetings.  Start work on Phase 1 as soon as it is made available, even in parallel with HW1.

The team must submit a ZIP archive of the TP1 folder, described in Task 1 below.  The detailed requirements for this team assignment are specified below.  Each team member is required to verify that the contents of the submission are correct and work as required before the assignment is submitted, and that Canvas accepted the submission before the deadline.  This is not something you can delegate to other team members!  Submitting at the last minute makes it impossible for the team to verify a successful submission or recover from something unexpected.

TP1 Submission: If the team is still working on this submission on the day it is due, submit what you have at noon.  With that submission made, continue to work to finish the assignment.  The last successful submission will be graded.  If there are any problems with this noon submission, the team will have plenty of time to resolve the issue.

Do not contact the grader, TA, or the instructor about a late submission.  Team projects will not be accepted if they are late, even by just one second! Accidents, illnesses, family emergencies, and computer, internet, or power failure are not valid excuses for failing to submit before the deadline.

Regardless of the reason, do not email submissions to the grader, TA, or instructor.  All Individual and Team Project submissions must be made through Canvas.

Tasks
This team project phase requires the team to perform the following tasks.

Create a folder named "TP1" and use this folder to hold the various elements of the submission.

In parallel with the following tasks, establish a set of Team Norms to help members move from being a group of students to a high-performance team.  This requires each team member to think about how projects have failed and the downside risks that need to be mitigated.  An Ed Discussion Thread has been established to help the entire class capture issues that have caused problems in the past. Given a list of issues, the team must address each as needed to minimize the negative impact.  This should be done collaboratively to build stronger connections among the team members.  Ed Discussion Category "Team Norms" threads are not the place to propose Team Norms.  Rather, use it to discuss the risks and related issues.  Develop norms that are right for your team and this class.  A deliverable for this task is listed below.

One key norm is to meet at least twice a week for a "Standup" meeting, where each team member describes in just two to three minutes the following.
     1) what they accomplished since the previous standup,
     2) what they plan to accomplish before the next standup, and
     3) any issues that are blocking progress.  

This meeting must be recorded so it can be used to create a 2 to 3-minute summarization of each standup.  (These summaries must be created in alignment with Task 8.3.  Use Zoom to do the recording and record to the cloud, not your computer.  When you record to the cloud, it does an amazing job compressing the files, which does not happen if you record to your computer.)

These communication meetings are used to identify when collaboration may be needed.  Waiting until integration to discover that two or more team members made changes to the same set of classes (methods and attributes) without agreeing on what those changes should be can be catastrophic!  Standups are not problem-solving meetings.  (For more about this kind of meeting, read this article, Daily Scrum MeetingLinks to an external site..)

When you are finished producing your team norms, create a "Team Norm.pdf" of the team norms list with a brief explanation of why each is important to the team, and have each member of the team sign it.  Add the PDF to the TP1 folder.

Find a set of professionally produced applications or webpages that require users to log in and support updating that user’s account information (e.g., name, email address, and password). Reach and document a shared agreement on the user experience and interface aspects that the team project should employ in a document named "User Experience.pdf" and place this PDF in the TP1 folder.  The document must include screenshots of various application screens with a short description of what is shown and why the team believes it is important.  Tailor the team's application GUI for all new roles into something that the team believes will reflect well on their professionalism when applying for internships and jobs!

Identify and list the input validation required to ensure every input field for the phase is checked.  Produce a document named "InputValidation.pdf".  (This goes beyond just the username validation of HW1.) Ensure that input validation is performed before using the input.  Issue useful error messages when validation fails and process the input when validation succeeds.  Add the PDF to the TP1 folder.

Study the required user stories for this phase of the project, the 1) New User and 2) Admin functions, and implement them all. (Study this PDF.) The provided foundational code implements only a subset of these user stories.  The team must work together to identify elements of those user stories that still need to be implemented.  This set of User Stories forms the "TP1 Sprint Backlog.pdf".  Add this PDF to the TP1 folder.  Collaborate across the team to create and document a plan (e.g., who will do what and when) to implement the Sprint Backlog. Emphasize identifying and resolving unknowns and risks as early as possible.  (E.g., If the obvious solution does not work, ensure there is enough time to experiment as needed to find a solution that does, with enough time remaining to implement the components and integrate them into the submission well before the deadline.) Store this plan, which includes the Sprint Backlog, in a document named "TP1 Plan.pdf" with the date that the plan was produced.  If the plan needs to be updated during the Sprint, produce an updated version of the earlier plan and add it to the bottom of the "TP1 Plan.pdf" with a new date so that all the plans are in the PDF in sequential order. When TP1 is finished, add the TP1 Plan.pdf to the TP1 folder.

Establish and document a list of new automated tests (e.g., like those in the PasswordEvaluationTestingAutomation Class) that must be produced and used.  Ensure that all of the input validation work identified in Task 3 is addressed.  Build on the test cases in the code provided to you.  Ensure that each test case, old and new, specifies what is being tested and how it will be tested.  Store this list of test cases in a document named "TP1 Test Cases.pdf". Add the PDF to the TP1 folder. Implement the tests and address issues these tests uncover.

Implement, providing high-quality documentation, both internal comments and external documentation (e.g., architecture, detailed design), test, and enhance the provided code and your plan as required to satisfy the requirements. UML Diagrams should be produced and created using Astah.  These external documents shall be placed into a folder named "TP1 Design Documents", and that folder placed into the TP1 folder created in Task 1.   (For TP1 deliverables, we will accept the use of other tools, but the submission must look professional.  For TP2 and TP3, you must use Astah!)  Documentation issues that required changes to be made, and any issues that required unplanned effort, must be included in the TP1 Plan.pdf document created in Task 4.

Produce three screencasts.  Ensure the grader can view what you produce by using a standard format (e.g., .mp4, .mov).  Also, be aware of the size limitation on the total size of your submission.  Use Zoom, record to the cloud, and do not record faces in orer to keep all screencasts below 100MB.  (Below 50 MB is even better!)

1) The first demonstrates that the new functional requirements have been implemented and validated using JUnit, other tools, or manual testing.  Name this screencast "TP1 Functional Requirements Validated" with an appropriate filename extension (e.g., .mp4, .mov).

2) The second explains how the requirements align with the architecture (which may need to be updated from what was provided), the potentially updated detailed design, the code, the tests, and the stakeholders’ needs as described in the product vision.  Provide enough detail so it is easy for the grader to recognize that each speaker really does understand the code.  Each team member must present a portion of these screencasts, and the length of their contribution must be roughly the same. Name this screencast "TP1 Flow Explained" with an appropriate filename extension (e.g., .mp4, .mov).

3) The third "screencast" is actually a folder of a set of short recordings that summarize each of the team's standup meetings.  This whole folder should be less that 100MB in size!  Every team member must provide at least one two-minute to three-minute recording summarizing one of the team’s Standup meetings.  Create a folder named "TP1 Scrum Standup Video Recordings".  Store each recording with a name in the form "TP1 Scrum Standup <date>" with an appropriate filename extension (e.g., .mp4, .mov).  Replace the "<date>" with the date of the Standup Meeting.

Add the first two screencasts and the screencast folder to the TP1 folder.

Compress the TP1 submission folder into a ZIP archive and submit it.

Submit this ZIP archive before the deadline so there is enough time for the upload of the submission to finish, for Canvas to process the submission, and for Canvas to add it to its data repository before the deadline. Just starting the upload before the deadline is not adequate!  It is the responsibility of every member of the team to ensure the submission satisfies the requirements, that the submission has been successful, and that it is visible in Canvas before the deadline.  This is not possible to do if the upload starts a few minutes before the deadline.

Perform and Submit Both Parts of the Peer Evaluation for this assignment before the deadline.
You will also receive a grade deduction if you fail to fill out the Peer Review Google Form and properly submit it before the deadline.  You will also receive a deduction if you do not submit a PDF of the acknowledgement of a successful submission to Google Canvas of the evaluation to the Canvas, or if you fail to submit it before the deadline!

TP1 Submission: If the team is still working on this submission on the day that it is due, submit what you have at noon, and then continue to work to finish the assignment.  The last successful submission is the one that will be graded.

Do not contact the grader, TA, or the instructor about a late submission.  Team projects will not be graded if they are late, even by just one second! Accidents, illnesses, family emergencies, and computer, internet, or power failure are not valid excuses for failing to submit before the deadline.

Do not email submissions, regardless of the reason, to the grader, TA, or the instructor.  All Individual and Team Project submissions must be made through Canvas.


Deliverables
A TP1 submission archive must contain the following items.

Task 1: The TP1 archive and its contents are named properly as described in Task 1. (5%)

Task 2: The "TP1-Team<n> Team Norms.pdf", created in Task 2, is in the TP1 Archive, and aligns with the requirements. (5%)

Task 3:  The "User Experience.pdf", created in Task 3, includes screenshots from a professional application and text that explains what the team likes and how it enhances the user experience. (5%)

Task 4: The "Input Validation.pdf", created in Task 3, is in the TP1 Archive, and it identifies all the text input fields in TP1. (5%)

Task 5.1: The "Sprint Backlog.pdf", created in Task 4, is in the TP1 Archive, and it contains all the User Stories for New Users and Admins that need to be implemented.  (Do not include those that were already implemented in the provided code.) (5%)

Task 5.2: The "TP1 Plan.pdf", created in Task 4, is in the TP1 Archive, and it aligns with the requirements. (5%)

Task 6: The "TP1 Test Cases.pdf", created in Task 5, consists of a series of tests like those in the PasswordEvaluationTestingAutomation Class.  The PDF must be in the TP1 Archive, and the test cases provided must align with the requirements. (5%)

Task 7: The "TP1 Design Documents" folder, created in Task 6, must contain the external design documents that align with the provided external documents. (5%)

Three Screencasts as described in Task 8: (60%)
The screencasts are named properly and use one of the required formats (MOV or MP4), and each must be less than 100 MB in size.  If the grader is unable to view the screencast, all deliverable aspects will be assessed as zero.  If the cause of failure to upload the TP1 Submission is due to one or more screencasts being larger than 100 MB, the entire TP1 project will be assessed as zero.

Task 8.1: The TP1 Functional Requirements Validated screencast demonstrates that the new functional requirements have been implemented and validated using JUnit, other tools, or manual testing. (20%)
Textual materials shown in the screencast must be readable, and the audio must be clear and understandable. (2%)
The screencast was presented by every member of the team, and the duration of each was roughly the same. (3%)
The screencast shows and explains each of the deliverables from the received epics, the user stories, the architecture, detailed design, code, and testing, and how each is consistent with the course examples.  This includes all the tests listed in the TP1 Plan.pdf. (10%)
The screencast shows the code (the quality, formatting, internal comments, and Javadoc comments), and it is consistent with the provided materials (5%)

Task 8.2: The TP1 Flow Explained screencast explains how the requirements align with the updated architecture, updated detailed design, the code, the tests, and the stakeholders’ needs as described in the product vision. (20%)
Textual materials shown in the screencast must be readable, and the audio must be clear and understandable. (2%)
The screencast was presented by every member of the team, and the duration of each was roughly the same. (3%)
The screencast explains each of the required deliverables and how each aligns with the provided examples. (E.g., How does the architecture flow from the requirements, the detailed requirements from the architecture, and the code from the detailed design?) (10%)
The screencast explains how the code satisfies each of the requirements and followed by a short demonstration of that satisfaction. (5%)

Task 8.3: The TP1 Scrum Standup Video Recordings Folder: Within the folder are at least four screencasts summaries of the actual standup meeting in .mp4 or .mov format.    Standup meetings must happen at least twice a week with at least 24 hours between them. The date and time stamp for the summary screencast must be within 24 hours of the Standup is summarizes. More than three weeks are available for TP1 after this assignment was posted. Use need all three of these weeks to address risks, deal with unknowns, make progress, and deal with other outside-of-class obligations. (20%)
At least 4 standups were summarized by the same number of very short screencasts.  If you have fewer than four (meaning your team worked on the assignment for less than 8 days) provide a document in the folder explaining why. (3%)
Each member of the team produced at least one screencast in the folder. (3%)
Each screencast must be at least two minutes and no longer than three minutes, and have a date and time stamp within 24 hours of the standup it covers.  There must be at least 24 hours between actual standup meetings. (3%)
The screencast shows a list of the user stories, produced in Task 4, are in one for three stages: 1) in the backlog, 2) in process, or 3) completed, and those in stage 2 or 3 have a team member, or members’ name(s) associated with it.   Do not show the speaker's face during this recording as it makes the resulting file too large. (3%)
Each screencast explains for each stage 2 item what was done, what is planned to be done, and what major issues are blocking progress. (8%)