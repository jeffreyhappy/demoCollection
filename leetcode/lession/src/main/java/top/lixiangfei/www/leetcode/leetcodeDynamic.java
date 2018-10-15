package top.lixiangfei.www.leetcode;

import java.util.ArrayList;
import java.util.List;

public class leetcodeDynamic {

    public void test(){
//        int result = climbStairs(4);
//        System.out.println(result);
//        int[] nums = {10,-1,0,3,4,1,2,3,4,5,6,-1};
//        int[] nums = {10,11,10,-1,-2,-3};
//        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
//        int[] nums = {7,1,5,3,6,4};
//        int[] nums = {7,6,4,3,1};
//        int[] nums = {1,2,3,1};
//        int[] nums = {2,7,9,3,1};
//        int result = maxSubArray2(nums);
//        int result = maxProfit(nums);
//        int result = rob(nums);
//        List<String> result = fizzBuzz(16);
//        int result = countPrimes2(10);
//        boolean result = isPowerOfThree(9);
//        int count = hammingWeight(  2147483647);
//        int count = hammingWeight(11);
//        int count = hammingDistance(1,4);
//        int result  = reverseBits(43261596);
        List<List<Integer>> result  = generate(5);
        PrintUtils.print(result);
    }

    public int climbStairs(int n) {
        return climbBottom2Top(n);
//        int[] array = new int[n+1];
//        for (int i = 0 ; i < array.length ; i++){
//            array[i] = -1;
//        }
//        return climb(n,array);
    }


    public int climb(int n ,int[] array){
        if (n == 1){
            return 1;
        }
        if (n == 2){
            return 2;
        }
        if (array[n] != -1){
            return array[n];
        }
        int step = climb(n-1,array)+climb(n-2,array);
        array[n] = step;
        return step;
    }

    public int climbBottom2Top(int n ){
        if (n == 1){
            return 1;
        }
        if (n == 2){
            return 2;
        }


        int pos = 3;
        int prev1 = 1;
        int prev2 = 2;
        int result = 0;
        while (pos <= n){
            result = prev1 + prev2;
            prev1 = prev2;
            prev2 = result;
            pos++;
        }
        return result;
    }



    public int maxSubArray(int[] nums) {
        if (nums.length == 0){
            return 0;
        }
        int pos = 1;
        int result = nums[0];
        int maxResult = nums[0];
        while (pos < nums.length){
            if (nums[pos] > nums[pos-1]){
                result+=nums[pos];
            }else {
                if (result > maxResult){
                    maxResult = result;
                }
                result=nums[pos];
            }
            pos++;
        }
        return maxResult;
    }

    public int maxSubArray2(int[] nums) {
        if (nums.length == 0){
            return 0;
        }
        int currentSum = nums[0];
        int maxSum = nums[0];
        for (int i = 1 ; i < nums.length ; i++){
            if (currentSum < 0){
                currentSum = nums[i];
            }else {
                currentSum+=nums[i];
            }

            maxSum = currentSum > maxSum ? currentSum :maxSum;
        }
        return maxSum;
    }


    public int maxProfit(int[] prices) {

        if (prices.length == 0){
            return 0;
        }
        int buy = prices[0];
        int result = 0;
        int maxResult = 0;
        for (int i = 1; i < prices.length;i++){
            int current = prices[i];
            if (buy > current ){
                buy = current;
            }else {
                result = current - buy;
            }
            if (result > maxResult){
                maxResult = result;
            }
        }
        return maxResult;
    }



    public int rob(int[] nums) {

//        int currentResult = nums[0];
//        int maxtResult = nums[0];
//        int lastPos = 0;
//        for (int i = 1 ; i < nums.length ;i++){
//            if (i - lastPos == 1){
//                if (nums[i] > currentResult){
//                    currentResult = nums[i];
//                }
//            }else {
//                currentResult+= nums[i];
//                lastPos = i;
//            }
//
//            if (maxtResult < currentResult){
//                maxtResult = currentResult;
//            }
//        }
        if (nums == null){
            return 0;
        }
        if (nums.length == 0){
            return 0;
        }
        int[] resultTmp = new int[nums.length];
        for (int i = 0 ; i < nums.length ; i++){
            resultTmp[i] = -1;
        }
        return sumRob(nums.length-1,nums,resultTmp);
    }


    public int sumRob(int n ,int[] nums,int[] resultTmp){


        if (n == 0){
            return nums[0];
        }
        if (n == 1){
            return nums[0] > nums [1] ? nums[0]:nums[1];
        }

        if (resultTmp[n] != -1){
            return resultTmp[n];
        }
        int pre1 = sumRob(n-1,nums,resultTmp);
        int pre2 = sumRob(n-2,nums,resultTmp);
        if (pre1 > pre2 + nums[n]){
            resultTmp[n] = pre1;
            return pre1;
        }else {
            resultTmp[n] = pre2 + nums[n];
            return pre2 + nums[n];
        }
    }


    public List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 1 ; i <= n ; i ++){
            String value = String.valueOf(i);
            if ( i % 3 == 0){
                value = "Fizz";
            }
            if (i % 5 == 0){
                value = "Buzz";
            }
            if (i % 15 == 0){
                value = "FizzBuzz";
            }
            list.add(value);
        }
        return list;
    }


    public int countPrimes(int n) {
        if (n == 0){
            return 0;
        }
        if (n == 1){
            return 0;
        }
        int count = 0;
        int max = n/2+1;

        for (int i = 2 ; i < n ; i++){
            boolean isPrime = true;
            for (int j = 2; j < max ; j++){
                if (i %j == 0 && i != j){
                    isPrime = false;
                    break;
                }
            }
            if (isPrime){
//                PrintUtils.print(i);
                count++;
            }
        }
        return count;
    }


    public int countPrimes2(int n) {
        if (n == 0){
            return 0;
        }
        if (n == 1){
            return 0;
        }
        int count = 0;
        int[] mark = new int[n+1];
        for (int i = 0 ; i < mark.length ; i++){
            mark[i] = 1;
        }
        for (int i = 2 ; i < n ; i++){
            if (mark[i] == 1){
                for (int j = i ; j < n ; j= j+i){
                    mark[j] = -1;
                }
                count++;
            }

        }
        return count;
    }


    public boolean isPowerOfThree(int n) {
        if(n == 0){
            return false;
        }
        if (n == 1){
            return true;
        }
        int result = n / 3;
        int remain = n % 3;
        while (result > 1){
            if (result > 0 && result < 3){
                return false;
            }
            if (remain != 0){
                return false;
            }
            remain = result % 3;
            result= result /3;
        }

        if (result == 0){
            return false;
        }
        if (result == 1 && remain == 0){
            return true;
        }else {
            return false;
        }


    }



    public int hammingWeight(int n) {
        int count = 0;
        int moveCount = 32;
        while (moveCount != 0 ){
            int result = n& 1;
            if (result == 1){
                count++;
            }
            n = n >> 1;
            moveCount--;
        }

        return count;
    }


    public int hammingDistance(int x, int y) {
        int count = 0;
        int moveCount = 32;
        int n = x^y;
        while (moveCount != 0 ){
            int result = n&1;
            if (result == 1){
                count++;
            }
            n = n >> 1;
            moveCount--;
        }

        return count;
    }


    public int reverseBits(int n) {
        int result = 0;
        int moveCount = 32;
        while (moveCount != 0 ){
            int oneResult = n& 1;
            result+=oneResult;
            n = n >> 1;
            moveCount--;
            if (moveCount != 0){
                result = result << 1;
            }
        }
        return result;
    }


    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> resultList = new ArrayList<>();
        int row  = 0;
        while (row < numRows){
            List<Integer> oneRow = new ArrayList<>();
            if (row > 1){
                List<Integer> lastRow =  resultList.get(row-1);
                int thisRowCount = lastRow.size() +1;
                oneRow.add(1);
                for (int i = 1; i < thisRowCount-1 ; i++){
                    oneRow.add(lastRow.get(i-1)+lastRow.get(i));
                }
                oneRow.add(1);
            }else if (row == 1){
                oneRow.add(1);
                oneRow.add(1);
            }else {
                oneRow.add(1);
            }
            resultList.add(oneRow);
            row++;
        }
        return resultList;
    }

}
