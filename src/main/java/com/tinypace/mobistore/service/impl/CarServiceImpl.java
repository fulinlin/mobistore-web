package com.tinypace.mobistore.service.impl;

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

import com.tinypace.mobistore.entity.License;
import com.tinypace.mobistore.entity.SysCarBrand;
import com.tinypace.mobistore.entity.SysCarModel;
import com.tinypace.mobistore.service.CarService;

@Service
public class CarServiceImpl extends CommonServiceImpl implements CarService {

	@Override
	public List<SysCarBrand> listBrand() {
		
		String hql = "from SysCarBrand b order by convert_gbk(b.name) asc"; 
		List ls = getListByHQL(hql);
		
		return ls;
	}
	
	@Override
	public List<SysCarModel> listModelByBrand(String brandId) {

		String hql = "from SysCarModel m where m.brandId = ? order by convert_gbk(m.name) asc"; 
		List ls = getListByHQL(hql, brandId);
		
		return ls;
	}

	@Override
	public void impPers() {
		String encoding="UTF-8";
        File file=new File("/home/aaron/model.csv");
//		File file=new File("/Users/aaron/tinypace/outsourcing/project/wolai/model.csv");
        if(file.isFile() && file.exists()){
            InputStreamReader read;
			try {
				read = new InputStreamReader(
				new FileInputStream(file),encoding);
			
	            BufferedReader bufferedReader = new BufferedReader(read);
	            int i = 0;
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
	        		dc2.add(Restrictions.eq("name",modelStr));
	        		List ls2 = getDao().findAllByCriteria(dc2);
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
