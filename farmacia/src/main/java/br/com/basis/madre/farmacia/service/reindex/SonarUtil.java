package br.com.basis.madre.farmacia.service.reindex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SonarUtil {

    private SonarUtil() {
    }

    public static <T> List<T> instantiateList(List<T> list) {
        if (list == null) {
            return new ArrayList<T>();
        }
        return list;
    }

    public static <T> Set<T> instantiateSet(Set<T> set) {
        if (set == null) {
            return new HashSet<T>();
        }
        return set;
    }

    public static byte[] returnFile(byte[] file) {
        if (file != null) {
            return file.clone();
        }
        return null;
    }

}
