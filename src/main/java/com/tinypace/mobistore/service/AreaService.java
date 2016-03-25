package com.tinypace.mobistore.service;

import java.util.List;

import com.tinypace.mobistore.entity.StrRecipient;
import com.tinypace.mobistore.entity.SysArea;

public interface AreaService extends CommonService {

	List<SysArea> list(String type, String provinceId, String cityId);

	void remove(StrRecipient rec);

}
