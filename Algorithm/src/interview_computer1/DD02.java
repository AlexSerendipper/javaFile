package interview_computer1;

import com.sun.javafx.image.IntPixelGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * 滴滴3.18
 *    小A所在的企业中，每个员工在请求授权的时候都需要向他上面的某一级领导提出申请。
 * 为了避免混乱，一个员工只会向他的上级领导中技术能力与他最相近的那位领导提出申请。现在小 A 想知道每个人的申请对象是谁。
 *    小A所在企业的组织架构可以用一棵有根树来描述。即除了树根对应的最高领导以外，每个人都有唯一的一个直属领导。
 * 同时，每个员工的技术能力均可以用一个正整数表示，称为技术等级。两个人之间的技术能力越近则他们的技术等级之差的绝对值越小。
 *
 * 第一行有一个正整数 n 代表小 A 所在企业中的员工的数量
 * 第二行有 n-1个正整数，第i个数 fi(i<fi≤n)代表编号为i的员工的直属领导是fi。编号为n的员工是没有直属领导的最高级领导。
 * 第三行有 几个正整数，第i个数 ai 代表编号为i的员工的技术等级。技术等级的范围在1到n之间。
 *
 * 样例输入
6
3 3 5 5 6
2 5 4 1 3 6
 * 样例输出
5 3 5 5 6
 *
 * 提示
 * 编号为2的员工有三个上级领导3，5，6。其中3和6与他的技术能力同样接近但是3在组织架构上离他最近，因此他在请求授权时会向编号为3的员工提出申请，
 @author Alex
 @create 2024-03-17-17:56
 */

// 感觉还是用树形结构最合适，使用深度优先遍历来计算
public class DD02 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        // 把所有的节点放到arraylist中，为了方便构建树
        ArrayList<Node> arr = new ArrayList<>();
        for (int i = 0; i < n-1; i++) {
            Node node = new Node();
            node.setId(i+1);
            node.setParentNodeId(sc.nextInt());
            arr.add(node);
        }

        // rootNode指向最高级领导
        Node rootNode = new Node();
        rootNode.setId(n);
        arr.add(rootNode);

        // 为所有节点添加能力值
        for(Node node:arr){
            node.setEnergy(sc.nextInt());
        }

        // 构建树
        for(Node node:arr){
            Node parentNode = findNodeByID(arr, node.getParentNodeId());
            // 根节点的parentNode为null
            if(parentNode!=null){
                node.addParentNode(parentNode);
            }
        }

        // 构建完成后，开始计算每个id员工的请求路径
        for(Node node:arr){
            if(node.getParentNode()==null){
                break;
            }
            // 计算所有员工的上级和自己的能力差值的最小值的上级id
            int parentID=0;
            int max = 99;
            int energy = node.getEnergy();

            // 查找当前节点与所有上级节点的能力差值的最小值
            while(node.getParentNode()!=null){
                int diffEnergy = Math.abs(energy - node.getParentNode().getEnergy());

                // 如果差值小了，则记录下来，最终返回
                if(diffEnergy<max){
                    parentID = node.getParentNodeId();
                    max = diffEnergy;
                }

                node = node.getParentNode();
            }
            System.out.print(parentID+" ");
        }



        for (int i = 1; i < n; i++) {
            Node curNode = new Node(i, 0, null);// 创建当前节点
            int preNodeId = sc.nextInt();  // 上级节点ID

            if(preNodeId!=n){
                // 先查看是否有ID为preNodeId的节点，如果没有则创建
                if (rootNode.findNodeById(preNodeId)==null) {
                    Node preNode = new Node();  // 上级节点
                    preNode.setId(preNodeId);
                    preNode.addChildNode(preNode);
                }

            }
        }
    }

    // 查找arraylist中指定id的节点
    public static Node findNodeByID(ArrayList<Node> arr, int ID){
        for(Node node:arr){
            if(node.getId()==ID){
                return node;
            }
        }
        // 没有找到返回null
        return null;
    }


}


// 由于每个人只有唯一一个上级，所以根据树形结构应该能比较快计算出所有距离
class Node{
    private int id;
    private int parentNodeId;
    private int energy;  // 代表能力
    private Node parentNode;
    private List<Node> childNodes=new ArrayList<>();

    public Node() {
    }

    public Node(int id, int energy, List<Node> childNodes) {
        this.id = id;
        this.energy = energy;
        this.childNodes = childNodes;
    }

    // 添加子节点
    public void addChildNode(Node childNode){
        childNode.setParentNode(this);  // 设置子节点的父节点为当前节点
        childNodes.add(childNode);  // 为当前节点添加子节点
    }
    // 添加父节点
    public void addParentNode(Node parentNode){
        // 设置当前节点的父节点
        this.parentNode = parentNode;
        // 为父节点添加子节点
        parentNode.getChildNodes().add(this);
    }


    // 根据ID查找节点
    public Node findNodeById(int targetID){
        return null;
    }

    // get set 方法
    public int getId() {
        return id;
    }

    public int getEnergy() {
        return energy;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public List<Node> getChildNodes() {
        return childNodes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public void setChildNodes(List<Node> childNodes) {
        this.childNodes = childNodes;
    }

    public int getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(int parentNodeId) {
        this.parentNodeId = parentNodeId;
    }
}