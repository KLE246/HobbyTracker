# Hobby Tracker

## Document milestones and progress towards a goal of mastery

This app aims to provide a way to track one's progress in their hobbies and skills 
such as music, sports, and anything anyone could come up with. Potential information that
could be recorded include:

- number of hours spent so far practicing skill
- progression on a given date
- milestones and when they happened

then to display:

- a log of all dates that progression has been made
- how often milestones have been set
- current total hours of all skills

Anyone with skills they want to start working on can use the **Milestone Tracker**
to track their own progress as well as show to others how fast <sup><sub> or slow </sub></sup>
their advancement has been.

As someone with many dropped and ongoing hobbies, a tracker like this allows easy
re-entry into old ones and further motivation for current skills. Instead of a messy
notes app on my phone, an easy to use organizational program will make entries and 
review much  more appealing.

## User Stories

- As a user, I want to be able to add a hobby to a list of hobbies
- As a user, I want to be able to add hours of progress to a list of times when I've had progression
- As a user, I want to be able to add a milestone to a hobby and store it with the date and total hours in the hobby
- As a user, I want to be able to see a log of milestones in a hobby
- As a user, I want to be able to see a log of when I added hours to my progression

- As a user, I want to be able to save my hobby list and all information contained in each hobby
- As a user, I want to be able to reopen my saved file to continually update my hobby information

## Phase 4: Task 2

Option
- Test and design a class in your model package that is robust.  You must have at least one method that throws a 
checked exception.  You must have one test for the case where the exception is expected and another where the exception 
is not expected.

Originally addTime() in Hobby had REQUIRES: a positive time

To make Hobby robust, addTime() was changed to throw an exception when negative numbers were inputted for the time. The
exception gets caught in the addTime() method in HobbyListGUI and updates the message within the app to notify the user.
## Phase 4: Task 3
Refactoring done on HobbyListGUI
- A dialog box class could be made to make new pop up frames whenever a new one is instantiated. 
- 2 panels in the main frame can be their own class
- HobbyList panel would handle the revalidation functionality on update
- The middle panel would be instantiated and removed on list selection as its own object instead of a panel in the frame
- The left button panel could stay in the main class as it is just a set of buttons that aren't updated

