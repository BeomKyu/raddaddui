package com.example.myapplication;

public class IngredientData {
    private String receipeimg;
    private String img;
    private String trademark ;
    private String expirationdate;
    private String title;
    private String userId;
    private String[] manual;


    public IngredientData(String img, String trademark, String expirationdate, String userId){
        this.img = img;
        this.trademark  = trademark;
        this.expirationdate = expirationdate;
        this.userId = userId;
    }

    public IngredientData(String getReceipeImg, String title, String[] manual){
        this.receipeimg = getReceipeImg;
        this.title  = title;
        this.manual = manual;

    }
    public String[] getManual(){
        return  this.manual;
    }

    public String getReceipeImg()
    {
        return this.receipeimg;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getImg()
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
