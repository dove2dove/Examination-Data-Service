/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dov.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author dove
 */
@Entity
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "Username")
    private String username;
    @Column(name = "Fullname")
    private String fullname;
    @Column(name = "Email")
    private String email;
    @Column(name = "Userpassword")
    private String userpassword;
    @Basic(optional = false)
    @Column(name = "Usertype")
    private String usertype;
    @Column(name = "Role")
    private String role;
    @Column(name = "Lastlogon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastlogon;
    @Column(name = "Lastlogonip")
    private String lastlogonip;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("user")
    @OrderBy("id")
    private List<Bookedexams> bookedexamsList = new LinkedList<>();
    @JoinColumn(name = "Institution_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("userList")
    private Institution institution;

    public boolean removeBookedexams(Bookedexams bookedexams) {
        if (bookedexams != null) {
        	bookedexams.setUser(null);
            return bookedexamsList.remove(bookedexams);
        }
        return false;
    }
   
    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String username, String usertype) {
        this.id = id;
        this.username = username;
        this.usertype = usertype;
    }

    @PrePersist
    private void insertBookedexamsk(){
    	for (int k=0; k < bookedexamsList.size(); k++){
    		bookedexamsList.get(k).setUser(this);
    	}
    }
    @PreUpdate
    private void updateBookedexams(){
    	for (int k=0; k < bookedexamsList.size(); k++){
    		bookedexamsList.get(k).setUser(this);
    	}
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getLastlogon() {
        return lastlogon;
    }

    public void setLastlogon(Date lastlogon) {
        this.lastlogon = lastlogon;
    }

    public String getLastlogonip() {
        return lastlogonip;
    }

    public void setLastlogonip(String lastlogonip) {
        this.lastlogonip = lastlogonip;
    }

    public List<Bookedexams> getBookedexamsList() {
        return bookedexamsList;
    }

    public void setBookedexamsList(List<Bookedexams> bookedexamsList) {
        this.bookedexamsList = bookedexamsList;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.User[ id=" + id + " ]";
    }
    
}
