package ua.javaexternal_shulzhenko.repair_service.services.editing;

import ua.javaexternal_shulzhenko.repair_service.models.forms.UserEditingForm;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.UsersDBService;

import java.util.LinkedList;
import java.util.List;

public class UserEditor {

    private UserEditingForm form;
    private User user;
    private List<String> edits;

    public UserEditor(UserEditingForm form, User user) {
        this.form = form;
        this.user = user;
        edits = new LinkedList<>();
    }

    public UserEditor compareFirstName(){
        if(!form.getFirstName().equals(user.getFirstName())){
            edits.add("firstName");
        }
        return this;
    }

    public UserEditor compareLastName(){
        if(!form.getLastName().equals(user.getLastName())){
            edits.add("lastName");
        }
        return this;
    }

    public UserEditor compareEmail(){
        if(!form.getEmail().equals(user.getEmail())){
            edits.add("email");
        }
        return this;
    }

    public UserEditor compareRole(){
        if(!form.getRole().equals(user.getRole())){
            edits.add("role");
        }
        return this;
    }

    public void edit(){
        UsersDBService.editUser(form, edits);
    }
}