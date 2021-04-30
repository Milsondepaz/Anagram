/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milsondev.abcfinlab.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

    private MultipartFile file = null;
    
    @GetMapping("/")
    public String home() {

        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile pr_file, RedirectAttributes attributes) throws IOException {
        if (pr_file.isEmpty()) {
            attributes.addFlashAttribute("message", "Select a file to upload.");
            return "redirect:/";
        }
        String fileName = StringUtils.cleanPath(pr_file.getOriginalFilename());
        file = pr_file;
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
        return "redirect:/";
    }
    
    
    public void processar (){
        try {
            String content = new String(file.getBytes());
        } catch (IOException ex) {
            
            
        }
        
        
    }

}
