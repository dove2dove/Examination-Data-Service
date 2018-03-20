/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dov.model;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author dove
 */
@Entity
@Table(name = "answers")
@NamedQueries({
    @NamedQuery(name = "Answers.findAll", query = "SELECT a FROM Answers a")})
public class Answers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "answers")
    private boolean answers;
    @Basic(optional = false)
    @Lob
    @Column(name = "choices")
    private String choices;
    @Basic(optional = false)
    @Column(name = "correct")
    private boolean correct;
    @Basic(optional = false)
    @Column(name = "ansorder")
    private Character ansorder;
    @JoinColumn(name = "questions_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("answersList")
    private Questions questions;

    public Answers() {
    }

    public Answers(Long id) {
        this.id = id;
    }

    public Answers(Long id, boolean answers, String choices, boolean correct, Character ansorder) {
        this.id = id;
        this.answers = answers;
        this.choices = choices;
        this.correct = correct;
        this.ansorder = ansorder;
    }
    public Answers(boolean answers, String choices, boolean correct, Character ansorder) {
        this.answers = answers;
        this.choices = choices;
        this.correct = correct;
        this.ansorder = ansorder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getAnswers() {
        return answers;
    }

    public void setAnswers(boolean answers) {
        this.answers = answers;
    }

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }

    public boolean getCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Character getAnsorder() {
        return ansorder;
    }

    public void setAnsorder(Character ansorder) {
        this.ansorder = ansorder;
    }

    public Questions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
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
        if (!(object instanceof Answers)) {
            return false;
        }
        Answers other = (Answers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Answers[ id=" + id + " ]";
    }
    
}
