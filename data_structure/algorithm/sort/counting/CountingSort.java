package sort.counting;

import java.util.Arrays;

/*
 * description:
 * author: W W
 * date：2019-07-18 14:23
 * */
public class CountingSort {

    /** 计数排序法
    * @param
    * @param
    * @param
    * @return
    * @throws
    * @author W W
    * @date 2019-07-18 14:23:48
    */
    public static void main(String[] args) {
        int[] array={8,7,6,1,2,3,4,5};
        int maxVal=getMaxVal(array)+1;
        countintSort(array,maxVal);
        System.out.println(Arrays.toString(array));
    }

    public static int getMaxVal(int[] arr){
        int maxVal=arr[0];
        for(int i=1;i<arr.length;i++){
            if(arr[i]>maxVal){
                maxVal=arr[i];
            }
        }
        return maxVal;
    }

    public static int[] countintSort(int[] arr,int bucketLen){
        int[] bucket=new int[bucketLen];
        for(int val:arr){
            bucket[val]++;
        }
        int startIndex=0;
        for(int i=0;i<bucketLen;i++){
            while (bucket[i]>0){
                arr[startIndex++]=i;
                bucket[i]--;
            }
        }
        return arr;
    }

}
