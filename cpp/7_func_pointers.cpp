// -*- Mode: C++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
// Using function pointers to generalize functions over types.

#include <iostream>
#include <vector>

using namespace std;

const vector<int> *fibon_seq(int size);
const vector<int> *lucas_seq(int size);
const vector<int> *pell_seq(int size);
const vector<int> *triang_seq(int size);
const vector<int> *square_seq(int size);
const vector<int> *pent_seq(int size);

// // Here's a specific version of a function we'll generalize.
// bool fibon_elem(int pos, int &elem) {
//     const vector<int> *pseq = fibon_seq(pos);

//     if (!pseq) {
//         elem = 0;
//         return false;
//     }
//     elem = (*pseq)[pos-1];
//     return true;
// }

bool seq_elem(int pos, int &elem, const vector<int>* (*seq_ptr) (int)) {
    // invoke function addressed by seq_ptr.
    const vector<int> *pseq = seq_ptr(pos);

    // error check seq_ptr.
    if (!seq_ptr)
        cout << "seq_ptr is set to null!" << endl;

    if(!pseq) {
        elem = 0;
        return false;
    }
    elem = (*pseq)[pos-1];
    return true;
}

int main(int argc, char ** argv) {
    // a pointer to a function specifies it's type signature.

    // Kinda obvious, so not gonna write a bunch of test code.

    return 0;
}
