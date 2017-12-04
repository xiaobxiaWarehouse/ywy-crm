package com.ywy.test.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.ywy.entity.customer.CrmCustomer;
import com.ywy.entity.industry.CrmIndustry;
import com.ywy.service.HelloService;
import com.ywy.service.clue.CrmClueAllocateHisService;
import com.ywy.service.clue.CrmClueAllocateService;
import com.ywy.service.clue.CrmClueService;
import com.ywy.service.customer.CrmCustomerService;
import com.ywy.service.industry.CrmIndustryService;
import com.ywy.test.BaseTest;

public class HelloTest extends BaseTest {

	@Resource
	private HelloService helloService;
	@Resource
	private CrmCustomerService crmCustomerService;
	@Resource
	private CrmIndustryService crmIndustryService;
	@Resource 
	private CrmClueService crmClueService;
	@Resource 
	private CrmClueAllocateService crmClueAllocateService;
	@Resource 
	private CrmClueAllocateHisService crmClueAllocateHisService;
	
	@Test
	public void testMe() {
		System.out.println("hello world");
	}
	
//	@Test
	public void testsaveCustomers() {
		CrmCustomer crmCustomer = new CrmCustomer();
		crmCustomer.setCustomerId(1L);
		crmCustomer.setAddress("优盘时代");
		crmCustomer.setName("碧能科技");
		crmCustomer.setIndustry("衣服");
		System.out.println(crmCustomerService.saveCrmCustomers(crmCustomer));
	}
	
//	@Test
	public void testgetCustomers() {
		System.out.println(crmCustomerService.getCustomersDetail(1L));
	}
	
//	@Test
	public void testdeleteCustomers() {
		System.out.println(crmCustomerService.deleteCrmCustomer(1L));
	}
	
//	@Test
	public void testsaveIndustry() {
		CrmIndustry crmIndustry = new CrmIndustry();
		crmIndustry.setNodeName("扳手4");
		crmIndustry.setLevel(4L);
		System.out.println(crmIndustryService.saveCrmIndustry(crmIndustry));
	}
	
//	@Test
	public void testgetClue(long clueId) {
//		System.out.println(crmClueService.getClueDetail(clueId));
	}
	
//	@Test
//	public void testClueAllocate(CrmClueAllocate crmClueAllocate) {
//		System.out.println(crmClueAllocateService.clueAllocate(crmClueAllocate));
//	}
	
//	@Test
	public void testAllocatedClue(int pageNo) {
//		System.out.println(crmClueAllocateService.allocatedClue(pageNo));
	}
}
