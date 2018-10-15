package top.lixiangfei.www.leetcode;

public class other {
    public void test(){
//        int result = lastMajorityMultiple(1,2,3,5,6);
        int[] array = new int[]{1,2,7,7,10,9,7,8,3,6,7,8,2};
//        int[] array = new int[]{1,2,3,4,5,6,7};
//        int[] array = new int[]{1,7,7,8,3,6,7,10,12};
        int result = longestManotoneSequence2(array);
        System.out.println(result);
        int n = 1;

    }


    public int lastMajorityMultiple(int a,int b,int c,int d,int e){

        //除尽就是取余为0
        for (int i = 1 ; i < 100 ; i++){
            int aRemain = i%a ;
            int bRemain = i%b ;
            int cRemain = i%c ;
            int dRemain = i%d ;
            int eRemain = i%e ;

            int count = 0;
            if (aRemain == 0){
                count++;
            }
            if (bRemain == 0){
                count++;
            }
            if (cRemain == 0){
                count++;
            }
            if (dRemain == 0){
                count++;
            }
            if (eRemain == 0){
                count++;
            }
            //起码有三个为0,所以小于等于2
            if (count>= 3){
                return i;
            }
        }
        //没找到
        return -1;
    }


    public int  longestManotoneSequence(int[] seq){
        if (seq.length == 1){
            return 1;
        }


        int currentCount = 1;
        int lastCount  = 0;
        for (int i = 1 ; i < seq.length ; i++){
            int preValue = seq[i-1];
            int currentValue  = seq[i];
            if (preValue < currentValue){
                currentCount++;
            }else {
                if (currentCount > lastCount){
                    lastCount = currentCount;
                    currentCount=0;
                }
            }
        }
        if (currentCount > lastCount){
            lastCount = currentCount;
        }
        return lastCount;


    }
    public int  longestManotoneSequence2(int[] arr){
        // 判断数组是都为空，为空返回0
        if (arr.length <= 0)
            return 0;

        int first = 0;
        int last = 0;
        int index = 0;
        // 各种长度的连续单调子序列长度，都放入集合中，一会比较出最大值；
        int[] list = new int[arr.length];
        int actualSize = 0;
        // 1 单点递增连续子序列的情况，没有破坏数组的元素顺序
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] < arr[i]) {
                last = i;
            } else {
                index = last - first + 1; // 连续递增中断，记录子序列长度
                list[actualSize] = index;
                actualSize++; // 保存到集合中
                first = last = i; // 指针归到i所在位置继续
            }
        }

//        // 2 单调递减连续子序列情况
//        for (int i = 1; i < arr.length; i++) {
//            if (arr[i - 1] > arr[i]) {
//                last = i;
//            } else {
//                index = last - first + 1; // 连续递减中断，记录子序列长度
//                list.add(index); // 也，保存到集合中
//                first = last = i; // 指针归到i所在位置继续
//            }
//        }
        int max = 0;
        for (int i = 0 ; i < actualSize ; i++){
            if (max < list[i]){
                max = list[i];
            }
        }
        // 求出集合中的最大值，即子序列的最大值
        return max;

    }
}
