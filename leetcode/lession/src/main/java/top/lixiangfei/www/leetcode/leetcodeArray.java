package top.lixiangfei.www.leetcode;

public class leetcodeArray {
    public void test() {
//        int[] num1 = mock();
//        int[] num2 = {2,22,23,33,34,50};
        int[] num1 = {2, 0};
        int[] num2 = {1};
//        printList(num1);
//        printList(num2);
//        merge(num1,1,num2,1);
//        int result = firstBadVersion(4);
//        System.out.println(result);
//        printList(result);
//        int[] result = asteroidCollision2(new int[]{5, 10, -5});
//        int[] result = asteroidCollision2(new int[]{5,  -5});
//        int[] result = asteroidCollision2(new int[]{10, 2, -5});
//        int[] result = asteroidCollision2(new int[]{-2, -1, 1, 2});
//        int[] result = asteroidCollision2(new int[]{-2, -2, -2, -2});
//        int[] result = asteroidCollision2(new int[]{2, 1, -2, -2});
        int[] result = asteroidCollision2(new int[]{-2, 1, -2, -1});
        PrintUtils.print(result);
    }

    private void printList(int[] node) {
        for (int i = 0; i < node.length; i++) {
            System.out.print(node[i]);
            System.out.print(",");
        }
        System.out.println();
    }

    public int[] mock() {
        int[] num = new int[20];
        for (int i = 0; i < 10; i++) {
            num[i] = i * 2;
        }
        return num;
    }


    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) {
            return;
        }
//        boolean isPositive = true;
//        if (nums1.length >= 2){
//            if (nums1[0] < nums1[1]){
//                isPositive = true;
//            }else {
//                isPositive = false;
//            }
//        }
//
//        if (nums2.length >= 2){
//            if (nums2[0] < nums2[1]){
//                isPositive = true;
//            }else {
//                isPositive = false;
//            }
//        }


        int leftPos = 0;
        int rightPos = 0;
        int[] nums1Backup = new int[m];
        for (int i = 0; i < m; i++) {
            nums1Backup[i] = nums1[i];
        }

        for (int i = 0; i < m + n; i++) {
            if (leftPos == m) {
                nums1[i] = nums2[rightPos];
                rightPos++;
                continue;
            }
            if (rightPos == n) {
                nums1[i] = nums1Backup[leftPos];
                leftPos++;
                continue;
            }

//            if (isPositive){
            if (nums1Backup[leftPos] <= nums2[rightPos]) {
                nums1[i] = nums1Backup[leftPos];
                leftPos++;
            } else {
                nums1[i] = nums2[rightPos];
                rightPos++;
            }
//            }else {
//                if (nums1Backup[leftPos] >= nums2[rightPos]){
//                    nums1[i] = nums1Backup[leftPos];
//                    leftPos++;
//                }else {
//                    nums1[i] = nums2[rightPos];
//                    rightPos++;
//                }
//            }


        }
    }


    public int firstBadVersion(int n) {
        int left = 1;
        int right = n;
        int middle = left / 2 + right / 2;
        while (left <= right) {
            System.out.println("middle =" + middle);
            if (isBadVersion(middle)) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
            middle = (right - left) / 2 + left;
        }
        return left;
    }

    //    2126753390
//    1702766719
    public boolean isBadVersion(int n) {
        if (n >= 2) {
            return true;
        }
        return false;
    }


    public int[] asteroidCollision(int[] asteroids) {
        int size = asteroids.length;
        int pos = 0;
        while (true) {
            if (size == 0){
                break;
            }

            //当前指针已经移出去了,已经跑完了
            if (pos >= size || pos < 0){
                break;
            }


            int currentValue = asteroids[pos];

            //往右找
            if (currentValue > 0) {
                if (pos == size -1){
                    break;
                }
                int rightValue = asteroids[pos + 1];
                if (rightValue > 0) {
                    //下一个往右
                    pos++;
                    continue;
                } else {
                    //下一个往左,开始比较
                    if (Math.abs(currentValue) > Math.abs(rightValue)) {
                        remove(asteroids, pos + 1);
                        size--;
                        continue;
                    } else if (Math.abs(currentValue) == Math.abs(rightValue)) {
                        remove(asteroids, pos);
                        remove(asteroids, pos);
                        size -= 2;
                        continue;
                    } else {
                        remove(asteroids, pos);
                        size--;
                        continue;
                    }
                }
            }
            //往左找
            if (currentValue < 0) {
                if (pos == 0) {
                    //左边没有了
                    pos++;
                    continue;
                }
                int leftValue = asteroids[pos - 1];
                if (leftValue < 0) {
                    //往左什么都不做
                    pos++;
                    continue;
                } else {
                    //下一个往右,开始比较
                    if (Math.abs(currentValue) > Math.abs(leftValue)) {
                        remove(asteroids, pos - 1);
                        size--;
                        pos--;
                        continue;
                    } else if (Math.abs(currentValue) == Math.abs(leftValue)) {
                        remove(asteroids, pos - 1);
                        remove(asteroids, pos - 1);
                        size -= 2;
                        pos -= 2;
                        continue;
                    } else {
                        remove(asteroids, pos);
                        size--;
                        pos--;
                        continue;
                    }
                }

            }

        }
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = asteroids[i];
        }
        return result;
    }

    public int[] asteroidCollision2(int[] asteroids){
        int[] rightTmp = new int[asteroids.length];
        int rightTmpSize = 0;
        int[] leftTmp = new int[asteroids.length];
        int leftTmpSize = 0;
        for (int i = 0 ; i < asteroids.length ;i++){
            int currentValue = asteroids[i];
            if (currentValue > 0){
                rightTmp[rightTmpSize++]=currentValue;
            }else {
                boolean isBothClear = false;
                for (int j = rightTmpSize-1;j>=0 ;j--){
                    int tmpValue = rightTmp[j];
                    if (tmpValue + currentValue > 0){
                        break;
                    }else if (tmpValue + currentValue == 0){
                        rightTmpSize--;
                        isBothClear = true;
                        break;
                    }else {
                        rightTmpSize--;
                    }
                }
                if (rightTmpSize==0 && !isBothClear){
                    leftTmp[leftTmpSize++] = currentValue;
                }
            }
        }
        //往右的数组和往左的数组并存的情况下,一定是往右的在右边,往左的在左边
        //要么就是单边数组,纯往左,纯往右
        int[] result = new int[rightTmpSize+leftTmpSize];
        for (int i = 0 ; i < leftTmpSize; i++){
            result[i] = leftTmp[i];
        }
        for (int i = 0 ; i <rightTmpSize;i++ ){
            result[leftTmpSize+i]=rightTmp[i];
        }
        return result;
    }




    private void remove(int[] arr, int pos) {
        for (int i = pos; i < arr.length - 1; i++) {
            arr[i] = arr[i+ 1];
        }
    }
}
