package plus.axz.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import plus.axz.community.dto.FileDto;
import plus.axz.community.exception.CustomizeErrorCode;
import plus.axz.community.exception.CustomizeException;
import plus.axz.community.provider.AliyunProvider;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Controller
public class FileController {
    @Autowired
    private AliyunProvider aliyunProvider;

    // 阿里云上传接口
    @ResponseBody
    @RequestMapping("/file/upload")
    public FileDto uploadFile(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        FileDto fileDto = new FileDto();
        try {
            assert file != null;
            String url = aliyunProvider.fileUpload(file.getInputStream(), Objects.requireNonNull(file.getOriginalFilename()));
            fileDto.setUrl(url);
            fileDto.setSuccess(1);
            fileDto.setMessage("上传成功");
        } catch (IOException e) {
            fileDto.setSuccess(0);
            fileDto.setMessage("上传失败");
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.UPLOAD_FILE_ERROR);
        }
        return fileDto;
    }
}
