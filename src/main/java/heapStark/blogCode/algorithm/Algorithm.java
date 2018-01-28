package heapStark.blogCode.algorithm;

import java.util.*;

/**
 * blogcode leetCode
 * Created by wangzhilei3 on 2018/1/25.
 */
public class Algorithm {
    /**
     * 链表排序
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {

        if (head == null || head.next == null)
            return head;
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode right = sortList(slow.next);
        slow.next = null;
        ListNode left = sortList(head);
        return mergeSorted(left, right);

    }

    /**
     * mergeSorted
     *
     * @param left
     * @param right
     * @return
     */
    public ListNode mergeSorted(ListNode left, ListNode right) {

        ListNode temp_head = new ListNode(0);
        ListNode temp_node = temp_head;
        while (left != null && right != null) {
            if (left.val < right.val) {
                temp_node.next = left;
                left = left.next;
            } else {
                temp_node.next = right;
                right = right.next;
            }
            temp_node = temp_node.next;
        }
        if (left != null)
            temp_node.next = left;
        if (right != null)
            temp_node.next = right;
        return temp_head.next;
    }

    /**
     * 递归解法效率很差
     *
     * @param head
     * @return
     */
    public ListNode reorderListHelper(ListNode head) {

        return head;
    }

    /**
     * 快慢指针拆分
     *
     * @param head
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null)
            return;
        ListNode after = reverseList(twoList(head));

        // 合并两个链表
        ListNode first = head;
        while (first != null && after != null) {
            ListNode ftemp = first.next;
            ListNode aftemp = after.next;
            first.next = after;
            first = ftemp;
            after.next = first;
            after = aftemp;
        }
    }

    /**
     * 链表拆分
     *
     * @param head
     * @return
     */
    public ListNode twoList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        // 快满指针找到中间节点
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // 拆分链表
        ListNode temp = slow.next;
        slow.next = null;
        return temp;
    }

    /**
     * 链表逆序
     *
     * @param head
     */
    public ListNode reverseList(ListNode head) {
        ListNode tail = head;
        head = null;
        while (tail != null) {
            ListNode temp = tail.next;
            tail.next = head;
            head = tail;
            tail = temp;
        }
        return head;
    }

    /**
     * 合并链表
     *
     * @param listNode1
     * @param listNode2
     * @return
     */
    public ListNode merge(ListNode listNode1, ListNode listNode2) {
        ListNode tail = new ListNode(1);
        ListNode listNode = tail;
        while (listNode1 != null && listNode2 != null) {
            tail.next = listNode1;
            tail.next.next = listNode2;
            tail = listNode2;
            listNode2.next = listNode1.next;
            listNode1 = listNode1.next;
            listNode2 = listNode2.next;
        }
        return listNode.next;
    }

    /**
     * 有环快慢指针碰撞
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode fast = head;
        ListNode low = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            low = low.next;
            if (fast == low) {
                return true;
            }

        }
        return false;
    }

    /**
     * 环的位置
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                ListNode slow2 = head;
                while (slow2 != slow) {
                    slow = slow.next;
                    slow2 = slow2.next;
                }
                return slow;
            }
        }
        return null;
    }

    /**
     * 合并有K个序数组
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

    /**
     * swapPairs pre, cur, temp 三组变量
     *
     * @param head
     * @return
     */
    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        for (ListNode pre = dummy, cur = head, temp; cur != null && cur.next != null; pre = cur, cur = cur.next) {
            temp = cur.next;
            cur.next = temp.next;
            temp.next = pre.next;
            pre.next = temp;
        }
        return dummy.next;
    }

    /**
     * 倒数k个前置
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null || head.next == null)
            return head;
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        ListNode cur = head;
        ListNode pre = head;
        int total;
        for (total = 1; cur.next != null; total++)
            cur = cur.next;
        for (int i = 1; i < total - k % total; i++) {
            pre = pre.next;
        }
        cur.next = preHead.next;
        preHead.next = pre.next;
        pre.next = null;

        return preHead.next;
    }

    /**
     * 链表去重
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {

        ListNode cur = head;
        while (cur != null) {
            while (cur.next != null && cur.val == cur.next.val) {
                cur.next = cur.next.next;
            }
            cur = cur.next;
        }
        return head;
    }

    /**
     * @param head
     * @return
     */
    public ListNode deleteAllDuplicates(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode newHead = new ListNode(head.val - 1);
        newHead.next = head;
        ListNode cur = head;
        ListNode pre = newHead;
        while (cur != null && cur.next != null) {
            if (cur.val != cur.next.val) {
                pre = cur;
            } else {
                while (cur.next != null && cur.val == cur.next.val) {
                    cur = cur.next;
                }
                pre.next = cur.next;
            }
            cur = cur.next;
        }
        return newHead.next;
    }

    /**
     * sortedListToBST
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head)
    {
        if(head==null) return null;
        if(head.next == null) return new TreeNode(head.val);
        ListNode slow = head;
        ListNode fast = head.next;
        while(fast.next!=null && fast.next.next!=null)
        {
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
     *
     1) begin开始指向0， end一直后移，直到begin - end区间包含T中所有字符。
     记录窗口长度d
     2) 然后begin开始后移移除元素，直到移除的字符是T中的字符则停止，此时T中有一个字符没被
     包含在窗口，
     3) 继续后移end，直到T中的所有字符被包含在窗口，重新记录最小的窗口d。
     4) 如此循环知道end到S中的最后一个字符。
     时间复杂度为O(n)
     * @param S
     * @param T
     * @return
     */
    public String minWindow(String S, String T) {
        int[] map = new int[128];
        //init map, 记录T中每个元素出现的次数
        for(int i = 0; i < T.length(); i++) {
            map[T.charAt(i)]++;
        }
        // begin end两个指针指向窗口的首位，d记录窗口的长度， counter记录T中还有几个字符没被窗口包含
        int begin = 0, end = 0, d = Integer.MAX_VALUE, counter = T.length(), head = 0;
        // end指针一直向后遍历
        while(end < S.length()) {
            // map[] > 0 说明该字符在T中出现，counter-- 表示对应的字符被包含在了窗口，counter--, 如果s中的字符没有在T中出现，则map[]中对应的字符-1后变为负值
            if(map[S.charAt(end++)]-- > 0) {
                counter--;
            }
            // 当counter==0时，说明窗口已经包含了T中的所有字符
            while (counter == 0) {
                if(end - begin < d) {
                    d = end - (head = begin);
                }
                if(map[S.charAt(begin++)]++ == 0) {  // begin开始后移，继续向后寻找。如果begin后移后指向的字符在map中==0，表示是在T中出现的，如果没有出现，map[]中的值会是负值。
                    counter++;                      // 在T中的某个字符从窗口中移除，所以counter++。
                }
            }
        }
        return d==Integer.MAX_VALUE ? "" :S.substring(head, head+d);
    }


    /**
     * 链表求和
     *
     * @param l1
     * @param l2
     * @return
     */
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


    public void sortedListToBSTTest() {
        ListNode listNode = new ListNode(3);
        listNode.next = new ListNode(5);
        //listNode.next.next = new ListNode(8);
        TreeNode treeNode = sortedListToBST(listNode);
        System.out.print("");
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

    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        if (intervals == null || intervals.size() == 0) {
            return new ArrayList<>(Arrays.asList(newInterval));
        }
        for (Interval interval : intervals) {
            if (interval.end < newInterval.start) {

            } else if (interval.end >= newInterval.start) {
                interval.end = Math.max(interval.end, newInterval.start);
            }
        }


        return null;
    }
}


class Comp implements Comparator<ListNode> {
    public int compare(ListNode o1, ListNode o2) {
        return o1.val - o2.val;
    }
}

class Interval {
    int start;
    int end;

    Interval() {
        start = 0;
        end = 0;
    }

    Interval(int s, int e) {
        start = s;
        end = e;
    }
}
