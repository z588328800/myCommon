package priv.zl.mycommon.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListUtils {
    public static <T> List<T> mapToList(Map<String, T> map) {
        List<T> list = new ArrayList<T>();
        Collection<T> collection = map.values();
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            T value = (T) iterator.next();
            list.add(value);
        }
        return list;
    }
}
