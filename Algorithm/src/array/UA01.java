package array;


/**
 * 2. 二分查找：https://leetcode.cn/problems/binary-search/
 *
 * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1
 @author Alex
 @create 2023-06-03-21:55
 */

class UA01 {
    // 若定义 target 是在一个在左闭右闭的区间里，也就是[left, right] （这个很重要非常重要）。
    public int search(int[] nums, int target) {
        int left = 0;  // 定义左指针
        int right = nums.length-1;  // 定义右指针，即形成了一个 [left,right]的一个区间。注意索引是比length-1的
        while(left<=right){
            int middle = left + ((right-left) >>1);  // 防止溢出，>>1相当于除2操作，只不过是只针对整数的除2操作，所以免去了floor操作
            if(nums[middle]<target){
                left = middle + 1;  // target 在右区间，在[middle + 1, right)中
            }else if(nums[middle]>target){
                right = middle - 1;   // target 在左区间，在[left, middle)中
            }else{
                return middle;
            }
        }
        return -1;
    }


    // 如果说定义 target 是在一个在左闭右开的区间里，也就是[left, right) ，那么二分法的边界处理方式则截然不同。
    public int search2(int[] nums, int target) {
        int left = 0;  // 定义左指针
        int right = nums.length;  // 定义右指针，即形成了一个 [left,right)的一个区间
        while(left<right){  // 因为left == right的时候，在[left, right)是无效的空间，所以使用 <
            int middle = left + ((right-left) >>1);  //  防止溢出，>>1相当于除2操作，只不过是只针对整数的除2操作，所以免去了floor操作
            if(nums[middle]<target){
                left = middle + 1;  // target 在右区间，在[middle + 1, right)中
            }else if(nums[middle]>target){
                right = middle;   // target 在左区间，在[left, middle)中
            }else{
                return middle;
            }
        }
        return -1;
    }
};

