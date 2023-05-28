package com.sdms.controller;

import com.sdms.common.result.LayuiResult;
import com.sdms.config.PictureConfig;
import com.sdms.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;

@Api("图片文件上传api")
@Controller
@Slf4j
public class FileUploadController {

    private final static String NAME = "file";

    @Resource
    private PictureConfig pictureConfig;

    /**
     * 本地图片文件上传接口 "/upload"
     *
     * @param request 图片文件上传请求,要求参数名是 file, (例如：用原生form提交,input标签需要添加 name="file" )
     * @return JSON格式的对象, code == 0 表示上传成功 , code == 1 表示上传失败
     *
     *
     * 方法的参数是HttpServletRequest对象，通过类型转换为MultipartHttpServletRequest对象，用于获取上传的文件。
     *
     * 代码首先判断是否是MultipartHttpServletRequest类型的请求，如果不是，则返回一个请求异常的错误结果。
     *
     * 获取上传的文件对象，存储在multipartFile变量中，如果获取到的文件对象为null，则返回参数异常的错误结果。
     *
     * 获取上传文件的原始文件名，如果文件名为空，则返回文件名为空的错误结果。
     *
     * 通过pictureConfig获取文件存储路径，将文件存储在指定路径下。
     *
     * 创建目标文件对象dest，并判断其父目录是否存在，如果不存在则尝试创建。
     *
     * 将上传的文件保存到目标文件中。
     *
     * 如果保存过程中发生异常，则记录错误日志，并返回文件保存失败的错误结果。
     *
     * 如果保存成功，则返回一个成功的结果，包含文件的访问URL。
     *
     * 私有方法result用于生成返回结果对象，并将文件访问URL添加到结果中
     */
    @ApiOperation("ajax:本地图片文件上传")
    @PostMapping("/upload")
    @ResponseBody
    //multipart/form-data
    public LayuiResult<Object> upload(HttpServletRequest request) {
        MultipartHttpServletRequest mRequest;
        if (request instanceof MultipartHttpServletRequest) {
            mRequest = (MultipartHttpServletRequest) request;
        } else {
            return result(1, "failure:请求异常", null);
        }
        val multipartFile = mRequest.getFile(NAME);
        if (null == multipartFile) {
            return result(1, "failure:参数异常,请检查参数名是否为" + NAME, null);
        }
        val originalFilename = multipartFile.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)) {
            return result(1, "failure:文件名为空", null);
        }
        val path = pictureConfig.getPath();
        val dest = new File(path + originalFilename);
        if (!dest.getParentFile().exists()) {
            if (!dest.getParentFile().mkdirs()) {
                return result(1, "failure:服务器存储路径创建失败", null);
            }
        }
        try {
            //transferTo(File dest): 将上传文件保存到指定的目标文件
            multipartFile.transferTo(dest);
        } catch (Exception e) {
            log.error("文件上传失败: error = " + e.getMessage());
            return result(1, "failure:文件保存失败", null);
        }
        return result(0, "success", "/sdms-images/" + originalFilename);
    }

    private LayuiResult<Object> result(Integer code, String msg, String pictureURL) {
        val m = new HashMap<String, Object>();
        m.put("pictureURL", pictureURL);
        return new LayuiResult<>(code, msg, null, Collections.singletonList(m));
    }

}