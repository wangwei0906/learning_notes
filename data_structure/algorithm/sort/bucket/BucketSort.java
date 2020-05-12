package sort.bucket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
 * description:
 * author: W W
 * date：2019-07-18 14:54
 * */
public class BucketSort {

    public static void main(String[] args) {
        double [] array = new double[] {4.12,6.421,0.0023, 3.0,2.123,8.122,4.12,10.09};
        array=buketSort(array);
        System.out.println(Arrays.toString(array));
    }

    public static double[] buketSort(double[] array){
        double min=array[0];
        double max=array[0];
        //打到最大值
        for(int i=0;i<array.length;i++){
            if(array[i]<min){
                min=array[i];
            }
            if(array[i]>max){
                max=array[i];
            }
        }
        double d = max - min;
        int bucketNum=array.length;
        List<List<Double>> bucketList=new ArrayList<>();
        //初始化每个桶
        for(int i=0;i<bucketNum;i++){
            bucketList.add(new LinkedList<Double>());
        }
        //分配到每个桶
        for(int index=0;index<array.length;index++){
            //获得所属的桶位置,通过这个公式:(array[i] - min)  * (bucketNum-1) / d
            int bucketIndex=(int)((array[index]-min)*(bucketNum-1)/d);
            bucketList.get(bucketIndex).add(array[index]);
        }
        //对每个桶做分别排序
        for(int j=0;j<bucketNum;j++){
            Collections.sort(bucketList.get(j));
        }

        double[] resultArray=new double[array.length];
        int startIndex=0;
        for(int i=0;i<bucketList.size();i++){
            List<Double> bucket=bucketList.get(i);
            for(double val:bucket){
                resultArray[startIndex++]=val;
            }
        }
        return resultArray;
    }
}
