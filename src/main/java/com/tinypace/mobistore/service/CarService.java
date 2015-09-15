package com.tinypace.mobistore.service;

import java.util.List;
import com.tinypace.mobistore.entity.SysCarBrand;
import com.tinypace.mobistore.entity.SysCarModel;

public interface CarService extends CommonService {

	List<SysCarBrand> listBrand();

	List<SysCarModel> listModelByBrand(String brandId);

	void impPers();

	
}
