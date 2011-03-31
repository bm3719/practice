// -*- Mode: C++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
// Some writing/reading file practice.

#include <iostream>
#include <string>
#include <fstream>

#define TEST 0

using namespace std;

int main(int argc, char ** argv) {
    string usr_name = "bm3719";
    int num_tries = 2;
    int num_right = 3;
    
    // Open a file for output in append mode.
    ofstream outfile("seq_data.txt", ios_base::app);

    // If a file fails to open, it's handler will be 0;
    if (!outfile) {
        cout << "outfile error.";
        // Handle file error.
    } else {
        outfile << usr_name << ' '
                << num_tries << ' '
                << num_right << endl;
    }

    // Reading files
    ifstream infile("seq_data.txt");

    if (!infile) {
        cout << "infile error.";
    } else {
        string name;
        int nt, nr;
        while (infile >> name) {
            infile >> nt >> nr;
            cout << "User: " << name << ", Tries: " << nt << ", Right: " << nr << endl;
        }
    }        

    // This is all pretty standard.  Only other thing to note is that it's also
    // possible to open a file for read/write by bitwise OR-ing ios_base enums.
    fstream iofile("seq_data.txt", ios_base::in | ios_base:: out);
    if (!iofile) {
        cout << "iofile error.";
    } else {
        // Reset position to front of file.
        iofile.seekg(0);
        // Do some stuff.
    }
    
    return 0;
}
