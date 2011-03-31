// Time-stamp: <2010-06-01 23:45:10 (bm3719)>
// Notes from JS:TGP
/*global document */

// Chapter 4: Functions

// Function objects: Functions are objects, linked to Function.prototype, along
// with other internal linkage, including the function definition being the
// value passed to the constructor.

// Function literal: First class functions.

// Create a variable called add and store an anonymous function in it that adds
// two numbers.
var add = function (a, b) {
    return a + b;
};

// Closures: Function objects created by function literals contain links to the
// context they were nested within.
function inner(f) {
    return f(12);
}
function outer() {
    var c = 10,
    add_10 = function (a) {
        return a + c;
    };
    return inner(add_10);
}

// Invocation: When invoked, functions recieve two parameters: this and the
// function's arguments.  There's 4 invocation patterns in JavaScript: method,
// function, constructor, and apply.  These differ in how the `this' parameter
// is initialized.

// Method invocation pattern: A function that is a property of an object is
// considered a method.
var myObject = {
    value: 0,
    increment: function (inc) {
        this.value += typeof inc === 'number' ? inc : 1;
    }
};

// Function invocation pattern: Normal external functions where `this' is bound
// to the global object (instead of the outer function, which would've made
// more sense).  This means that this doesn't see the proper properties like
// here, where myObject.value will not be doubled.
myObject.wrongDouble = function () {
    var helper = function () {
        this.value = add(this.value, this.value);
    };
    helper();
};

// The rather ugly work-around to this is to create a variable to bind this to
// in the function's scope.  I'm thinking that just passing this as a parameter
// might make more sense most of the time.
myObject.double = function () {
    var that = this,  // lame
    helper = function () {
        that.value = add(that.value, that.value);
    };
    helper();
};

// Constructor invocation pattern: Prototypal inheritance.  When a function is
// called with `new', a new object is created with a hidden link to the value
// of the function's prototype member, and this is then bound to the new
// object.

// Create a constructor function call Quo.  It makes an object with a status
// property.
var Quo = function (string) {
    this.status = string;
};
// Give all Quo instances a public method called get_status.
Quo.prototype.get_status = function () {
    return this.status;
};
// Make an instance of Quo.
var myQuo = new Quo("confused");  // myQuo.status = 'confused'.

// Function objects that need to be called with `new' start with a capital
// letter by convention.

// Note that Chapter 5 details a better alternative to constructor invocation,
// meaning this generally isn't necessary to use.

// Apply invocation pattern: Like objects, functions can have methods.  

// The apply method allows construction of an array of arguments to use to
// invoke a function.  The first parameter is what is bound to `this' and the
// second is an array of parameters.

var array = [3, 4];
var sum = add.apply(null, array);

// There's another way to use this with objects, but it doesn't seem that
// useful to me.

// Arguments: An arguments array is available to functions which allows one to
// write functions that don't specify the number of arguments it takes.  Ch. 6
// has a better approach to this problem, so don't use this if it can be
// helped.

var sum = function () {
    var i, sum = 0;
    for (i = 0; i < arguments.length; i += i) {
        sum += arguments[i];
    }
    return sum;
};

//document.writeln(sum(4, 8, 15, 16, 23, 42)); // 108

// Return: Standard return behavior.  Just note that return always returns
// something, if nothing's specified, undefined is returned.

// Exceptions: Standard behavior.  A good way to add invariants to code.

var add = function (a, b) {
    if (typeof a !== 'number' || typeof b !== 'number') {
        throw {
            name : 'TypeError',
            message: 'add needs numbers'
        };
    }
    return a + b;
};
var try_it = function () {
    try {
        add("seven");
    } catch (e) {
        document.writeln(e.name + ': ' + e.message);
    }
};
try_it();

// catch blocks always catch everything, not certain exception types.  If
// various types of exceptions need to be handled, this will have to be done
// inside the single catch block.

// Augmenting Types: Adding a method to Object.prototype or Function.prototype
// will provide that method to all such types.  This can also be done for
// arrays, strings, numbers, regular expressions, and booleans.

// This augments the Function.prototype with the `method' method, allowing us
// to no longer have to type the name of the prototype property. See Number
// augment for example of use.
Function.prototype.method = function (name, func) {
    this.prototype[name] = func;
    return this;
};

// As JavaScript has no integer type, this allows us to extract just the
// integer portion of a number -- a rather common task.
Number.method('integer', function () {
    return Math[this < 0 ? 'ceil' : 'floor'](this);
});

//document.writeln(-10 / 3).integer()); // =3

// Add a trim method.
String.method('trim', function () {
    return this.replace(/^\s+|\s+$/g, '');
});

document.writeln('"' + '   neat   '.trim() + '"');

// This is a good way to extend the JavaScript language.  With prototype
// inheritance, all objects affected are updated, even if declared prior to
// augmenting.

// A good practice is to only augment a method if it doesn't already exist.
Function.prototype.method = function (name, func) {
    if (!this.prototype[name]) {
        this.prototype[name] = func;
        return this;
    }
};

// Recursion: Same as other languages.  Only thing to keep in mind is that
// recursion in JavaScript is handy for walking the DOM.

// This visits every node in the tree in HTML source order, starting from a
// given node, invoking a function and passing it along to child nodes.
var walk_the_DOM = function walk(node, func) {
    func(node);
    node = node.firstChild;
    while (node) {
        walk(node, func);
        node = node.nextSibling;
    }
};

// This takes an attribute name string and an optional matching value, and
// calls walk_the_DOM, passing it a function that looks for an attribute name
// in the node.  The matching nodes are returned.
var getElementsByAttribute = function (att, value) {
    var results = [];
    walk_the_DOM(document.body, function (node) {
        var actual = node.nodeType === 1 && node.getAttribute(att);
        if (typeof actual === 'string' &&
            (actual === value || typeof value !== 'string')) {
            results.push(node);
        }
    });
    return results;
};

// JavaScript does not provide tail recursion.

// Scope: Just remember that JavaScript has function scope, but not block
// scope.

// Closure: Functions have access to items in the scope they're defined in
// (except for `this' and arguments).  getElementsByAttribute is an example of
// this, where it has access to the the results array for use within
// walk_the_DOM, even though results isn't declared within it.

// This assigns the results of invoking this anonymous function to myObject.
// This is an object containing two methods which continue to have access to
// the value variable.  Note the () on the last line.
var myObject = function () {
    var value = 0;
    return {
        increment: function (inc) {
            value += typeof inc === 'number' ? inc : 1;
        },
        getValue: function () {
            return value;
        }
    };
}();

// The Quo constructor (see above) just created an object with a status
// property and a get_status method (which was useless since status was
// directly accessible anyway).  This changes status to be private.
var quo = function (status) {
    return {
        get_status: function () {
            return status;
        }
    };
};

// Make an instance of quo.  No `new' prefix is needed here.
var myQuo = quo("amazed");
document.writeln(myQuo.get_status());

// Callbacks: This is just the use of functions as first class objects, passing
// them as parameters to other functions to handle asynchronous events.

// Module: A function or object that combines functions and closures,
// presenting an interface while hiding its state and implementation.
// Functions produce modules, eliminating the need for global variables.

// This adds a deentityify method to String objects, which looks for HTML
// elements in a string and replaces them with their equivalents.
String.method('deentityify', function () {
    // The entity table.  It maps entity names to characters.
    var entity = {
        quot: '"',
        lt: '<',
        gt: '>'
    };
    // Return the deentityify method.
    return function () {
        // This is the deentityify method.  It calls the string replace method,
        // looking for substrings that start with '&' and end with ';'.  If the
        // characters in between are in the entity table, then replace the
        // entity with the character from the table.  It uses a regexp.
        return this.replace(/&([^&;]+);/g,
                            function (a, b) {
                                var r = entity[b];
                                return typeof r === 'string' ? r : a;
                            });
    };
}());

//document.writeln('&lt;&quot;&gt;'.deentityify());  // <">

// The module pattern can also be used to secure data.  And example here is
// given for an object that creates unique serial numbers.

// Cascade: For routines that set a value, instead of returning nothing, these
// can return `this' instead, which allows one to set up a cascade.
// getElement('myBoxDiv').
//     move(350, 150).
//     widget(100).
//     on('mousemose', 'drag').
//     on('mouseup', 'stopdrag);

// Curry: Allows creation of a new function by combining a function and an
// argument.  Requires adding a curry method.
Function.method('curry', function () {
    var slice = Array.prototype.slice,
    args = slice.apply(arguments),
    that = this;
    return function () {
        return that.apply(null, args.concat(slice.apply(arguments)));
    };
});

// var add1 = add.curry(1);
// document.writln(add1(6));

// Memoization: Remembering the results of previous operations, eliminating
// unneccessary work.
var fibonacci = (function () {
    var memo = [0, 1],
    fib = function (n) {
        var result = memo[n];
        if (typeof result !== 'number') {
            result = fib(n - 1) + fib(n - 2);
            memo[n] = result;
        }
        return result;
    };
    return fib;
}());

// This can be genericized by this `memoizer' function.
var memoizer = function (memo, formula) {
    var recur = function (n) {
        var result = memo[n];
        if (typeof result !== 'number') {
            result = formula(recur, n);
            memo[n] = result;
        }
        return result;
    };
    return recur;
};

// Fib example rewritten to use memoizer.
var fibonacci = memoizer([0, 1], function (recur, n) {
    return recur(n - 1) + recur(n - 2);
});

// Factorial using memoizer.
var factorial = memoizer([1, 1], function ( recur, n) {
    return n + recur(n - 1);
});





