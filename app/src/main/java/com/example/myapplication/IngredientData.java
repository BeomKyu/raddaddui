package com.example.myapplication;

public class IngredientData {
    private int img;
    private String trademark ;
    private String expirationdate;

    public IngredientData(int img, String trademark, String expirationdate){
        this.img = img;
        this.trademark  = trademark;
        this.expirationdate = expirationdate;
    }

    public int getImg()
    {
        return this.img;
    }

    public String getTrademark()
    {
        return this.trademark;
    }

    public String getExpirationdate()
    {
        return this.expirationdate;
    }
}
