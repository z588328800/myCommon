package priv.zl.mycommon.domain.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "Log", onCreated = "")
public class Log {
    /**
     * name = "id"：数据库表中的一个字段
     * isId = true：是否是主键
     * autoGen = true：是否自动增长
     * property = "NOT NULL"：添加约束
     */
    @Column(name = "id", isId = true, autoGen = true, property = "NOT NULL")
    public int id;
    @Column(name = "data")
    public String data;
    @Column(name = "content")
    public String content;

    public Log() {
    }

    public Log(String data, String content) {
        this.data = data;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
