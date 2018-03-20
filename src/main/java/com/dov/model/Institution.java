/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dov.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 *
 * @author dove
 */
@Entity
@Table(name = "Institution")
@NamedQueries({
    @NamedQuery(name = "Institution.findAll", query = "SELECT i FROM Institution i")})
public class Institution implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "Instcode")
    private String instcode;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Column(name = "Address")
    private String address;
    @Column(name = "City")
    private String city;
    @Column(name = "State")
    private String state;
    @Column(name = "Country")
    private String country;
    @Column(name = "Officephone")
    private String officephone;
    @Column(name = "Mobilephone")
    private String mobilephone;
    @Column(name = "Website")
    private String website;
    @Lob
    @Column(name = "Logo")
    private byte[] logo;
    @Column(name = "Emailserver")
    private String emailserver;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "institution", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("institution")
    @OrderBy("id")
    private List<Examinations> examinationsList = new LinkedList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "institution", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties("institution")
    @OrderBy("id")    
    private List<User> userList = new LinkedList<>();

    public boolean removeUser(User user) {
    	if (user != null) {
    	user.setInstitution(null);
        return userList.remove(user);
        }
        return false;       
    }

    public boolean removeExamination(Examinations exams) {
        if (exams != null) {
            exams.setInstitution(null);
            return examinationsList.remove(exams);
        }
        return false;
    }
    public Institution() {
    }

    public Institution(Long id) {
        this.id = id;
    }

    public Institution(Long id, String instcode, String name) {
        this.id = id;
        this.instcode = instcode;
        this.name = name;
    }

    @PreUpdate
    private void updateAllChild(){
    	for (int k=0; k < userList.size(); k++){
    		userList.get(k).setInstitution(this);
    	}
    	for (int k=0; k < examinationsList.size(); k++){
    		examinationsList.get(k).setInstitution(this);
    	} 	
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstcode() {
        return instcode;
    }

    public void setInstcode(String instcode) {
        this.instcode = instcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOfficephone() {
        return officephone;
    }

    public void setOfficephone(String officephone) {
        this.officephone = officephone;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getEmailserver() {
        return emailserver;
    }

    public void setEmailserver(String emailserver) {
        this.emailserver = emailserver;
    }

    public List<Examinations> getExaminationsList() {
        return examinationsList;
    }

    public void setExaminationsList(List<Examinations> examinationsList) {
        this.examinationsList = examinationsList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institution)) {
            return false;
        }
        Institution other = (Institution) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Institution[ id=" + id + " ]";
    }
    
}
