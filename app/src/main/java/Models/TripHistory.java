package Models;

public class TripHistory
{
    protected String user_name;
    protected String date;
    protected String dollars_spent;

    public TripHistory(String user_n, String date, String dollars)
    {
        this.user_name = user_n;
        this.date = date;
        this.dollars_spent = dollars;
    }

    public void set_user_name(String n)
    {
        this.user_name = n;
    }

    public void set_date(String n)
    {
        this.date = n;
    }
    public void set_spent(String n)
    {
        this.dollars_spent = n;
    }

    public String get_user_name()
    {
        return this.user_name;
    }

    public String get_date()
    {
        return this.date;
    }

    public String get_dollars_spent()
    {
        return this.dollars_spent;
    }
}
