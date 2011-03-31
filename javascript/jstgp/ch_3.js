// Time-stamp: <2010-05-19 09:16:10 (bm3719)>
// Notes from JS:TGP
/*global document */

// Chapter 3: Objects

// Object literals

var stooge = {
    "first-name": "Jerome",
    "last-name": "Howard"
};
// Access with...
// stooge["first-name"]

var flight = {
    airline: "Oceanic",
    number: 815,
    departure: {
        IATA: "SYD",
        time: "2004-09-22 14:55",
        city: "Sydney"
    },
    arrival: {
        IATA: "LAX",
        time: "2004-09-23 10:42",
        city: "Los Angeles"
    }
};
// Access with...
// flight.number
// flight.arrival.IATA

// Accessing a non-existant property will return undefined.  A trick for this
// is to use || to specify a default value.
var status = flight.status || "unknown";

// Access a property of a non-existant property will throw a type error.  It's
// possible to check for the first level property first using &&.
var statusTime = (flight.status && flight.status.time) || "unknown";

// Updating objects: assigning values to properties updates them, assigning to
// non-existant properties adds that property.
stooge["first-name"] = "ssdfs";
stooge["middle-name"] = "sdkbcu";

// Note: Like Java/C#, objects are always passed by reference.

// Prototypes: Similar to inheritance concept.  Since JavaScript's built in
// method to select an object's prototype is sloppy, this book suggests
// defining this create method.
if (typeof Object.create !== 'function') {
    Object.create = function (o) {
        var F = function () {};
        F.prototype = o;
        return new F();
    };
}

// Usage example:
var another_stooge = Object.create(stooge);
// While this does inherit the values of the prototype, overwriting them
// doesn't change the prototype properties, nor does appending stuff to it
// append to the prototype.
another_stooge["first-name"] = "Kevin";
another_stooge.nickname = "Moe";
// However, updating the prototype does propogate its changes down.
stooge.profession = "actor";

// Reflection: Type check a property.

// typeof flight.number;  // returns 'number'.
// typeof flight.arrival; // returns 'object'.
// typeof flight.sdfg;    // returns 'undefined'.

// since a ton of properties are inherited from prototypes, sometimes one might
// only want to check the non-property chain properties.  do so using the
// hasownproperty method.
// flight.hasownproperty('airline');     // returns true;
// flight.hasownproperty('constructor'); // returns false;

// enumeration: `for in' can loop over all properties in an object, including
// functions and prototype properties.
function loopprops(obj) {
    var name;
    for (name in obj) {
        if (typeof obj[name] !== 'function') {
            document.writeln(name + ': ' + obj[name]);
        }
    }
}
// note that these properties will be enumerated unordered.  see book for a
// ordering example using an array.

// delete: removing a property from an object.
another_stooge.some_prop = 'sdfg';
delete another_stooge.some_prop;

// global abatement: global variables are stupid.  one way to mitigate this is
// to use a single global for an app.
var MYAPP = {};
MYAPP.stooge = {
    "first-name": "joe",
    "last-name": "howard"
};
MYAPP.flight = flight;
// Note that closures can be used to this same effect, seen in Chapter 4.
