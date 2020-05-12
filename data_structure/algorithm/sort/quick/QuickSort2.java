package sort.quick;

import java.util.Arrays;

/*
 * description:
 * author: W W
 * date：2019-07-15 22:38
 * */
public class QuickSort2 {


    public static void main(String[] args) {
        int[] array={8,7,6,1,2,3,4,5};
        quickSort(array,0,array.length-1);
        System.out.println(Arrays.toString(array));
    }

    /** 快速排序法（指针交换法） 与填坑法相比，指针交换法进行元素交换的次数更少
     * @param array 待排序数组
     * @param startIndex 左边位置
     * @param endIndex 右边位置
     * 思路：不做赘述，我会在我的bolg中一步步的说明
     * @date 2019-07-15 22:38:35
     */
    public static void quickSort(int[] array,int startIndex,int endIndex){
        int left=startIndex;
        int right=endIndex;
        if(left>=right){
            return;
        }
        int pivot=array[left];
        while (left!=right){
            while (left<right && array[right]>pivot){
                right--;
            }
            while (left<right && array[left]<=pivot){
                left++;
            }
            if(left<right){
                int temp=array[right];
                array[right]=array[left];
                array[left]=temp;
            }
        }
        //截止这里，left和right已经重合
        int tempNum=array[left];
        array[left]=pivot;
        array[startIndex]=tempNum;
        quickSort(array,startIndex ,left-1);
        quickSort(array,left+1 ,endIndex);
    }
}
