package com.example.wgjavafxmlapplication;

/** Class that creates an In-House Part*/
public class Outsourced extends Part {

    private String companyName;

    /**Creates an In-House Part.
     @param id ID of part.
     @param name Name of part.
     @param price Price of part.
     @param stock Amount of parts.
     @param min Minimum parts required.
     @param max Maximum parts required.
     @param companyName Company Name of part. */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Change the value of the parts Company Name
     @param companyName value to set */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** @return companyName. */
    public String getCompanyName() {
        return companyName;
    }
}
