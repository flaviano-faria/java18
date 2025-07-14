package com.java18.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    public void createFile() {
        try (FileWriter fw = new FileWriter("filetest.txt");
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("こんにちは世界");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
