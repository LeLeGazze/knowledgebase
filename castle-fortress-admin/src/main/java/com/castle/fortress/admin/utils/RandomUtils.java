package com.castle.fortress.admin.utils;
 
import java.io.Serializable;
import java.util.Random;
 
/**
 *  生成随机用户名
 * @author sunhr
 * @date 2023/4/23 13:08
 */
public class RandomUtils implements Serializable {

  /**
   * 随机获取英文+数字（用户名）
   *
   * @param engCode 小写英文的数量
   * @return
   */
  public static String verifyUserName(int engCode) {
    //声明一个StringBuffer存储随机数
    StringBuilder sb = new StringBuilder();
    char[] englishCodeArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    Random random = new Random();
    //获取英文
    for (int i = 0; i < engCode; i++) {
      char num = englishCodeArray[random.nextInt(englishCodeArray.length)];
      sb.append(num);
    }
      return sb.toString();
  }

}