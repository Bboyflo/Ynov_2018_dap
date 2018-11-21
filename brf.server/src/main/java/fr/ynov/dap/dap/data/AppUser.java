package fr.ynov.dap.dap.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Florian
 *
 */
@Entity
public class AppUser {

    /**.
     * declaration de l'id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**.
     * declaration de name
     */
    @Column(unique = true)
    private String name;

    /**.
     * Declaration de la liste des comptes
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccountData> googleAccounts;

    /**.
     * Creation des compte
     * @param account Le compte
     */
    public void addGoogleAccount(final GoogleAccountData account) {
        account.setOwner(this);
        this.getAccounts().add(account);
    }

    /**.
     * Ajout des comptes dans la liste
     * @return googleAccounts
     */
    public List<GoogleAccountData> getAccounts() {
        return googleAccounts;
    }

    /**.
     * recupération du nom
     * @return le nom
     */
    public String getName() {
        return name;
    }

    /**.
     * Implémente le nom
     * @param theName nom
     */
    public void setName(final String theName) {
        this.name = theName;
    }

}
