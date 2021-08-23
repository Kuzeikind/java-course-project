package dao.domain;

import dao.enums.RangerRank;

public class Ranger extends AbstractEntity {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private RangerRank rank;

    public Ranger(long id, String firstName, String lastName, String email, RangerRank rank) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.rank = rank;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RangerRank getRangerRank() {
        return rank;
    }

    public void setRangerRank(RangerRank rank) {
        this.rank = rank;
    }
}

