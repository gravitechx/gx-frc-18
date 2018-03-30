package org.gravitechx.frc2018.utils.loader.file;

import org.gravitechx.frc2018.utils.loader.trajectory.Trajectory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FileHandeler {
    public File file;

    public FileHandeler(File file){
        this.file = file;
    }

    /**
     * Read a file into a biconsumer
     * Specifies the first option as a string and the second as the object to which the parsed line will be written
     * @param lineConsumer
     */
    public <D> void read(BiConsumer<String, D> lineConsumer, D data) {
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                lineConsumer.accept(scanner.next(), data);
            }
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }
    }

    public <D> void read(Consumer<String> lineConsumer) {
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                lineConsumer.accept(scanner.next());
            }
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }
    }

    CSVConsumer LINEARIZE_CSV = new CSVConsumer() {
        @Override
        public void accept(String s, LinkedList<String> list) {
            parseToTokens(s, ',', list);
        }
    };
}
