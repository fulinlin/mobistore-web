package com.wolai.platform.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wolai.platform.entity.License;
import com.wolai.platform.entity.SysCarBrand;
import com.wolai.platform.entity.SysCarModel;
import com.wolai.platform.service.CarService;

@Service
public class CarServiceImpl extends CommonServiceImpl implements CarService {

	@Override
	public List<SysCarBrand> listBrand() {
		
		DetachedCriteria dc = DetachedCriteria.forClass(SysCarBrand.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		
		dc.addOrder(Order.desc("name"));
		List<SysCarBrand> ls = (List<SysCarBrand>) findAllByCriteria(dc);
		
		return ls;
	}
	
	@Override
	public List<SysCarModel> listModelByBrand(String brandId) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(SysCarModel.class);
		dc.add(Restrictions.eq("brandId", brandId));
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		
		dc.addOrder(Order.desc("name"));
		List<SysCarModel> ls = (List<SysCarModel>) findAllByCriteria(dc);
		
		return ls;
	}

	@Override
	public void impPers() {
		String encoding="UTF-8";
        File file=new File("/Users/aaron/tinypace/outsourcing/project/wolai/model.csv");
        if(file.isFile() && file.exists()){
            InputStreamReader read;
			try {
				read = new InputStreamReader(
				new FileInputStream(file),encoding);
			
	            BufferedReader bufferedReader = new BufferedReader(read);
	            String lineTxt = null;
	            while((lineTxt = bufferedReader.readLine()) != null){
	                //System.out.println(lineTxt);
	                
	                String[] arr = lineTxt.trim().split(";");
	                String brandStr = arr[0];
	                String modelStr = arr[1];
	                System.out.println(brandStr + "   " + modelStr);
	                
	        		DetachedCriteria dc = DetachedCriteria.forClass(SysCarBrand.class);
	        		dc.add(Restrictions.eq("name",brandStr));
	        		List ls = getDao().findAllByCriteria(dc);
	        		
	        		SysCarBrand brand = null;
	        		if (ls.size() > 0) {
	        			brand = (SysCarBrand) ls.get(0);
	        		} else {
	        			brand = new SysCarBrand();
		                brand.setName(brandStr);
		                saveOrUpdate(brand);
	        		}
	                
	        		DetachedCriteria dc2 = DetachedCriteria.forClass(SysCarModel.class);
	        		dc2.add(Restrictions.eq("brandId",brand.getId()));
	        		dc2.add(Restrictions.eq("name",brandStr));
	        		List ls2 = getDao().findAllByCriteria(dc);
	        		if (ls2.size() == 0) {
	        			SysCarModel model = new SysCarModel();
		                model.setBrandId(brand.getId());
		                model.setName(modelStr);
		                saveOrUpdate(model);
	        		}
	            }
	            read.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}

}
