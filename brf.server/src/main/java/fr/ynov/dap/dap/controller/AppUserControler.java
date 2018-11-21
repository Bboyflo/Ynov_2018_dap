package fr.ynov.dap.dap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;

/**
 * @author Florian
 */
@RestController
public class AppUserControler {

    /**.
     * declaration de appUserRepostory
     */
    @Autowired
    private AppUserRepository appUserRepostory;

    /**
     * @param userKey nom du compte
     * @throws Exception fonction
     */
    @RequestMapping("/user/add/{userKey}")
    public void addAppUser(@PathVariable("userKey") final String userKey) throws Exception {
        AppUser utilisateur = new AppUser();
        utilisateur.setName(userKey);
        appUserRepostory.save(utilisateur);
    }
}
