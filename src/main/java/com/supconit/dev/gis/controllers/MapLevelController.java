package com.supconit.dev.gis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.dev.gis.entities.MapLevel;
import com.supconit.dev.gis.services.MapLevelService;
import com.supconit.hl.base.entities.GeoArea;
import com.supconit.hl.base.services.GeoAreaService;
import com.supconit.hl.common.controllers.BaseControllerSupport;
import com.supconit.hl.common.entities.ScoMessage;
import com.supconit.hl.platform.entities.MenuLayer;
import com.supconit.hl.platform.services.MenuLayerService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import jodd.util.StringUtil;

@Controller
@RequestMapping("/dev/gis/mapLevel")
public class MapLevelController extends BaseControllerSupport {
	
	@Autowired
	private MapLevelService mapLevelService;
	
	@Autowired
	private GeoAreaService geoAreaService;
	
	@Autowired
	private MenuLayerService menuLayerService;

	@RequestMapping("go")
	public String go(ModelMap model) {
		List<GeoArea> topArea = geoAreaService.findByParentId(0L);
		model.put("topArea", topArea);
		return "dev/gis/mapLevel/mapLevel_list";
	}
	
	@ResponseBody
    @RequestMapping("list")
	public Pageable<MapLevel> list(
			Pagination<MapLevel> page,@ModelAttribute MapLevel mapLevel,
			ModelMap model) throws Exception {
		Pageable<MapLevel> pager = mapLevelService.findByCondition(page, mapLevel);
		
		model.put("mapLevel", mapLevel);
		model.put("pager", pager);
		return pager;
	}
	
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(MapLevel mapLevel) {
		try{		
			if(mapLevel.getType() == 2){//系统图
	        	if(StringUtil.isEmpty(mapLevel.getSystemCode())){
	        		return ScoMessage.error("系统编码为空!");
	        	}
	        	List<MapLevel> res = mapLevelService.findAllBySystemCode(mapLevel.getSystemCode());
	        	if(res != null && res.size()>0){
	        		return ScoMessage.error("系统编码为"+mapLevel.getSystemCode()+"的系统图已经存在!");
	        	}
        	}
	        if(mapLevel.getId()==null){
	            mapLevelService.insert(mapLevel);	
	        }else{
	            mapLevelService.update(mapLevel);
	        }
		}catch(Exception e){
			return ScoMessage.error(e.getMessage());
    	} 
        return ScoMessage.success("dev/gis/mapLevel/go", "操作成功。");
	}
	
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long id) throws Exception {
		if(id != null){
			Pagination<MenuLayer> pager = new Pagination<MenuLayer>();
			MenuLayer condition = new MenuLayer();
			condition.setSystemId(id);
			Pageable<MenuLayer>  res = menuLayerService.findByCondition(pager, condition);
			if(res!= null && res.getTotal()>0){
				return ScoMessage.error("系统图在菜单图层中有使用!");
			}
		}
		mapLevelService.deleteById(id);
		
		return ScoMessage.success("dev/gis/mapLevel/go", "删除成功。");
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id,  ModelMap model) throws Exception {
		if (null != id) {
			MapLevel mapLevel = mapLevelService.getById(id);
			if (null == mapLevel) {
				throw new IllegalArgumentException("Object does not exist");
			}
			model.put("mapLevel", transMapLevel(mapLevel));			
		}		
		
		List<GeoArea> topArea = geoAreaService.findByParentId(0L);
		
		model.put("mapLevelList", mapLevelService.findAllBuild());
		model.put("topArea", topArea);
		model.put("viewOnly", viewOnly);
		return "dev/gis/mapLevel/mapLevel_edit";
	}
	/**
	 * 查看修改页面去掉'.0'用
	 */
	private MapLevel transMapLevel(MapLevel mapLevel){
		String str = ".0";
    	String sminlon = String.valueOf(mapLevel.getMinlon());
    	String smaxlon = String.valueOf(mapLevel.getMaxlon());
    	String sminlat = String.valueOf(mapLevel.getMinlat());
    	String smaxlat = String.valueOf(mapLevel.getMaxlat());
    	String scenterx = String.valueOf(mapLevel.getCenterx());
    	String scentery = String.valueOf(mapLevel.getCentery());
    	if(sminlon.indexOf(str)+2==sminlon.length()){
    		sminlon = sminlon.substring(0, sminlon.indexOf(str));
    	}
    	if(smaxlon.indexOf(str)+2==smaxlon.length()){
    		smaxlon = smaxlon.substring(0, smaxlon.indexOf(str));
    	}
    	if(sminlat.indexOf(str)+2==sminlat.length()){
    		sminlat = sminlat.substring(0, sminlat.indexOf(str));
    	}
    	if(smaxlat.indexOf(str)+2==smaxlat.length()){
    		smaxlat = smaxlat.substring(0, smaxlat.indexOf(str));
    	}
    	if(scenterx.indexOf(str)+2==scenterx.length()){
    		scenterx = scenterx.substring(0, scenterx.indexOf(str));
    	}
    	if(scentery.indexOf(str)+2==scentery.length()){
    		scentery = scentery.substring(0, scentery.indexOf(str));
    	}
    	mapLevel.setSminlon(sminlon);
    	mapLevel.setSmaxlon(smaxlon);
    	mapLevel.setSminlat(sminlat);
    	mapLevel.setSmaxlat(smaxlat);
    	mapLevel.setScenterx(scenterx);
    	mapLevel.setScentery(scentery);
		return mapLevel;
	}
	@ResponseBody
	@RequestMapping(value ="copyValue", method = RequestMethod.POST) 
	public MapLevel copyValue(@RequestParam Long id){
		return mapLevelService.getById(id);
	}
	
	@ResponseBody
	@RequestMapping(value ="getFloor") 
	public List<GeoArea> getFloor(@RequestParam Long id){
		if(id !=null){
			return geoAreaService.findByParentId(id);
		}
		return null;
	}
	@ResponseBody
	@RequestMapping(value = "batchMerge", method = RequestMethod.POST)     
	public ScoMessage batchMerge() throws Exception {
		mapLevelService.batchMerge();
		return ScoMessage.success("dev/gis/mapLevel/go", "批量初始地图等级成功。");
	}	
}
