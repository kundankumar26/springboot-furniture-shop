package com.example.furnitureshop.models;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="confirmation_token_table")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name="username_token")
    private String username;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public ConfirmationToken(long tokenid, String confirmationToken, Date createdDate, String username, User user) {
        this.tokenid = tokenid;
        this.confirmationToken = confirmationToken;
        this.createdDate = createdDate;
        this.username = username;
        this.user = user;
    }

    public ConfirmationToken(User user) {
        this.user = user;
        createdDate = new Date();
        username = user.getEmpUsername();
        confirmationToken = UUID.randomUUID().toString();
    }

    public ConfirmationToken() {

    }

    public long getTokenid() {
        return tokenid;
    }

    public void setTokenid(long tokenid) {
        this.tokenid = tokenid;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}