#-*- mode: makefile; tab-width: 4; -*-
# Example from Managing Projects with GNU Make, chapter 1.

count_words: count_words.o lexer.o -lfl
	gcc count_words.o lexer.o -lfl -ocount_words
	size count_words

count_words.o: count_words.c
	gcc -c count_words.c

# Example of automatic variable.  $< = the first prereq.
lexer.o: lexer.c
	gcc -c $<

lexer.c: lexer.l
	flex -t lexer.l > lexer.c

# GNU Make includes a .PHONY target to indicate targets do not represent files.
.PHONY: clean
clean:
	@printf "Cleaning...\n"
	rm -f *.o lexer.c

.PHONY: all
all: count_words

# TAGS is a standard phony target, typically used for generating tag info for
# the project.
.PHONY: TAGS
TAGS:
	gtags -v
