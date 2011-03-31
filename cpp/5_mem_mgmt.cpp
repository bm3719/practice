// -*- Mode: C++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
// Just a quick refresher on new and delete.

int main(int argc, char ** argv) {

    // newing types
    int *pi;
    pi = new int;

    pi = new int(1024);

    // Allocates array of heap elements.
    int *pia = new int[24];

    // deleting stuff
    delete pi;
    delete [] pia;

    // Note that delete already checks if the pointer is non-zero.
    
    return 0;
}
