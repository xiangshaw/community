package plus.axz.community.controller.admin.v1;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plus.axz.community.dto.AdUserDto;
import plus.axz.community.mapper.UserMapper;
import plus.axz.community.model.pojo.User;
import plus.axz.community.model.common.dtos.Result;
import plus.axz.community.model.common.enums.ResultEnum;
import plus.axz.community.service.AdUserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoxiang
 * @date 2023年02月16日
 * @Description: admin后台用户查询端口控制器
 */
@RestController
@RequestMapping("/api/v1/aduser/")
public class AdUserController {
    @Autowired
    private AdUserService adUserService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/userStatus/{id}")
    public boolean adUserStatus(@PathVariable("id") String id) {
        return adUserService.findUserStatusById(id);
    }

    @PostMapping("/save")
    public Result addSaveUser(@RequestBody User user) {
        return adUserService.addUser(user);
    }

    @PostMapping("/list")
    public Result listUser(@RequestBody AdUserDto adUserDto) {
        return adUserService.listUser(adUserDto);
    }


    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) {
        return adUserService.updateUser(user);
    }

    @DeleteMapping("/remove/{id}")
    public Result removeUser(@PathVariable("id") String id) {
        return adUserService.removeUser(id);
    }

    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable("id") String id, @PathVariable("status") Boolean status) {
        return adUserService.updateStatus(id, status);
    }

    @GetMapping("/findUserName/{search}")
    public Result findUserName(@PathVariable("page") @RequestParam(defaultValue = "1") Integer page,
                               @PathVariable("size") @RequestParam(defaultValue = "10") Integer size,
                               @PathVariable("search") @RequestParam(defaultValue = "") String search) {
        return adUserService.findUserName(page, size, search);
    }

    @GetMapping("/{id}")
    public Result findUserById(@PathVariable("id") String id) {
        return adUserService.findUserById(id);
    }

    @GetMapping("/count")
    public Result countUser() {
        return adUserService.countUser();
    }

    @PostMapping("/adUpload")
    public Result adminUpload(MultipartFile file) {
        return adUserService.adminUpload(file);
    }

    /**
     * Excel导出
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = CollUtil.newArrayList();
        List<User> all = userMapper.selectList(null);
        for (User user : all) {
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("用户名", user.getName());
            row1.put("手机号", user.getPhone());
            row1.put("性别", user.getSex());
            row1.put("头像", user.getAvatarUrl());
            list.add(row1);
        }
        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    /**
     * Excel导入
     * 导入的模板可以使用 Excel导出的文件
     *
     * @param file Excel
     * @return
     * @throws IOException
     */
    @PostMapping("/import")
    public Result upload(@RequestBody MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
        List<User> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            User user = new User();
            user.setName(row.get(0).toString());
            user.setSex((Boolean) row.get(1));
            user.setPhone(row.get(2).toString());
            saveList.add(user);
        }
        for (User user : saveList) {
            userMapper.insert(user);
        }
        return Result.okResult(ResultEnum.SUCCESS);
    }
    // 只修改描述,头像
    @PutMapping("/adUserInfo")
    public Result updateUserInfo(User user){
       return adUserService.updateUserInfo(user);
    }
}
