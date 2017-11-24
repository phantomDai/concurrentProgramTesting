package generatelist;

public class QuickSort {
    public QuickSort() {
    }
    public int[] quickSort(int[] arr,int _left,int _right ){

        if(_left >= _right){  //待排序的数组至少有两个
            return arr;
        }
        int left = _left ;
        int right = _right ;
        int temp = arr[left] ;//标志位
        while(left < right){   //从左右两边交替扫描，直到left = right

            while(right > left && arr[right] >= temp) {
                right--;        //从右往左扫描，找到第一个比基准元素小的元素
            }
            arr[left] = arr[right];  //找到这种元素arr[right]后与arr[left]交换

            while(left < right && arr[left] <= temp) {
                left++;         //从左往右扫描，找到第一个比基准元素大的元素
            }
            arr[right] = arr[left];  //找到这种元素arr[left]后，与arr[right]交换
        }
        arr[left] = temp;    //基准元素归位
        quickSort(arr,_left,left-1);  //对基准元素左边的元素进行递归排序
        quickSort(arr, right+1,_right);  //对基准元素右边的进行递归排序

        return arr ;
    }

}
