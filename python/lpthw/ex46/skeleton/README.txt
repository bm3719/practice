This is a skeleton project for starting new Python projects.  To use it:

- Ensure python-pip, python-distribute, python-nose, and python-virtualenv are
  installed.  The easiest way to do this is to install pip manually (or from an
  OS package manager), then use pip to install the rest (e.g. `pip install
  nose').

- Recursively copy this to wherever you want to have your project

- Rename the `NAME' directory to the project name.

- Edit setup.py.

- Rename tests/NAME_tests.py.  Edit it to use my project name.

- If all this is setup correctly, you should be able to run `nosetests' from
  the project directory.

- Create tests in the tests/ directory.

- Add source to the project directory.

- Put any scripts to run the project a bin/ directory.  Add that to setup.py so
  it gets installed (if you want that).  It should be possible to use pip to
  install/uninstall the project, if set up correctly.
