package com.semester_project.smd_project;

public class Guider_info {
    private String Name;
    private String Age;
    //private Areas Areas;
    private String Year;
    private String Price;

    public Guider_info(String name, String age/*, List<Areas> areas*/, String year, String price)
    {
        this.Name = name;
        this.Age = age;
        //this.Areas = get_Areas();
        this.Year = year;
        this.Price = price;
    }

    public void set_Areas(Areas area)
    {
        //this.Areas= area;
    }

    public void set_Year(String year)
    {
        this.Year = year;
    }

    public void set_Price(String price)
    {
        this.Price = price;
    }

    public String get_Name()
    {
      return  this.Name;
    }

    public String get_Age()
    {
      return this.Age;
    }

    /*public Areas get_Areas()
    {
        return this.Areas;
    }*/

    public String get_Year()
    {
        return this.Year;
    }

    public String get_Price()
    {
        return this.Price;
    }
}
