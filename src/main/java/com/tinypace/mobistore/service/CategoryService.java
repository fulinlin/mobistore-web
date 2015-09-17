package com.tinypace.mobistore.service;


import java.util.List;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.entity.StrCategory;

public interface CategoryService extends CommonService {

	List<StrCategory> listAll();
}
