package usecommonclass;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 【java.math.BigDecimal】【java.math.BigInteger】
 *  Integer类作为int的包装类，能存储的最大整型值为231-1，Long类也是有限的，最大为263-1。如果要表示再大的整数，只能使用BigInteger、BigDecimal
 *
 * 【常用方法】
 *   public BigInteger abs()：                                    # 返回此 BigInteger 的绝对值的 BigInteger。
 *   BigInteger add(BigInteger val) ：                            # 返回其值为 (this + val) 的 BigInteger
 *   BigInteger subtract(BigInteger val) ：                       # 返回其值为 (this - val) 的 BigInteger
 *   BigInteger multiply(BigInteger val) ：                       # 返回其值为 (this * val) 的 BigInteger
 *   BigInteger divide(BigInteger val) ：                         # 返回其值为 (this / val) 的 BigInteger。整数相除只保留整数部分。
 *   BigInteger remainder(BigInteger val) ：                      # 返回其值为 (this % val) 的 BigInteger。
 *   BigInteger[] divideAndRemainder(BigInteger val)：            # 返回包含 (this / val) 后跟(this % val) 的两个 BigInteger 的数组。
 *   BigInteger pow(int exponent) ：                              # 返回其值为 (thisexponent) 的 BigInteger
 *
 *   public BigDecimal add(BigDecimal augend)
 *   public BigDecimal subtract(BigDecimal subtrahend)
 *   public BigDecimal multiply(BigDecimal multiplicand)
 *   public BigDecimal divide(BigDecimal divisor, int scale, int roundingMode)
 *
 *  【保留有效数字】String类也能实现类似功能
 *   BigDecimal bd = new BigDecimal(1/3);               # 将1/3的结果用bd存储
 *   bd = bd.setScale(10, RoundingMode.HALF_DOWN);      # 保留10位有效数字，四舍五入模式（还有向上取整、向下取整模式）
 @author Alex
 @create 2023-01-04-18:54
 */
public class UseCommonClass09 {
    @Test
    public void test1() {
        BigInteger bi = new BigInteger("12433241123");
        BigDecimal bd = new BigDecimal("12435.351");
        BigDecimal bd2 = new BigDecimal("11");
        System.out.println(bi);
        // System.out.println(bd.divide(bd2));  // 没有指定四舍五入报错
        System.out.println(bd.divide(bd2, BigDecimal.ROUND_HALF_UP));
        System.out.println(bd.divide(bd2, 15, BigDecimal.ROUND_HALF_UP));  // 指定保留15为位小数，且四舍五入、
    }
}
