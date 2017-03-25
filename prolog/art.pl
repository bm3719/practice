% -*- mode: prolog -*-

% This is my notes file from reading The Art of Prolog.  This code is only
% tested with SWI-Prolog.

% Some prolog-mode notes:
% - Use M-x run-prolog to start a Prolog REPL.
% - Use C-c C-b to send the buffer to the REPL.

% General SWI-Prolog usage tips:
% - When there are multiple answers to a question (i.e. `member(X, [1,2,3]).'),
%   the system will present one of the answers.  If one wants to reject it, hit
%   ; to go to the next one.
% - Use `nodebug.' to exit debug mode.
% - The trace command can be used to see execution internals.
% - `help(help).' will pull up the manual, which has an X11-enabled browser.
% - `halt.' or C-d exits SWI-Prolog.

% Chapter 1: Basic Constructs

% Facts: Statements about a relationship that exists between objects.  Also
% called a predicate.  The symbols representing things are atoms.
father(terach, abraham).
father(abraham, isaac).
mother(sarah, isaac).
male(abraham).
male(terach).
male(isaac).
female(sarah).

% I think simple atoms can also be facts.
sunny.

% Predicates and atoms should begin with lower case letters.

% A finite set of facts consitutes a program.

% Queries: Retrieves information about whether a relation holds.  Note that
% unlike in the book, the question mark syntax doesn't seem to exist.  To query
% whether abraham is the father of isaac, one would just enter `father(abraham,
% isaac).' at the ?- prompt.  In these notes, I'll use syntax like this:

% ?- father(abraham, isaac).

% To denote a query being executed at the prompt.

% Identity: From P deduce P.  A query is a logical consquence of an identical
% fact.

% Variables: Represent an unspecified but single entity.

% ?- father(abraham, X).

% Term: The data structure of logic programs.  Constants and variables are
% terms.  Compound terms are a functor and a sequence of one or more arguments
% (which define its arity).

% A compound term has the form:
% f(t1, t2, ..., tn)
% Composed of functor of name f, arity n, and ti arguments.

% Examples: s(0), name(john, doe), tree(tree(nil, 3, nil), 5, R).

% Ground: Queries, goals, and terms where variables do not occur.  Nonground is
% where they do.

% Substitution: Set of pairs of variables and terms that can fill them.

% Instance: A substitution for a term, e.g., goal mother(sarah, isaac) is an
% instance of mother(X, Y) with substitution {X = sarah, Y = isaac}.

% Existential Queries: The query father(abraham, X) reads "Does there exist and
% X such that abraham is the father of X?".

% Solution: An instance of a nonground query.  Queries can have multiple
% solutions.

% Universal facts: Using variables to summarize many facts.

likes(abraham, pomegranates).
likes(sara, pomegranates).

% likes(X, pomegranates) universally quantifies the above.

% Instantiation: An instance of a universally quantified statement.

% A universal fact example: Everybody likes himself.
likes(X, X).

% Common instance: When an instance is common to multiple terms.
plus(0, 3, 3).

% The above is a common instance of:
% ?- plus(0, 3, Y).
% ?- plus(0, X, X).

% Answering a query using a fact is done by finding the common instance of
% both.  If none exist, the answer is false.

% Conjunctive queries: Conjunction of goals posed as a query.  Here, comma
% denotes a logical AND.  E.g., this returns {X = abraham, Y = isaac}.

% ?- father(terach, X), father(X, Y).

% Rules: Define relationships in terms of new relationships.  Note that the
% book uses ← to denote logical implication, whereas SWI-Prolog uses :-.

son(X, Y) :- father(Y, X), male(X).

% The above will cause son(abraham, isaac) to return true.

grandfather(X, Y) :- father(X, Z), father(Z, Y).

% Defining a grandparent is more conveniently done via an auxilliary
% relationship, rather than enumerating all the combinations of father and
% mother relationships.
parent(X, Y) :- father(X, Y).
parent(X, Y) :- mother(X, Y).

grandparent(X, Y) :- parent(X, Z), parent(Z, Y).

% Procedure: A collection of rules with the same predicate in the head.  parent
% here is an example.


% Chapter 2

% Instead of doing exercises, I'm going to use ideas in chapters to write
% programs.

killed(me, slug).
killed(me, warthog).
killed(me, mole-rat).
killed(larry, harry).

human(me).
human(gary).
human(larry).
human(harry).

ugly(slug).
ugly(warthog).
ugly(mole-rat).
cute(cat).

murderer(Suspect) :- killed(Suspect, Victim), human(Victim).

%% killed(me, gary).

%% killed(me, cat).

%% murderer(Suspect) :- killed(Suspect, Victim),
%%                      (human(Victim) ; cute(Victim)).

%% killed(me, me).

%% murderer(Suspect) :- killed(Suspect, Victim),
%%                      (human(Victim) ; cute(Victim)),
%%                      Suspect \= Victim.


% Structured data

trial(murderer(larry), judge(brice, moore), monday).

presides(FirstName, Day) :- trial(murderer(_), judge(FirstName, _), Day).


% Chapter 3

%% factorial(0, s(0)).
%% factorial(s(N), F) :- factorial(N, F1), times(s(N), F1, F).
%% times(X, Y, Z).
