package org.gravitechx.frc2018.utils.loader.file;

import java.util.LinkedList;
import java.util.function.BiConsumer;

public abstract class CSVConsumer implements BiConsumer<String, LinkedList<String>> {
    protected LinkedList<String> parseToTokens(String s, char sep, LinkedList<String> list){
        int breakIndex = s.indexOf(sep);
        if(breakIndex != -1){
            list.add(s.substring(0, breakIndex));
            parseToTokens(s.substring(breakIndex + 1), sep, list);
        } else if(s.length() > 0) list.add(s);
        return list;
    };
}