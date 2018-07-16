//
// Created by Administrator on 2018/7/16 0016.
//

#include "FileAction.h"
#include "fstream"

FileAction::FileAction(std::string fileName) {
    this->fileName = fileName;
}

//bool FileAction::create() {
//    std::ofstream oFile;
//    oFile.open(fileName,std::ofstream::app);
//    if (!oFile){
//        std::ofstream  oFile;
//        oFile.open(fileName, std::ofstream::app);
//        oFile.close();
//        return true;
//    }
//    return false;
//}

std::string FileAction::read() {
    std::ifstream ifstream;
    ifstream.open(fileName);
    if (!ifstream){
        return "";
    }
//    int length = ifstream.tellg();
    //数组初始化
    //1 char content[100];
    //2 char* content = new char[100];
    char* content = new char[1000];
    ifstream.read(content,1000);
    ifstream.close();
    return std::string(content);
}


void FileAction::write(std::string info) {
    std::ofstream oFile;
    oFile.open(fileName,std::ofstream::app);
    oFile<< info;
    oFile.close();
}