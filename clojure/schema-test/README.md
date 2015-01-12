# schema-test

A test project to investigate Prismatic Schema.

3 maps are relevant here.
- Schema: This represents the validation conditions that the data map must meet
  to be considered validated.
- Transforms: The (eventually user-configurable) map of functions that
  transform each of the data map's fields.  This gets the data into a state
  where the schema can be validated against it.
- Data: A data map represents one record from the input data.

## Usage

Look at dev/user.clj for code representing the proof of concept.

## License

None.
