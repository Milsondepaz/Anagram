/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milsondev.abcfinlab.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Mein
 */
@RequestMapping
@Controller
public class HomeController {

    private ArrayList<String> listWords = new ArrayList();
    private ArrayList<String> listAnagram = new ArrayList();

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile pr_file, RedirectAttributes attributes) throws IOException {

        if (pr_file.isEmpty()) {
            attributes.addFlashAttribute("message2", "Sorry, your file is empty.");
            return "redirect:/";
        }

        attributes.addFlashAttribute("message1", " Your file has been successfully uploaded and processed!");

        listWords = new ArrayList();
        listAnagram = new ArrayList();

        TextContentToArray(pr_file);

        StringBuilder strAnagramGroup = new StringBuilder();
        for (int i = 0; i < listWords.size(); i++) {
            String str1 = listWords.get(i);
            boolean isAnagram = false;
            for (int j = i + 1; j < listWords.size(); j++) {
                String str2 = listWords.get(j);
                if (isAnagrama(str1, str2)) {
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

        attributes.addFlashAttribute("output", output);

        return "redirect:/";
    }

    private char[] StringToArrayChar(String str) {
        char[] ch = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }
        return ch;
    }

    private boolean isAnagrama(String pr_str1, String pr_str2) {

        char[] str1 = StringToArrayChar(pr_str1.toLowerCase());
        char[] str2 = StringToArrayChar(pr_str2.toLowerCase());

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

    private void TextContentToArray(MultipartFile file) throws IOException {
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
