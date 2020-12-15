package Models;

public class User {
    private String username;
    private String useremail;
    private String password;
    private String profilepic;
    private String coverpic;
    private String phone;
    private String address;
    private String country;
    private String budget;
    private String age;
    private String experience;

    public User(String username, String useremail, String password, String profilepic, String coverpic, String phone, String address, String country, String budget_, String age_, String experience_)
    {
        this.username = username;
        this.useremail = useremail;
        this.password = password;
        this.profilepic = profilepic;
        this.coverpic = coverpic;
        this.phone = phone;
        this.address = address;
        this.country = country;
        this.budget = budget_;
        this.age = age_;
        this.experience = experience_;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", useremail='" + useremail + '\'' +
                ", password='" + password + '\'' +
                ", profilepic='" + profilepic + '\'' +
                ", coverpic='" + coverpic + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", budget='" + budget + '\'' +
                ", age='" + age + '\'' +
                ", experience='" + experience + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getCoverpic() {
        return coverpic;
    }

    public void setCoverpic(String coverpic) {
        this.coverpic = coverpic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
