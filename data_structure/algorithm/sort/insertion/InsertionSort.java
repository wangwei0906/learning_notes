package sort.insertion;

import java.util.Arrays;

/*
 * description:
 * author: W W
 * date：2019-06-28 15:44
 * */
public class InsertionSort {

    public static void main(String[] args) {
        int[] array={8,7,6,1,2,3,4,5};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     *  插入排序法(InsertionSort)
     *  主要思想:将待排序数组分类，左边（或右边）的任务有序，在无序的这部分元素中，去有序的数组中找到合适的位置插入
     *  时间复杂度:  最好情况为：O(n)
     *               最差情况为：O(n^2)
     *               平均时间复杂度为：O(n^2)
     *  排序方式:内排序
     *  稳定性：稳定
     * @param array 待排序数组
     * @return void
     * @author wangwei
     * @date 2019-06-27 09:48:28
     */
    public static void sort(int[] array){
        final int length=array.length;
        //从1开始。默认array[0]已经有序
        for(int i=1;i<length;i++){
            //取出待排序的元素
            int temp=array[i];
            int j=i;
            while(j>0&&temp<array[j-1]){
                //逐个右移
                array[j]=array[j-1];
                j--;
            }
            //说明在已排序好的元素中，找到了合适的插入位置。做插入操作
            if(j!=i){
                array[j]=temp;
            }
        }
    }

}
