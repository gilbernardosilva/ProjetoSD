package edu.ufp.inf.sd.rmi.project.server;

import java.io.Serializable;

public class SubjectState implements Serializable {

    private Integer id;
    private String character;


    public SubjectState(Integer id) {
        this.id = id;
    }
    public SubjectState(Integer id, String character) {
        this.id = id;
        this.character = character;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
