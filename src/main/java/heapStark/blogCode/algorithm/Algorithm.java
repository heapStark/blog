package heapStark.blogCode.algorithm;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/25.
 */
public class Algorithm {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode re = new ListNode((l1.val + l2.val) % 10);
        re.next = addTwoNumbers(l1.next, l2.next, (l1.val + l2.val) / 10);
        return re;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2, int x) {
        if (l1 == null && l2 == null && x == 0) {
            return null;
        }
        if (l1 == null) {
            return addTwoNumbers(l2, new ListNode(x));
        }
        if (l2 == null) {
            return addTwoNumbers(l1, new ListNode(x));
        }
        ListNode re = new ListNode((l1.val + l2.val + x) % 10);
        re.next = addTwoNumbers(l1.next, l2.next, (l1.val + l2.val + x) / 10);
        return re;
    }

    /**
     * 合并有序数组
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        if (null == lists || lists.size() <= 0)
            return null;
        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(lists.size(), new Comp());
        for (ListNode head : lists) {
            if (null != head)
                queue.offer(head);
        }
        ListNode dummy = new ListNode(0);
        ListNode pnode = dummy;
        while (!queue.isEmpty()) {
            ListNode node = queue.poll();
            pnode.next = node;
            if (node.next != null)
                queue.offer(node.next);
            pnode = pnode.next;

        }
        return dummy.next;

    }

    @Test
    public void sortedListToBSTTest() {
        ListNode listNode = new ListNode(3);
        listNode.next = new ListNode(5);
        //listNode.next.next = new ListNode(8);
        TreeNode treeNode = sortedListToBST(listNode);
        System.out.print("");
    }

    /**
     * 有序列表转二叉搜索树
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        TreeNode root = new TreeNode(slow.next.val);
        root.right = sortedListToBST(slow.next.next);
        slow.next = null;
        root.left = sortedListToBST(head);
        return root;
    }

    /**
     * 快排
     *
     * @param array
     * @param startIndex
     * @param endIndex
     */
    private void sortCore(int[] array, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }

        int boundary = boundary(array, startIndex, endIndex);
        sortCore(array, startIndex, boundary - 1);
        sortCore(array, boundary + 1, endIndex);
    }

    /**
     * 切分数组
     *
     * @param array
     * @param startIndex
     * @param endIndex
     * @return
     */
    private int boundary(int[] array, int startIndex, int endIndex) {
        int standard = array[startIndex]; // 定义标准
        int leftIndex = startIndex; // 左指针
        int rightIndex = endIndex; // 右指针

        while (leftIndex < rightIndex) {
            while (leftIndex < rightIndex && array[rightIndex] >= standard) {
                rightIndex--;
            }
            array[leftIndex] = array[rightIndex];

            while (leftIndex < rightIndex && array[leftIndex] <= standard) {
                leftIndex++;
            }
            array[rightIndex] = array[leftIndex];
        }

        array[leftIndex] = standard;
        return leftIndex;
    }

    public ArrayList<TreeNode> generateTrees(int n) {

        return generateTrees(1, n);
    }

    public ArrayList<TreeNode> generateTrees(int n, int m) {
        if (n < 1) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = n; i <= m; i++) {
            TreeNode treeNode = new TreeNode(i);
            ArrayList arrayList1 = generateTrees(n, i - 1);
            ArrayList arrayList2 = generateTrees(i + 1, m);

        }

        return arrayList;
    }
}

class Comp implements Comparator<ListNode> {
    public int compare(ListNode o1, ListNode o2) {
        return o1.val - o2.val;
    }
}
