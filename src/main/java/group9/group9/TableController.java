package group9.group9;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;

/* this file can be combined into the reservation page */


@Controller
public class TableController {
     boolean checkHoliday=false;
  @Autowired
  TableRepository tableRepository;  

  @Autowired
  ReservationRepository reservationRepository; 

  @Autowired
  HolidayRepository holidayRepository; 
  
  
// this method will direst to addTable html Page
@GetMapping("/displayAvailableTable")
public String showAddTable(Model map, HttpServletRequest request){
    List<TableEntity> tableList= tableRepository.findByIsReserved(false);
  
    map.addAttribute("list", tableList);

    //fetch the cookie and find the userid
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
    
    return "displayAvailableTable";
}


// this method will get Table Information from addtable html
// Save the information to Database on Table Entity 
@PostMapping("/saveTable")
public String insertTableDB(@ModelAttribute("tableModel")TableModel tableModel, ModelMap model){
TableEntity tableEntity= new TableEntity();
tableEntity.setCapacity(tableModel.getCapacity());
tableEntity.setReserved(tableModel.isReserved());
tableRepository.save(tableEntity);
String message ="Your Information is Successfully  Save to Our System ";
model.addAttribute("msg", message);
return "addTable";
}


//This Method will Show all the Table on the system 
@RequestMapping("/displayTable")
public String displayAvailableTable(ModelMap model){
    List<TableEntity> tableList= tableRepository.findAll();

     model.addAttribute("list", tableList);
    return "displayAvailableTable";

    
}

@RequestMapping("/selectTable")
public String selectTable(@RequestParam(name="tid")String tid,Model model,HttpServletRequest request){
    int id=Integer.parseInt(tid);
    TableEntity tableList= tableRepository.findById(id).get();
  System.out.println("Table id IS ---"+ id +"\n\n\n\n");
    //ReservationEntity reservationEntity= new ReservationEntity();
    //reservationEntity.setTableId(id);
   
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
    
    List<ReservationEntity> usersReservation = reservationRepository.findByUserid(Integer.parseInt(userid));
  ///checking is holiday or not


  

    String date = usersReservation.get(0).getDate();
    System.out.println("Date zis ======= "+date+"     -----\n\n\n\n\n\n\n");

///
     HolidayEntity he=holidayRepository.findByDate(date);
         boolean flag=he.isHoliday();

      if(checkHoliday==false){
         System.out.println(flag);
         if(flag==true){
             ReservationEntity re= new ReservationEntity();
            re.setHoliday(true);
            reservationRepository.save(re);
            checkHoliday=true;
            model.addAttribute("tid",id);
            return "payment";
        }

    }
///
    ///checking is holiday or not
    //HolidayEntity holi=holidayRepository.findByDate(date);

  

    String time = usersReservation.get(0).getTime();
    String email=usersReservation.get(0).getEmail();
    String fname=usersReservation.get(0).getFullName();
    String phone=usersReservation.get(0).getPhoneNumber();
      

    

    //System.out.println("Date And Time =="+ date+"     " +time+"\n");
    //Integer table_id = usersReservation.get(0).getTableId();

    //List<ReservationEntity> conflictChecker = reservationRepository.findByDateAndTableId(date,table_id);
    // no conflicts, assign table id
    ///if(conflictChecker.isEmpty()){
       // usersReservation.get(0).setTableId(id);
    //}
    //else{
       // if(time.substring(0,3).equals(conflictChecker.get(0).getTime().substring(0,3))){//there is a conflict
           // List<TableEntity> table= tableRepository.findAll();

             //model.addAttribute("list", table);
            //return "displayAvailableTable";
        //}
        //else{//no conflict
           // usersReservation.get(0).setTableId(id);
        //}
      
        System.out.println(userid);
        int userId=Integer.parseInt(userid);


        //System.out.println(userId+"User id is =-------");
        
        TableEntity tableInfo= tableRepository.findById(id).get();
           
        int noOfGuest=tableInfo.getCapacity();
        tableInfo.setReserved(true);
        tableRepository.save(tableInfo);

        //System.out.println(tableInfo+" Table Information ------");


    


    ReservationEntity reservationEntity= new ReservationEntity();
    //HolidayEntity he=holidayRepository.findByDate(date);
    //boolean holiday=he.isHoliday();
    
    
    
    reservationEntity.setDate(date);
    reservationEntity.setEmail(email);
    reservationEntity.setFullName(fname);
    reservationEntity.setNumOfGuests(noOfGuest);
    reservationEntity.setPhoneNumber(phone);
    reservationEntity.setTableId(id);
    


    reservationEntity.setTime(time);
    reservationEntity.setUserId(userId);
   // tableList.setReserved(true);
    //tableRepository.save(tableList);
    reservationRepository.save(reservationEntity);
     
    List<ReservationEntity> list=reservationRepository.findByUserid(userId);
    

    model.addAttribute("list",list );
    //if(holiday){

      //  return "payment";
    //}

    return "reservationHistory";
}

@RequestMapping("/checkReservation")
public String checkReservation(Model map){
    
    return "allReservation";
}



//@RequestMapping("/combineTable")
//public String combineTable(@RequestParam(name="guestNo") String  guest,Model map){

//return "reservationHistory";
//}



@RequestMapping("/combineTable")
public String combineTable( @RequestParam(name="num")String num, Model map,HttpServletRequest request){
    ////User an only combine table if the User needed table is not Avialable 

    int ng=Integer.parseInt(num);
    List<TableEntity> tc =tableRepository.findByCapacityAndIsReserved(ng,false);
    List<TableEntity> t1=null;
    List<TableEntity> t2=null;
     if(!tc.isEmpty()){
        List<TableEntity> tableList= tableRepository.findByIsReserved(false);
        map.addAttribute("list", tableList);
      return "displayAvailableTable";
     }
      
     if(ng>8 || ng<1){
        List<TableEntity> tableList= tableRepository.findByIsReserved(false);
        map.addAttribute("list", tableList);
      return "displayAvailableTable"; 
     }



    List<Integer> listTid=new ArrayList<>();
int guest=Integer.parseInt(num);
if(guest%2!=0){
    guest++;
}

if(guest>2 && guest<9){


if(guest==8){
    t1= tableRepository.findByCapacityAndIsReserved(6, false);
    t2= tableRepository.findByCapacityAndIsReserved(2, false);
   if(t1.size()==0 || t2.size()==0){///////////////////////
    List<TableEntity> tableList= tableRepository.findByIsReserved(false);
    map.addAttribute("list", tableList);
     return "displayAvailableTable";
   }
   listTid.add(t1.get(0).getTable_id());
   listTid.add(t2.get(0).getTable_id());


}
else if(guest==6){
     t1= tableRepository.findByCapacityAndIsReserved(4, false);
     t2= tableRepository.findByCapacityAndIsReserved(2, false);
    if(t1.size()==0 || t2.size()==0){///////////////////////
        List<TableEntity> tableList= tableRepository.findByIsReserved(false);
        map.addAttribute("list", tableList);
         return "displayAvailableTable";
    }
    listTid.add(t1.get(0).getTable_id());
    listTid.add(t2.get(0).getTable_id());
}

else{// guest ===4

    t1= tableRepository.findByCapacityAndIsReserved(2, false);
    t2= tableRepository.findByCapacityAndIsReserved(2, false);

  
    if(t1.size()==0 || t2.size()==0){
        List<TableEntity> tableList= tableRepository.findByIsReserved(false);
        map.addAttribute("list", tableList);
         return "displayAvailableTable";
       }
    listTid.add(t1.get(0).getTable_id());
    listTid.add(t2.get(0).getTable_id());

}

}
else{

    return "displayAvailableTable";  
}
System.out.println("List if Table Id Is --");
System.out.println(listTid);
map.addAttribute("list", listTid);

int tableNo1=listTid.get(0);
int tableNo2=listTid.get(1);
  TableEntity firstTable=tableRepository.findById(tableNo1).get();
  TableEntity secondTable=tableRepository.findById(tableNo2).get();

  int table1Guest=firstTable.getCapacity();
  int table2Guest=secondTable.getCapacity();

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

    List<ReservationEntity> usersReservation = reservationRepository.findByUserid(Integer.parseInt(userid));
    String date = usersReservation.get(0).getDate();
    String time = usersReservation.get(0).getTime();
    String email=usersReservation.get(0).getEmail();
    String fname=usersReservation.get(0).getFullName();
    String phone=usersReservation.get(0).getPhoneNumber();

    HolidayEntity he=holidayRepository.findByDate(date);
    boolean flag=he.isHoliday();


//////////checking holiday ///////////////////
 if(checkHoliday==false){
    System.out.println(flag);
    if(flag==true){
        ReservationEntity re= new ReservationEntity();
       re.setHoliday(true);
       reservationRepository.save(re);
       checkHoliday=true;
       map.addAttribute("tid",table1Guest);
       return "payment";
   }
}
////////////////////////////////////////

    ReservationEntity reservationEntity= new ReservationEntity();
    //HolidayEntity he=holidayRepository.findByDate(date);
    //boolean holiday=he.isHoliday();
    
    
    int ui=Integer.parseInt(userid);///assign User id
    reservationEntity.setDate(date);
    reservationEntity.setEmail(email);
    reservationEntity.setFullName(fname);
    reservationEntity.setNumOfGuests(table1Guest);
    reservationEntity.setPhoneNumber(phone);
    reservationEntity.setTableId(tableNo1);
    


    reservationEntity.setTime(time);
    reservationEntity.setUserId(ui);
   // tableList.setReserved(true);
    //tableRepository.save(tableList);
    reservationRepository.save(reservationEntity);
    ReservationEntity reservation= new ReservationEntity();
////// second Time 
    reservation.setDate(date);
    reservation.setEmail(email);
    reservation.setFullName(fname);
    reservation.setNumOfGuests(table2Guest);
    reservation.setPhoneNumber(phone);
    reservation.setTableId(tableNo2);
    


    reservation.setTime(time);
    reservation.setUserId(ui);
   // tableList.setReserved(true);
    //tableRepository.save(tableList);
    reservationRepository.save(reservation);
















TableEntity table1=tableRepository.findById(listTid.get(0)).get();
table1.setReserved(true);
tableRepository.save(table1);

TableEntity table2=tableRepository.findById(listTid.get(1)).get();
table2.setReserved(true);
tableRepository.save(table2);
List<ReservationEntity> list=reservationRepository.findByUserid(ui);
 map.addAttribute("list",list );
   


return "reservationHistory";


}












}
