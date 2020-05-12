package sort.shell;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
 * description:
 * author: W W
 * date：2019-07-02 14:30
 * */
public class ShellSort {

    public static void main(String[] args) {
        int[] array={5, 4, 3, 1, 2, 6, 7, 8, 9};
        sort(array);
        System.out.println(Arrays.toString(array));

    }

    /**
    * 希尔排序法(ShellSort)
    * 主要思想:让相隔[增量]之间的元素有序，逐步缩小[增量]，使得数组逐步有序（因为趋近有序，所以不会像插入排序那样频繁的移动数据）
    * 时间复杂度: 最好情况为:已经有序,O(n)
    *             最坏的情况为:希尔增量----O(n^2)，Hibbard增量()----O(n^(3/2))
    *             平均情况:O(n^1.5)
    *
    * @param array 待排序数组
    * @return
    * @author W W
    * @date 2019-07-04 14:34:29
    */
    public static void sort(int[] array){
        final int length=array.length;
        //初始分为三个局部
        //目前主流的两个增量策略为: 希尔增量序列公式=n/2 ,最坏O(n^2)
        // Hibbard增量序列公式=(len + 1)/ 2 - 1 ,最坏O(n^(3/2))
        int increment=(length + 1)/ 2 - 1;
        while (increment>0){
            //内部其实就是一个插入排序。（多个组的数据做插入排序）
            for(int i= increment;i<length;i++){
                //临时存放待排序的值
                int temp=array[i];
                int j=i;
                while (j>=increment && temp<array[j-increment]){
                    array[j]=array[j-increment];
                    j-=increment;
                }
                //插入
                array[j]=temp;
            }
            increment= (increment + 1)/ 2 - 1;
        }
    }
}
