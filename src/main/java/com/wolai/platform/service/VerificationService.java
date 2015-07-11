package com.wolai.platform.service;

import java.util.Map;

import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysVerificationCode;

public interface VerificationService extends CommonService {

	SysVerificationCode checkCode(String phone, String verificationCode);


}
