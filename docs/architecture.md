# TestAgent 架构

版权所有 © 2026 上海如静知华信息科技有限公司。

Vue 3 双端界面负责计划、执行和门禁协作；Spring Boot 提供 JWT API，`TestDesignService` 以可解释规则组合冒烟、契约、关键回归、数据库回滚和事故防回归。MySQL/Flyway 保存任务、执行反馈、环境与审批记录。

接入企业 CI/CD 时应使用最小权限令牌，隔离测试环境，并由发布负责人确认 P0/P1 阻断和例外审批。
