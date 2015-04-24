/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lockwallet;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.andhsli.hotspotlogin.SimpleCrypto;

/**
 *
 * @author giovanni
 */
public class PwdItem {

    private int id;
    private String domain;
    private String username;
    private String password;
    private String comment;

    public PwdItem() {
        this.domain = "";
        this.username = "";
        this.password = "";
        this.comment = "";
    }
    
    public PwdItem(String d, String u, String p, String c){
        this.domain=d;
        this.username=u;
        this.password=p;
        this.comment=c;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setDomain(String d) {
        this.domain = d;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setUsername(String u) {
        this.username = u;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public String getPassword() {
        return this.password;
    }

    public void setComment(String c) {
        this.comment = c;
    }

    public String getComment() {
        return this.comment;
    }
    
    /*
    * Returns a password hint.
    * If the password is less than 2 chars, the method returns null
    * If the password is less than 5 chars the method returns the first 2 letters
    * Otherwise it returns the first 3 letters and the last one.
    */
    public String getPasswordHint(){
        String hint;
        if(this.password.length()<2) return null;
        
        if(this.password.length()<5){
            hint=new String(this.password.substring(0, 2));
            return hint;
        }
        else{
            hint=new String(this.password.substring(0, 3));
            hint = hint+"...";
            hint=hint+this.password.substring(this.password.length()-1);
            return hint;
        }
    }
    
    //encrypt the password and return it
    public String EncryptPassword(String passphrase){
        if(passphrase==null || this.password==null) return null;
        
        try {
            this.domain=SimpleCrypto.encrypt(passphrase, this.domain);
            this.username=SimpleCrypto.encrypt(passphrase, this.username);
            this.password=SimpleCrypto.encrypt(passphrase, this.password);
            this.comment=SimpleCrypto.encrypt(passphrase, this.comment);
            
        } catch (Exception ex) {
            Logger.getLogger(PwdItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.password;
    }
    
    //decrypt the password and return it
    public String DecryptPassword(String passphrase){
        if(passphrase==null || this.password==null) return null;
        
        try {
            this.domain=SimpleCrypto.decrypt(passphrase, this.domain);
            this.username=SimpleCrypto.decrypt(passphrase, this.username);
            this.password=SimpleCrypto.decrypt(passphrase, this.password);
            this.comment=SimpleCrypto.decrypt(passphrase, this.comment);
        } catch (Exception ex) {
            Logger.getLogger(PwdItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.password;
    }
}
