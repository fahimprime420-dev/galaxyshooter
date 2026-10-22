cmake_minimum_required(VERSION 3.22.1)
project("galaxy" LANGUAGES CXX)

add_library(galaxy SHARED native-lib.cpp)
target_compile_features(galaxy PRIVATE cxx_std_20)

find_library(log-lib log)
target_link_libraries(galaxy ${log-lib})
