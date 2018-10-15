package top.lixiangfei.www.leetcode;

import java.util.ArrayList;
import java.util.List;

public class leetcodeTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public void test() {
        TreeNode treeNode = mock();
//        TreeNode treeNode = mockBST();
        print(treeNode);
        System.out.println();
//        int depth = maxDepth(treeNode);
//        boolean result = isSymmetric(null);
//        boolean result = isSymmetric(treeNode);
        List<List<Integer>> result = levelOrder(treeNode);
        System.out.println(result);
    }

    public TreeNode mock() {
        TreeNode root = new TreeNode(0);
        TreeNode l11 = new TreeNode(1);
        TreeNode l12 = new TreeNode(2);
        TreeNode l21 = new TreeNode(3);
        TreeNode l22 = new TreeNode(4);
        TreeNode l23 = new TreeNode(5);
        TreeNode l24 = new TreeNode(6);
        root.left = l11;
        root.right = l12;
        l11.left = l21;
        l11.right = l22;
        l12.left = l23;
        l12.right = l24;
        return root;
    }
//    [2147483644,-2147483648,2147483646,null,null,2147483645,2147483647]

    public TreeNode mockBST() {
        TreeNode root = new TreeNode(2147483644);
        TreeNode l11 = new TreeNode(-2147483648);
        TreeNode l12 = new TreeNode(2147483646);
        TreeNode l21 = new TreeNode(1);
        TreeNode l22 = new TreeNode(6);
        TreeNode l23 = new TreeNode(2147483645);
        TreeNode l24 = new TreeNode(2147483647);
        root.left = l11;
        root.right = l12;
//        l11.left = l21;
//        l11.right = l22;
        l12.left = l23;
        l12.right = l24;
        return root;
    }

    public void print(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val);
        System.out.print(",");
        print(root.left);
        print(root.right);

    }

    public int maxDepth(TreeNode root) {
        return maxDepth(root, 1);
    }

    public int maxDepth(TreeNode root, int depth) {
        if (root == null) {
            return depth - 1;
        }
        int left = maxDepth(root.left, depth + 1);
        int right = maxDepth(root.right, depth + 1);
        return left > right ? left : right;
    }


    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left != null) {
            if (root.val <= root.left.val) {
                return false;
            }
        }

        if (root.right != null) {
            if (root.val >= root.right.val) {
                return false;
            }
        }
        boolean leftResult = isValidBST(root.left, null,root);
        boolean rightResult = isValidBST(root.right,root,null);
        return leftResult && rightResult;
    }


    public boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) {
            return true;
        }
        if (root.left != null) {
            if (root.val <= root.left.val) {
                return false;
            }
        }

        if (root.right != null) {
            if (root.val >= root.right.val) {
                return false;
            }
        }

        if (min != null){
            if(!(root.val > min.val)){
                return false;
            }
        }
        if (max != null){
            if (!(root.val < max.val)){
                return false;
            }
        }

        boolean leftResult = isValidBST(root.left, min, root);
        boolean rightResult = isValidBST(root.right, root, max);
        return leftResult && rightResult;

    }
//    public static class Node{
//        TreeNode node;
//        Node next;
//
//        public Node(TreeNode node){
//            this.node = node;
//        }
//    }
    public boolean isSymmetric(TreeNode root) {
        if (root == null){
            return true;
        }
        if (root.left == null && root.right ==null){
            return true;
        }

        TreeNode[] leftArray = new TreeNode[1000];
        TreeNode[] rightArray = new TreeNode[1000];
        int leftCount = getAllNode(root.left,leftArray,0);
        int rgihtCount = getRightAllNode(root.right,rightArray,0);
        if (leftCount != rgihtCount){
            return false;
        }
        for (int i = 0 ; i < leftCount ; i++){
            if (leftArray[i] == null &&rightArray[i] == null){
                continue;
            }
            if (leftArray[i] == null){
                return false;
            }
            if (rightArray[i] == null){
                return false;
            }
            if (leftArray[i].val != rightArray[i].val){
                return false;
            }
        }
        return true;
    }

    public int getAllNode(TreeNode root,TreeNode[] nodeArray,int currentPos){
        if (root == null){
            nodeArray[currentPos] = null;
            return currentPos;
        }
        nodeArray[currentPos] = root;
        int leftDonePos =  getAllNode(root.left,nodeArray,currentPos+1);
        int rightDonePos = getAllNode(root.right,nodeArray,leftDonePos+1);
        return rightDonePos;
    }


    public int getRightAllNode(TreeNode root,TreeNode[] nodeArray,int currentPos){
        if (root == null){
            nodeArray[currentPos] = null;
            return currentPos;
        }
        nodeArray[currentPos] = root;
        int rightDonePos = getRightAllNode(root.right,nodeArray,currentPos+1);
        int leftDonePos =  getRightAllNode(root.left,nodeArray,rightDonePos+1);
        return leftDonePos;
    }


    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> baseList = new ArrayList<>();
//        if (root == null){
//            return baseList;
//        }
//        List<Integer> list = new ArrayList<>();
//        list.add(root.val);
//        baseList.add(list);

        processLevelOrder2(root,baseList,0);
//        processLevelOrder(root,baseList,1);
        return baseList;
    }

    public void processLevelOrder2(TreeNode node,List<List<Integer>> resultList,int level){
        System.out.println("level "+ level);
        if (node == null){
            return;
        }
        List<Integer> list ;
        if (level < resultList.size()){
            list = resultList.get(level);
            list.add(node.val);

        }else {
            list = new ArrayList<>();
            list.add(node.val);
            resultList.add(list);
        }
        processLevelOrder2(node.left,resultList,level+1);
        processLevelOrder2(node.right,resultList,level+1);
    }

    public void processLevelOrder(TreeNode node,List<List<Integer>> resultList,int level){
        System.out.println("level "+ level);
        if (node == null){
            return;
        }
        List<Integer> list ;
        if (level < resultList.size()){
            list = resultList.get(level);
        }else {
            list = new ArrayList<>();
        }
        if (node.left != null){
            list.add(node.left.val);
        }
        if (node.right != null){
            list.add(node.right.val);
        }
        if (list.size() == 0){
            return;
        }
        resultList.add(list);
        processLevelOrder(node.left,resultList,level+1);
        processLevelOrder(node.right,resultList,level+1);
    }
//    public boolean isValidBST(TreeNode root, int min, int max,boolean haveBottom,boolean haveTop) {
//        if (root == null) {
//            return true;
//        }
//        if (haveTop){
//            if (!(root.val < max)) {
//                return false;
//            }
//        }
//
//        if (haveBottom){
//            if (!(root.val > min)) {
//                return false;
//            }
//        }
//
//
//
//        if (root.left != null) {
//            if (root.val <= root.left.val) {
//                return false;
//            }
//        }
//
//        if (root.right != null) {
//            if (root.val >= root.right.val) {
//                return false;
//            }
//        }
//
//
//
//        boolean leftResult = isValidBST(root.left, min, root.val);
//        boolean rightResult = isValidBST(root.right, root.val, max);
//        return leftResult && rightResult;
//    }

}
