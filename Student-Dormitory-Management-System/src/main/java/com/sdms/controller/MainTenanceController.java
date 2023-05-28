package com.sdms.controller;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.LayuiResult;
import com.sdms.entity.*;
import com.sdms.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

import static com.sdms.common.result.LayuiResult.ResultCode.FAILED;
import static com.sdms.common.result.LayuiResult.ResultCode.SUCCESS;
import static com.sdms.common.util.StringUtils.parseLongList;
@Slf4j
@Controller
public class MainTenanceController {
    @Resource
    private MainTenanceService mainTenanceService;

    @Resource
    private RoomService roomService;

    @Resource
    private StudentService studentService;

    @GetMapping(value = {"/admin/mainTenance-list"})
    public String toAdminMainTenanceList(Model model) {
        log.info(mainTenanceService.listAllStatuses().toString());
        model.addAttribute("statuses", mainTenanceService.listAllStatuses());
        return "admin/mainTenance-list"; // Thymeleaf模板的名字,表示 templates/admin/room-request-list.html
    }

    @ApiOperation("ajax:分页查询报修申请信息")
    @RequestMapping(value = "/admin/mainTenances", method = {RequestMethod.POST})
    @ResponseBody
    public Page<MainTenance> fetchPage(@RequestBody PageRequest pageRequest) {
        return mainTenanceService.fetchPage(pageRequest);
    }

    @ApiOperation("ajax:根据id查询报修申请")
    @GetMapping("/admin/mainTenance/{id}")
    @ResponseBody
    public MainTenance getMainTenanceById(@PathVariable Long id) {

        return mainTenanceService.getMainTenanceById(id);
    }

    @ApiOperation("处理报修申请")
    @GetMapping("/admin/mainTenance/edit")
    public LayuiResult<String> toEditMainTenanceById(@RequestParam(defaultValue = "-1") Long id, Model model) {
        val mainTenance = mainTenanceService.getMainTenanceById(id);
        log.info(mainTenance.toString());
        if (mainTenance != null) {
            mainTenance.setStatusCode(1);
            mainTenanceService.saveMainTenance(mainTenance);
            return new LayuiResult<>(SUCCESS,null,null);
        }
        return new LayuiResult<>(FAILED,null,null);
    }



    @ApiOperation("保存报修申请")
    @GetMapping("/admin/mainTenance/save")
    public String saveRoomRequest(MainTenance mainTenance, RedirectAttributes attributes) {
        val result = mainTenanceService.saveMainTenance(mainTenance);
        if (result.isSuccess()) {
            attributes.addFlashAttribute("info", "操作成功");
        } else {
            attributes.addFlashAttribute("error", result.getMsg() + ",保存报修申请失败");
        }
        return "redirect:/admin/mainTenance-list";
    }

    @ApiOperation("ajax:根据若干id删除报修申请")
    @RequestMapping(value = "/admin/mainTenance/delete", method = {RequestMethod.POST})
    @ResponseBody
    public LayuiResult<String> deleteRoomRequestByIds(String ids) {
        val idList = parseLongList(ids);
        if (mainTenanceService.deleteMainTenanceByIds(idList).isSuccess()) {
            return new LayuiResult<>(SUCCESS, null, null);
        } else {
            return new LayuiResult<>(FAILED, null, null);
        }
    }
    @Autowired
    private UserService userService;
    //住宿申请
    @ApiOperation("新增报修申请")
    @GetMapping("/mainTenance/new")
    @ResponseBody
    public LayuiResult<String> addNewRoomRequest(String id,String description) {
        Student student = studentService.getStudentByUserId(id);
        if (mainTenanceService.newMainTenance(student.getId(),student.getRoomId(),description).isSuccess()) {
            return new LayuiResult<>(SUCCESS, null, null);
        } else {
            return new LayuiResult<>(FAILED, null, null);
        }
    }
    @ApiOperation("转发到住宿申请")
    @GetMapping("/mainTenance/input")
    public String toMainTenanceInput()
    {
        return "mainTenance";
    }


}
