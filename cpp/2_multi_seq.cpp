// -*- Mode: C++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
// Print out multiple sequences.

#include <vector>
#include <iostream>
#include <string>

#define TEST 0

using namespace std;

int main(int argc, char ** argv) {
    const int seq_size = 18;
    int elem_seq[seq_size] = {
        1, 2, 3,   // Fibonacci
        3, 4, 7,   // Lucas
        2, 5, 12,  // Pell
        3, 6, 10,  // Triangular
        4, 9, 16,  // Square
        5, 12, 22  // Pentagonal
    };

    // Init a vector<int> with these values.  The second param here is a
    // reference value.
    vector<int> elem_vec(elem_seq, elem_seq + sizeof(elem_seq)/sizeof(int));

    const int max_seq = 6;
    string seq_names[max_seq] = {
        "Fibonacci",
        "Lucas",
        "Pell",
        "Trangular",
        "Square",
        "Pentagonal"
    };

    // Print these series.
    for (int i = 0; i < max_seq; i++) {
        cout << "The first 3 values in the " << seq_names[i] << " Series are: ";
        for (int j = i * 3; j < i * 3 + 3; j++) {
            cout << elem_seq[j];
            cout << ((j < i * 3 + 2) ? ", " : ".\n");
        }
    }    
    
    return 0;
}
