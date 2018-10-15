package top.lixiangfei.www.leetcode;

import java.io.IOException;

public class clicker {
    public static void main(String[] arg){
        String cmd1 = "adb shell input tap 183 1831";
        String cmd2 = "adb shell input tap 940 1831";
        for (int i = 0 ; i < 40 ; i ++){
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(cmd1);
                System.out.println("i = " + i + "run " + cmd1);
                Thread.sleep(2000);
                runtime.exec(cmd2);
                System.out.println("i = " + i + "run " + cmd2);
                Thread.sleep(1000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
