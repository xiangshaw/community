package plus.axz.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import plus.axz.community.config.AliyunParams;
import plus.axz.community.exception.CustomizeErrorCode;
import plus.axz.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
public class AliyunProvider {

    @Autowired
    private AliyunParams aliyunParams;

    public String fileUpload(InputStream fileStream, String fileName){
        // 生成OSSClient
        OSS ossClient = new OSSClientBuilder().build(aliyunParams.getEndpoint(), aliyunParams.getAccessKeyId(), aliyunParams.getAccessKeySecret());
        //生成唯一的文件名
        String[] fileArray = fileName.split("\\.");
        if (fileArray.length > 1) {
            fileName = UUID.randomUUID().toString() + "." + fileArray[fileArray.length - 1];
        }else{
            throw new CustomizeException(CustomizeErrorCode.UPLOAD_FILE_ERROR);
        }
        try {
            ossClient.putObject(aliyunParams.getBucketName(), fileName, fileStream);
            // 设置URL过期时间为1年。3600 * 1000 * 24 * 365
            Date expiration = new Date(new Date().getTime() + 3600 * 1000 * 24 * 365L);
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            URL url = ossClient.generatePresignedUrl(aliyunParams.getBucketName(), fileName, expiration);

            return url.toString();
        } catch (Exception oe) {
            oe.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        throw new CustomizeException(CustomizeErrorCode.UPLOAD_FILE_ERROR);
    }
}
