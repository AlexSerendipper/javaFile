package binaryTree;

import java.util.List;

/**
 @author Alex
 @create 2023-07-18-9:33
 */
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
