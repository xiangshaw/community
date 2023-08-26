package plus.axz.community;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Console;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plus.axz.community.dto.TagDto;
import plus.axz.community.model.pojo.Sort;
import plus.axz.community.model.Student;
import plus.axz.community.model.vo.SortTagVo;
import plus.axz.community.service.SortService;
import plus.axz.community.service.TagService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class CommunityApplicationTests {

    @Test
    void contextLoads() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("http://open.iciba.com/dsapi")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCaptcha() {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.write("D:/line.png");
        Console.log(lineCaptcha.getCode());
        boolean verify = lineCaptcha.verify("1234");
        System.out.println();
    }

    @Autowired
    private TagService tagService;
    @Autowired
    private SortService sortService;

    @Test
    public void testTagAll() {
        /*for (int i = 0; i < x.size(); i++) {
            System.out.println("输出测试" +  x.get(i).getSortName());
        }
        System.out.println(x.get(0).getSortName());*/

       /* TagCache tagCache = new TagCache();
        List<TagDto> tagDto = tagCache.get();*/
        List<Sort> x = sortService.FindAll();
        TagDto dto = new TagDto();
        List<String> strings = Collections.singletonList(x.get(0).getSortName());
        String s = strings.toString().replaceAll("\\[", "").replaceAll("]", "");
        dto.setCategoryName(s);
        List<SortTagVo> tag = tagService.getSortByTag(s);
        List<String> list = new ArrayList<>();
        int size = tag.size();
        for (SortTagVo sortTagVo : tag) {
            list.add(String.valueOf(sortTagVo.getTagName()));
        }
        dto.setTags(list);

        System.out.println();
        System.out.println("==========================================================================================");
        System.out.println("==========================================================================================");
        System.out.println("输出数据: " + dto);
        System.out.println("输出数据size: " + size);
        System.out.println("==========================================================================================");
        System.out.println("==========================================================================================");

        /*System.out.println("tagDtos：" + tagDto);
        System.out.println("tags：" + tagDto.get(0).getTags());
        System.out.println("getCategoryName: " + tagDto.get(1).getCategoryName());


        System.out.println("tagCache 数据:" + tagCache);*/







        /*Result result = tagService.FindAll();
        System.out.println(result.getData());*/



        /*List<Tag> tags = tagService.FindAll();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            list.add(String.valueOf(tags.get(i)));
        }
        System.out.println("list 结果输出");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
        System.out.println(tags);*/

        /*list.forEach(System.out::println);
        System.out.println("list 结果输出");
        list.stream().forEach(System.out::println);*/
    }

    @Test
    //传入指定时间
    public void getTimeStamp() {
        Date date = new Date();
        System.out.println(date);
        long times = date.getTime();
        System.out.println(times);
        //第二种方法：
        /*new Date().getTime();*/
    }

    @Test
    public void getRandom(){
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        Random random = new Random();
        // 转成字符串
        String i = String.valueOf(random.nextInt(9));
        for (int j = 0; j < 5; j++) {
            // 6位
            i += random.nextInt(9);
        }
        System.out.println(code);
        System.out.println(i);
    }

    @Test
    public void getTime() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 获取今天的日期
        String s = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        long time1 = simpleDateFormat.parse(s).getTime();
        System.out.println("今天：" + s);
        System.out.println("今天时间戳：" + time1);
        // 获取昨天的日期  当前秒数减去一天的秒数
        Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24); // 前天，大前天，一礼拜计算 +- 24*n
        String s1 = new SimpleDateFormat("yyyy-MM-dd").format(yesterday);
        long time = simpleDateFormat.parse(s1).getTime();
        System.out.println("昨天：" + s1);
        System.out.println("昨天时间戳：" + time);

        // 13位的时间戳，其精度是毫秒(ms)
        // 10位的时间戳，其精度是秒(s)
        // 13位数的时间戳转化为10位数的时间戳, 除以1000
        // 10位数的时间戳转化为13位数的时间戳, 乘以1000
        // 获得当前时间的时间戳，单位是毫秒
        // new Date().getTime()

        /*GregorianCalendar calendar = new GregorianCalendar();
        int dataYear = calendar.get(Calendar.YEAR);
        int dataMonth = calendar.get(Calendar.MONTH)+1;
        int dataDay = calendar.get(Calendar.DATE);
        Date date = new Date();

        long time = date.getTime();
        Date time1 = calendar.getTime();
        String format = new SimpleDateFormat().format(time);
        System.out.println(dataYear +""+ dataMonth+"" + dataDay);
        System.out.println(date);
        System.out.println(time);
        System.out.println(time1);
        System.out.println(format);*/
    }

    @Test
    public void getTimes(){
        // 指定格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long[] longs = new Long[6];
        // 获取昨日时间戳
        for (int i = 1; i < 6; i++) {
            longs[i] = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * i).getTime();
        }
        for (int i = 1; i < longs.length; i++) {
            System.out.println(longs[i]);
        }

    }

    @Test
    public void getStu(){
        Student student = new Student();
    }
}
