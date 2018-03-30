package org.gravitechx.frc2018.utils.loader.file;
import java.util.LinkedList;
import java.util.function.Consumer;

public class TableCSVConsumer extends CSVConsumer implements Consumer<String> {
    private LinkedList<String> content;

    public TableCSVConsumer(){
        content = new LinkedList<>();
    }

    @Override
    public void accept(String s, LinkedList<String> list) {
        parseToTokens(s, ',', list);
    }

    @Override
    public void accept(String s){
        parseToTokens(s, ',', content);
    }

    /**
     * Reads a single row of the CSV into a array
     * @param sizeOfRow the size of a row in the CSV
     * @return
     */
    public String[] returnNextRow(int sizeOfRow){
        if(content.size() < sizeOfRow) return null;
        String[] row = new String[sizeOfRow];
        for(int i = 0; i < sizeOfRow; i++){
            row[i] = content.pop();
        }

        return row;
    }
}