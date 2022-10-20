#include <iostream>
#include <fstream>

int main() {
    std::ifstream file;
    file.open("Index.mgs");
    if (file) {
        std::cerr << "Index exists.";
    }

    return 0;
}
