package sort.bubble;

import java.util.Arrays;

/*
 * description:
 * author: W W
 * date：2019-06-26 14:20
 * */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array={2, 4, 3, 1, 5, 6, 7, 8, 9};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
    *  冒泡排序法(BubbleSort)
    *  时间复杂度:  最好情况为：O(n)  最差情况为：O(n^2)  平均时间复杂度为：O(n^2)
    *  排序方式:内排序
    *  稳定性：稳定
    * @param array 待排序数组
    * @return void
    * @author wangwei
    * @date 2019-06-27 09:48:28
    */
    public static void sort(int[] array){
        final int length=array.length;
        //定义一个哨兵，标记是否发生交换位置（目的是为了优化外层循环）
        boolean hasSwap=true;
        //内存循环，每一趟的比较次数
        int count=length-1;
        for(int i=0;i<length-1;i++){
            //没有发生变换。说明已经有序了。外层循环可以不用继续下去了
            //例如待排序数组为{8,7,6,1,2,3,4,5} 当把8,7,6冒上去后。第4趟（index=3）就已经有序了，不需要发生交换。直接可跳出外层循环
            if(!hasSwap){
                break;
            }
            hasSwap=false;
            //想想办法优化内层循环
            //动态的控制内部循环次数。例如当待排序数组为{2, 4, 3, 1, 5, 6, 7, 8, 9} 当把4冒到5前面后。第二趟可以直接从下标3开始。而不用从7开始
            int last=0;
            for(int j=0;j<count;j++){
                if(array[j]>array[j+1]){
                    hasSwap=true;
                    last=j;
                    swap(array,j ,j+1);
                }
            }
            count=last;
        }
    }

    //数组元素交换
    private static void swap(int[] array,int idx1,int idx2){
        int temp=array[idx1];
        array[idx1]=array[idx2];
        array[idx2]=temp;
    }
}
