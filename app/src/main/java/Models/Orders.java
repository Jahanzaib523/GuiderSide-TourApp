package Models;

public class Orders
{
    private String Image;
    private String guider_name;
    private String guider_email;
    private String from_location;
    private String to_location;
    private String star_tdate;
    private String end_date;
    private String order_status;
    private String payment;

    public Orders(String image, String guider_name, String guider_email,
                  String from_location, String to_location, String star_tdate, String end_date) {
        Image = image;
        this.guider_name = guider_name;
        this.guider_email = guider_email;
        this.from_location = from_location;
        this.to_location = to_location;
        this.star_tdate = star_tdate;
        this.end_date = end_date;
    }

    public Orders(String image, String guider_name, String guider_email,
                  String from_location, String to_location, String star_tdate, String end_date, String order_status, String p) {
        Image = image;
        this.guider_name = guider_name;
        this.guider_email = guider_email;
        this.from_location = from_location;
        this.to_location = to_location;
        this.star_tdate = star_tdate;
        this.end_date = end_date;
        this.order_status = order_status;
        this.payment = p;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "Image='" + Image + '\'' +
                ", guider_name='" + guider_name + '\'' +
                ", guider_email='" + guider_email + '\'' +
                ", from_location='" + from_location + '\'' +
                ", to_location='" + to_location + '\'' +
                ", star_tdate='" + star_tdate + '\'' +
                ", end_date='" + end_date + '\'' +
                ", order_status='" + order_status + '\'' +
                ", payment='" + payment + '\'' +
                '}';
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getGuider_name() {
        return guider_name;
    }

    public void setGuider_name(String guider_name) {
        this.guider_name = guider_name;
    }

    public String getGuider_email() {
        return guider_email;
    }

    public void setGuider_email(String guider_email) {
        this.guider_email = guider_email;
    }

    public String getFrom_location() {
        return from_location;
    }

    public void setFrom_location(String from_location) {
        this.from_location = from_location;
    }

    public String getTo_location() {
        return to_location;
    }

    public void setTo_location(String to_location) {
        this.to_location = to_location;
    }

    public String getStar_tdate() {
        return star_tdate;
    }

    public void setStar_tdate(String star_tdate) {
        this.star_tdate = star_tdate;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
