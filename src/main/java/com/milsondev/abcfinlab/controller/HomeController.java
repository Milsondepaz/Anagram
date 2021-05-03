/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milsondev.abcfinlab.controller;

import com.milsondev.abcfinlab.service.AnagramService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
 * HomeController.java Purpose: Class responsible for receiving http requests
 * from a client (browser), forward to a specific method and return a View as an
 * answer.
 *
 * @author Milson
 */
@RequestMapping
@Controller
public class HomeController {

    //dependency injection of the AnagramService.java Class  
    @Autowired
    private AnagramService anagramService;

    /*
     * Receive a "Get" request and return the View index
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /*
     * Receives a "Post" request with two parameters, the file uploaded by the user and RedirectAttributes from Spring,
     * This second parameter will be used to send the data to be rendered by View to the client.
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile pr_file, RedirectAttributes attributes) throws IOException {
        if (pr_file.isEmpty()) {
            attributes.addFlashAttribute("message2", "Sorry, your file is empty.");
            return "redirect:/";
        }
        attributes.addFlashAttribute("message1", " Your file has been successfully uploaded and processed!");
        attributes.addFlashAttribute("output", anagramService.processFile(pr_file));
        return "redirect:/";
    }
}
