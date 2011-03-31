// -*- Mode: C++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
// Practice problem: Implement itoa() with only std::iostream.

#include <iostream>

using namespace std;

// One better way to do this would be using the ASCII values for the numbers
// and returning those, but I don't have those memorized.
string digittoi(int num) {
    switch (num)
    {
    case 1:
        return "1";
    case 2:
        return "2";
    case 3:
        return "3";
    case 4:
        return "4";
    case 5:
        return "5";
    case 6:
        return "6";
    case 7:
        return "7";
    case 8:
        return "8";
    case 9:
        return "9";
    default:
        return "0";
    }
}

string itoa(int num) {
    string output = "";

    while (num > 0) {
        int digit = num % 10;
        output = digittoi(digit) + output; 
        
        num -= digit;
        if (num > 0) {
            num /= 10;
        }
    }
    if (output == "")
    {
        output = "0";
    }
    
    return output;
}

int main(int argc, char ** argv) {
    int input_num;
    cout << "Enter number to be converted to string: ";
    cin >> input_num;

    cout << "Conversion of " << itoa(input_num) << " complete.\n";
    return 0;
}
