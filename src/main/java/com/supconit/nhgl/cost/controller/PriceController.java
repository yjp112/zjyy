
package com.supconit.nhgl.cost.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.cost.entities.Price;
import com.supconit.nhgl.cost.service.PriceService;

@Controller
@RequestMapping("nhgl/cost/price")
public class PriceController extends BaseControllerSupport {
	@Autowired
	private PriceService priceService;

    /*
    price
    */
    @RequestMapping("list")
	public String list(ModelMap model,Long startYear) {
    	Price price = new Price();
    	Calendar c = Calendar.getInstance();
    	if(startYear==null){
    		price.setStartYear(Long.valueOf(c.get(Calendar.YEAR)));
    	}else{
    		price.setStartYear(startYear);
    	}  
    	price.setEndYear(price.getStartYear()+1);
    	model.put("startYear",price.getStartYear());
    	model.put("thisYear", Long.valueOf(c.get(Calendar.YEAR)));
    	model.put("list",priceService.findByYearMonth(price));
    	
    	return "nhgl/cost/price/list";
	}
    @ResponseBody
    @RequestMapping("save")
    public void save(Long[] ids ,Long[] years,Long[] months,Double[] electrics,Double[] waters,Double[] gass,Double[] energys){
    	if(ids!=null && ids.length>0){
	    	List<Price> lst = new ArrayList<Price>();
	    	for(int i=0 ;i< ids.length;i++){
	    		if(ids[i]!=null){
		    		Price p = new Price();
		    		System.out.println(ids[i]+","+years[i]+","+months[i]+","+electrics[i]+","+waters[i]+","+gass[i]+","+energys[i]);
		    		p.setId(ids[i]);    		
		    		p.setYear(years[i]);
		    		p.setMonth(months[i]);
		    		p.setElectric(electrics[i]);
		    		p.setWater(waters[i]);
		    		p.setGas(gass[i]);
		    		p.setEnergy(energys[i]);
		    		lst.add(p);
	    		}
	    	}
			priceService.update(lst);	
    	}
    	
    }		
}
