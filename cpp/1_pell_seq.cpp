// -*- Mode: C++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
// Populate a vector with Pell Series integers and print them out.

#include <vector>
#include <iostream>

#define TEST 0

using namespace std;

int main(int argc, char ** argv) {
    int seq_size = 18;
    vector<int> pell_seq(seq_size);

    pell_seq[0] = 1; // assign 1 to first element
    pell_seq[1] = 2; // assign 2 to second element
    for (int ix = 2; ix < seq_size; ++ix)
    {
        pell_seq[ix] = pell_seq[ix - 2] + 2*pell_seq[ix - 1];
    }

#if TEST    
    for (int i = 0; i < seq_size; i++)
    {
        cout << pell_seq[i] << "\n";
    }
#endif

#if TEST
    // Example of how to initialize a vector with an array.
    int arr[] = {1, 2, 3, 4, 6 };
    vector<int> elem_seq(arr, arr + (sizeof(arr)/sizeof(int)));
#endif

    // Output values.
    cout << "The first " << pell_seq.size() << " numbers of the Pell Series are: ";
    for (int i = 0; i < pell_seq.size(); i++) {
        cout << pell_seq[i];
        (i < pell_seq.size() - 1) ? cout << " " : cout << ".\n";
    }
    
    return 0;
}
