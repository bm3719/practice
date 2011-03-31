// -*- Mode: C++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
// Creating a template function to take in vectors of any type.

#include <iostream>
#include <vector>

using namespace std;

template <typename elemType>
void display_message(const string &msg,
                     const vector<elemType> &vec) {
    cout << msg << ": ";
    for (int i = 0; i < vec.size(); i++) {
        elemType t = vec[i];
        cout << i << ' ';
    }
    cout << endl;
}

int main(int argc, char ** argv) {
    vector<int> v1;
    vector<int>::iterator it;
    it = v1.begin();
    it = v1.insert(it, 1);
    it = v1.insert(it, 2);

    display_message("int vector", v1);

    return 0;
}
