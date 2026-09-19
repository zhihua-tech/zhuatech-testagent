/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.testagent.controller;

import cn.zhuatech.testagent.common.ApiResponse;
import cn.zhuatech.testagent.service.TestDesignService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/ai/testing")
@PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')")
public class TestDesignController {
    private final TestDesignService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public TestDesignController(TestDesignService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/design")
    public ApiResponse<TestDesignService.Result> design(@Valid @RequestBody TestDesignService.Request request) {
        return ApiResponse.ok("测试计划已生成", service.design(request));
    }
}
