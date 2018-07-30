package blog.util;

import java.io.File;

/**
 * @author Administrator
 * 删除文件工具类
 */
public class RemoveFile {
    /**
     * 删除单个文件
     * @param fileName
     * @return
     */
    public static boolean removeSingle(File file) {
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + file.getName() + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + file.getName() + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + file.getName() + "不存在！");
            return false;
        }
    }
}
