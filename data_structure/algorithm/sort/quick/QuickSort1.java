package sort.quick;

import java.util.Arrays;

/*
 * description:
 * author: W W
 * date：2019-07-15 21:05
 * */
public class QuickSort1 {

    public static void main(String[] args) {
        int[] array={8,7,6,1,2,3,4,5};
        sort(array,0,array.length-1);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 快速排序（填坑法）
     * @param array 待排序数组
     * @param startIndex 起始位置
     * @param endIndex 结束为止
     * 思路：取左数组头作为基准点，从右边往左边遍历，比基准小的数字，指针“Index”记录该数位置（这里给这个位置取个通俗的名字“坑”）将该值赋给基准点，左边起点+1
     *       切换，从左边开始往右边遍历，找到比基准大的数字,指针“Index”记录该数位置，把放入坑，右边终点左移（-1），当左指针和右边指针重逢，将基准填入。
     *        数组进行“分而治之”，对数组进行拆分，递归的做前面描述的事情
     * @author W W
     * @date 2019-07-15 21:06:12
     */
   public static void sort(int[] array,int startIndex,int endIndex){
        if(startIndex>=endIndex){
            return;
        }
        int pivotIndex=partition(array, startIndex,endIndex);
        sort(array,startIndex,pivotIndex-1);
        sort(array,pivotIndex+1 ,endIndex);
   }

   // the sorting logic
   public static int  partition(int[] array,int startIndex,int endIndex){
        //这个就是坑位置
        int index=startIndex;
        //基准数字
        int pivot=array[startIndex];
        int left=startIndex;
        int right=endIndex;
        while (right>=left){
            while (right>=left){
                //如果右边的比左边小
                if(array[right]<pivot){
                    array[left]=array[right];
                    left++;
                    index=right;
                    break;
                }
                right--;
            }
            while (right>=left){
                if(array[left]>=pivot){
                    array[right]=array[left];
                    index=left;
                    right--;
                    break;
                }
                left++;
            }
        }
        array[index]=pivot;
        return index;
   }
}
