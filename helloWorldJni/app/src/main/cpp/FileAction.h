//
// Created by Administrator on 2018/7/16 0016.
//

#ifndef APP_FILEACTION_H
#define APP_FILEACTION_H


#include <string>

class FileAction {

public :
    FileAction(std::string fileName);
    void write(std::string info);
    std::string read();
//    bool create();

private:
    std::string fileName;
};


#endif //APP_FILEACTION_H
