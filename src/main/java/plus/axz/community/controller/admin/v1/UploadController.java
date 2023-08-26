package plus.axz.community.controller.admin.v1;

/**
 * @author xiaoxiang
 * @date 2023年02月11日
 * @Description:
 */

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 本地文件上传Controller
 */
@CrossOrigin(origins = "*") // 解决跨域问题
@Controller
public class UploadController {

    /**
     * 上传地址
     */
    @Value("${file.upload.path}")
    private String filePath;
    @Value("${file.upload.ip}")
    private String ip;

    // 跳转上传页面
    @RequestMapping("/userInfo")
    public String test() {
        return "userInfo";
    }

    // 执行上传
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        // 定义唯一标识
        String uuid = IdUtil.fastSimpleUUID();
        // 定义上传文件保存路径
        String path = filePath + "userAvatar/" + uuid + "_" + filename;
        // 新建文件
        File filepath = new File(path);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            // 写入文件
            FileUtil.writeBytes(file.getBytes(), path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 根据用户ID查询描述 并返回
        // 将src路径发送至html页面
        model.addAttribute("avatarUrl", ip + uuid);
        return "userAvatar";
    }

    @RequestMapping("/uploads")
    public String uploads(@RequestParam("file") MultipartFile file,
                         @RequestParam("bio")  String bio,
                          Model model) {
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        // 定义唯一标识
        String uuid = IdUtil.fastSimpleUUID();
        // 定义上传文件保存路径
        String path = filePath + "userAvatar/" + uuid + "_" + filename;
        // 新建文件
        File filepath = new File(path);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            // 写入文件
            FileUtil.writeBytes(file.getBytes(), path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 根据用户ID查询描述 并返回

        // 将src路径发送至html页面
        model.addAttribute("bio",bio);
        model.addAttribute("avatarUrl", ip + uuid);
        return "userInfo";
    }

    /**
     * 下载接口
     *
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        String basePath = filePath + "userAvatar/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }
}
