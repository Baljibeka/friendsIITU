package com.example.friendsIITU.controller;

import com.example.friendsIITU.data.FriendBase;
import com.example.friendsIITU.model.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@Controller
@RequestMapping("/friends")
public class CRUDController {
    private FriendBase friendBase;

    @Autowired
    public CRUDController(FriendBase friendBase) {
        this.friendBase = friendBase;
    }
    @GetMapping
    public String index(Model model){
        model.addAttribute("friends",friendBase.getFriends());
        return "index";
    }
    @GetMapping("/{id}")
    public String filter(@PathVariable("id") int id,Model model){
        model.addAttribute("friend",friendBase.find(id));
        return "show";
    }
    @GetMapping("/register")
    public String newFriend(Model model){
        model.addAttribute("friend",new Friend());
        return "create";
    }
   /* @GetMapping("/login")
    public String login(@RequestParam("username") String username,@RequestParam("password") String password, Model model) throws FileNotFoundException {
        boolean checker=check(username,password);
        if (checker) return "welcome";
        else return "index";
    }*/
    @PostMapping
    public String create(@ModelAttribute("friend") Friend friend){
        friendBase.save(friend);
        return "redirect:/friends";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        friendBase.delete(id);
        return "redirect:/friends";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,Model model){
        model.addAttribute("friend",friendBase.find(id));
        return "edit";
    }
    @PutMapping("/{id}")
    public String update(@PathVariable("id") int id,@ModelAttribute("friend") Friend updatedFriend){
        friendBase.update(id, updatedFriend);
        return "redirect:/friends";
    }

}
