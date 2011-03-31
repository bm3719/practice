// Time-stamp: <2010-06-26 23:13:21 (bm3719)>
// Notes from JS:TGP
/*global document */

// Chapter 6: Arrays

// The JavaScript array is different from the array provided in most languages.
// It is an object that attempts to mimic a lot of these properties.  They are
// as slow as any other object (with the exception of an integer property names
// trick), and they have their own literal format.  See chapter 8 for a list of
// built in array methods.

// Array Literals: A built-in notation for creating new array values, these are
// the pair of brackets surrounding zero or more values, separated by commas.
// The first value gets the property name 0, and so on.
var empty = [];
var numbers = [
    'zero', 'one', 'two', 'three', 'four',
    'five', 'six', 'seven', 'eight', 'nine'
];
// empty[1]; // undefined
// numbers[1] // 'one'
// empty.length; // 0
// numbers.length; // 10

// This object literal produces a similar result:
var numbers_object = {
    '0': 'zero',
    '1': 'one',
    '2': 'two',
    '3': 'three',
    '4': 'four',
    '5': 'five',
    '6': 'six',
    '7': 'seven',
    '8': 'eight',
    '9': 'nine'
};
// The difference is that `numbers' inherits from Array.prototype, whereas
// `numbers_object' inherits from Object.prototype, so `numbers' gets more
// useful methods alone with the `length' property.

// In most languages, objects in an array are required to be the same type.
// This is not so in JavaScript.
var misc = [
    'string', 98.6, true, false, null, undefined,
    ['nested', 'array'], {object: true}, NaN, Infinity
];

// Length: `length' is a built-in property on all arrays.  The difference with
// arrays in most languages is that out of bound errors are not possible, since
// adding to an index which doesn't exist will just fill in the length to fit
// the new value.

var myArray = [];
// myArray.length; // 0
myArray[10000] = true;
// myArray.length; // 10001

// The length can be set explicity.  Making it smaller than the number of
// values contained will truncate them.

// New elements can be added at the end by assigning them to an index equal to
// the current length.
myArray[myArray.length] = 'something';

// But, it's probably more convenient to use `push'.
myArray.push('new');

// Delete: An operator to remove elements from an array.
delete numbers[2];

// This leaves a hole in the array though.  What you really want to do is
// delete and shift the names.  You can do this with `splice'.  This takes an
// ordinal and the number of elements to remove.  Additional arguments will be
// inserted at that index.
var numbers = [
    'zero', 'one', 'two', 'three', 'four',
    'five', 'six', 'seven', 'eight', 'nine'
];
numbers.splice(2, 1); // ['zero, 'one', 'three', ...

// Enumeration: JavaScript provides a `for in' statement.  However, since it
// makes no guarantee about order and can interate over inherited properties,
// it's best to use the standard `for' loop.

// Confusion: When to use an array vs. an object?

// When the property names are small, sequential integers, use an array,
// otherwise use an object.

// JavaScript itself has problems with what type arrays are.  The `typeof' of
// an array returns `object'.  A workaround for this is to make an isArray
// function like this:
var is_array = function (value) {
    return value && typeof value === 'object' && value.constructor === Array;
};

// Unfortunately, even this fails if the array was constructed in a different
// window or frame.  To detect foreign arrays, do something like this:
var is_array = function (value) {
    return Object.prototype.toString.apply(value) === '[object Array]';
};

// See also: isArray in utils.js.

// Methods: JavaScript provides built-in methods for arrays.  These are stored
// in Array.prototype, which can be augmented.

// If we wanted to add a method to do computation on an array:
Array.method('reduce', function (f, value) {
    var i;
    for (i = 0; i < this.length; i += 1) {
        value = f(this[i], value);
    }
    return value;
});

// `reduce' takes a function and a starting value and for each element, calls
// the function on the element and the value.  If passing a function that adds
// two numbers, it computes the sum.  If passing a function that multiplies two
// values, it computes the product. 
var data = [4, 8, 15, 16, 23, 42];

// Define two functions.  One adds two numbers, the other multiplies.
var add = function (a, b) {
    return a + b;
};
var mult = function (a, b) {
    return a * b;
};

// Invoke the data's reduce method, passing in the add function.
var sum = data.reduce(add, 0); // sum is 108.

// Invoke reduce again, passing in the mult function.
var product = data.reduce(mult, 1); // product is 7418880.

// Because arrays are objects, we can add methods to individual arrays.

// Give the data array a total function.
data.total = function () {
    return this.reduce(add, 0);
};
var total = data.total();

// Since the `total' method's name is a string and not an integer, adding it to
// `data' doesn't increase its length.

// Do not use Object.create to create arrays.  This will create objects taht
// don't have the length property.

// Dimensions: JavaScript arrays are not initialized.  Create an array with []
// and it will be empty.  Access a missing element and you will get
// `undefined'.  JavaScript doesn't provide a way to initialize arrays, and
// some algorithms expect default values.  You can add this yourself with:
Array.dim = function (dimension, initial) {
    var a = [], i;
    for (i = 0; i < dimension; i += 1) {
        a[i] = initial;
    }
    return a;
};

// Make an array initialized with 10 zeros.
var myArray = Array.dim(10, 0);

// JavaScript doesn't provide multi-dimensional arrays, but like most
// languages, it can have arrays of arrays.
var matrix = [
    [0, 1, 2],
    [3, 4, 5],
    [6, 7, 8]
];
// matrix[2][1]; // 7

// To make a matrix, you will have to build it yourself.  Note: Do not use
// something like Array.dim(n, []), since this will assign all elements to the
// same array.
// for (i = 0; i < n, i += 1) {
//     my_array[i] = [];
// }

// This is how to initialize a matrix:
Array.matrix = function (m, n, initial) {
    var a, i, j, mat = [];
    for (i = 0; i < m; i += 1) {
        a = [];
        for (j = 0; j < n; j += 1) {
            a[j] = initial;
        }
        mat[i] = a;
    }
    return mat;
};

// Make a 4x4 matrix filled with zeros.
var myMatrix = Array.matrix(4, 4, 0);

// Method to make an identity matrix (a square matrix with 1s in the diagonal).
Array.identity = function (n) {
    var i, mat = Array.matrix(n, n, 0);
    for (i = 0; i < n; i += 1) {
        mat[i][i] = 1;
    }
    return mat;
};

myMatrix = Array.identity(4);
