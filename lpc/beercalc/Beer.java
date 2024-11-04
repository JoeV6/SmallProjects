package org.lpc.beercalc;

import java.util.ArrayList;
import java.util.List;

public class Beer {
    private double overallRating;

    private List<BeerRating> ratingList = new ArrayList<>();
    private String name;

    public Beer(String name) {
        this.name = name;
    }

    public void addRating(BeerRating rating) {
        ratingList.add(rating);
    }
}
