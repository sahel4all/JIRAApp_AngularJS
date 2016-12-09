package org.deloitte.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by msahel on 8/15/2016.
 */
@Controller
public class LayoutController {

    @RequestMapping("reports")
    public String getReportsLayoutPage(){
        return "reports";}

    @RequestMapping("statistics")
    public String getSummaryLayoutPage(){return "statistics";}

}
