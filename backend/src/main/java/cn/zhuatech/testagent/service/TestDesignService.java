/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.testagent.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于变更风险生成可解释的回归测试组合，不调用外部模型。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class TestDesignService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result design(Request request) {
        int score = request.changeRisk() * 14 + Math.min(20, request.changedFiles() / 3);
        if (request.databaseChange()) score += 18;
        if (request.productionIncident()) score += 22;
        if (request.currentCoveragePercent() < 70) score += 12;
        if (request.flakyRatePercent() > 5) score += 8;
        score = Math.min(100, score);
        String priority = score >= 75 ? "P0" : score >= 50 ? "P1" : "P2";
        int generatedCases = Math.max(8, request.criticalFlows() * 5 + request.changedFiles() / 2);
        int targetCoverage = Math.min(95, Math.max(request.currentCoveragePercent() + 8, 80));
        List<String> suites = new ArrayList<>(List.of("变更影响冒烟", "关键链路回归", "接口契约校验"));
        List<String> gates = new ArrayList<>(List.of("失败用例必须关联变更", "P0/P1 缺陷阻断发布"));
        if (request.databaseChange()) suites.add("数据库迁移与回滚验证");
        if (request.productionIncident()) suites.add("线上事故复现与防回归");
        if (request.flakyRatePercent() > 5) gates.add("隔离不稳定用例并复跑三次");
        return new Result(request.serviceCode(), score, priority, generatedCases, targetCoverage,
            score >= 75 ? 2 : 4, suites, gates, priority.equals("P0") ? "RELEASE_BLOCKED" : "READY_FOR_REVIEW");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotBlank String serviceCode, @Min(1) @Max(5) int changeRisk,
                          @Min(1) int changedFiles, @Min(1) int criticalFlows,
                          @Min(0) @Max(100) int currentCoveragePercent,
                          @Min(0) @Max(100) int flakyRatePercent,
                          boolean databaseChange, boolean productionIncident) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(String serviceCode, int riskScore, String priority, int generatedCases,
                         int targetCoveragePercent, int recommendedParallelism,
                         List<String> testSuites, List<String> releaseGates, String releaseState) {}
}
