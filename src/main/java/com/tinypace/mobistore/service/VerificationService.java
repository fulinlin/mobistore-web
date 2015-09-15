package com.tinypace.mobistore.service;

import java.util.Map;

import com.tinypace.mobistore.entity.SysUser;
import com.tinypace.mobistore.entity.SysVerificationCode;

public interface VerificationService extends CommonService {

	SysVerificationCode checkCode(String phone, String verificationCode);


}
