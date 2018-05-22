package com.barry.bigdata.controller;

import com.barry.bigdata.base.BaseController;
import com.barry.bigdata.entity.Staff;
import com.barry.bigdata.service.StaffService;
import com.barry.bigdata.utils.ResultType;
import com.barry.bigdata.vo.ResultData;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Auther: hants
 * @Date: 2018-05-22 17:52
 * @Description: 视图控制实现
 */
@RestController
@RequestMapping("/staff")
public class StaffController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private StaffService staffService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    /**
     * @Descripton: 保存
     * @param staff
     * @Date: 2018/3/22
     */
    @PostMapping("/save")
    public ResultData save(Staff staff){
        try {
            staffService.save(staff);
            return setResponseEntity(ResultType.SUCCESS.getDescription(), ResultType.SUCCESS.getCode(),null,true);
        } catch (Exception e) {
            logger.error("StaffController save exception {} ", e);
            return setResponseEntity(ResultType.SERVER_ERROR.getDescription(),ResultType.SERVER_ERROR.getCode(),null,false);
        }
    }

    /**
     * @Descripton: 根据id查询
     * @Author: Sorin
     * @param id
     * @Date: 2018/3/22
     */
    @GetMapping("/get/{id}")
    public ResultData getBean(@PathVariable String id){
        try {
            List<Staff> apples = staffService.getById(new Staff(), id);
            return setResponseEntity(ResultType.SUCCESS.getDescription(), ResultType.SUCCESS.getCode(), ImmutableMap.of("date", apples),true);
        } catch (Exception e) {
            logger.error("StaffController getBean exception {} ", e);
            return setResponseEntity(ResultType.SERVER_ERROR.getDescription(),ResultType.SERVER_ERROR.getCode(),null,false);
        }
    }

}

