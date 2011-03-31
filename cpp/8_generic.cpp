// -*- Mode: C++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
// Generic programming practice

#include <iostream>
#include <fstream>
#include <functional>
#include <algorithm>
#include <vector>
#include <string>
#include <list>

using namespace std;

// globals
const int int_size = 12;
const int string_size = 4;
const int int_not_present = 1024;

// Given a vector of ints and an int value, return a pointer to the int within
// the vector, or a 0 if not present.
int* find_v1(vector<int> &vec, int value) {
    for (int i = 0; i < vec.size(); i++) {
        if (vec[i] == value) {
            return &vec[i];
        }
    }
    return 0;
}

// Turn find_v1 into a template function.
template <typename elemType>
elemType* find_v2(vector<elemType> &vec, elemType value) {
    for (int i = 0; i < vec.size(); i++) {
        if (vec[i] == value) {
            return &vec[i];
        }
    }
    return 0;
}

// Now change this into a function that accepts any array type.
template <typename elemType>
elemType* find_v3(elemType *array, int size, elemType value) {
    if (!array || size == 0)
        return 0;
    
    for (int i = 0; i < size; i++, array++) {
        if (*array == value) {
            return array;
        }
    }
    return 0;
}

// Version of display that uses iterators.  For some reason, this doesn't seem
// to work on g++.
template <typename elemType>
void display(vector<elemType> &vec, ostream &os) {
//    vector<elemType>::iterator iter = vec.begin();
//     vector<elemType>::iterator end_it = vec.end();
//     for(; iter == end_it; iter++) {
//         os << *iter << ' ';
//     }
    os << endl;
}

// Now an implementation of find that accepts any sequential type (arrays,
// vectors, lists, etc).
template <typename IteratorType, typename elemType>
IteratorType find_v4(IteratorType first, IteratorType last, const elemType &value) {
    for (; first != last; ++first) {
        if (value == *first)
            return first;
    }
    return last;
}


int main(int argc, char ** argv) {
    // 3.1
    cout << "Testing find_v1():" << endl;
    const int seq_size = 18;
    int elem_seq[seq_size] = {
        1, 2, 3,   // Fibonacci
        3, 4, 7,   // Lucas
        2, 5, 12,  // Pell
        3, 6, 10,  // Triangular
        4, 9, 16,  // Square
        5, 12, 22  // Pentagonal
    };
    vector<int> elem_vec(elem_seq, elem_seq + sizeof(elem_seq)/sizeof(int));
    cout << "16 in elem_vec's address: " << (find_v1(elem_vec, 16)) << endl;
    cout << "34 in elem_vec's address: " << (find_v1(elem_vec, 34)) << endl;

    cout << "Testing find_v2():" << endl;
    cout << "16 in elem_vec's address: " << (find_v2(elem_vec, 16)) << endl;
    cout << "34 in elem_vec's address: " << (find_v2(elem_vec, 34)) << endl;

    cout << "Testing find_v3():" << endl;
    int ia[8] = { 1, 1, 2, 3, 5, 8, 13, 21 };
    double da[6] = { 1.5, 2.0, 2.5, 3.0, 3.5, 4.0 };
    string sa[4] = { "asd", "sdfd", "bcsdf", "sdwe" };
    cout << "2 in ia's address: " << find(ia, ia+8, 2) << endl;
    cout << "\"sdfd\" in sa's address: " << find(sa, sa+4, sa[1]) << endl;

    // 3.2
    cout << "Testing iterators:" << endl;
    for (vector<int>::iterator it = elem_vec.begin(); it != elem_vec.end(); it++) {
        cout << *it << ' ';
    }
    cout << endl;

    cout << "Testing find_v4():" << endl;
    const int asize = 8;
    int ia2[asize] = { 1, 1, 2, 3, 5, 8, 13, 21 };
    vector<int> ivec(ia2, ia2 + asize);
    list<int> ilist(ia2, ia2 + asize);

    int *pia = find_v4(ia2, ia2+asize, 21);
    if (pia != ia2+asize) {
        cout << "21 found in ia2." << endl;
    }

    vector<int>::iterator it2;
    it2 = find_v4(ivec.begin(), ivec.end(), 21);
    if (it2 != ivec.end()) {
        cout << "21 found in ivec." << endl;
    }

    list<int>::iterator it3;
    it3 = find_v4(ilist.begin(), ilist.end(), 21);
    if (it3 != ilist.end()) {
        cout << "21 found in ilist." << endl;
    }

    // Skipping ahead a bit from 3.4.  Go back and read the rest of Ch. 3
    // later.
    
    
    return 0;
}
