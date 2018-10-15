package top.lixiangfei.www.leetcode;

import java.util.Random;

public class leetcodeNode {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public void test() {
        ListNode listNode = mockRandom(4);
        ListNode listNode2 = mockRandom(4);
//        ListNode listNode = new ListNode(2);
//        ListNode listNode2 = new ListNode(1);
//        printList(listNode);
//        printList(listNode2);
        ListNode mock = new ListNode(3);
        ListNode mock2 = new ListNode(2);
        ListNode mock3 = new ListNode(0);
        ListNode mock4 = new ListNode(-4);
        ListNode mock5 = new ListNode(2);
        ListNode mock6 = new ListNode(3);
        mock.next = mock2;
        mock2.next = mock3;
        mock3.next = mock;
//        mock4.next = mock5;
//        mock5.next = mock6;



//        ListNode result = reverseList2(listNode);
//        ListNode result = mergeTwoLists(listNode,listNode2);
//        printList(mock);
//        boolean result = hasCycle(mock);
        ListNode listNodeOne = mock(1);
        ListNode listNodeTwo = mock(9,9);
        PrintUtils.printNode(listNodeOne);
        PrintUtils.printNode(listNodeTwo);
        ListNode result = addTwoNumbers(listNodeOne,listNodeTwo);
        PrintUtils.printNode(result);
//        System.out.println(result);
    }


    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }


    public ListNode mock() {
//        ListNode listNode = new ListNode(1);
//        ListNode listNode2 = new ListNode(2);
//        ListNode listNode3 = new ListNode(3);
//        ListNode listNode4 = new ListNode(4);
//        ListNode listNode5 = new ListNode(5);
//        listNode.next = listNode2;
//        listNode2.next = listNode3;
//        listNode3.next = listNode4;
//        listNode4.next = listNode5;
//        return listNode;
        return mock(5);
    }


    public ListNode mock(int count ) {
        ListNode head = new ListNode(1);
        ListNode current = head;
        for (int i = 1 ; i < count ; i++){
            current.next = new ListNode(i);
            current = current.next;
        }
        return head;
//        ListNode listNode = new ListNode(1);
//        ListNode listNode2 = new ListNode(2);
//        ListNode listNode3 = new ListNode(3);
//        ListNode listNode4 = new ListNode(4);
//        ListNode listNode5 = new ListNode(5);
//        listNode.next = listNode2;
//        listNode2.next = listNode3;
//        listNode3.next = listNode4;
//        listNode4.next = listNode5;
    }

    public ListNode mock(int ... data){
        ListNode head = new ListNode(-1);
        ListNode current = head;

        for (int i = 0 ; i < data.length ; i ++){
            ListNode newNode = new ListNode(data[i]);
            current.next = newNode;
            current  = newNode;
        }
        return head.next;
    }

    public ListNode mockRandom(int count ) {
        ListNode head = new ListNode(1);
        ListNode current = head;
        Random random = new Random();
        for (int i = 1 ; i < count ; i++){
            current.next = new ListNode(random.nextInt(10));
            current = current.next;
        }
        return head;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        int count = 1;
        ListNode current = head;
        while (current.next != null) {
            current = current.next;
            count++;
        }
        if (count == 1 && n == 1) {
            return null;
        }

        int pos = count - n;
        if (pos == 0) {
            return head.next;
        }
        int currentPos = 0;
        ListNode currentNodeAgain = head;

        while (currentPos != pos) {
            if (currentPos + 1 == pos) {
                currentNodeAgain.next = currentNodeAgain.next.next;
                break;
            }
            currentNodeAgain = currentNodeAgain.next;
            currentPos++;
        }

        return head;
    }

    private void printList(ListNode node) {
        while (node.next != null) {
            System.out.print(node.val);
            System.out.print(",");
            node = node.next;
        }
        System.out.println(node.val);
    }

    public ListNode reverseList(ListNode head) {
        if (head == null){
            return head;
        }
        if (head.next == null){
            return head;
        }
        ListNode currentNode = head;
        ListNode newHead = null;
        while (currentNode.next != null) {
            if (currentNode.next.next == null) {
                newHead = currentNode.next;
                currentNode.next = null;
                break;
            }
            currentNode = currentNode.next;

        }


        currentNode = head;
        ListNode newNode = newHead;
        while (currentNode.next != null) {
            if (currentNode.next.next == null) {
                newNode.next = currentNode.next;
                newNode = newNode.next;
                currentNode.next = null;
                currentNode = head;
                continue;
            }
            currentNode = currentNode.next;

        }
        newNode.next = head;
        return newHead;

    }



    public ListNode reverseList2(ListNode head) {
        if (head == null){
            return head;
        }
        if (head.next == null){
            return head;
        }

        ListNode tail = head;
        ListNode movedHead = head;
        while ( movedHead.next != null){
            ListNode next = movedHead.next;
            head.next = head.next.next;
            next.next = tail;
            tail = next;
        }
        return tail;
    }


    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null){
            return l2;
        }
        if (l2 == null){
            return l1;
        }
        ListNode head ;
        if (l1.val < l2.val){
            head = l1;
            l1 = l1.next;
        }else {
            head = l2;
            l2 = l2.next;
        }




        ListNode currentNode = head;
        while (true){
            ListNode newL1 = l1;
            ListNode newL2 = l2;
            if (newL1 == null){
                currentNode.next = newL2;
                break;
            }
            if (newL2 == null){
                currentNode.next = newL1;
                break;
            }
            if (newL1.val < newL2.val){
                currentNode.next = newL1;
                l1 = l1.next;
            }else {
                currentNode.next = newL2;
                l2 = l2.next;
            }
            currentNode = currentNode.next;
        }

        return head;
    }


    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null){
            return l2;
        }
        if (l2 == null){
            return l1;
        }
        ListNode head = new ListNode(-1);
        ListNode currentNode = head;
        while (true){
            if (l1 == null){
                currentNode.next = l2;
                break;
            }
            if (l2 == null){
                currentNode.next = l1;
                break;
            }
            if (l1.val < l2.val){
                currentNode.next = l1;
                l1 = l1.next;
            }else {
                currentNode.next = l2;
                l2 = l2.next;
            }
            currentNode = currentNode.next;
        }

        return head.next;
    }


    public boolean isPalindrome(ListNode head) {
        if (head==null){
            return true;
        }
        if (head.next == null){
            return true;
        }
        ListNode reversedHead = new ListNode(head.val);
        ListNode current = head.next;
        while (current != null){
            ListNode pre = new ListNode(current.val);
            pre.next = reversedHead;
            reversedHead = pre;
            current = current.next;
        }
        while (reversedHead != null && head != null){
            if (reversedHead.val != head.val){
                return false;
            }
            reversedHead = reversedHead.next;
            head = head.next;
        }
        return true;

    }


    public boolean isPalindrome2(ListNode head) {
        if (head==null){
            return true;
        }
        if (head.next == null){
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (true){
            if (slow == null || (slow.next == null)){
                return true;
            }
            if (fast.next == null ){
                if (slow.val == fast.val){
                    return true;
                }else {
                    return false;
                }
            }
            if (fast.next.val == slow.val && fast.next.next == null){
                fast.next = null;
                slow = slow.next;
                fast = slow;
            }else {
                fast = fast.next;
            }
        }
    }


    public boolean hasCycle(ListNode head) {
        if (head == null){
            return false;
        }
        if (head.next == null){
            return false;
        }
        ListNode next = head.next;
        while (next != null){
            if (next == head){
                return true;
            }
            next = next.next;
        }
        return false;
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode resultNode = new ListNode(-1);
        ListNode currentNode = resultNode;
        boolean needAdd1 = false;
        while (true){
            ListNode newNode ;
            if (l1 == null && l2 == null ){
                if ( needAdd1){
                    newNode = new ListNode(1);
                    currentNode.next = newNode;
                }
                break;
            }
            if (l1 == null || l2 == null){
                while (l1 != null){
                    int value = l1.val + (needAdd1 ? 1 : 0);
                    if (value >= 10){
                        newNode = new ListNode(value-10);
                        needAdd1 = true;
                    }else {
                        newNode = new ListNode(value);
                        needAdd1 = false;
                    }
                    currentNode.next = newNode;
                    currentNode = newNode;
                    l1 = l1.next;
                }

                while (l2 != null){
                    int value = l2.val + (needAdd1 ? 1 : 0);
                    if (value >= 10){
                        newNode = new ListNode(value-10);
                        needAdd1 = true;
                    }else {
                        newNode = new ListNode(value);
                        needAdd1 = false;
                    }
                    currentNode.next = newNode;
                    currentNode = newNode;
                    l2 = l2.next;
                }
                continue;
            }

            ListNode left  = l1;
            ListNode right = l2;
            int value = left.val + right.val + (needAdd1 ? 1 : 0) ;
            if (value >= 10){
                newNode = new ListNode(value-10);
                needAdd1 = true;
            }else {
                newNode = new ListNode(value);
                needAdd1 = false;
            }
            currentNode.next = newNode;
            currentNode = newNode;
            l1 = l1.next;
            l2 = l2.next;
        }
        return resultNode.next;
    }
}
