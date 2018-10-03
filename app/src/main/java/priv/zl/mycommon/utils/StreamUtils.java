package priv.zl.mycommon.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

public class StreamUtils {


    /**
     * 流转换成string
     *
     * @param is
     * @return
     */
    public static String streamToString(InputStream is) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] bytes = new byte[1024];

        int temp = -1;

        try {
            while ((temp = is.read(bytes)) != -1) {
                bos.write(bytes, 0, temp);
            }
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 复制文件
     *
     * @param copyFile
     * @param toPath
     */
    public static void fileChannelCopy(String copyFile, String toPath) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        File s = new File(copyFile);
        File t = new File(toPath);
        if (s.exists() && s.isFile()) {
            System.out.println("需要复制的文件存在");
            try {
                fi = new FileInputStream(s);
                fo = new FileOutputStream(t);
                in = fi.getChannel();// 得到对应的文件通道
                out = fo.getChannel();// 得到对应的文件通道
                in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fi.close();
                    in.close();
                    fo.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("需要复制的文件不存在 ");
        }
    }
}
