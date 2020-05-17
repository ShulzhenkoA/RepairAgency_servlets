package ua.javaexternal_shulzhenko.repair_service.services.database_services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.javaexternal_shulzhenko.repair_service.constants.Role;
import ua.javaexternal_shulzhenko.repair_service.exceptions.DataBaseInteractionException;
import ua.javaexternal_shulzhenko.repair_service.models.forms.RegistrationForm;
import ua.javaexternal_shulzhenko.repair_service.models.forms.UserEditingForm;
import ua.javaexternal_shulzhenko.repair_service.services.editing.impl.UserEditor;
import ua.javaexternal_shulzhenko.repair_service.services.resourses.ApplicationResourceBundle;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UsersDBServiceTest {

    @BeforeAll
    static void setTestMode() {
        ApplicationResourceBundle.setTestBundle();
    }

    @ParameterizedTest
    @CsvSource("Adding test, Adding test, adding_test@mail.com, User1234, en, CUSTOMER")
    void creatingUser_addUserToDatabase(
            String firstName, String lastName, String email, String password, String language, String role){
        RegistrationForm form = mock(RegistrationForm.class);
        when(form.getFirstName()).thenReturn(firstName);
        when(form.getLastName()).thenReturn(lastName);
        when(form.getEmail()).thenReturn(email);
        when(form.getPassword()).thenReturn(password);
        when(form.getLanguage()).thenReturn(language);
        when(form.getRole()).thenReturn(Role.valueOf(role));

        assertDoesNotThrow(() ->  UsersDBService.createUser(form));
    }

    @ParameterizedTest
    @CsvSource(" 6,Transaction success, Transaction success, trns_success@mail.com, MANAGER")
    void editingUserTransaction_correctData_editsUserCompletely(
            int id, String firstName, String lastName, String email, String role){
        UserEditingForm form = mock(UserEditingForm.class);
        when(form.getId()).thenReturn(id);
        when(form.getFirstName()).thenReturn(firstName);
        when(form.getLastName()).thenReturn(lastName);
        when(form.getEmail()).thenReturn(email);
        when(form.getRole()).thenReturn(Role.valueOf(role));
        List<UserEditor.UserEdits> edits = new LinkedList<>();
        edits.add(UserEditor.UserEdits.FIRST_NAME);
        edits.add(UserEditor.UserEdits.LAST_NAME);
        edits.add(UserEditor.UserEdits.EMAIL);
        edits.add(UserEditor.UserEdits.ROLE);

        assertDoesNotThrow(() ->  UsersDBService.editUser(form, edits));
    }

    @ParameterizedTest
    @CsvSource(" 6, Failure, Failure,  , MANAGER")
    void editingUserTransaction_failure_rollbackPreviousUpdates(
            int id, String firstName, String lastName, String email, String role){
        UserEditingForm form = mock(UserEditingForm.class);
        when(form.getId()).thenReturn(id);
        when(form.getFirstName()).thenReturn(firstName);
        when(form.getLastName()).thenReturn(lastName);
        when(form.getEmail()).thenReturn(email);
        when(form.getRole()).thenReturn(Role.valueOf(role));
        List<UserEditor.UserEdits> edits = new LinkedList<>();
        edits.add(UserEditor.UserEdits.FIRST_NAME);
        edits.add(UserEditor.UserEdits.LAST_NAME);
        edits.add(UserEditor.UserEdits.EMAIL);
        edits.add(UserEditor.UserEdits.ROLE);

        assertThrows(DataBaseInteractionException.class, () ->  UsersDBService.editUser(form, edits));
    }
}