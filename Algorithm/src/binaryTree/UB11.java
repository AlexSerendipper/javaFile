package binaryTree;

/**
 * 404. 左叶子之和：https://leetcode.cn/problems/sum-of-left-leaves/
 * 定义：节点A的左孩子不为空，且左孩子的左右孩子都为空（说明是叶子节点），那么A节点的左孩子为左叶子节点
 @author Alex
 @create 2023-07-28-14:54
 */
public class UB11 {
    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode(1);
        rootNode.left = new TreeNode(2);
        rootNode.right = new TreeNode(3);
        rootNode.right.left = new TreeNode(2);
        rootNode.left.left = new TreeNode(4);
        rootNode.left.right = new TreeNode(5);
        new UB11().sumOfLeftLeaves(rootNode);
    }
    public int sumOfLeftLeaves(TreeNode root) {  // 1、确定参数和返回值
        if (root == null) return 0;  // 2、中止条件
        if (root.left == null && root.right== null) return 0;  // 只有当前遍历的节点是父节点，才能判断其子节点是不是左叶子。 所以如果当前遍历的节点是叶子节点，那其左叶子也必定是0。。。
                                                               // 关键是要理清，我们是使用父节点来判断！所以要遍历父节点
                                                               // 实这个也可以不写，如果不写不影响结果，但就会让递归多进行了一层。
        // 单层循环逻辑
        int leftValue = sumOfLeftLeaves(root.left);  // 左，即求左子树的左叶子节点
        int rightValue = sumOfLeftLeaves(root.right);  // 右，即求右子书的左叶子节点

        int midValue = 0;
        if (root.left != null && root.left.left == null && root.left.right == null) {  // 如果当前节点左节点不为空，其左节点的左节点和左节点的右节点为空，说明当前节点为左叶子节点，故记录数据
            midValue = root.left.val;
        }

        int sum = midValue + leftValue + rightValue;  // 中
        return sum;
    }


}
