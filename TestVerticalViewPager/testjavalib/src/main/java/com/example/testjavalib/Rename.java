package com.example.testjavalib;

/**
 * Created by Apollo on 2018/3/5 19:27.
 */

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ������������
 * @author jack
 */
class ReplacementChain{
    private Map<String,String> map;


    public ReplacementChain() {
        this.map = new HashMap<String, String>();
    }

    public Map<String, String> getMap() {
        return map;
    }

    // ����µ��滻����(�ַ����滻)
    public ReplacementChain  addRegulation(String oldStr , String newStr){
        this.map.put(oldStr, newStr);
        return this;
    }

}

/**
 * ��������
 * @author Jack
 */
class Rename {

    /**
     * ����������
     * @param path
     * @param replacementChain
     */
    public static void multiRename(String path,ReplacementChain replacementChain){
        File file = new File(path);
        boolean isDirectory = file.isDirectory();

        /** ��������ļ��У��ͷ���* */
        if(!isDirectory){
            System.out.println(path + "����һ���ļ��У�");
            return;
        }

        String[] files = file.list();
        File f = null;
        String filename = "";
        String oldFileName = ""; //֮ǰ������
        /** ѭ�����������ļ�* */
        for(String fileName : files){
            oldFileName = fileName;
            Map<String, String> map = replacementChain.getMap();
            for (Entry<String, String> entry : map.entrySet()) {
                fileName = fileName.replace(entry.getKey(), entry.getValue());
            }

            f = new File(path + "\\" + oldFileName); //�����ַ��ԭ·������һ��
            f.renameTo(new File(path + "\\" +  fileName));
        }
        System.out.println("��ϲ�������������ɹ���");
    }

    public static void main(String[] args) {
        ReplacementChain replacementChain = new ReplacementChain();
        replacementChain.addRegulation("������һ���ó��ó���ǰ׺Ŷ��~~~~~", "").addRegulation("rgbvrShow2D_src_main_res_drawable-xxhdpi_", "");
        Rename.multiRename("F:\\RGBProjects\\Majiabao\\android\\һԪץ����\\icon\\һԪץ�����滻��ͼ��\\drawable-xxhdpi", replacementChain);
    }
}