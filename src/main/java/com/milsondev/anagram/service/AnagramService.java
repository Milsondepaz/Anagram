/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milsondev.anagram.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * HomeController.java Purpose: This is a class of service, it’s like the heart
 * of application, all operations and logic have been implemented here.
 *
 * @author Milson
 */
@Service
public class AnagramService {

    private ArrayList<String> listWords = new ArrayList();
    private ArrayList<String> listAnagram = new ArrayList();

    /*
     * Main class method, responsible for processing the file (with the help of another private method)
     * and return the String with the group of anagrams founded in the input file.
     */
    public String processFile(MultipartFile pr_file) throws IOException {

        listWords = new ArrayList();
        listAnagram = new ArrayList();

        FileContentToArray(pr_file);

        StringBuilder strAnagramGroup = new StringBuilder();
        for (int i = 0; i < listWords.size(); i++) {
            String str1 = listWords.get(i);
            boolean isAnagram = false;
            for (int j = i + 1; j < listWords.size(); j++) {
                String str2 = listWords.get(j);
                if (isAnagram(str1, str2)) {
                    isAnagram = true;
                    strAnagramGroup.append(str2);
                    strAnagramGroup.append(" ");
                    listWords.remove(str2);
                    j--;
                }
            }
            if (isAnagram) {
                strAnagramGroup.insert(0, str1 + " ");
                strAnagramGroup.deleteCharAt(strAnagramGroup.length() - 1);
                listAnagram.add(strAnagramGroup.toString());
            }
            strAnagramGroup = new StringBuilder();
        }

        String output = "";
        for (int i = 0; i < listAnagram.size(); i++) {
            output = output + listAnagram.get(i) + "\n";
        }

        return output;
    }

    /*
     * This method is the one that finally checks if two words are anagrams and returns a Boolean value.
     */
        static boolean isAnagram(String pr_str1, String pr_str2) {

        char[] str1 = pr_str1.toLowerCase().toCharArray();
        char[] str2 = pr_str2.toLowerCase().toCharArray();

        int n1 = str1.length;
        int n2 = str2.length;

        if (n1 != n2) {
            return false;
        }

        Arrays.sort(str1);
        Arrays.sort(str2);

        for (int i = 0; i < n1; i++) {
            if (str1[i] != str2[i]) {
                return false;
            }
        }

        return true;
    }

    /*
    * This method reads the contents of the input file (all words within the file) and inserts them into an ArrayList - listWords
    */
    private void FileContentToArray(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            InputStreamReader inputStreamReader = null;
            try {
                InputStream inputStream = file.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader br = new BufferedReader(inputStreamReader);
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    listWords.add(sCurrentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
