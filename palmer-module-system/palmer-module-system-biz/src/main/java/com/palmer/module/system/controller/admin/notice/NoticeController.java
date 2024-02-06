package com.palmer.module.system.controller.admin.notice;


import cn.hutool.core.bean.BeanUtil;
import com.palmer.framework.common.pojo.CommonResult;
import com.palmer.framework.common.pojo.PageParam;
import com.palmer.framework.common.pojo.PageResult;
import com.palmer.framework.excel.core.utils.ExcelUtils;
import com.palmer.module.system.controller.admin.notice.vo.NoticeCreateReqVO;
import com.palmer.module.system.controller.admin.notice.vo.NoticePageReqVO;
import com.palmer.module.system.controller.admin.notice.vo.NoticeRespVO;
import com.palmer.module.system.controller.admin.notice.vo.NoticeUpdateReqVO;
import com.palmer.module.system.convert.notice.NoticeConvert;
import com.palmer.module.system.dal.dataobject.notice.NoticeDO;
import com.palmer.module.system.service.notice.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.palmer.framework.common.pojo.CommonResult.success;


@Tag(name = "管理后台 - 通知公告")
@RestController
@RequestMapping("/system/notice")
@Validated
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @PostMapping("/create")
    @Operation(summary = "创建通知公告")
    @PreAuthorize("@ss.hasPermission('system:notice:create')")
    public CommonResult<Long> createNotice(@Valid @RequestBody NoticeCreateReqVO reqVO) {
        Long noticeId = noticeService.createNotice(reqVO);
        return success(noticeId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改通知公告")
    @PreAuthorize("@ss.hasPermission('system:notice:update')")
    public CommonResult<Boolean> updateNotice(@Valid @RequestBody NoticeUpdateReqVO reqVO) {
        noticeService.updateNotice(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除通知公告")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:notice:delete')")
    public CommonResult<Boolean> deleteNotice(@RequestParam("id") Long id) {
        noticeService.deleteNotice(id);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获取通知公告列表")
    @PreAuthorize("@ss.hasPermission('system:notice:query')")
    public CommonResult<PageResult<NoticeRespVO>> getNoticePage(@Validated NoticePageReqVO reqVO) {
        return success(NoticeConvert.INSTANCE.convertPage(noticeService.getNoticePage(reqVO)));
    }

    @GetMapping("/get")
    @Operation(summary = "获得通知公告")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:notice:query')")
    public CommonResult<NoticeRespVO> getNotice(@RequestParam("id") Long id) {
        return success(NoticeConvert.INSTANCE.convert(noticeService.getNotice(id)));
    }


    @Operation(summary = "导出通知")
    @GetMapping("/export")
    @PermitAll
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public void export(HttpServletResponse response, @Valid NoticePageReqVO exportReqVO) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<NoticeDO> list = noticeService.getNoticePage(exportReqVO).getList();
        // 导出
        ExcelUtils.write(response, "通知数据.xls", "数据", NoticeRespVO.class,
                BeanUtil.copyToList(list, NoticeRespVO.class));


    }

}
