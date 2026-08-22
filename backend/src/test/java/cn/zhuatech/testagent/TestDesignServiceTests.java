/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.testagent;
import cn.zhuatech.testagent.service.TestDesignService; import org.junit.jupiter.api.Test; import static org.assertj.core.api.Assertions.assertThat;
class TestDesignServiceTests { private final TestDesignService service=new TestDesignService();
 @Test void blocksHighRiskRelease(){var result=service.design(new TestDesignService.Request("PAYMENT",5,42,8,61,9,true,true));assertThat(result.priority()).isEqualTo("P0");assertThat(result.releaseState()).isEqualTo("RELEASE_BLOCKED");}
 @Test void producesRegressionSuites(){var result=service.design(new TestDesignService.Request("CRM",2,8,3,82,2,false,false));assertThat(result.testSuites()).contains("关键链路回归");}}
