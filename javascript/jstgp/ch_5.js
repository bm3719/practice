// Time-stamp: <2010-06-26 21:30:43 (bm3719)>
// Notes from JS:TGP
/*global document */

// Chapter 5: Inheritance

// Pseudoclassical: JavaScript's prototype inheritance looks superficially
// classical.

// Upon creation of a function object, the Function constructor runs something
// like this:
// this.prototype = { constructor: this };

// Included from ch_4.js as a helper.
Function.prototype.method = function (name, func) {
    this.prototype[name] = func;
    return this;
};

// Here's an example implementation of how the `new' keyword actually works.
// Note that this demonstrates it as a method, not as a keyword.
Function.method('new', function () {
    // Create a new object that inherits from the constructor's prototype.
    var that = Object.create(this.prototype),
    // Invoke the constructor, binding `this' to the new object.
    other = this.apply(that, arguments);

    // If its return value isn't an object, substitute the new object.
    return (typeof other === 'object' && other) || that;
});

// The following implements a pseudoclassical pattern that mimcs standard OOP
// (at least in some respects).

// Define a constructor and augment its prototype.
var Mammal = function (name) {
    this.name = name;
};
Mammal.prototype.get_name = function () {
    return this.name;
};
Mammal.prototype.says = function () {
    return this.saying || '';
};

// Create an instance of the above pseudoclass.
var myMammal = new Mammal('Herb the Mammal');
var name = myMammal.get_name();  // 'Herb the Mammal';

// Another pseudoclass that inherits from Mammal by defining its constructor
// function and replacing its prototype with an instance of Mammal.
var Cat = function (name)  {
    this.name = name;
    this.saying = 'meow';
};
// Replace Cat prototype with a new instance of Mammal.
Cat.prototype = new Mammal();
// Augment the new prototype with `purr' and `get_name' methods.
Cat.prototype.purr = function (n) {
    var i, s = '';
    for (i = 0; i < n; i += 1) {
        if (s) {
            s += '-';
        }
        s += 'r';
    }
    return s;
};
Cat.prototype.get_name = function () {
    return this.says() + ' ' + this.name + ' ' + this.says();
};
// Use the above.
var myCat = new Cat('Henrietta');
var says = myCat.says();     // 'meow'
var purr = myCat.purr(5);    // 'r-r-r-r-r'
var name = myCat.get_name(); // 'meow Henrietta meow'

// To clean up this pseudoclassical pattern some, we can add an `inherits'
// method.
Function.method('inherits', function (Parent) {
    this.prototype = new Parent();
    return this;
});

// Combine this with cascade and we can do all the above in one statement.
var Cat = function (name) {
    this.name = name;
    this.saying = 'meow';
}.
    inherits(Mammal).
    method('purr', function (n) {
        var i, s = '';
        for (i = 0; i < n; i += 1) {
            if (s) {
                s += '-';
            }
            s += 'r';
        }
        return s;
    }).
    method('get_name', function () {
        return this.says() + ' ' + this.name + ' ' + this.says();
    });

// This approach still has serious problems.  All properties are public,
// there's no access to parent methods, and forgetting to use `new' will result
// in global variables and possibly clobbering names when we think we're in
// non-global scope.

// The only cure to this is to use the convention of capital letters for
// constructor functions that require `new', but even better is to not use
// `new' at all.

// Object Specifiers: When a constructor needs a ton of arguments, it's usually
// smarter to just use an object specifier.  This allows named parameters to be
// passed in a single object, in any order.

// Instead of...
// var myObject = maker(f, l, m, c, s);
// use an object specifier...
// var myObject = maker({
//     first: f,
//     last: l,
//     middle: m,
//     state: s,
//     city: c
// });

// This can also be used in the case of passing JSON objects to constructors.

// Prototypal: Simpler than OOP inheritance, this is simply just making an
// object that you intend to use and making other versions of it that also are
// used.  It basically just does away with the concept of defining you object
// structure separately.

var myMammal = {
    name: 'Herb the Mammal',
    get_name: function () {
        return this.name;
    },
    says: function () {
        return this.saying || '';
    }
};

// Now that we have a useful object, we can make as many more as we want with
// Object.create (see chapter 3), customizing them as desired.
var myCat = Object.create(myMammal);
myCat.name = 'Herrietta';
myCat.saying = 'meow';
myCat.purr = function (n) {
    var i, s = '';
    for (i = 0; i < n; i += 1) {
        if (s) {
            s += '-';
        }
        s += 'r';
    }
    return s;
};
myCat.get_name = function () {
    return this.says() + ' ' + this.name + ' ' + this.says();
};

// This type of inheritence is called "differential inheritence", which is the
// specification of the differences from the object upon which one is based.

// This applies to datastructures as well, as in this example of a TeX parser
// that applies a block function when a new left brace is encountered to model
// the new scope (yet preserve the effects of the containing scope).
// var block = function () {
//     // Remember the current scope.  Make a new scope that includes everything
//     // from the current one.
//     var oldScope = scope;
//     scope = Object.create(scope);

//     // Advance past the left curly brace.
//     advance('{');

//     //Parse using the new scope.
//     parse(scope);

//     // Advance past the right curly brace and discard the new scope, restoring
//     // the old one.
//     advance('}');
//     scope = oldScope;
// };

// Functional: An application of the module pattern that grants privacy.

// First we make a function that creates objects.  This doesn't require `new',
// so the name starts with a lower case letter.  This function does four steps:
// 1) It creates a new object in one of these manners: makes an object literal,
// calls a constructor function with `new', uses Object.create, or call any
// function that returns and object.
// 2) Optionally defines private variables and methods.
// 3) Augments that new object with methods which have priviledged access to
// stuff in step 2.
// 4) Returns the new object.

// Functional template:
// var constructor = function (spec, my) {
//     var that; // and any other private vars.
//     my = my || {};
//     // Add shared variables and functions to my.
//     that = // Some new object.
//     // Add priviledged methods to that.
//     return that;
// };
// The spec object contains anything needed to create an instance.  Its
// contents should be copied to private variables or transformed by other
// functions.  spec can also just be a single value if needed.  The `my' object
// isa container of secrets that are shared by the constructors in the
// inheritance chain.  It is optional and created if none is passed.

// After declaring private instance variables and adding shared secrets to
// `my', make a new object and assign it to `that'.  Then we add the methods to
// `that' which make up its public interface.  This should be done in a two
// step process so other methods don't have to call `that.something'
// explicitly.

// var methodical = function () {
//     // ...
// };
// that.methodical = methodical;

// Finally, return `that'.

// Here's this process used on the mammal example.  This doesn't need `my', so
// it's excluded.  The `name' and `saying' properties are completely private
// and only accessible through public methods.
var mammal = function (spec) {
    var that = {};
    that.get_name = function () {
        return spec.name;
    };
    that.says = function () {
        return spec.saying || '';
    };
    return that;
};

var myMammal = mammal({name: 'Herb'});

// In the pseudoclassical pattern, the Cat constructor had to duplicate work
// that was done in Mammal's constructor.  Not so here, since cat will call
// mammal's constructor, meaning you only have to specify the differences.
var cat = function (spec) {
    spec.saying = spec.saying || 'meow';
    var that = mammal(spec);
    that.purr = function (n) {
        var i, s = '';
        for (i = 0; i < n; i += 1) {
            if (s) {
                s += '-';
            }
            s += 'r';
        }
        return s;
    };
    that.get_name = function () {
        return that.says() + ' ' + spec.name + ' ' + that.says();
    };
    return that;
};

var myCat = cat({name: 'Henrietta'});

// The functional pattern gives a way to deal with super methods.  This will
// add this feature to Objects.
Object.method('superior', function (name) {
    var that = this,
    method = that[name];
    return function () {
        return method.apply(that, arguments);
    };
});

// Here's an example using the above.
var coolcat = function (spec) {
    var that = cat(spec),
    super_get_name = that.superior('get_name');
    that.get_name = function (n) {
        return 'like ' + super_get_name() + ' baby';
    };
    return that;
};

// Basically, the functional pattern rules.  It is THE way to write complicated
// JavaScript.  It creates objects that are "durable", meaning they cannot be
// compromised from outside.

// Parts: Objects can be composed of sets are parts.  Here's an example that
// adds event processing to any object, adding an `on' method, a `fire' method,
// and a private event registry.

var eventuality = function (that) {
    var registry = {};
    that.fire = function (event) {
        // Fire an event on an object.  The event can be either a string
        // containing the name of the event or an object containing a type
        // property containing the name of the event.  Handlers registered by
        // the `on' methodthat mathc the event name will be invoked.
        var array, func, handler, i,
        type = typeof event === 'string' ? event : event.type;

        // If an array of handlers exists for this event, then loop through it
        // and execute the handlers in order.
        if (registry.hasOwnProperty(type)) {
            array = registry[type];
            for (i = 0; i < array.length; i += 1) {
                handler = array[i];

                // A handler record contains a method and an optional array of
                // parameters.  If the method is a name, look up the function.
                func = handler.method;
                if (typeof func === 'string') {
                    func = this[func];
                }

                // Invoke a handler.  If the record contained parameters, then
                // pass them.  Otherwise pass the event object.
                func.apply(this, handler.parameters || [event]);
            }
        }
        return this;
    };

    that.on = function (type, method, parameters) {
        // Register an event.  Make a handler record.  Put it in a handler
        // array, making one if it doesn't yet exist for this type.
        var handler = {
            method: method,
            parameters: parameters
        };
        if (registry.hasOwnProperty(type)) {
            registry[type].push(handler);
        } else {
            registry[type] = [handler];
        }
        return this;
    };
    return that;
};

// We could call `eventuality' on any objects, giving it event handling or call
// it in a constructor function before that is returned:
// eventuality(that);

// If we want it to have access to private state, we could pass it the `my'
// bundle.




