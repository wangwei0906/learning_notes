package sort.merge;

import java.util.Arrays;

/*
 * description: 归并排序算法
 * author: W W
 * date：2019-07-05 10:01
 * */
public class MergeSort {


    //高级排序算法之归并算法
    public static void main(String[] args) {
        int[] array={8,7,6,1,2,3,4,5};
        int[] result=sort(array);
        System.out.println(Arrays.toString(result));
    }


    /**
    * 归并排序算法
    * 基本思路:采用递归(分治法)的方式。将分别排好的数组进行合并
    * 时间复杂度:
    *           最坏情况:O（n log n）
    *           最好情况:O(n log n)
    *           平均时间复杂度:O(n log n)
    * 排序方式：外排序
    * 稳定性：稳定
    * @param
    * @return
    * @throws
    * @author W W
    * @date 2019-07-05 10:04:27
    */
    //这里面包含递归的基本思路
    public  static int[] sort(int[] array){
        int[] arr= Arrays.copyOf(array,array.length);
        if(arr.length<2){
            return arr;
        }
        int middle= (int) Math.floor(arr.length/2);
        int[] left=Arrays.copyOfRange(arr, 0, middle);
        int[] right=Arrays.copyOfRange(arr, middle,arr.length);
        return merge(sort(left),sort(right));
    }



    public static int[] merge(int[] left,int[] right){
        int[] result=new int[left.length+right.length];
        int i=0;
        while (left.length>0&&right.length>0){
            if(left[0]<right[0]){
                result[i++]=left[0];
                left=Arrays.copyOfRange(left,1 ,left.length);
            }else{
                result[i++]=right[0];
                right=Arrays.copyOfRange(right,1 ,right.length);
            }
        }
        while (left.length > 0) {
            result[i++] = left[0];
            left = Arrays.copyOfRange(left, 1, left.length);
        }

        while (right.length > 0) {
            result[i++] = right[0];
            right = Arrays.copyOfRange(right, 1, right.length);
        }
        return result;
    }
}
