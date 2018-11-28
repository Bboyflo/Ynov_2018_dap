package fr.ynov.dap.dap.microsoft.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.dap.microsoft.service.EventService;

/**
 * @author Florian
 */
@Controller
public class EventsController {

    /**
     * Déclaration de appUserRepository
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * @param model .
     * @param request .
     * @param userKey .
     * @param redirectAttributes .
     * @return La page event
     */
    @RequestMapping("/events")
    public String events(@RequestParam("userKey") final String userKey, Model model, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        AppUser appUser = appUserRepository.findByName(userKey);
        String Url = "";
        if (appUser != null) {
            model.addAttribute("accounts", appUser.getMicrosoftAccounts());
            for (MicrosoftAccountData account : appUser.getMicrosoftAccounts()) {
                Url = EventService.firstEvent(redirectAttributes, account, model, userKey);
            }
            model.addAttribute("logoutUrl", "/logout");
            model.addAttribute("mail", "/mail?userKey=" + userKey);
            model.addAttribute("contact", "/contacts?userKey=" + userKey);
            return Url;
        }
        return "redirect:/";
    }
}
