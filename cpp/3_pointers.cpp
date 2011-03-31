// -*- Mode: C++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
// Some basic point practice.

#include <iostream>
#include <vector>
#include <cstdlib> // for srand

#define TEST 0

using namespace std;

int main(int argc, char ** argv) {
    // Some pointer refresher.
    int ival = 1024;
    int * pi;  // Declare a pointer to an object of type int.

    // Using ival evals to the value of ival.
    // Using &ival evals to the address of ival.

    // Initialize pi to ival's address.
    pi = &ival;

    // Using pi evals to the address held by pi.
    // Using *pi evals to the value of the object held by pi.
    
#if TEST
    // Make sure the pointer pi references something, then prints out 1024.
    if (pi && *pi == 1024)
    {
        cout << *pi << "\n";
    }
#endif
    // End pointer refresher.

    // Create vectors to make a less ugly version of multi_seq
    vector<int> fibonacci, lucas, pell, triangular, square, pentagonal;
    // Create a vector<int> pointer and point it at nothing for now.
    vector<int> *pv = 0;
    // We can then assign this to the above vectors using: pv = &pell;

    // But, a better way is to create a vector of these addresses.
    const int seq_cnt = 6;
    vector<int> *seq_addrs[seq_cnt] = {
        &fibonacci, &lucas, &pell,
        &triangular, &square, &pentagonal
    };
    // This is an array of elements of type vector<int>*.

    // To randomize which is the current vector.
    srand(seq_cnt);
    int seq_index = rand() % seq_cnt;
    vector<int> *current_vec = seq_addrs[seq_index];

    // To call a member function of the template pointed to by current_vec, we
    // need to use the arrow member selection operator (->), instead of the
    // standard member selection operator (.).
    if (current_vec && !current_vec->empty())
    {
        // Do something.
    }

    // To use the subscript operator, we must dereference the pointer.
    if (current_vec && (*current_vec)[1] == 1)
    {
        // Do something.
    }

    // Not bothering to rewrite the actual population and output of these
    // vectors.
    
    return 0;
}
