package kwetter.beans;

import kwetter.service.interfaces.IKwetterService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by geh on 2-4-14.
 */
@SessionScoped
@Named("registerBean")
public class RegisterBean implements Serializable
{
    @Inject
    private IKwetterService service;

    private String name;
    private String password;
    private String email;
    private String web;
    private String bio;

    public RegisterBean()
    {
    }

    public String register()
    {
        if(this.name != null && this.password != null && this.service.addApplication(name, this.hashPassword(password), email, bio, web))
        {
            return "?faces-redirect=true";
        }
        else
        {
            return "loginerror.xhtml?faces-redirect=true";
        }
    }

    public boolean activate()
    {
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String name = map.get("name");
        String pass = map.get("key");

        return this.service.activate(name, pass);
    }

    private String hashPassword(String password)
    {
        String output = "";

        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            output = bigInt.toString(16);

            System.out.println(output);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return output;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getWeb()
    {
        return web;
    }

    public void setWeb(String web)
    {
        this.web = web;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }
}
