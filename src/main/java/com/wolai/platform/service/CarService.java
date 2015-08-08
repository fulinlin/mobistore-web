package com.wolai.platform.service;

import java.util.List;
import com.wolai.platform.entity.SysCarBrand;
import com.wolai.platform.entity.SysCarModel;

public interface CarService extends CommonService {

	List<SysCarBrand> listBrand();

	List<SysCarModel> listModelByBrand(String brandId);

	
}
