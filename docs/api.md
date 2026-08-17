# TestAgent API

版权所有 © 2026 上海如静知华信息科技有限公司。

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/auth/login` | 登录并获取 JWT |
| GET | `/api/admin/dashboard` | 测试运营中心 |
| GET | `/api/admin/work-orders` | 发布测试计划 |
| GET | `/api/shopfloor/dashboard` | 测试工程师工作台 |
| POST | `/api/shopfloor/work-orders/{id}/reports` | 提交执行结果 |
| POST | `/api/ai/testing/design` | 生成测试组合与发布门禁 |

设计接口输入变更风险、文件数、关键流程、覆盖率、不稳定率、数据库变更和事故标记，输出风险优先级、建议用例数、覆盖目标、测试套件和发布状态。
