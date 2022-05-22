package com.example.myapplication;

public class IngredientData {
    private int img;
    private String trademark ;
    private String expirationdate;
    private String title;
    private String userId;


    public IngredientData(int img, String trademark, String expirationdate, String userId){
        this.img = img;
        this.trademark  = trademark;
        this.expirationdate = expirationdate;
        this.userId = userId;
    }

    public IngredientData(int img, String title){
        this.img = img;
        this.title  = title;

    }

    public int getReceipeImg()
    {
        return this.img;
    }

    public String getTitle()
    {
        return this.title;
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

    public String getUserId(){
        return  this.userId;
    }
}
