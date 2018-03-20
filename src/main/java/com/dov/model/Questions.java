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
@Table(name = "questions")
@NamedQueries({
    @NamedQuery(name = "Questions.findAll", query = "SELECT q FROM Questions q")})
public class Questions implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questions", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("questions")
    @OrderBy("id")
    private List<Answers> answersList = new LinkedList<>();
    @JoinColumn(name = "bookedexam_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("questionsList")
    private Bookedexams bookedexams;

    public Questions() {
    }

    public Questions(Long id) {
        this.id = id;
    }

    public Questions(Long id, boolean multipleanswer, int queorder, String question) {
        this.id = id;
        this.multipleanswer = multipleanswer;
        this.queorder = queorder;
        this.question = question;
    }
    public Questions(boolean multipleanswer, int queorder, String question) {
        this.multipleanswer = multipleanswer;
        this.queorder = queorder;
        this.question = question;
    }
    @PrePersist
    private void insertAnswers(){
    	for (int k=0; k < answersList.size(); k++){
    		answersList.get(k).setQuestions(this);
    	}
    }   
	@PreUpdate
	public void updateAnswers() {
		for (int j = 0; j < answersList.size(); j++) {
			answersList.get(j).setQuestions(this);
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

    public List<Answers> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(List<Answers> answersList) {
        this.answersList = answersList;
    }

    public Bookedexams getBookedexams() {
        return bookedexams;
    }

    public void setBookedexams(Bookedexams bookedexams) {
        this.bookedexams = bookedexams;
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
        if (!(object instanceof Questions)) {
            return false;
        }
        Questions other = (Questions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Questions[ id=" + id + " ]";
    }
    
}
