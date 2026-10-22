/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.testagent.workbench;

import static cn.zhuatech.testagent.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "requirements", "tests");
    var requirements = strings(r, "requirements", 1, 200);
    Set<String> known = new LinkedHashSet<>(requirements);
    if (known.size() != requirements.size()) throw new IllegalArgumentException("需求编号重复");
    Map<String, List<Map<String, Object>>> links = new LinkedHashMap<>();
    known.forEach(k -> links.put(k, new ArrayList<>()));
    Set<String> ids = new HashSet<>();
    for (var t : rows(r, "tests", 0, 300)) {
      keys(t, "id", "requirements", "status");
      String id = id(t, "id");
      unique(ids, id);
      String status = text(t, "status", 20);
      if (!Set.of("PASSED", "FAILED", "BLOCKED", "NOT_RUN").contains(status))
        throw new IllegalArgumentException("测试状态错误");
      var refs = strings(t, "requirements", 1, 200);
      if (new HashSet<>(refs).size() != refs.size()) throw new IllegalArgumentException("测试需求引用重复");
      for (String ref : refs) {
        if (!known.contains(ref)) throw new IllegalArgumentException("未知需求引用");
        links.get(ref).add(Map.of("id", id, "status", status));
      }
    }
    List<Map<String, Object>> out = new ArrayList<>();
    int passed = 0, covered = 0;
    for (var entry : links.entrySet()) {
      var ts = entry.getValue();
      Set<String> statuses = new HashSet<>();
      ts.forEach(t -> statuses.add(t.get("status").toString()));
      String state =
          ts.isEmpty()
              ? "UNCOVERED"
              : statuses.contains("FAILED")
                  ? "FAILED"
                  : statuses.contains("BLOCKED")
                      ? "BLOCKED"
                      : statuses.contains("NOT_RUN") ? "NOT_RUN" : "PASSED";
      if (!ts.isEmpty()) covered++;
      if (state.equals("PASSED")) passed++;
      out.add(Map.of("requirement", entry.getKey(), "status", state, "tests", ts));
    }
    return Map.of(
        "requirements",
        out,
        "coveredPercent",
        round(covered * 100.0 / known.size()),
        "passedPercent",
        round(passed * 100.0 / known.size()),
        "allPassed",
        passed == known.size());
  }
}
