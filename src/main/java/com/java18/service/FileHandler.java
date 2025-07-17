package com.java18.service;

import java.io.*;

public class FileHandler {

    public void createFile() {
        try (FileWriter fw = new FileWriter("filetest.txt");
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("こんにちは世界");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readFile() {
        try (FileReader fr = new FileReader("filetest.txt");
             BufferedReader br = new BufferedReader(fr)) {
            String line = br.readLine();
            return line;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
