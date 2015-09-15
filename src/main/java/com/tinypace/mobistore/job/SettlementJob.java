package com.tinypace.mobistore.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.Bill.PayStatus;
import com.tinypace.mobistore.service.ApiKeyService;
import com.tinypace.mobistore.service.BillService;
import com.tinypace.mobistore.service.PaymentUnionpayService;

@Service
public class SettlementJob {

	@Autowired
	BillService billService;
	
	@Autowired
	PaymentUnionpayService paymentUnionpayService;
	
	@Autowired
	ApiKeyService apiKeyService;
	
	private static Log log = LogFactory.getLog(SettlementJob.class);
	
	/**
	 * 检查未付费后付款账单，并计算应付费用并扣除相应金额
	 */
	@Scheduled(cron="${schedul.unionpay.check}")
	public void excute(){
		List<Bill> bills = billService.findPaidBillButNotCallBack();
		if(log.isDebugEnabled()){
			log.debug("===开始扫描，共"+bills.size()+"比账单===");
		}
		int count = 0;
		for(Bill bill:bills){
			Map<String, String> resp = paymentUnionpayService.postPayQuery(bill.getId(),bill.getTradeNo());
			String respCode = resp.get("respCode");
			String origRespCode= resp.get("origRespCode");
			String origRespMsg = resp.get("origRespMsg");
			bill.setTradeMsg(respCode+"|"+origRespMsg);
			if("00".equals(respCode)){
				bill.setTradeResponseTime(new Date());
				if("00".equals(origRespCode)){
					bill.setPayStatus(PayStatus.SUCCESSED);
					bill.setTradeStatus(origRespCode);
				}else{
					bill.setPayStatus(PayStatus.FEATURE);
					bill.setTradeStatus(origRespCode);
				}
				billService.saveOrUpdate(bill);
				count++;
			}else{
				bill.setTradeStatus("N/A");
				log.error("===银联接口查询错误===："+respCode+"|"+origRespMsg);
			}
		}
		if(log.isDebugEnabled()){
			log.debug("===开始结束，共"+bills.size()+"比账单,处理成功"+count+"条===");
		}
	}
}
