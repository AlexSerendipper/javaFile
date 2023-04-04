package bean;

/**
 @author Alex
 @create 2023-02-16-10:07
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车对象:由于购物车模型中，不再需要与数据库交互，故不再需要DAO层，故将所有方法直接放于cart类中
 */
public class Cart {

//    private Integer totalCount;  // 申明在getTotalCount中，每次调用都能获取最新的值
//    private BigDecimal totalPrice;  // 申明在getTotalPrice中，每次调用都能获取最新的值

    /**
     * 如果使用List就需要自己遍历来比较id是否相同，故使用map更简洁
     * key 是商品编号，
     * value，是商品信息
     */
    private Map<Integer, CartItem> items = new LinkedHashMap<Integer, CartItem>();

    /**
     * 1、添加商品项
     *
     * @param cartItem
     */
    public void addItem(CartItem cartItem) {
        // 先查看购物车中是否已经添加过此商品，如果已添加，则数量累加，总金额更新，如果没有添加过，直接放到集合中即可
        CartItem item = items.get(cartItem.getId());
        if (item == null) {
            // 之前没添加过此商品
            items.put(cartItem.getId(), cartItem);
        } else {
            // 已经添加过的情况
            item.setCount(item.getCount() + 1); // 数量累加
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));  // 更新总金额，使用multiply可以避免精度损失
        }
    }

    /**
     * 2、删除商品项
     */
    public void deleteItem(Integer id) {
        items.remove(id);
    }


    /**
     * 3、清空购物车
     */
    public void clear() {
        items.clear();
    }


    /**
     * 4、修改商品数量
     */
    public void updateCount(Integer id, Integer count) {
        // 先查看购物车中是否有此商品。如果有，修改商品数量，更新总金额
        CartItem cartItem = items.get(id);
        if (cartItem != null) {
            cartItem.setCount(count);  // 修改商品数量
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount()))); // 更新总金额
        }
    }


    public Cart() {
    }


    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for(Map.Entry<Integer,CartItem> entry:items.entrySet()){
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

//   总数量是通过items中的项目计算的，不是通过set设置的
//    public void setTotalCount(Integer totalCount) {
//        this.totalCount = totalCount;
//    }
//
//    public void setTotalPrice(BigDecimal totalPrice) {
//        this.totalPrice = totalPrice;
//    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
