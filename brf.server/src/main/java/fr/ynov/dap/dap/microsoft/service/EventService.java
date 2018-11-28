package fr.ynov.dap.dap.microsoft.service;

import java.io.IOException;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.dap.microsoft.auth.TokenResponse;
import fr.ynov.dap.dap.microsoft.data.MicrosoftAccountData;

/**
 * @author Florian
 */
public class EventService {

    /**.
     * Déclaration de MAXRESULTS
     */
    private static final int MAXRESULTS = 1;

    /**
     * @param redirectAttributes .
     * @param account .
     * @param model .
     * @param userKey .
     * @return Nombre de mail non lu
     */
    public static String firstEvent(RedirectAttributes redirectAttributes, MicrosoftAccountData account, Model model,
            String userKey) {
        TokenResponse tokens = account.getTokenResponse();
        String tenantId = account.getTenantId();
        if (tokens == null) {
            // No tokens in session, user needs to sign in
            redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            return "redirect:/";
        }

        tokens = AuthHelper.ensureTokens(tokens, tenantId);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

        // Sort by start time in descending order
        String sort = "start/dateTime DESC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";

        try {
            PagedResult<Event> events = outlookService.getEvents(sort, properties, MAXRESULTS).execute().body();
            model.addAttribute("events", events.getValue());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }

        return "event";
    }
}
