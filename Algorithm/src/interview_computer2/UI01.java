package interview_computer2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 广州4399测试开发工程师
 * 在一个网游中，玩家可以使用各种技能来攻击敌人。
 * 每个技能都有自己的冷却时间，即使用一次技能后需要等待一定时间才能再次使用该技能。
 * 不同的技能之间，有个公共的冷却时间，即使用一次技能后，需要等待一定时间才能再次使用技能(包括自己跟其他技能)
 * 现在给定一个技能列表和对应的冷却时间，技能公共冷却时间，
 * 以及玩家使用技能的顺序，请计算玩家完成所有技能所需的总时间(技能释放时间忽略不计，可视作释放瞬间即完成)
 *
 * 示例输入
 * #技能列表
 * skills =["A","B","C"]
 * #对应冷却时间
 * cooldowns = [3, 5, 2]
 * #技能公共冷却时间
 * common cooldown =1
 * #使用技能的顺序
 * sequence =["A","B","C""C"]
 *
 * 其输出结果为4
 *
 * 解析:
 * 公共冷却时间common cooldown，指的是任意两个ski之间，必须至少间隔common cooldown时间以上才可以释放，
 * 题中common cooldown为1，其意义为释放完skill A后，
 * 必须要间隔1秒后才可以释放skill A或者其他skill;
 * skill A对应的cooldowns为3，其意义使用完skill A后，必须要间隔3秒后才可以选择释放skill A;
 * 故按照ABCC的顺序使用技能，总共需要花费的时间为0+1+1+2=4秒
 *
 * 要求:
 * 一：请编写一个函数 calculate total cooldown time(skills,cooldowns, common cooldown, sequence),
 * 其中 skils 是一个包含技能名称的列表，cooldowns 是一个包含对应冷却时间的列表，common cooldown为技能公共冷却时间，
 * sequence 是玩家使用技能的顺序。函数应该返回玩家完成所有技能所需的总时间.
 * 二、以备注形式，将你用于自测的测试用例附在代码最后(如题干中的示例)
 *
 @author Alex
 @create 2023-08-15-9:30
 */
public class UI01 {
    public static void main(String[] args) {
        String[] skills= {"A","B","C"};
        int [] cooldowns = {6, 8, 10};
        int common_cooldown =2;
        String[] sequence={"A","B","B","C","B"};
        calculate_total_cooldown_time(skills,cooldowns,common_cooldown,sequence);
        // 0+2+8+2+6 = 18s
/**
 * 示例输入
 * #技能列表
 * skills= {"A","B","C"};
 * #对应冷却时间
 * int [] cooldowns = {6, 8, 10};
 * #技能公共冷却时间
 * int common_cooldown =2;
 * #使用技能的顺序
 * sequence={"A","B","B","C","B"};
 *
 * 其输出结果为18
 *
 * 解析:
 * 故按照ABBCB的顺序使用技能，总共需要花费的时间为0+2+8+2+6 = 18s
 */
    }

    public static void calculate_total_cooldown_time(String[] skills, int[] cooldowns, int common_cooldown, String[] sequence){
        // 记录时间
        ArrayList<Integer> arrs = new ArrayList<>();

        // 初始化每个技能对应的时间索引, 键为技能，值为对应的时间序列索引（应动态）
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < skills.length; i++) {
            map.put(skills[i],-1);
        }

        for (int i = 0; i < sequence.length; i++) {
            // 第一次添加
            if(arrs.isEmpty()){
                map.put(sequence[i],i);
                arrs.add(0);
            // 并非第一次添加
            }else {
                // 如果是第一次释放
                if(map.get(sequence[i])==-1){
                    arrs.add(common_cooldown);
                    map.put(sequence[i],i);
                // 如果不是第一次释放该技能
                }else {
                    Integer idx = map.get(sequence[i]);  // 得到上一次释放该技能的索引
                    int t = 0;  // 记录上一次到这次过了多久
                    for (int j = idx+1; j <= i-1; j++) {
                        t += arrs.get(j);
                    }

                    if(t>=cooldowns[getIndex(skills,sequence[i])]){  // 如果比技能时间长
                        arrs.add(common_cooldown);
                        map.put(sequence[i],i);
                    }else {
                        arrs.add(cooldowns[getIndex(skills,sequence[i])]-t);
                        map.put(sequence[i],i);
                    }

                }
            }
        }


        int result = 0;
        for (int i = 0; i < arrs.size(); i++) {
            result += arrs.get(i);
        }
        System.out.println(result);

    }

    // 得到数组中指定元素的索引
    public static int getIndex(String[] skills,String target){
        for (int i = 0; i < skills.length; i++) {
            if(skills[i].equals(target)){
                return i;
            }
        }
        return 0;
    }
}
