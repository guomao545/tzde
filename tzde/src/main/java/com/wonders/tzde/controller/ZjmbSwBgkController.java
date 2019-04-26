package com.wonders.tzde.controller;

import com.wonders.tzde.service.ZjmbSwBgkService;
import com.wonders.tzde.model.ZjmbSwBgk;
import com.wonders.tzde.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "死亡接口",tags = "死亡数据管理")
@RestController
@RequestMapping("/zjmbSwBgk")
public class ZjmbSwBgkController {
    private Logger logger = LoggerFactory.getLogger(ZjmbSwBgkController.class);
    @Autowired
    private ZjmbSwBgkService zjmbSwBgkService;

    /**
     * 从台州市慢病系统中抽取死亡数据以接口的形式将数据传输给台州市公共数据共享平台
     * @param params
     * @return
     */
    @GetMapping("/getSwData")
    @ApiOperation("死亡数据共享接口")
    public Result getSwData(ZjmbSwBgk params) {
        try {
            if (params == null) {
                params = new ZjmbSwBgk();
            }
            params.setVcShbz("3");      // 只取区县审核通过状态的
            // 如果没有指定发送状态的，则默认只取未发送的
            if (params.getVcUpSpFlag() == null) {
                params.setVcUpSpFlag(Short.valueOf("0"));
            }

            List<Map<String, Object>> list = zjmbSwBgkService.getSwData(params);
            if (CollectionUtils.isEmpty(list)) {
                return Result.fail(-1, "没有可获取的数据", null);
            }

            // 更新状态，并去除返回值中不需要的字段
            List<String> vcBgkidList = new ArrayList<>();
            list.forEach(p -> {
                if (p.get("vcBgkid") != null) {
                    vcBgkidList.add((String) p.get("vcBgkid"));
                }
                p.remove("vcBgkid");
            });
            Map<String, Object> updateParams = new HashMap<>();
            updateParams.put("vcUpSpFlag", 1);
            updateParams.put("vcBgkidList", vcBgkidList);
            zjmbSwBgkService.updateUploadFlag(updateParams);

            return Result.success(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.fail(-1, "获取数据失败", null);
        }

    }
}
