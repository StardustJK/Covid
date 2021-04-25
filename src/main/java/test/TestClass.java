package test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bupt.covid.dao.PatientTripDao;
import com.bupt.covid.pojo.PatientTrip;
import com.bupt.covid.response.PatientTripResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TestClass {



    @ResponseBody
    public static void main(String args[]) {
        String url = "http://2019ncov.nosugartech.com/data.json";
        RestTemplate restTemplate = new RestTemplate();
        PatientTripResponse patientTrip = restTemplate.getForObject(url, PatientTripResponse.class);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll((List<HashMap<String, Object>>) patientTrip.getData());
        List<PatientTrip> patientTrips = jsonArray.toJavaList(PatientTrip.class);
//        List<PatientTrip> todayList = null;
//        Date today=new Date();
//        System.out.println("today :  " + today);
//        Calendar c=Calendar.getInstance();
//        c.setTime(today);
//        c.add(Calendar.DAY_OF_MONTH,-1);
//        Date yesterday=c.getTime();
//        todayList = patientTrips.stream()
//                .filter((PatientTrip p) -> yesterday.before(p.getCreated_at()))
//                .collect(Collectors.toList());
//        todayList.forEach((PatientTrip p) -> System.out.println(p.getCreated_at()));
////        patientTripDao.saveAll(patientTrips);
        System.out.println(" insert data done");
    }





}
