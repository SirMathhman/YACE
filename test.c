//
// Created by mathm on 10/20/2022.
//

#include <stdio.h>

int main() {
    FILE *file;
    errno_t error = fopen_s(&file, "Index.mgs", "r");
    if (error != 2) {
        printf_s("%s", "Index.mgs existed");
    }
    return 0;
}