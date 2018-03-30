package org.gravitechx.frc2018.utils.loader.file;

import org.gravitechx.frc2018.utils.loader.file.FileHandeler;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.ListIterator;

import static org.gravitechx.frc2018.DataConst.LEFT_TEST;
import static org.gravitechx.frc2018.DataConst.PATH_TO_EXAMPLE_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileHandelerTest {
    @Test
    void testingFileExists() {
        File file = new File(PATH_TO_EXAMPLE_FILE);
        String path;

        try {
            path = file.getCanonicalPath();
        }catch (Exception e){
            path = file.getAbsolutePath();
        }

        assertTrue(file.exists(), "File: " + path + " does not exist.");
    }

    @Test
    void csvCanLinearize() {
        File file = new File(PATH_TO_EXAMPLE_FILE);
        FileHandeler fh = new FileHandeler(file);

        LinkedList<String> tokens = new LinkedList<>();
        fh.read(fh.LINEARIZE_CSV, tokens);

        ListIterator<String> listIterator = tokens.listIterator();
        int i = 0;
        while(listIterator.hasNext()){
            assertEquals(Double.parseDouble(listIterator.next()),  LEFT_TEST[i], "File differs from real at value " + i + ".");
            i++;
        }
    }

    @Test
    void csvChunks(){
        File file = new File(PATH_TO_EXAMPLE_FILE);
        FileHandeler fh = new FileHandeler(file);
        TableCSVConsumer csvConsumer = new TableCSVConsumer();
        fh.read(csvConsumer);
        String[] buffer;
        int i = 0, r = 0;
        while((buffer = csvConsumer.returnNextRow(3)) != null){
            for (int c = 0; c < buffer.length; c++){
                assertEquals(LEFT_TEST[i], Double.parseDouble(buffer[c]), "Row: " + r + " Col: " + c + " and index " + i + "does not match.");
                i++;
            }
            r++;
        }
    }
}