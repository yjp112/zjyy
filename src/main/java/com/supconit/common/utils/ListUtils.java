package com.supconit.common.utils;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;

/**
 * @文 件 名：ListUtils.java
 * @创建日期：2013年7月5日
 * @版    权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版    本: 
 * @描    述：
 */
public final class ListUtils {
/** 
 *@方法名称:partition
 *@作    者:丁阳光
 *@创建日期:2013年7月5日
 *@方法描述: 把list 按照每组size个元素进行分组，  
 *如list=[a, b, c, d, e]，size=3,则result=[[a, b, c], [d, e]]
 * @param list 需要分组的列表
 * @param size 每组的长度
 * @return List<List<T>>
 */
public static <T> List<List<T>> partition(List<T> list, int size) {
      if(list==null){
          throw new RuntimeException("list 不能为空");
      }
      if(size<=0){
          throw new RuntimeException("size 必须大于0");
      }
    return (list instanceof RandomAccess)
        ? new RandomAccessPartition<T>(list, size)
        : new Partition<T>(list, size);
  }

  private static class Partition<T> extends AbstractList<List<T>> {
    final List<T> list;
    final int size;

    Partition(List<T> list, int size) {
      this.list = list;
      this.size = size;
    }

    @Override public List<T> get(int index) {
      int listSize = size();
      if (index < 0 || index >= listSize) {
          throw new IndexOutOfBoundsException("index索引越界。");
        }
      int start = index * size;
      int end = Math.min(start + size, list.size());
      return list.subList(start, end);
    }

    @Override public int size() {
      int result = list.size() / size;
      if (result * size != list.size()) {
        result++;
      }
      return result;
    }

    @Override public boolean isEmpty() {
      return list.isEmpty();
    }
  }

  private static class RandomAccessPartition<T> extends Partition<T>
      implements RandomAccess {
    RandomAccessPartition(List<T> list, int size) {
      super(list, size);
    }
  }
 
  public static void main(String[] args) {
    String[] arr={"a","b","c","d","e"};
    List<String> list=Arrays.asList(arr);
    List<List<String>> resultList=ListUtils.partition(list, 3);
    System.out.println(resultList.size());
    for (List<String> list2 : resultList) {
        System.out.println(list2);
    }
}
}
