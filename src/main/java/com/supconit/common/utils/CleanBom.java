package com.supconit.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.io.IOUtils;

/**
 * Created with IntelliJ IDEA.
 * User: noah
 * Date: 2/26/13
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class CleanBom {

    public static void main(String[] args) {

        //指定查找文件的父目录javafolderE:\\workspace\\ywgl\\src\\main\\java
        File parent = new File("D:\\indigoworkspace\\P22-YWGL\\05-source\\ywgl\\src\\main\\java\\com\\supconit\\ywgl\\energy");

        List<File> javaFiles = findJavaFile(parent);

        int count = 0;
        for (File javaFile : javaFiles) {
            if (isBomFile(javaFile)) {
                count++;

                cleanBom(javaFile);
            }

        }
        System.out.println("bom=" + count);

    }

    /**
     * 清除bom编码
     *
     * @param file
     */
    public static void cleanBom(File file) {

        File tempFile = new File(file.getAbsolutePath() + ".tmp");

        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(tempFile);
            fis = new FileInputStream(file);
            fis.read(new byte[3]);//读取前3个byte
            IOUtils.copy(fis, fos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(fis);
        }

        if (!file.delete()) {
            System.out.println("Could not delete file");
        }

        if (!tempFile.renameTo(file)) {
            System.out.println("Could not rename file");
        }

        System.out.println(file.getAbsolutePath() + ">>clean bom");

    }

    /**
     * 查找子目录下所有java文件
     *
     * @param parent
     * @return
     */
    public static List<File> findJavaFile(File parent) {

        List<File> result = new ArrayList<File>();

        Stack<File> stack = new Stack<File>();
        stack.push(parent);

        while (!stack.isEmpty()) {

            File popFile = stack.pop();

            if (popFile.isDirectory()) {
                for (File file : popFile.listFiles()) {
                    stack.add(file);
                }
            } else {
                if (popFile.getName().endsWith(".java")) {
                    result.add(popFile);
                }
            }
        }

        return result;
    }

    /**
     * 判断是否为bom编码文件
     *
     * @param file
     * @return
     */
    public static boolean isBomFile(File file) {
        boolean isBom = false;
        FileInputStream fileIS = null;
        try {
            fileIS = new FileInputStream(file);

            byte[] bomBytes = new byte[3];
            fileIS.read(bomBytes);

            //EF BB BF
            if (bomBytes[0] == -17 && bomBytes[1] == -69 && bomBytes[2] == -65) {
                isBom = true;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fileIS);

        }
        return isBom;
    }
}