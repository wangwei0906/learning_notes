package sort.selection;

import java.util.Arrays;

/*
 * description:
 * author: W W
 * date：2019-06-27 11:06
 * */
public class SelectionSort {

    public static void main(String[] args) {
        int[] array={8,7,6,1,2,3,4,5};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     *  选择排序法(SelectionSort)
     *  时间复杂度:  最好情况为：O(n^2)
     *               最差情况为：O(n^2)
     *               平均时间复杂度为：O(n^2)
     *  排序方式:内排序
     *  稳定性：不稳定
     * @param array 待排序数组
     * @return void
     * @author wangwei
     * @date 2019-06-27 09:48:28
     */
    public static void sort(int[] array){
        final int length=array.length;
        for(int i=0;i<length-1;i++){
            int minimumIndex=i;
            for(int j=minimumIndex+1;j<length;j++){
                if(array[j]<array[minimumIndex]){
                    minimumIndex=j;
                }
            }
            swap(array,i , minimumIndex);
        }
    }

    //数组元素交换
    private static void swap(int[] array,int idx1,int idx2){
        int temp=array[idx1];
        array[idx1]=array[idx2];
        array[idx2]=temp;
    }
}
