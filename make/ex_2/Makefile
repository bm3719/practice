#-*- mode: makefile; tab-width: 4; -*-
# Example from Managing Projects with GNU Make, chapter 2.

# $^ = The filenames of all the prereqs, space-delimited.
# $@ = The filename representing the target.
count_words: count_words.o counter.o lexer.o -lfl
	gcc $^ -o $@

# $< = the first prereq.
count_words.o: count_words.c include/counter.h
	gcc -c $<

counter.o: counter.c include/counter.h include/lexer.h
	gcc -c $<

lexer.o: lexer.c include/lexer.h
	gcc -c $<

lexer.c: lexer.l
	flex -t $< > $@

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
