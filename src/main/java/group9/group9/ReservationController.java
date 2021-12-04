package group9.group9;

//import java.util.List;

//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;


import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import java.time.LocalDate;

@Controller
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private HolidayRepository holidayRepository;

    @GetMapping("/reservation")
	public String reservation(Model model, HttpServletRequest request) {
        
        model.addAttribute("validationError", ""); //nothing for the time being

        //time restrictions for the past
        LocalDate now = LocalDate.now();
        LocalDate later = now.plusYears(2);
        model.addAttribute("now", now);
        model.addAttribute("later", later);

        Cookie cookie1[] = request.getCookies();
        String userid="";

        for(int i=0; i<cookie1.length; i++) {
            userid = cookie1[i].getValue();
            try{
                Integer.parseInt(userid);
            }
            catch(NumberFormatException e)
            {
                userid=null;
            }
            if(userid != null)
            {
                break;
            }
        }

        List<UserInfoEntity> temp = userInfoRepository.findByUserid(Integer.parseInt(userid));
        //prefill fullName, phoneNumber and email using the info in the profile page
        //for reservation
        ReservationModel reservationModel = new ReservationModel();

        if (!temp.isEmpty()){
            reservationModel.setFullName(temp.get(0).getFullName());
            reservationModel.setPhoneNumber(temp.get(0).getPhone());
            System.out.println(temp.get(0).getPhone() + "  " + temp.get(0).getEmail());
            reservationModel.setEmail(temp.get(0).getEmail());
        }

        model.addAttribute("reservationModel", reservationModel);



        // List<UserInfoEntity> userInfoEntity = userInfoRepository.findByUserid(Integer.parseInt(userid));

        // if (userInfoEntity.isEmpty()) {
        //     // No user with that client id exists, which means it's a guest.
        //     model.addAttribute("guestStatus", "You are reseving as a guest user");
        // }

        return "reservation";
	}

 
    @RequestMapping(value = "/reservation", method = RequestMethod.POST, params = "showAvailableTables")
    public String showAvailableTables(Model model, @ModelAttribute ReservationModel reservationModel, HttpServletRequest request) {
        
        /* input validation for reservation variable go here */
        //also check for table availability
        if (reservationModel.getFullName() == "" || 
            reservationModel.getFullName().length() > 50 ||
            !isNumber(reservationModel.getPhoneNumber()) ||
            !isValidEmailAddress(reservationModel.getEmail()))
        {
            model.addAttribute("validationError", "You have entered invalid parameter. Please try again.");

            return "reservation";
        }

        //get to cookie to find out the userid for this user
        Cookie cookie1[] = request.getCookies();
        String userid="";
        for(int i=0; i<cookie1.length; i++) {
            userid = cookie1[i].getValue();
            try{
                Integer.parseInt(userid);
            }
            catch(NumberFormatException e)
            {
                userid=null;
            }
            if(userid != null)
            {
                break;
            }
        }

        ReservationEntity newReservationEntity = new ReservationEntity();

        newReservationEntity.setFullName(reservationModel.getFullName());
        newReservationEntity.setPhoneNumber(reservationModel.getPhoneNumber());
        newReservationEntity.setEmail(reservationModel.getEmail());
        newReservationEntity.setDate(reservationModel.getDate());
        newReservationEntity.setTime(reservationModel.getTime());
        // newReservationEntity.setNumOfGuests(Integer.parseInt(reservationModel.getNumOfGuests()));
        newReservationEntity.setUserId(Integer.parseInt(userid));

        

        //  HolidayEntity he=holidayRepository.findByDate(reservationModel.getDate());
        //  boolean flag=he.isHoliday();
        //  if(flag==true){
        //     newReservationEntity.setHoliday(true);
        //     reservationRepository.save(newReservationEntity);
        //     return "payment";
         //}
        // 

        // newReservationEntity.setHoliday(false);
        reservationRepository.save(newReservationEntity);
        
        return "redirect:/displayAvailableTable";
    }

    private boolean isNumber(String str){
        boolean flag = true;
        for(int i = 0; i < str.length(); i++){
            if(Character.isDigit(str.charAt(i)) == false){
                flag = false;
                break;
            }
        }
        return flag;
    }



    //source: https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }




/////////////////////////////////
    public boolean isValidDate (String date){
        boolean flag=false;
        String year="";
        String day="";
        String month="";
     for(int i=0;i<10;i++){
       if(i<4){
         year= year+date.charAt(i);
       }
       else if(i>4 && i<7){
           month=month+date.charAt(i);
    
       }
       else if (i>7){
    
       day=day+date.charAt(i);
       }
     
     else{
    
     }
    }
    //System.out.println(year+" "+month+"  "+day+"  \n");
     int y=Integer.parseInt(year);
     int m=Integer.parseInt(month);
     int d=Integer.parseInt(day);

    
    
     //System.out.println(y+"  "+m+"  "+d+"  \n");
    if(y>2020 && m>0 && m<13 && d>0 && d<31){
    flag=true;
    }
    else{
        flag=false;
    }

    if(flag){
        return true;
    }
    else{
        return false;
    }
     
    }

    



@RequestMapping("/seeReservation")
public String adminReservation(Model model){
  
  
    model.addAttribute("list", reservationRepository.findAll());


    return "allReservation";
}

@RequestMapping("/deleteReservation")
public String adminReservation(@RequestParam(name="id")String id,Model model){
  
    int ids=Integer.parseInt(id);

    reservationRepository.deleteById(ids);
  


    model.addAttribute("list", reservationRepository.findAll());


    return "allReservation";
}





@RequestMapping("/reservationOfGuest")
	public String reservationOfGuest(Model model) {
        
        ReservationModel loginModel = new ReservationModel();
        model.addAttribute("reservationModel", loginModel);

        return "reservation";
	}




}
