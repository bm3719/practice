// Time-stamp: <2010-06-26 23:07:56 (bm3719)>
// A collection of utility functions for JavaScript, mostly to address various
// bad language design decisions.  Most of these ideas are from the book
// JavaScript: The Good Parts.

/*jslist plusplus: false */
/*global */

// JavaScript's built in method to select an object's prototype is sloppy, this
// create method will override it.
// Usage: var another_stooge = Object.create(stooge);
if (typeof Object.create !== 'function') {
    Object.create = function (o) {
        var F = function () {};
        F.prototype = o;
        return new F();
    };
}

// This adds support for super methods to Objects.  Useful in the functional
// pattern where full data-hiding is possible.
Object.method('superior', function (name) {
    var that = this,
    method = that[name];
    return function () {
        return method.apply(that, arguments);
    };
});

// This augments the Function.prototype with the `method' method, allowing us
// to no longer have to type the name of the prototype property.  This only
// adds the method if it doesn't exist.  See following augments for example of
// use.
Function.prototype.method = function (name, func) {
    if (!this.prototype[name]) {
        this.prototype[name] = func;
        return this;
    }
};

// As JavaScript has no integer type, this allows us to extract just the
// integer portion of a number -- a rather common task.
Number.method('integer', function () {
    return Math[this < 0 ? 'ceil' : 'floor'](this);
});

// Add a trim method.
String.method('trim', function () {
    return this.replace(/^\s+|\s+$/g. '');
});

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

// Check if parameter really is an array.  Addresses a bunch of obscure
// problems that cause this to not work normally.  Only potential problem that
// still exists is if propertyIsEnumerable is overridden.
function isArray(arr) {
    if (arr && typeof arr === 'object' &&
        typeof arr.length === 'number' &&
        !(arr.propertyIsEnumerable('length'))) {
        return true;
    }
    else {
        return false;
    }
}

// JavaScript doesn't provide a way to initialize arrays, and some algorithms
// expect default values.  You can add this yourself with:
Array.dim = function (dimension, initial) {
    var a = [], i;
    for (i = 0; i < dimension; i += 1) {
        a[i] = initial;
    }
    return a;
};

// Method to initialize a matrix:
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

// Method to make an identity matrix (a square matrix with 1s in the diagonal).
Array.identity = function (n) {
    var i, mat = Array.matrix(n, n, 0);
    for (i = 0; i < n; i += 1) {
        mat[i][i] = 1;
    }
    return mat;
};
