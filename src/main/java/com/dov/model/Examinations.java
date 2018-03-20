/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dov.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 *
 * @author dove
 */
@Entity
@Table(name = "examinations")
@NamedQueries({
    @NamedQuery(name = "Examinations.findAll", query = "SELECT e FROM Examinations e")})
public class Examinations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "Examcode")
    private String examcode;
    @Column(name = "Shortdescription")
    private String shortdescription;
    @Lob
    @Column(name = "Fulldescription")
    private String fulldescription;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Passscore")
    private BigDecimal passscore;
    @Basic(optional = false)
    @Column(name = "Totalquestions")
    private int totalquestions;
    @Column(name = "Durationmillsec")
    private Integer durationmillsec;
    @Basic(optional = false)
    @Column(name = "Examtime")
    private String examtime;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "examinations", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("examinations")
    @OrderBy("id")
    private List<Bookedexams> bookedexamsList = new LinkedList<>();
    @JoinColumn(name = "Institution_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("examinationsList")
    private Institution institution;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examinations", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("examinations")
    @OrderBy("id")
    private List<Questionsbank> questionsbankList = new LinkedList<>();
 
    public Examinations() {
    }

    public Examinations(Long id) {
        this.id = id;
    }

    public Examinations(Long id, String examcode, BigDecimal passscore, int totalquestions, String examtime) {
        this.id = id;
        this.examcode = examcode;
        this.passscore = passscore;
        this.totalquestions = totalquestions;
        this.examtime = examtime;
    }
    @PrePersist
    private void insertQuestionsBank(){
    	for (int k=0; k < questionsbankList.size(); k++){
    		questionsbankList.get(k).setExaminations(this);
    	}
    }
    @PreUpdate
    private void updateQuestionsBank(){
    	for (int k=0; k < questionsbankList.size(); k++){
    		questionsbankList.get(k).setExaminations(this);
    	}
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamcode() {
        return examcode;
    }

    public void setExamcode(String examcode) {
        this.examcode = examcode;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getFulldescription() {
        return fulldescription;
    }

    public void setFulldescription(String fulldescription) {
        this.fulldescription = fulldescription;
    }

    public BigDecimal getPassscore() {
        return passscore;
    }

    public void setPassscore(BigDecimal passscore) {
        this.passscore = passscore;
    }

    public int getTotalquestions() {
        return totalquestions;
    }

    public void setTotalquestions(int totalquestions) {
        this.totalquestions = totalquestions;
    }

    public Integer getDurationmillsec() {
        return durationmillsec;
    }

    public void setDurationmillsec(Integer durationmillsec) {
        this.durationmillsec = durationmillsec;
    }

    public String getExamtime() {
        return examtime;
    }

    public void setExamtime(String examtime) {
        this.examtime = examtime;
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

    public List<Questionsbank> getQuestionsbankList() {
        return questionsbankList;
    }

    public void setQuestionsbankList(List<Questionsbank> questionsbankList) {
        this.questionsbankList = questionsbankList;
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
        if (!(object instanceof Examinations)) {
            return false;
        }
        Examinations other = (Examinations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Examinations[ id=" + id + " ]";
    }
    
}
