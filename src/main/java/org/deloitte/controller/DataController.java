package org.deloitte.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
@RestController
@Component

public class DataController {

    @Value("${jsonPath}")
    private String jsonPath;
    @Value("${iedssJsonPath}")
    private String iedssJsonPath;
    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);

    @RequestMapping(value="/jsonData",method = RequestMethod.GET)
    public ResponseEntity getJSONData(){
        System.out.println("getJSONData:");
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader(jsonPath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray jsonObject = (JSONArray) obj;
        Iterator itr= jsonObject.iterator();
        JSONArray jsonObjectCopy = new JSONArray();

        while(itr.hasNext()){
            JSONObject featureJsonObj = (JSONObject)itr.next();
           // System.out.println("Status:"+featureJsonObj.get("Status"));
            if(featureJsonObj.get("Status")!=null && featureJsonObj.get("Status").toString().length()>0){
                featureJsonObj.put("Status",featureJsonObj.get("Status").toString().substring(0,featureJsonObj.get("Status").toString().indexOf('(')));
            }
            if(featureJsonObj.get("Defect Severity")!=null && featureJsonObj.get("Defect Severity").toString().length()>0){
                featureJsonObj.put("Defect Severity",featureJsonObj.get("Defect Severity").toString().substring(0,featureJsonObj.get("Defect Severity").toString().indexOf(' ')));
            }
            jsonObjectCopy.add(featureJsonObj)  ;
        }
        return new ResponseEntity(jsonObjectCopy, HttpStatus.OK);
    }

    @RequestMapping(value="/jsonData/{track}",method = RequestMethod.GET)
    public ResponseEntity getJSONDataTrackWise(@PathVariable("track") final String trackName){
        System.out.println("get JSONDataTrackWise:");
        JSONArray jsonObjectCopy = new JSONArray();
        jsonObjectCopy=getJSONArray(iedssJsonPath,trackName);
        System.out.println("trackName:"+trackName);
        return new ResponseEntity(jsonObjectCopy, HttpStatus.OK);
    }

    @RequestMapping(value="/iedssJsonData/{track}",method = RequestMethod.GET)
    public ResponseEntity getIEDSSJSONDataTrackWise(@PathVariable("track") final String trackName){
        System.out.println("getIEDSSJSONDataTrackWise:");
        JSONArray jsonObjectCopy = new JSONArray();
        jsonObjectCopy=getDataByCriteria(iedssJsonPath,trackName,"Status","New-Triaged");
        System.out.println("trackName:"+trackName);
        return new ResponseEntity(jsonObjectCopy, HttpStatus.OK);
    }

    private JSONArray getJSONArray(String filePath, String trackName){
        String appName=getApplicationTrackName(trackName);
        Object obj = null;
        JSONParser parser = new JSONParser();
        try {
            obj = parser.parse(new FileReader(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray jsonObject = (JSONArray) obj;
        Iterator itr= jsonObject.iterator();
        JSONArray jsonObjectCopy = new JSONArray();


        while(itr.hasNext()){
            JSONObject featureJsonObj = (JSONObject)itr.next();
            // System.out.println("Status:"+featureJsonObj.get("Status"));
            if(featureJsonObj.get("Status")!=null && featureJsonObj.get("Status").toString().length()>0){
                featureJsonObj.put("Status",featureJsonObj.get("Status").toString().substring(0,featureJsonObj.get("Status").toString().indexOf('(')));
            }
            if(featureJsonObj.get("Defect Severity")!=null && featureJsonObj.get("Defect Severity").toString().length()>0){
                featureJsonObj.put("Defect Severity",featureJsonObj.get("Defect Severity").toString().substring(0,featureJsonObj.get("Defect Severity").toString().indexOf(' ')));
            }

            if(featureJsonObj.get("DES Track")!=null && featureJsonObj.get("DES Track").toString().equalsIgnoreCase(appName)){
                featureJsonObj.put("DES Track",featureJsonObj.get("DES Track"));
                System.out.println("Matching DES Track:"+featureJsonObj.get("DES Track"));
                jsonObjectCopy.add(featureJsonObj)  ;
            }

        }
        return jsonObjectCopy;
    }
    @RequestMapping(value="/jsonData/{track}/{fieldIndicator}/{criteria}",method = RequestMethod.GET)
    public ResponseEntity getJSONDataByCriteria(@PathVariable("track") final String trackName,@PathVariable("fieldIndicator") final String fieldIndicator,
                                                @PathVariable("criteria") String criteria){
        System.out.println("getJSONDataByCriteria:");
        JSONArray jsonObjectCopy = new JSONArray();
        jsonObjectCopy=getDataByCriteria(jsonPath,trackName,fieldIndicator,criteria);
        return new ResponseEntity(jsonObjectCopy, HttpStatus.OK);
    }

    private JSONArray getDataByCriteria(String filePath, String trackName,String fieldIndicator,String criteria){
        JSONParser parser = new JSONParser();
        Object obj = null;
        String appName="";

        appName=getApplicationTrackName(trackName);
        System.out.println("trackName:"+trackName);
        System.out.println("criteria:"+criteria);
        System.out.println("fieldIndicator :"+fieldIndicator);
        try {
            obj = parser.parse(new FileReader(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray jsonObject = (JSONArray) obj;
        Iterator itr= jsonObject.iterator();
        JSONArray jsonObjectCopy = new JSONArray();


        while(itr.hasNext()){
            StringTokenizer st = new StringTokenizer(criteria,"-");

            JSONObject featureJsonObj = (JSONObject)itr.next();
            System.out.println("next iteration  RTC ID:"+featureJsonObj.get("RTC-ID").toString());
            // System.out.println("Status:"+featureJsonObj.get("Status"));
            if(featureJsonObj.get("Status")!=null && featureJsonObj.get("Status").toString().length()>0){
                featureJsonObj.put("Status",featureJsonObj.get("Status").toString().substring(0,featureJsonObj.get("Status").toString().indexOf('(')-1));
                //System.out.println("status::"+featureJsonObj.get("Status").toString().substring(0,featureJsonObj.get("Status").toString().indexOf('(')));
                // System.out.println("status lenght::"+featureJsonObj.get("Status").toString().substring(0,featureJsonObj.get("Status").toString().indexOf('(')).length());
            }
            if(featureJsonObj.get("Defect Severity")!=null && featureJsonObj.get("Defect Severity").toString().length()>0){
                featureJsonObj.put("Defect Severity",featureJsonObj.get("Defect Severity").toString().substring(0,featureJsonObj.get("Defect Severity").toString().indexOf(' ')));

            }
            //Filtering the JSON data as per the criteria
            if(featureJsonObj.get("DES Track")!=null && featureJsonObj.get("DES Track").toString().equalsIgnoreCase(appName)){
                featureJsonObj.put("DES Track",featureJsonObj.get("DES Track"));
                System.out.println("Matching DES Track :"+featureJsonObj.get("DES Track"));
                System.out.println(" fieldIndicator 1 length:"+featureJsonObj.get(fieldIndicator).toString().length());
                // System.out.println(" fieldIndicator 1:"+featureJsonObj.get(fieldIndicator).toString());
                //System.out.println(" RTC ID:"+featureJsonObj.get("RTC-ID").toString());
                System.out.println("st.toString():"+st.toString());
                while (st.hasMoreTokens()) {
                    String innerCriteria=st.nextToken();
                    //System.out.println("criteria 1 len:"+criteria.length());
                    System.out.println("innerCriteria 1:"+innerCriteria);
                    if(featureJsonObj.get(fieldIndicator)!=null && featureJsonObj.get(fieldIndicator).toString().equalsIgnoreCase(innerCriteria)){
                        System.out.println("Matching fieldIndicator:"+featureJsonObj.get(fieldIndicator));
                        System.out.println("Matching innerCriteria:"+innerCriteria);
                        jsonObjectCopy.add(featureJsonObj)  ;
                    }
                }
            }
        }
        return jsonObjectCopy;
    }

    private String getApplicationTrackName(String trackName){
        String appName="";
        if(trackName.equalsIgnoreCase("BO")){
            appName="Application Team/Back Office";
        }else if(trackName.equalsIgnoreCase("FO")){
            appName="Application Team/Front Office";
        }else if(trackName.equalsIgnoreCase("EDBC")){
            appName="Application Team/EDBC";
        }else if(trackName.equalsIgnoreCase("CO")){
            appName="Application Team/Correspondence";
        }else if(trackName.equalsIgnoreCase("IN")){
            appName="Application Team/Interfaces";
        }else if(trackName.equalsIgnoreCase("RP")){
            appName="Application Team/Reports";
        }else if(trackName.equalsIgnoreCase("SP")){
            appName="Application Team/Support";
        }
        return appName;
    }
}
