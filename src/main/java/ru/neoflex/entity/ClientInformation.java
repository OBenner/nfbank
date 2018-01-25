package ru.neoflex.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "client")
public class ClientInformation {

    private long id;

    private String name;

    private String lastname;

    private String birth;

    private String password;

    private long clientAccount;


    public ClientInformation(String name, String lastname, String birth, String password, long clientAccount) {
        this.name = name;
        this.lastname = lastname;
        this.birth = birth;
        this.password = password;
        this.clientAccount = clientAccount;
    }

    public ClientInformation(long id, String name, String lastname, String birth, String password, long clientAccount) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.birth = birth;
        this.password = password;
        this.clientAccount = clientAccount;
    }

    public ClientInformation() {

    }


    public long getId() {
        return id;
    }

    @XmlElement
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    @XmlElement
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirth() {
        return birth;
    }

    @XmlElement
    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public long getClientAccount() {
        return clientAccount;
    }

    @XmlElement
    public void setClientAccount(long clientAccount) {
        this.clientAccount = clientAccount;
    }

    @Override
    public String toString() {
        return "ClientInformation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birth=" + birth +
                ", password='" + password + '\'' +
                ", clientAccount=" + clientAccount +
                '}';
    }
}
