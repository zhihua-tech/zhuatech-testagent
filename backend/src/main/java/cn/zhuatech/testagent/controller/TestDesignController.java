/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.testagent.controller;

import cn.zhuatech.testagent.common.ApiResponse;
import cn.zhuatech.testagent.service.TestDesignService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/testing")
@PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')")
public class TestDesignController {
    private final TestDesignService service;
    public TestDesignController(TestDesignService service) { this.service = service; }
    @PostMapping("/design")
    public ApiResponse<TestDesignService.Result> design(@Valid @RequestBody TestDesignService.Request request) {
        return ApiResponse.ok("测试计划已生成", service.design(request));
    }
}
