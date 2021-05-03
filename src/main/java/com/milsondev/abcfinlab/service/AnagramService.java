/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milsondev.abcfinlab.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
     * 1- A check is made if both strings have the same size.
     * 2- All characters in both Strings are converted to lower case, if applicable.
     * 3- The first String - str1 is traversed, acquiring the value of the ASCII table for each character
     *    through the ^ xor operation, thus having the bits equivalent in the r variable.
     * 4- The same process is repeated for the second String str2, the ^ xor operation, will always be done with the r variable
     * 5- At the end, the value of the variable r is checked if both strings are the same/contain exactly the same characters,
     * the xor operation will reset all beats, and the variable will have a value of 0.
     */
    public boolean isAnagram(String str1, String str2) {
        if (str1.length() == str2.length()) {
            str1 = str1.toLowerCase();
            str2 = str2.toLowerCase();
            int r = 0;

            for (int i = 0; i < str1.length(); i++) {
                r = r ^ str1.charAt(i);
            }

            for (int i = 0; i < str2.length(); i++) {
                r = r ^ str2.charAt(i);
            }

            return (r == 0);
        }
        return false;
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
