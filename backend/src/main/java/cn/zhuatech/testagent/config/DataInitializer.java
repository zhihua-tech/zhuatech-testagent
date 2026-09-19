/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.testagent.config;
import cn.zhuatech.testagent.model.*; import cn.zhuatech.testagent.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import java.time.LocalDate; import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Configuration public class DataInitializer {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Bean CommandLineRunner seed(OperatingUnitRepository units,WorkRecordRepository tasks,ResourceRegisterRepository resources,ReviewRecordRepository reviews,UserRepository users,PasswordEncoder encoder){return args->{if(units.count()>0)return;
 var u1=units.save(new OperatingUnit("QA-PLATFORM","质量平台组","研发效能中心",480));var u2=units.save(new OperatingUnit("ORDER-QA","订单测试组","交易研发中心",260));var u3=units.save(new OperatingUnit("MOBILE-QA","终端测试组","数字产品中心",180));
 var t1=tasks.save(new WorkRecord("TP-260817-042","ORDER-SVC","订单服务 R26.8.3",u2,186,142,5,LocalDate.now(),WorkRecord.Status.RUNNING,"冒烟+核心回归"));var t2=tasks.save(new WorkRecord("TP-260817-039","MEMBER-SVC","会员中心 R26.8.2",u1,96,96,0,LocalDate.now(),WorkRecord.Status.COMPLETED,"接口+权限"));var t3=tasks.save(new WorkRecord("TP-260817-036","SETTLE-SVC","结算服务热修复",u1,128,54,3,LocalDate.now(),WorkRecord.Status.RUNNING,"事故防回归"));var t4=tasks.save(new WorkRecord("TP-260816-031","PIM-SVC","商品中心 R26.8.1",u3,82,82,1,LocalDate.now().minusDays(1),WorkRecord.Status.COMPLETED,"全量回归"));
 resources.saveAll(List.of(new ResourceRegister("RUNNER-01","容器测试执行池",u1,ResourceRegister.Status.RUNNING,96),new ResourceRegister("ENV-ORDER","订单集成测试环境",u2,ResourceRegister.Status.RUNNING,98),new ResourceRegister("DEVICE-02","移动设备云",u3,ResourceRegister.Status.IDLE,91)));
 reviews.saveAll(List.of(new ReviewRecord("GT-260817-032",t1,"发布门禁",186,5,ReviewRecord.Result.PENDING,"顾清"),new ReviewRecord("GT-260817-027",t2,"覆盖确认",96,0,ReviewRecord.Result.PASSED,"周屿"),new ReviewRecord("GT-260817-018",t3,"事故防回归",128,3,ReviewRecord.Result.FAILED,"顾清")));
 String demo=encoder.encode("Demo@2026");users.saveAll(List.of(new UserAccount("operator",demo,"周屿",UserAccount.Role.DOMAIN_USER,"ORDER-QA"),new UserAccount("planner",demo,"顾清",UserAccount.Role.DOMAIN_OPERATOR,null),new UserAccount("quality",demo,"沈言",UserAccount.Role.QUALITY,null),new UserAccount("admin",encoder.encode("ZhuaTech@2026"),"系统管理员",UserAccount.Role.ADMIN,null)));};}
}
