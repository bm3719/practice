> module Ch13 where -- Building projects

See other files in . and subdirs for chapter project.  Only general notes
are here.

Workflow:
- Create directory
- cd to directory and run `cabal init'.  I think `cabal init -i', followed
  by a `n' will give a newer version of the interactive creation the book
  describes as default.
- Add .gitignore for Haskell from: https://github.com/github/gitignore
- Ensure "synopsis" (short) and "description" (any length) are populated in
  project-name.cabal.
- Set "hs-source-dirs" to "src", which is the location of the program
  modules.
- Ensure "main-is" is set to "Main.hs", where the `main' function is.
- Under "executable", add: ghc-options: -Wall -fwarn-tabs
- Add any additional libs used to "build-depends".

Running:

- Create project sandbox with: cabal v1-sandbox init
- Install deps into sandbox: cabal install --only-dependencies
- Build with: cabal build
- Alternatively: cabal repl

Notes:
- Figure out non-legacy versions of the above later.
- Sandbox builds seem to create a nested directory mess.  Use `tree' to
  visualize and find output executable.
