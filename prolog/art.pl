% -*- mode: prolog-mode -*-

% This is my notes file from reading The Art of Prolog.  This code is only
% tested with SWI-Prolog.

% Some prolog-mode notes: Use M-x run-prolog to start a Prolog REPL.  Use C-c
% C-b to consult buffer in REPL.

% General SWI-Prolog usage tips:
% - When there are multiple answers to a question (i.e. `member(X, [1,2,3]).'),
%   the system will present one of the answers.  If one wants to reject it, hit
%   ; to go to the next one.
% - Use `nodebug.' to exit debug mode.
% - The trace command can be used to see execution internals.
% - `help(help).' will pull up the manual, which has an X11-enabled browser.

% Chapter 1: Basic Constructs

% Facts: Statements about a relationship that exists between objects.  Also
% called a predicate.  The symbols representing things are atoms.
father(abraham, isaac).
mother(sarah, isaac).
male(abraham).
male(isaac).
female(sarah).

% I think simple atoms can also be facts.
sunny.

% Predicates and atoms should begin with lower case letters.

% A finite set of facts consitutes a program.

% Queries: Retrieves information about whether a relation holds.  Note that
% unlike in the book, the question mark syntax doesn't seem to exist.  To query
% whether abraham is the father of isaac, one would just do `father(abraham,
% isaac).'.
