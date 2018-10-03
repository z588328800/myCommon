package priv.zl.mycommon.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 版本信息
 */


/**
 * onCreated = "sql"：当第一次创建表需要插入数据时候在此写sql语句
 */
@Table(name = "child_info", onCreated = "")
public class VersionMessage {
    /**
     * name = "id"：数据库表中的一个字段
     * isId = true：是否是主键
     * autoGen = true：是否自动增长
     * property = "NOT NULL"：添加约束
     */
    @Column(name = "id", isId = true, autoGen = true, property = "NOT NULL")
    public int id;
    @Column(name = "downloadUrl")
    public String downloadUrl;
    @Column(name = "versionCode")
    public int versionCode;
    @Column(name = "versionDes")
    public String versionDes;
    @Column(name = "versionName")
    public String versionName;

    public VersionMessage() {
    }


    public VersionMessage(String downloadUrl, int versionCode, String versionDes, String versionName) {
        this.downloadUrl = downloadUrl;
        this.versionCode = versionCode;
        this.versionDes = versionDes;
        this.versionName = versionName;
    }

    @Override
    public String toString() {
        return "VersionMessage{" +
                "downloadUrl='" + downloadUrl + '\'' +
                ", versionCode=" + versionCode +
                ", versionDes='" + versionDes + '\'' +
                ", versionName='" + versionName + '\'' +
                '}';
    }
}