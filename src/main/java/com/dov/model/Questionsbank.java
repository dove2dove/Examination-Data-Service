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
@Table(name = "questionsbank")
@NamedQueries({
    @NamedQuery(name = "Questionsbank.findAll", query = "SELECT q FROM Questionsbank q")})
public class Questionsbank implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "multipleanswer")
    private boolean multipleanswer;
    @Basic(optional = false)
    @Column(name = "queorder")
    private int queorder;
    @Basic(optional = false)
    @Lob
    @Column(name = "question")
    private String question;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionsbank", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("questionsbank")
    @OrderBy("id")
    private List<Answersbank> answersbankList = new LinkedList<>();
    @JoinColumn(name = "examination_id", referencedColumnName = "Id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("questionsbankList")
    private Examinations examinations;

    public Questionsbank() {
    }

    public Questionsbank(Long id) {
        this.id = id;
    }

    public Questionsbank(Long id, boolean multipleanswer, int queorder, String question) {
        this.id = id;
        this.multipleanswer = multipleanswer;
        this.queorder = queorder;
        this.question = question;
    }
    public Questionsbank(int queorder, String question, boolean multipleanswer) {
        this.multipleanswer = multipleanswer;
        this.queorder = queorder;
        this.question = question;
    }
    
    @PrePersist
    private void insertAnswersBank(){
    	for (int k=0; k < answersbankList.size(); k++){
    		answersbankList.get(k).setQuestionsbank(this);
    	}
    }
    @PreUpdate
    public void updateAnswersBank(){
    	for (int j=0; j < answersbankList.size(); j++){
    		answersbankList.get(j).setQuestionsbank(this);
    	}
    }   
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getMultipleanswer() {
        return multipleanswer;
    }

    public void setMultipleanswer(boolean multipleanswer) {
        this.multipleanswer = multipleanswer;
    }

    public int getQueorder() {
        return queorder;
    }

    public void setQueorder(int queorder) {
        this.queorder = queorder;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answersbank> getAnswersbankList() {
        return answersbankList;
    }

    public void setAnswersbankList(List<Answersbank> answersbankList) {
        this.answersbankList = answersbankList;
    }

    public Examinations getExaminations() {
        return examinations;
    }

    public void setExaminations(Examinations examinations) {
        this.examinations = examinations;
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
        if (!(object instanceof Questionsbank)) {
            return false;
        }
        Questionsbank other = (Questionsbank) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Questionsbank[ id=" + id + " ]";
    }
    
}
