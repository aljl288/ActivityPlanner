package ukm.com.academiccalendar.Model;

public class Users {

    private String matric, name, phone, image, password;

    public Users(){

    }

    public Users(String matric, String name, String image, String phone, String password) {
        this.matric = matric;
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.password = password;
    }

    public String getMatric() {
        return matric;
    }

    public void setMatric(String matric) {
        this.matric = matric;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
