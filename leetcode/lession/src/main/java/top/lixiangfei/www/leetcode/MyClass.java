package top.lixiangfei.www.leetcode;

import java.util.HashMap;

public class MyClass {
    public static void main(String[] arg) {
//        int[] nums = new int[]{1,2,2,2,3,3,4,4,5,5,5};
//        int[] nums = new int[]{1,2,3,4,5,6,7};
//        int[] nums = new int[]{1,2,3,4,5,6};
//        int[] nums = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54};

//        int[] nums = {1,1,1,3,3,4,3,2,4,2};
//        int[] nums = {1,2,3,4,5,6,7,8,9,1};
//        int[] nums = {99,99};
//        int[] nums = {1,2,3,1};
//        int[] nums = {1,0,1,1};
//        int[] nums = {4,1,2,1,2};
//        int[] nums = {-1, -2, -2, 2, 2};
//        int[] nums = {-2,-2,-2,2, 2, 3, 2};

//        int[] nums = {43,16,45,89,45,-2147483648,45,2147483646,-2147483647,-2147483648,43,2147483647,-2147483646,-2147483648,89,-2147483646,89,-2147483646,-2147483647,2147483646,-2147483647,16,16,2147483646,43};
//        int[] nums = {-19,-46,-19,-46,-9,-9,-19,17,17,17,-13,-13,-9,-13,-46,-28};
//        int[] nums = {-401451,-177656,-2147483646,-473874,-814645,-2147483646,-852036,-457533,-401451,-473874,-401451,-216555,-917279,-457533,-852036,-457533,-177656,-2147483646,-177656,-917279,-473874,-852036,-917279,-216555,-814645,2147483645,-2147483648,2147483645,-814645,2147483645,-216555};
//        int[] nums = {40,-15,51,-33,41,19,-5,28,-50,-12,48,-46,-26,-37,41,54,51,-29,-30,-49,29,4,-7,30,18,32,6,9,24,-15,-13,12,20,-8,6,53,18,14,-2,-46,-25,-22,-20,-46,-28,-35,18,15,-3,49,54,-20,55,28,-39,4,52,-12,37,29,-29,15,50,46,42,-22,11,43,49,40,16,-3,-13,-33,-7,25,16,-24,-34,-50,-31,-37,-50,6,44,-25,-15,25,11,47,40,41,-24,32,49,-20,-29,-33,52,-2,-49,48,-25,24,-42,-37,39,-6,-49,54,-30,-44,15,-42,-28,-22,20,25,-30,-48,-31,12,-40,20,-14,-16,-40,42,-12,46,-1,-35,-6,39,-8,-32,50,-31,43,28,32,-8,5,-3,-48,11,5,55,9,-39,-39,42,-7,33,-35,9,14,-34,53,51,-32,30,44,50,-48,-44,-6,-5,19,12,-26,-16,-13,53,33,-1,23,4,-16,37,29,16,44,19,46,-32,39,5,47,-28,37,43,52,-42,33,24,-40,30,-24,48,-34,-5,-14,47,-14,55,-26,-2,-1,-44,14};
//        int[] nums = {1,2,3,1,2,3};
//        int[] nums = {0,1,2,3,4,5,0};
//        int[] nums = {1,2,1,3,2,5};
//        int[] nums = {0,-1};
//        int[] nums = {1,2,3};
//        int[] nums = {9,9,9};
//        int[] nums = {8};
//        int[] nums = {9};
//        int[] nums = {5,9,8,3,8,1,5,6,7,1};
//        int[] nums = {0,9,8,3,8,1,5,0,7,0};
//        int[] nums = {1,2,2,1};
//        int[] nums = {3,1,2,4,9,7,6,2};
//        int[] nums = {3,2,4};
        int[] nums = {-3, 4, 3, 90};
//        int[] nums2 = {2};
//        int[] nums2 = {1,1};
//        int[] nums = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54};
//        int[] nums = {1,2,3,4,5,6,7,8,9,10};
//        System.out.println(nums.length);

//        printList(nums);
//        printList(nums2);
//        moveZeroes3(nums);
//        rotate2(nums,2);
//        int[] result = moveZeroes(nums);
//        int[] ret = intersect(nums,nums2);
//        int[] ret = twoSum2(nums, 0);
//        printList(ret);

//        int[] nums = {1,2,3,5,19,20};
//        printList(nums);
//        int index = binarySearch(nums,20);
//        System.out.println(index);

//        String[][] boardstr = {
//                {".", "9", ".", ".", "4", ".", ".", ".", "."},
//                {"1", ".", ".", ".", ".", ".", "6", ".", "."},
//                {".", ".", "3", ".", ".", ".", ".", ".", "."},
//                {".", ".", ".", ".", ".", ".", ".", ".", "."},
//                {".", ".", ".", "7", ".", ".", ".", ".", "."},
//                {"3", ".", ".", ".", "5", ".", ".", ".", "."},
//                {".", ".", "7", ".", ".", "4", ".", ".", "."},
//                {".", ".", ".", ".", ".", ".", ".", ".", "."},
//                {".", ".", ".", ".", "7", ".", ".", ".", "."}};

//        String[][] boardstr = {
//                {"5", "3", ".", ".", "7", ".", ".", ".", "."},
//                {"6", ".", ".", "1", "9", "5", ".", ".", "."},
//                {".", "9", "8", ".", ".", ".", ".", "6", "."},
//                {"8", ".", ".", ".", "6", ".", ".", ".", "3"},
//                {"4", ".", ".", "8", ".", "3", ".", ".", "1"},
//                {"7", ".", ".", ".", "2", ".", ".", ".", "6"},
//                {".", "6", ".", ".", ".", ".", "2", "8", "."},
//                {".", ".", ".", "4", "1", "9", ".", ".", "5"},
//                {".", ".", ".", ".", "8", ".", ".", "7", "9"}
//        };

        String[][] boardstr = {
                {".",".",".",".",".",".","5",".","."},
                {".",".",".",".",".",".",".",".","."},
                {".",".",".",".",".",".",".",".","."},
                {"9","3",".",".","2",".","4",".","."},
                {".",".","7",".",".",".","3",".","."},
                {".",".",".",".",".",".",".",".","."},
                {".",".",".","3","4",".",".",".","."},
                {".",".",".",".",".","3",".",".","."},
                {".",".",".",".",".","5","2",".","."}
        };



        char[][] board = new char[9][9];
        for (int i = 0; i < boardstr.length; i++) {
            for (int j = 0; j < boardstr[i].length; j++) {
                board[i][j] = boardstr[i][j].toCharArray()[0];
            }
        }

//        int[][] matrix ={
//                { 5, 1, 9,11},
//                { 2, 4, 8,10},
//                {13, 3, 6, 7},
//                {15,14,12,16}
//        };

        int[][] matrix ={
                { 1, 2, 3,4},
                { 5, 6, 7,8},
                {9, 10, 11, 12},
                {13,14,15,16}
        };

//        leetcodeString leetcodeString = new leetcodeString();
//        leetcodeString.test();
//        print2List(matrix);
//        rotate(matrix);
//        print2List(matrix);
//        boolean ret = isValidSudoku(board);
//        System.out.println(ret);

//        leetcodeNode leetcodeNode = new leetcodeNode();
//        leetcodeNode.test();
//        leetcodeTree tree = new leetcodeTree();
//        tree.test();
        leetcodeArray array = new leetcodeArray();
        array.test();
//        leetcodeDynamic dynamic = new leetcodeDynamic();
//        dynamic.test();
//        other other = new other();
//        other.test();
    }


    private static void printList(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
            System.out.print(",");
        }
        System.out.println();
    }


    private static void print2List(int[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0 ; j < matrix[i].length ; j++){
                System.out.print(matrix[i][j]);
                System.out.print(",");
            }
            System.out.println();
        }
    }

    public static int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length == 1) {
            return nums.length;
        }
        int count = nums.length;
        int j = 0;
        for (int i = 1; i < count; i++) {
            if (nums[i] != nums[j]) {
                j++;
                nums[j] = nums[i];
            }
        }
        return j + 1;
    }

    public static int removeDuplicates2(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length == 1) {
            return nums.length;
        }
        int count = nums.length;
        int j = 0;
        boolean haveSame = false;
        for (int i = 1; i < count; i++) {

            if (nums[i] == nums[j] && !haveSame) {
                j++;
                nums[j] = nums[i];
                haveSame = true;
                continue;
            }

            if (nums[i] != nums[j]) {
                j++;
                nums[j] = nums[i];
                haveSame = false;
            }
        }
        return j + 1;
    }


    public static void rotate(int[] nums, int k) {
        k = k % nums.length;
        if (k == 0) {
            return;
        }
        int length = nums.length;
        int tmp;
        if (k < nums.length / 2) {
            for (int i = 0; i < k; i++) {
                tmp = nums[length - 1];
                for (int j = length - 1; j > 0; j--) {
                    nums[j] = nums[j - 1];
                }
                nums[0] = tmp;
            }
        } else {
            for (int i = 0; i < length - k; i++) {
                tmp = nums[0];
                for (int j = 0; j < length - 1; j++) {
                    nums[j] = nums[j + 1];
                }
                nums[length - 1] = tmp;
            }
        }


    }


//    public static void rotate2(int[] nums, int k) {
//        int length = nums.length;
//        int group = length / k;
//        int remain = length % k;
//        if (remain != 0){
//            group++;
//        }
//        int tmp ;
//        for (int i = 0 ; i < k; i ++ ){
//            if (remain != 0){
//                if (i <= remain-1){
//                    tmp = nums[length-remain+i];
//                }else {
//                    tmp = nums[length-remain-k + i];
//                }
//            }else {
//                tmp = nums[length-1-k+i];
//            }
//            for (int j = group -2 ; j >= 0; j--){
//                int oldPos = i+j*k;
//                int newPos = (i+(j+1)*k)%length;
//                nums[newPos] = nums[oldPos];
//            }
//            nums[i] = tmp;
//        }
//    }

    public static void rotate2(int[] nums, int k) {
        if (nums.length < 2) {
            return;
        }
        if (k == 0) {
            return;
        }
        int oldPos = 0;
        int holdOld = nums[oldPos];
        int holdNew = nums[oldPos];
        boolean isRemain = (nums.length % k == 0) ? true : false;
        int minus = nums.length - k;
        if (minus > 0) {
            boolean isDivdier = nums.length % minus == 0 ? true : false;
            isRemain = isRemain || isDivdier;
        }
        for (int i = 0; i < nums.length; i++) {
            int newPos = (oldPos + k) % nums.length;
            holdNew = nums[newPos];
            nums[newPos] = holdOld;
            if (newPos < oldPos && (newPos < minus && isRemain)) {
                oldPos = newPos + 1;
                holdOld = nums[oldPos];
            } else {
                oldPos = newPos;
                holdOld = holdNew;
            }
        }
    }


    public static boolean containsDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int base = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (base == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean containsDuplicateByInsert(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        for (int i = 1; i < nums.length; i++) {
            int rightPosition = i - 1;
            int indicator = i;
            while (rightPosition >= 0) {
                if (nums[indicator] < nums[rightPosition]) {
                    //exchange
                    int tmp = nums[rightPosition];
                    nums[rightPosition] = nums[indicator];
                    nums[indicator] = tmp;
                    indicator--;
                    rightPosition--;
                } else {
                    if (nums[indicator] == nums[rightPosition]) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }


    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {
            int base = nums[i];
            int last = i + 1 + k < nums.length ? i + 1 + k : nums.length;
            for (int j = i + 1; j < last; j++) {
                if (base == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     *
     * @param nums
     * @param k
     * @return
     */
    public static boolean containsNearbyDuplicate2(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {

            int left = i;
            int right = i + k < nums.length ? i + k : nums.length - 1;
            int tmpIndictor = 0;
            int tmp[] = new int[right - left + 1];
            for (int j = i; j <= right; j++, tmpIndictor++) {
                if (j >= nums.length) {
                    break;
                }
                tmp[tmpIndictor] = nums[j];
            }

            int tmpEnd = right - left + 1;
            for (int j = 0; j < tmpEnd; j++) {
                int insertIndicator = j + 1;
                int tmpStart = j;
                while (insertIndicator < tmpEnd && tmpStart >= 0) {
                    if (tmp[insertIndicator] < tmp[tmpStart]) {
                        int tmpV = tmp[insertIndicator];
                        tmp[insertIndicator] = tmp[tmpStart];
                        tmp[tmpStart] = tmpV;
                        tmpStart--;
                        insertIndicator--;
                    } else {
                        if (tmp[insertIndicator] == tmp[tmpStart]) {
                            return true;
                        }
                        break;
                    }
                }
            }

        }
        return false;
    }

    private static class NumEntry {
        public int num;
        public int[] indexs;
        public int actualCap;
    }

    public static boolean containsNearbyDuplicate3(int[] nums, int k) {
        final int DEFAULT_INITIAL_CAPACITY = 1 << 2;
        NumEntry[] entries = new NumEntry[nums.length];
        int actualEntrySize = 0;
        for (int i = 0; i < nums.length; i++) {
            boolean findNum = false;
            for (int j = 0; j < actualEntrySize; j++) {
                if (entries[j] == null) {
                    break;
                }
                if (entries[j].num == nums[i]) {
                    findNum = true;
                    for (int jj = 0; jj < entries[j].actualCap; jj++) {
                        if (i - entries[j].indexs[jj] <= k) {
                            return true;
                        }
                    }
                    if (entries[j].actualCap > entries[j].indexs.length * 0.7) {
                        //grow up
                        int[] newIndex = new int[entries[j].indexs.length * 2];
                        for (int it = 0; it < entries[j].indexs.length; j++) {
                            newIndex[it] = entries[j].indexs[it];
                        }
                        entries[j].indexs = newIndex;
                    }
                    entries[j].indexs[entries[j].actualCap] = i;
                    entries[j].actualCap++;

                }
            }
            if (!findNum) {
                NumEntry numEntry = new NumEntry();
                numEntry.indexs = new int[DEFAULT_INITIAL_CAPACITY];
                numEntry.indexs[0] = i;
                numEntry.actualCap = 1;
                numEntry.num = nums[i];
                entries[actualEntrySize] = numEntry;
                actualEntrySize++;
            }
        }
        return false;
    }


    private static class NumMapEntry {
        public int num;
        public int index;
    }

    public static boolean containsNearbyDuplicate4(int[] nums, int k) {
        NumMapEntry[] entries = new NumMapEntry[nums.length];
        int actualEntrySize = 0;
        for (int i = 0; i < nums.length; i++) {
            NumMapEntry findEntry = binarySearch(entries, actualEntrySize, nums[i]);
            if (findEntry != null) {
                if (i - findEntry.index <= k) {
                    return true;
                } else {
                    findEntry.index = i;
                }
            }
            //insert by order
            if (findEntry == null) {
                //first insert
                NumMapEntry numEntry = new NumMapEntry();
                numEntry.index = i;
                numEntry.num = nums[i];
                entries[actualEntrySize] = numEntry;
                actualEntrySize++;

                //second order
                for (int ii = 0; ii < actualEntrySize; ii++) {
                    int endPos = actualEntrySize - 2;
                    int currentPos = actualEntrySize - 1;
                    while (endPos >= 0) {
                        if (entries[currentPos].num < entries[endPos].num) {
                            NumMapEntry tmp = entries[currentPos];
                            entries[currentPos] = entries[endPos];
                            entries[endPos] = tmp;
                            currentPos--;
                            endPos--;
                        } else {
                            break;
                        }
                    }
                }


            }
        }
        return false;
    }

    static NumMapEntry binarySearch(NumMapEntry[] num, int actualSize, int v) {
        if (actualSize == 0) {
            return null;
        }
        int left = 0;
        int right = actualSize - 1;
        int middle = (right + left) / 2;
        while (left <= right) {
            int middleValue = num[middle].num;
            if (middleValue == v) {
                return num[middle];
            } else if (middleValue > v) {
                right = middle - 1;
                middle = (left + right) / 2;
            } else {
                left = middle + 1;
                middle = (left + right) / 2;
            }

        }

        return null;
    }

    static int binarySearch(int[] num, int v) {

        int left = 0;
        int right = num.length - 1;
        int middle = (right + left) / 2;
        while (left <= right) {
            int middleValue = num[middle];
            if (middleValue == v) {
                return middle;
            } else if (middleValue > v) {
                right = middle - 1;
                middle = (left + right) / 2;
            } else {
                left = middle + 1;
                middle = (left + right) / 2;
            }
        }

        return -1;
    }


    public static int singleNumber(int[] nums) {
        int max = 0;
        int min = 0;
        for (int i = 0; i < nums.length; i++) {
            if (max < nums[i]) {
                max = nums[i];
            }
            if (min > nums[i]) {
                min = nums[i];
            }
        }
        int length = max - min + 1;

        int[] bucket = new int[length];
        for (int i = 0; i < nums.length; i++) {
            int v = nums[i];
            bucket[v - min] += 1;
        }
        for (int i = 0; i < bucket.length; i++) {
            if (bucket[i] == 1) {
                return i + min;
            }
        }
        return -1;
    }


    public static int singleNumber2(int[] nums) {
        NumMapEntry[] entries = new NumMapEntry[nums.length];
        int actualSize = 0;
        for (int i = 0; i < nums.length; i++) {
            NumMapEntry findEntry = binarySearch(entries, actualSize, nums[i]);
            //insert by order
            if (findEntry == null) {
                //first insert
                NumMapEntry numEntry = new NumMapEntry();
                numEntry.index = 1;
                numEntry.num = nums[i];
                entries[actualSize] = numEntry;
                actualSize++;

                //second order
                for (int ii = 0; ii < actualSize; ii++) {
                    int endPos = actualSize - 2;
                    int currentPos = actualSize - 1;
                    while (endPos >= 0) {
                        if (entries[currentPos].num < entries[endPos].num) {
                            NumMapEntry tmp = entries[currentPos];
                            entries[currentPos] = entries[endPos];
                            entries[endPos] = tmp;
                            currentPos--;
                            endPos--;
                        } else {
                            break;
                        }
                    }
                }
            } else {
                findEntry.index++;
            }
        }
        for (int i = 0; i < actualSize; i++) {
            if (entries[i].index == 1) {
                return entries[i].num;
            }
        }
        return -1;
    }


    public static int singleNumber3(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int size = 0;
        int nagativeSize = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                size++;
            } else {
                nagativeSize++;
            }
        }
        int tmp[] = new int[size];
        int tmpActualSize = 0;
        int nagatvie[] = new int[nagativeSize];
        int tmpNagativeActualSize = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                tmp[tmpActualSize] = nums[i];
                tmpActualSize++;
            } else {
                nagatvie[tmpNagativeActualSize] = nums[i];
                tmpNagativeActualSize++;
            }
        }
        int result = singleNumberByBase(tmp, false);
        if (result != -1) {
            return result;
        }
        int resultNegative = singleNumberByBase(nagatvie, true);

        if (resultNegative != -1) {
            return resultNegative;
        }
        return -1;
    }

    public static int singleNumberByBase(int[] nums, boolean negative) {
        int[][] tmp = new int[10][nums.length];
        int divider = 1;
        int[] subTmpSize = new int[10];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (!negative) {
                if (max < nums[i]) {
                    max = nums[i];
                }
            } else {
                if (max > nums[i]) {
                    max = nums[i];
                }
            }
        }
        while (max / divider >= 10 || max / divider <= -10) {
//            printList(nums);
//            System.out.println();
            //first insert to tmp array
            for (int i = 0; i < nums.length; i++) {
                int v = nums[i] / divider % 10;
                if (v < 0) {
                    v = -v;
                }
                int[] subTmp = tmp[v];
                subTmp[subTmpSize[v]] = nums[i];
                subTmpSize[v] += 1;
            }

            //back to num
            int backSize = 0;
            for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < subTmpSize[i]; j++) {
                    nums[backSize] = tmp[i][j];
                    backSize++;
                }
                subTmpSize[i] = 0;

            }

            divider *= 10;
        }
        for (int i = 0; i < nums.length; i++) {
            int left = i - 1;
            int right = i + 1;
            int current = i;
            if (i == 0) {
                if (nums[current] != nums[right]) {
                    return nums[current];
                }
            } else if (i == nums.length - 1) {
                if (nums[current] != nums[left]) {
                    return nums[current];
                }
            } else {
                if (nums[current] != nums[left] && nums[current] != nums[right]) {
                    return nums[current];
                }
            }
        }
        return -1;
    }


    public static int singleNumber4(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int size = 0;
        int negativeSize = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                size++;
            } else {
                negativeSize++;
            }
        }
        int tmp[] = new int[size];
        int tmpActualSize = 0;
        int negative[] = new int[negativeSize];
        int tmpNegativeActualSize = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                tmp[tmpActualSize] = nums[i];
                tmpActualSize++;
            } else {
                negative[tmpNegativeActualSize] = (nums[i] + 1) * -1;
                tmpNegativeActualSize++;
            }
        }
        int result = singleNumberByBase4(tmp);
        if (result != -1) {
            return result;
        }
        int resultNegative = singleNumberByBase4(negative);

        if (resultNegative != -1) {
            return resultNegative * -1 - 1;
        }
        return -1;
    }


    public static int singleNumberByBase4(int[] nums) {
        int[][] tmp = new int[10][nums.length];
        int divider = 1;
        int[] subTmpSize = new int[10];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (max < nums[i]) {
                max = nums[i];
            }
        }
        while (max / divider >= 1) {

            //first insert to tmp array
            for (int i = 0; i < nums.length; i++) {
                int v = nums[i] / divider % 10;
                int[] subTmp = tmp[v];
                subTmp[subTmpSize[v]] = nums[i];
                subTmpSize[v] += 1;
            }

            //back to num
            int backSize = 0;
            for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < subTmpSize[i]; j++) {
                    nums[backSize] = tmp[i][j];
                    backSize++;
                }
                subTmpSize[i] = 0;

            }

            divider *= 10;
        }
        for (int i = 0; i < nums.length; i++) {
            int left = i - 1;
            int right = i + 1;
            int current = i;
            if (i == 0) {
                if (nums[current] != nums[right]) {
                    return nums[current];
                }
            } else if (i == nums.length - 1) {
                if (nums[current] != nums[left]) {
                    return nums[current];
                }
            } else {
                if (nums[current] != nums[left] && nums[current] != nums[right]) {
                    return nums[current];
                }
            }
        }
        return -1;
    }


    public static int[] singleNumber5(int[] nums) {
        if (nums.length == 1) {
            return nums;
        }
        int size = 0;
        int negativeSize = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                size++;
            } else {
                negativeSize++;
            }
        }
        int tmp[] = new int[size];
        int tmpActualSize = 0;
        int negative[] = new int[negativeSize];
        int tmpNegativeActualSize = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                tmp[tmpActualSize] = nums[i];
                tmpActualSize++;
            } else {
                negative[tmpNegativeActualSize] = (nums[i] + 1) * -1;
                tmpNegativeActualSize++;
            }
        }
        int[] actualResult = new int[2];
        int actualResultPos = 0;
        int[] result = singleNumberByBase5(tmp);
        for (int i = 0; i < result.length; i++) {
            if (result[i] != -1) {
                actualResult[actualResultPos] = result[i];
                actualResultPos++;
            }
        }
        if (actualResultPos == 2) {
            return actualResult;
        }

        int[] resultNegative = singleNumberByBase5(negative);


        for (int i = 0; i < resultNegative.length; i++) {
            if (resultNegative[i] != -1) {
                actualResult[actualResultPos] = resultNegative[i] * -1 - 1;
                actualResultPos++;
            }
        }
        return actualResult;
    }


    public static int[] singleNumberByBase5(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return nums;
        }
        int[][] tmp = new int[10][nums.length];
        int divider = 1;
        int[] subTmpSize = new int[10];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (max < nums[i]) {
                max = nums[i];
            }
        }
        //还是用基排序吧
        while (max / divider >= 1) {

            //first insert to tmp array
            for (int i = 0; i < nums.length; i++) {
                int v = nums[i] / divider % 10;
                int[] subTmp = tmp[v];
                subTmp[subTmpSize[v]] = nums[i];
                subTmpSize[v] += 1;
            }

            //back to num
            int backSize = 0;
            for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < subTmpSize[i]; j++) {
                    nums[backSize] = tmp[i][j];
                    backSize++;
                }
                subTmpSize[i] = 0;

            }

            divider *= 10;
        }
        int result[] = {-1, -1};
        int resultCurrent = 0;
        for (int i = 0; i < nums.length; i++) {
            int left = i - 1;
            int right = i + 1;
            int current = i;
            if (i == 0) {
                if (nums[current] != nums[right]) {
                    result[resultCurrent] = nums[current];
                    resultCurrent++;
                }
            } else if (i == nums.length - 1) {
                if (nums[current] != nums[left]) {
                    result[resultCurrent] = nums[current];
                    resultCurrent++;
                }
            } else {
                if (nums[current] != nums[left] && nums[current] != nums[right]) {
                    result[resultCurrent] = nums[current];
                    resultCurrent++;
                }
            }
        }
        return result;
    }


    public static int[] plusOne(int[] digits) {
        int result = 0;
        int plus = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            result += digits[i] * plus;
            plus *= 10;
        }
        result += 1;

        int resultLength = digits.length;
        if (digits[digits.length - 1] == 9) {
            resultLength += 1;
        }
        int[] resultArray = new int[resultLength];

        plus = 1;
        while (result / plus >= 1) {
            resultArray[resultLength - 1] = result / plus % 10;
            plus *= 10;
            resultLength--;
        }


        return resultArray;
    }

    public static int[] plusOne2(int[] digits) {

        digits[digits.length - 1] += 1;


        boolean needMove = digits[digits.length - 1] == 10 ? true : false;
        if (needMove) {
            digits[digits.length - 1] = 0;
        }

        for (int i = digits.length - 2; i >= 0; i--) {
            if (needMove) {
                if (digits[i] == 9) {
                    digits[i] = 0;
                    needMove = true;
                } else {
                    needMove = false;
                    digits[i] += 1;
                }
            }
        }

        if (needMove) {
            int[] ret = new int[digits.length + 1];
            ret[0] = 1;
            for (int i = 1; i < digits.length + 1; i++) {
                ret[i] = digits[i - 1];
            }
            return ret;
        }

        return digits;
    }


    /**
     *
     * @param nums
     */
    public static void moveZeroes(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            //find right non-zero
            while (nums[right] == 0) {
                right--;
            }
            if (left >= right) {
                return;
            }

            //finde left zero
            if (nums[left] == 0) {
                int tmp = nums[right];
                nums[right] = nums[left];
                nums[left] = tmp;
                left++;
                right--;
            } else {
                left++;
            }
        }
    }

    /**
     * 必须在原数组上操作，不能拷贝额外的数组。
     * 尽量减少操作次数。
     *
     * @param nums
     */
    public static void moveZeroes2(int[] nums) {
        if (nums.length < 2) {
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            int current = i;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] != 0) {
                    break;
                } else {
                    nums[j] = nums[current];
                    nums[current] = 0;
                    current--;
                }
            }
        }
    }

    public static void moveZeroes3(int[] nums) {
        if (nums.length < 2) {
            return;
        }
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i == left) {
                    left++;
                    continue;
                }
                nums[left] = nums[i];
                nums[i] = 0;
                left++;
            }
        }
    }


    public static int[] intersect(int[] nums1, int[] nums2) {
        int[] lessNum;
        int[] moreNum;
        if (nums1.length > nums2.length) {
            lessNum = nums2;
            moreNum = nums1;
        } else {
            lessNum = nums1;
            moreNum = nums2;
        }
        int[] ret = new int[lessNum.length];
        int findPos = 0;
        for (int i = 0; i < lessNum.length; i++) {
            for (int j = findPos; j < moreNum.length; j++) {
                if (lessNum[i] == moreNum[j]) {
                    ret[findPos] = lessNum[i];
                    //remove from moreNum
                    moreNum[j] = moreNum[findPos];
                    findPos++;
                    break;
                }
            }
        }

        int[] actualRet = new int[findPos];
        for (int i = 0; i < findPos; i++) {
            actualRet[i] = ret[i];
        }
        return actualRet;
    }


    public static int[] twoSum(int[] nums, int target) {

        int actualSize = 0;
        for (int i : nums) {
            if (i <= target) {
                actualSize++;
            }
        }
        int[] actualArray = new int[actualSize];
        int[] actualIndex = new int[actualSize];
        int insertNum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= target) {
                actualArray[insertNum] = nums[i];
                actualIndex[insertNum] = i;
                insertNum++;
            }
        }

        quickSort(actualArray, 0, actualArray.length - 1);
        for (int i = 0; i < actualArray.length; i++) {
            int remain = target - actualArray[i];
            for (int j = actualArray.length - 1; j >= 0; j--) {
                if (remain == actualArray[j]) {
                    return new int[]{actualIndex[i], actualIndex[j]};
                }
            }
        }
        return null;
    }


    public static int[] twoSum2(int[] nums, int target) {

//        int actualSize = 0 ;
//        for (int i : nums){
//            if (i <= target){
//                actualSize++;
//            }
//        }
//        int[] actualArray = new int[actualSize];
//        int[] actualIndex = new int[actualSize];
//        int insertNum = 0;
//        for (int i = 0 ; i < nums.length ; i ++){
//            if (nums[i] <= target){
//                actualArray[insertNum] = nums[i];
//                actualIndex[insertNum] = i;
//                insertNum++;
//            }
//        }
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (int i = 0; i < nums.length; i++) {
            int remain = target - nums[i];
            if (map.get(remain) != null) {
                return new int[]{i, map.get(remain)};
            } else {
                map.put(nums[i], i);
            }
        }
        return null;
    }


    private static void quickSort(int[] nums, int left, int right) {
        if (nums.length == 1) {
            return;
        }
        if (left < right) {
            int point = doQuickSort(nums, left, right);
//            int middle = (left+right)/2;
            quickSort(nums, left, point);
            quickSort(nums, point + 1, right);
        }
    }

    private static int doQuickSort(int[] nums, int left, int right) {

        int posLeft = left;
        int posRight = right;
        int pivot = nums[left];
        while (posLeft < posRight) {
            while (posLeft < posRight && nums[posRight] >= pivot) {
                posRight--;
            }
            if (posLeft >= posRight) {
                break;
            }
            nums[posLeft] = nums[posRight];
            posLeft++;
            while (posLeft < posRight && nums[posLeft] <= pivot) {
                posLeft++;
            }

            if (posLeft >= posRight) {
                break;
            }
            nums[posRight] = nums[posLeft];
            posRight--;
        }
        nums[posLeft] = pivot;
        return posLeft;
    }


    /**
     * 数字的ascii是48到57
     *
     * @param board
     * @return
     */
    public static boolean isValidSudoku(char[][] board) {
        int[] base = new int[10];
        //横
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int v = board[i][j];
                if (v >= 48 && v <= 57) {
                    if (base[v - 48] == 1) {
                        return false;
                    }
                    base[v - 48] += 1;
                }
            }
            for (int ii = 0; ii < 10; ii++) {
                base[ii] = 0;
            }
        }
        for (int i = 0; i < 10; i++) {
            base[i] = 0;
        }
        //竖
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int v = board[j][i];
                if (v >= 48 && v <= 57) {
                    if (base[v - 48] == 1) {
                        return false;
                    }
                    base[v - 48] += 1;
                }
            }
            for (int ii = 0; ii < 10; ii++) {
                base[ii] = 0;
            }
        }
        for (int i = 0; i < 10; i++) {
            base[i] = 0;
        }


        //3*3
        for (int a = 0; a < 9; a++) {
            int n = a * 3;
            int actualColumn = n % 9;
            int actualRow = n / 9;
//            System.out.println("actualColumn " + actualColumn + " actualRow " + actualRow);
            for (int i = 3*actualRow ; i < 3*actualRow +3 ; i++){
                for (int j = actualColumn ; j < actualColumn +3 ; j++){
//                    System.out.println("point  x " + j + " y " + i);
                    int v = board[i][j];
                    if (v >= 48 && v <= 57) {
                        if (base[v - 48] == 1) {
                            return false;
                        }
                        base[v - 48] += 1;
                    }
                }
            }
//            for (int j = actualColumn; j < actualColumn + 3; j++) {
//                for (int jj = actualRow; jj < actualRow + 3; jj++) {
//                    int v = board[jj][j];
//                    if (v >= 48 && v <= 57) {
//                        if (base[v - 48] == 1) {
//                            return false;
//                        }
//                        base[v - 48] += 1;
//                    }
//                }
//            }
            for (int ii = 0; ii < 10; ii++) {
                base[ii] = 0;
            }
        }
        return true;

    }


    public static void rotate(int[][] matrix) {
        int length = matrix.length;
        for (int x = 0 ; x < length-1 ; x++) {
            int hold = matrix[x][0];
            int posX = x;
            for (int y = 0; y < 4; y++) {


                int nextX = posX;
                if (y == 0) {
                    nextX = length - 1;
                } else if (y == 2) {
                    nextX = x;
                }
                int nextY = posX;

                int nextValue = matrix[nextY][nextX];
                matrix[nextY][nextX] = hold;
                hold = nextValue;
                posX = nextX;
            }
        }
    }




}
