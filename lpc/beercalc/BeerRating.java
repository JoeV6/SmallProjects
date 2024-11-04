package org.lpc.beercalc;

public class BeerRating {
    double alcoholPercentage;
    double initialTaste;
    double generalTaste;
    double afterTaste;
    double texture;
    double smell;
    double carbonation;
    double uniqueness;
    double drinkability;
    double price;

    double finalRating;

    private Matrix weightMatrix;


    public BeerRating(double alcoholPercentage, double initialTaste, double generalTaste, double afterTaste, double texture, double smell, double carbonation, double uniqueness, double drinkability, double price) {
        this.alcoholPercentage = alcoholPercentage;
        this.initialTaste = initialTaste;
        this.generalTaste = generalTaste;
        this.afterTaste = afterTaste;
        this.texture = texture;
        this.smell = smell;
        this.carbonation = carbonation;
        this.uniqueness = uniqueness;
        this.drinkability = drinkability;
        this.price = price;

        initializeWeightMatrix();
    }

    private void initializeWeightMatrix() {
        weightMatrix = new Matrix(10, 10);

        weightMatrix.set(0, 0, 1.5);  // alcoholPercentage weight
        weightMatrix.set(1, 0, 2.0);  // initialTaste weight
        weightMatrix.set(2, 0, 2.5);  // generalTaste weight
        weightMatrix.set(3, 0, 1.8);  // afterTaste weight
        weightMatrix.set(4, 0, 1.2);  // texture weight
        weightMatrix.set(5, 0, 1.7);  // smell weight
        weightMatrix.set(6, 0, 1.3);  // carbonation weight
        weightMatrix.set(7, 0, 2.0);  // uniqueness weight
        weightMatrix.set(8, 0, 2.2);  // drinkability weight
        weightMatrix.set(9, 0, 0.9);  // price weight
    }

    private Matrix createAttributeMatrix() {
        Matrix attributeMatrix = new Matrix(10, 1);
        attributeMatrix.set(0, 0, alcoholPercentage);
        attributeMatrix.set(1, 0, initialTaste);
        attributeMatrix.set(2, 0, generalTaste);
        attributeMatrix.set(3, 0, afterTaste);
        attributeMatrix.set(4, 0, texture);
        attributeMatrix.set(5, 0, smell);
        attributeMatrix.set(6, 0, carbonation);
        attributeMatrix.set(7, 0, uniqueness);
        attributeMatrix.set(8, 0, drinkability);
        attributeMatrix.set(9, 0, price);

        return attributeMatrix;
    }

    public double calculateFinalRating() {
        Matrix attributes = createAttributeMatrix();
        Matrix weightedScores = attributes.multiply(weightMatrix);

        double sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += weightedScores.get(i, 0);
        }

        finalRating = sum / 10;  // Normalizing by number of factors
        return finalRating;
    }
    public static class RatingBuilder {
        private double alcoholPercentage;
        private double initialTaste;
        private double generalTaste;
        private double afterTaste;
        private double texture;
        private double smell;
        private double carbonation;
        private double uniqueness;
        private double drinkability;
        private double price;

        public RatingBuilder() {
            // Default values (can be changed by the user)
            this.alcoholPercentage = 0;
            this.initialTaste = 0;
            this.generalTaste = 0;
            this.afterTaste = 0;
            this.texture = 0;
            this.smell = 0;
            this.carbonation = 0;
            this.uniqueness = 0;
            this.drinkability = 0;
            this.price = 0;
        }

        public RatingBuilder setAlcoholPercentage(double alcoholPercentage) {
            this.alcoholPercentage = alcoholPercentage;
            return this;
        }

        public RatingBuilder setInitialTaste(double initialTaste) {
            this.initialTaste = initialTaste;
            return this;
        }

        public RatingBuilder setGeneralTaste(double generalTaste) {
            this.generalTaste = generalTaste;
            return this;
        }

        public RatingBuilder setAfterTaste(double afterTaste) {
            this.afterTaste = afterTaste;
            return this;
        }

        public RatingBuilder setTexture(double texture) {
            this.texture = texture;
            return this;
        }

        public RatingBuilder setSmell(double smell) {
            this.smell = smell;
            return this;
        }

        public RatingBuilder setCarbonation(double carbonation) {
            this.carbonation = carbonation;
            return this;
        }

        public RatingBuilder setUniqueness(double uniqueness) {
            this.uniqueness = uniqueness;
            return this;
        }

        public RatingBuilder setDrinkability(double drinkability) {
            this.drinkability = drinkability;
            return this;
        }

        public RatingBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        // Build method to return a BeerRating object
        public BeerRating build() {
            return new BeerRating(alcoholPercentage, initialTaste, generalTaste, afterTaste, texture, smell, carbonation, uniqueness, drinkability, price);
        }
    }

    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public double getInitialTaste() {
        return initialTaste;
    }

    public void setInitialTaste(double initialTaste) {
        this.initialTaste = initialTaste;
    }

    public double getGeneralTaste() {
        return generalTaste;
    }

    public void setGeneralTaste(double generalTaste) {
        this.generalTaste = generalTaste;
    }

    public double getAfterTaste() {
        return afterTaste;
    }

    public void setAfterTaste(double afterTaste) {
        this.afterTaste = afterTaste;
    }

    public double getTexture() {
        return texture;
    }

    public void setTexture(double texture) {
        this.texture = texture;
    }

    public double getSmell() {
        return smell;
    }

    public void setSmell(double smell) {
        this.smell = smell;
    }

    public double getCarbonation() {
        return carbonation;
    }

    public void setCarbonation(double carbonation) {
        this.carbonation = carbonation;
    }

    public double getUniqueness() {
        return uniqueness;
    }

    public void setUniqueness(double uniqueness) {
        this.uniqueness = uniqueness;
    }

    public double getDrinkability() {
        return drinkability;
    }

    public void setDrinkability(double drinkability) {
        this.drinkability = drinkability;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFinalRating() {
        return finalRating;
    }

    public void setFinalRating(double finalRating) {
        this.finalRating = finalRating;
    }
}
