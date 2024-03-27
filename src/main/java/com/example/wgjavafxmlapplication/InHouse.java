package com.example.wgjavafxmlapplication;

/** Class that creates an In-House Part*/
public class InHouse extends Part{

    private int machineId;

    /**Creates an In-House Part.
     @param id ID of part.
     @param name Name of part.
     @param price Price of part.
     @param stock Amount of parts.
     @param min Minimum parts required.
     @param max Maximum parts required.
     @param machineId Machine ID of part. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Change the value of the parts Machine ID
     @param machineId value to set */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** @return Returns machineId. */
    public int getMachineId() {
        return machineId;
    }
}
