/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dov.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "bookedexams")
@NamedQueries({
    @NamedQuery(name = "Bookedexams.findAll", query = "SELECT b FROM Bookedexams b")})
public class Bookedexams implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Totalscore")
    private BigDecimal totalscore;
    @Column(name = "Totalcorrectanswers")
    private Integer totalcorrectanswers;
    @Column(name = "Startdatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdatetime;
    @Column(name = "Enddatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddatetime;
    @Basic(optional = false)
    @Column(name = "Status")
    private String status;
    @JoinColumn(name = "Examination_id", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("bookedexamsList")
    private Examinations examinations;
    @JoinColumn(name = "User_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("bookedexamsList")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookedexams", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("bookedexams")
    @OrderBy("id")
    private List<Questions> questionsList = new LinkedList<>();

    public Bookedexams() {
    }

    public Bookedexams(Long id) {
        this.id = id;
    }

    public Bookedexams(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    @PrePersist
    private void insertQuestions(){
    	for (int k=0; k < questionsList.size(); k++){
    		questionsList.get(k).setBookedexams(this);
    	}
    }
    @PreUpdate
    private void updateQuestions(){
    	for (int k=0; k < questionsList.size(); k++){
    		questionsList.get(k).setBookedexams(this);
    	}
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(BigDecimal totalscore) {
        this.totalscore = totalscore;
    }

    public Integer getTotalcorrectanswers() {
        return totalcorrectanswers;
    }

    public void setTotalcorrectanswers(Integer totalcorrectanswers) {
        this.totalcorrectanswers = totalcorrectanswers;
    }

    public Date getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(Date startdatetime) {
        this.startdatetime = startdatetime;
    }

    public Date getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(Date enddatetime) {
        this.enddatetime = enddatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Examinations getExaminations() {
        return examinations;
    }

    public void setExaminations(Examinations examinations) {
        this.examinations = examinations;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Questions> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
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
        if (!(object instanceof Bookedexams)) {
            return false;
        }
        Bookedexams other = (Bookedexams) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Bookedexams[ id=" + id + " ]";
    }
    
}
