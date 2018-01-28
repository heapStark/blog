package heapStark.blogCode.algorithm;

import java.util.*;

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

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return root.val;
        } else {
            int k = Math.max(root.val + maxPathSum(root.left), root.val + maxPathSum(root.right));
            k = Math.max(k, root.val);
            if (k > 0) {
                return k;
            } else return 0;
        }
    }

    public int longestValidParenthesesDP(String s) {
        if (s.length() == 0 || s.equals("")) {
            return 0;
        }
        char[] st = s.toCharArray();
        int[] dp = new int[st.length];
        int pre = 0;
        int res = 0;
        for (int i = 1; i < st.length; i++) {
            if (st[i] == ')') {
                pre = i - dp[i - 1] - 1;
                if (pre >= 0 && st[pre] == '(') {
                    dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public int longestValidParenthesesS(String s) {
        if (s == null || s.length() < 1)
            return 0;
        Stack<Integer> stack = new Stack<Integer>();
        int max = 0, left = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(')
                stack.push(i);
            else {
                if (!stack.isEmpty()) {
                    stack.pop();
                    if (!stack.isEmpty())
                        max = Math.max(max, i - stack.peek());
                    else
                        max = Math.max(max, i - left);
                } else
                    left = i;
            }
        }
        return max;
    }

    public boolean hasPathSum(TreeNode root, int target) {
        if (root == null) return false;

        if (root.val == target && root.left == null && root.right == null) return true;
        return hasPathSum(root.left, target - root.val)
                || hasPathSum(root.right, target - root.val);
    }

    ArrayList<ArrayList<Integer>> lists = new ArrayList<>();

    public void hasPathSumHelp(TreeNode root, int target, ArrayList<Integer> list) {
        if (root == null) return;
        list.add(root.val);
        if (root.val == target && root.left == null && root.right == null) {
            lists.add(list);
            return;
        } else {
            hasPathSumHelp(root.right, target - root.val, list);
            hasPathSumHelp(root.left, target - root.val, new ArrayList<>(list));
        }

    }

    public ArrayList<ArrayList<Integer>> pathSum(TreeNode root, int sum) {
        hasPathSumHelp(root, sum, new ArrayList<>());
        return lists;
    }


    ArrayList<ArrayList<Integer>> res = null;

    public ArrayList<ArrayList<Integer>> levelOrderBottom(TreeNode root) {
        res = new ArrayList<ArrayList<Integer>>();
        if (root == null)
            return res;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        levelOrderBottom(queue);
        return res;
    }

    private void levelOrderBottom(Queue<TreeNode> queue) {
        if (queue.isEmpty())
            return;
        int size = queue.size();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            if (node.left != null)
                queue.offer(node.left);
            if (node.right != null)
                queue.offer(node.right);
            list.add(node.val);
        }
        // 理解堆栈的原理，先进行递归，再将list保存到res中
        levelOrderBottom(queue);
        res.add(list);
    }


    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (root == null) return res;
        inorder(root, res);
        return res;
    }

    private static void inorder(TreeNode root, ArrayList<Integer> list) {
        if (root != null) {
            inorder(root.left, list);
            list.add(root.val);
            inorder(root.right, list);
        }
    }

    public int maxProfit(int[] prices) {
        int buy1 = Integer.MIN_VALUE, sell1 = 0, buy2 = Integer.MIN_VALUE, sell2 = 0;
        for (int i = 0; i < prices.length; i++) {
            buy1 = Math.max(buy1, -prices[i]);   //记录之前所有天最便宜的股价
            sell1 = Math.max(sell1, buy1 + prices[i]);  //记录之前所有天只进行1次买卖的最大利益
            buy2 = Math.max(buy2, sell1 - prices[i]);   //记录之前天先进行1次交易获得最大利益后，
            //再买到那次交易后最便宜股价时剩余的净利润
            sell2 = Math.max(sell2, buy2 + prices[i]); //记录之前天进行2次完整交易的最大利润
        }
        return sell2;

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
