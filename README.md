# My Personal Project - *Yu-Gi-Oh!* Trading Card Game Tracker

## Project Description

<p> My project is an application that can track the results of <em>Yu-Gi-Oh!</em> matches. <br>
In this application, the user will be able to add the result and details of a match to a list of matches. <br>
The user will also be able to view the list of matches and their results. The user will also be able to enter detailed information about their matches, including, but not limited to:</p>

- Date the match was played
- The type of deck the user played
- The type of deck the user's opponent played
- The name of the opponent
- Which games in the match were won/lost/drawn
- Whether or not the user won the die roll
- Notable moments and/or details about the match

With this data in mind, the user will be able to view and manipulate the data to show various statistics, such as:

- Win rate while playing a certain deck
- Win rate while playing against a certain deck
- Win rate against a particular opponent
- Win rate in Games 1, 2, and 3 of matches
- Win rate depending on winning or losing the die roll
- Percentage of matches drawn
- A combination of the above

This application will be used by any <em>Yu-Gi-Oh!</em> players who want to keep track of their match history and who are interested in their match statistics.
This project is of interest to me as I am an avid competitive <em>Yu-Gi-Oh!</em> player who would be personally interested in using such an application.

## User Stories

<p> As a user, I want to be able to add the results of my matches to a list. </p>
<p> As a user, I want to be able to remove the results of my matches from the list. </p>
<p> As a user, I want to be able to view the results of my matches. </p>
<p> As a user, I want to be able to view my overall match winrate. </p>
<p> As a user, I want to be able to view how often I win the die roll. </p>
<p> As a user, I want to be able to view details about my matches. </p>
<p> As a user, I want to be able to sort my match results by deck, opponent, die roll, etc. </p>
<p> As a user, I want to be able to see my win rate while playing a certain type of deck, playing against a certain type of deck, playing against a certain opponent, etc. </p>
<p> As a user, I want to see how winning or losing the die roll influences my win rate. </p>
<p> As a user, I want to be able to save all my matches into the application when I choose to. I want to have the option to save my matches or not. </p>
<p> As a user, I want to be able to reload my match log from where I left off previously, or start from scratch if I choose. </p>
<p> As a user, I want to be able to have the option to save my results when I quit the application. </p>

## Instructions for Grader
<p> You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking on "Log Match" and following the prompts.</p>
<p> You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking "View Match Results", then selecting as many or as few results by dragging the mouse or left-clicking while holding ctrl, then right-click to either delete the selected matches or view statistics for the selected matches.</p>
<p> You can locate my visual component by right-clicking a match/matches and clicking "Statistics".</p>
<p> You can save the state of my application by clicking "Save Match Results".</p>
<p> You can reload the state of my application by clicking "Load Match Results".</p>

## Phase 4: Task 2

Sample event log:

<p> Match result from 04/07/2024 added to match log.</p>
<p> Match result from 04/07/2024 added to match log.</p>
<p> Match results loaded.</p>
<p> Match result from 02/07/2024 added to match log.</p>
<p> Logged match results viewed.</p>
<p> Logged match results viewed.</p>
<p> Match result from 02/07/2024 removed from match log.</p>
<p> Match results saved.</p>

## Phase 4: Task 3

<p> Upon completing and reviewing my UML class diagram, I noticed that I have very few classes considering the amount of functionality my program has.
It is very likely that I am violating the single responsibility principle by having so many methods, especially in my MatchLog class. The MatchLog class likely does not have very high cohesion as well.
If I were to further work on this project, I would refactor this class to make subclasses in order to uphold the single responsibility principle, increase cohesion, and keep coupling low.
This would also make it easier to add new functionality in the future. It would make a lot of sense for MatchLog to solely focus on one thing,
i.e. just displaying the list of match results, and having the other functionality such as the statistics and visual component (barchart) in separate classes.</p>

<p> Another design element I would use as part of refactoring is to use the singleton pattern when generating unique match IDs for the console-based version of my application. 
Before, without singleton, I was running into issues such as match IDs being changed and reassigned. This would not happen if I used the singleton pattern instead.</p>

