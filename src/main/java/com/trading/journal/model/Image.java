package com.trading.journal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String screenName;

    private String screenType;

    private String screenFilePath;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH
            , CascadeType.MERGE, CascadeType.REFRESH} )
    @JoinColumn(name = "deal_id")
    private Deal deal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getScreenFilePath() {
        return screenFilePath;
    }

    public void setScreenFilePath(String screenFilePath) {
        this.screenFilePath = screenFilePath;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }
}
