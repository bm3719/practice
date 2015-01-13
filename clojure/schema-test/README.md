# schema-test

A test project creating a data hygiene system using
[Prismatic Schema](https://github.com/Prismatic/schema).

This consists of functions representing the overall data hygiene logic,
transform functions, and 2 maps.

The 2 maps are:
- Schema: This represents the validation conditions that the data map must meet
  to be considered validated and the transforms for each field.  The transforms
  are the (eventually user-configurable) map of functions that transform each
  of the data map's fields.  This gets the data into a state where the schema
  can be validated against it.
- Data: A data map represents one record from the input data.

## Usage

Look at dev/user.clj for code representing the proof of concept.

## TODO

Some things to improve this:
- Normalize the :fns key to be a function that takes a list of function
  symbols.  These will then always be called sequentially.  Include a way to
  specify parameters to this, so that the list can be stored in MongoDB.

## License

None.
