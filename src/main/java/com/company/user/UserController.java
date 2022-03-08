package com.company.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Random;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String showUserList(Model model) {
        List<User> listUsers = service.listUsers();
        model.addAttribute("listUsers", listUsers);
        return "users"; //logical view name in resource
    }

    // show user form
    @GetMapping("/users/new")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        // for dynamic page title, same html used for adding user form and edit user form
        // this is for adding user
        model.addAttribute("pageTitle", "Add New User");
        return "user_form";
    }

    // save users from user_form route /users/save
    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) {
        //th:object=${user} from form is the user parameter
        //calls service with repo.save method
        service.save(user);

        //used in th:if="message" attributeName
        ra.addFlashAttribute("message", "Successfully saved a user!");

        //after saving redirect to /users
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            //adds user object to the page with user attributeName or variable
            model.addAttribute("user", user);
            // this is for the title when editing a user, using the same form as add user
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", "Successfully saved a user!");
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.deleteUser(id);
            ra.addFlashAttribute("message", "User " + id + " has been deleted");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }
}