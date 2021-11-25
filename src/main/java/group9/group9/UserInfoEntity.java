package group9.group9;

import javax.persistence.*;

@Entity
public class UserInfoEntity {
  
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    Integer id;
    Integer userid;

    private  String fullName;

    private  String email;

    private String password;


    private  String phone;

    private  String address1;

    private  String address2;

    private  String city;

    private  String state;

    private  String zipcode;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId(){
        return userid;
    }
    public void setUserId(int userid) {
        this.userid = userid;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "UserInfoEntity [address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", email=" + email
                + ", fullname=" + fullName + ", phone=" + phone + ", state=" + state + ", userinfo_id=" + id
                + ", zipcode=" + zipcode + "]";
    }





}
