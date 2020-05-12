package sort.radix;

import java.util.Arrays;

/*
 * description:
 * author: W W
 * date：2019-07-19 09:43
 * */
public class RadixSort {


    public static void main(String[] args) {
        int[] array={8,9,6,11,23,1,9,18,10,213,33,7,87,91,180,35,52,716,106};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    /** 基数排序
     * 实现思路: 依次按个位数（十位数、百位数.....），放入对应编号的1-9的桶中，对最大位数进行排序后，整个数组就有序了
     * 时间复杂度:
     *      最好情况下:O(n * k)
     *      最坏情况下:O(n * k)
     *      平均时间复杂度:O(n * k)
     * 排序方式:外排序
     * 稳定性:稳定
     * @param array 待排序数组
     * @return void
     * @author W W
     * @date 2019-07-19 09:43:57
     */
    public static void sort(int[] array){
        int digitNum=getDigitNum(array);
        int mod=10;
        int tail=1;
        //我们通过一个公式，来依次获得个位、十位、百位:radix=Value % 10^n / 10^n-1
        for(int i=1;i<=digitNum;i++,mod*=10,tail*=10){
            int[][] temp=new int[10][0];
            //各就各位
            for(int j=0;j<array.length;j++){
                int val=array[j];
                int radix=val%mod/tail;
                temp[radix]=arrayAppend(temp[radix],val);
            }
            int startIndex=0;
            for(int out=0;out<temp.length;out++){
                int[] innerArr=temp[out];
                for(int in=0;in<innerArr.length;in++){
                    array[startIndex++]=innerArr[in];
                }
            }
        }
    }

    //对二维数组数组进行扩容
    public static int[] arrayAppend(int[] arr,int val){
        final int length=arr.length+1;
        arr= Arrays.copyOf(arr,length);
        arr[length-1]=val;
        return arr;
    }

    //获得最高位数
    public static int getDigitNum(int[] array){
        int maxValue=array[0];
        for(int i=0;i<array.length;i++){
            if(maxValue<array[i]){
                maxValue=array[i];
            }
        }
        return String.valueOf(maxValue).length();
    }

}
