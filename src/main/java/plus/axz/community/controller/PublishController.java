package plus.axz.community.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import plus.axz.community.cache.TagCache;
import plus.axz.community.dto.QuestionDto;
import plus.axz.community.dto.TagDto;
import plus.axz.community.exception.CustomizeErrorCode;
import plus.axz.community.exception.CustomizeException;
import plus.axz.community.model.pojo.Question;
import plus.axz.community.model.pojo.Sort;
import plus.axz.community.model.pojo.User;
import plus.axz.community.model.vo.SortTagVo;
import plus.axz.community.service.Impl.QuestionServiceImpl;
import plus.axz.community.service.SortService;
import plus.axz.community.service.TagService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class PublishController {
    @Autowired
    private QuestionServiceImpl questionService;
    @Autowired
    private SortService sortService;
    @Autowired
    private TagService tagService;


    public List<TagDto> get() {
        List<TagDto> tagDtos = new ArrayList<>();

        List<Sort> x = sortService.FindAll();
        TagDto tool = new TagDto();
        List<String> strings0 = Collections.singletonList(x.get(0).getSortName());
        String s = strings0.toString().replaceAll("\\[", "").replaceAll("]", "");
        tool.setCategoryName(s);
        List<SortTagVo> tag = tagService.getSortByTag(s);
        List<String> list = new ArrayList<>();
        for (SortTagVo sortTagVo : tag) {
            list.add(String.valueOf(sortTagVo.getTagName()));
        }
        tool.setTags(list);
        tagDtos.add(tool);

        TagDto program = new TagDto();
        List<String> strings1 = Collections.singletonList(x.get(1).getSortName());
        String s1 = strings1.toString().replaceAll("\\[", "").replaceAll("]", "");
        program.setCategoryName(s1);
        List<SortTagVo> tag1 = tagService.getSortByTag(s1);
        List<String> list1 = new ArrayList<>();
        for (SortTagVo sortTagVo : tag1) {
            list1.add(String.valueOf(sortTagVo.getTagName()));
        }
        program.setTags(list1);
        tagDtos.add(program);

        TagDto framework = new TagDto();
        List<String> strings2 = Collections.singletonList(x.get(2).getSortName());
        String s2 = strings2.toString().replaceAll("\\[", "").replaceAll("]", "");
        framework.setCategoryName(s2);
        List<SortTagVo> tag2 = tagService.getSortByTag(s2);
        List<String> list2 = new ArrayList<>();
        for (SortTagVo sortTagVo : tag2) {
            list2.add(String.valueOf(sortTagVo.getTagName()));
        }
        framework.setTags(list2);
        tagDtos.add(framework);

        TagDto server = new TagDto();
        List<String> strings3 = Collections.singletonList(x.get(3).getSortName());
        String s3 = strings3.toString().replaceAll("\\[", "").replaceAll("]", "");
        server.setCategoryName(s3);
        List<SortTagVo> tag3 = tagService.getSortByTag(s3);
        List<String> list3 = new ArrayList<>();
        for (SortTagVo sortTagVo : tag3) {
            list3.add(String.valueOf(sortTagVo.getTagName()));
        }
        server.setTags(list3);
        tagDtos.add(server);

        TagDto db = new TagDto();
        List<String> strings4 = Collections.singletonList(x.get(4).getSortName());
        String s4 = strings4.toString().replaceAll("\\[", "").replaceAll("]", "");
        db.setCategoryName(s4);
        List<SortTagVo> tag4 = tagService.getSortByTag(s4);
        List<String> list4 = new ArrayList<>();
        for (SortTagVo sortTagVo : tag4) {
            list4.add(String.valueOf(sortTagVo.getTagName()));
        }
        db.setTags(list4);
        tagDtos.add(db);

        TagDto other = new TagDto();
        List<String> strings5 = Collections.singletonList(x.get(5).getSortName());
        String s5 = strings5.toString().replaceAll("\\[", "").replaceAll("]", "");
        other.setCategoryName(s5);
        List<SortTagVo> tag5 = tagService.getSortByTag(s5);
        List<String> list5 = new ArrayList<>();
        for (SortTagVo sortTagVo : tag5) {
            list5.add(String.valueOf(sortTagVo.getTagName()));
        }
        other.setTags(list5);
        tagDtos.add(other);
        return tagDtos;
    }



    @GetMapping("/publish")
    public String publish(Model model) {
        //获取所有标签
        List<TagDto> tagDtos = get();
        model.addAttribute("tagDtos", tagDtos);
        return "publish";
    }

    /**
     * 发布问题
     *
     * @param id
     * @param title
     * @param description
     * @param tags
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "id") Long id,
                            @RequestParam(value = "title") String title,
                            @RequestParam(value = "description") String description,
                            @RequestParam(value = "tags") String tags,
                            Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tags", tags);

        //获取所有标签
        List<TagDto> tagDtos = get();
        model.addAttribute("tagDtos", tagDtos);

        //错误信息
        if (user == null) {
            model.addAttribute("error", "用户未登陆");
            return "publish";
        }
        //判断是否填入信息
        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "描述信息不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(tags)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        Question question = new Question();
        question.setId(id);
        question.setTitle(title.trim());
        question.setDescription(description);
        question.setTags(tags);
        question.setCreator(user.getId());
        questionService.createOrUpdateQuestion(question, user);

        //发布成功，返回主页面
        return "redirect:/";
    }


    /**
     * 修改问题
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        //通过问题id查找问题，存入作用域
        QuestionDto question = questionService.findById(id);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !question.getCreator().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.IS_NOT_LEGAL);
        }
        model.addAttribute("id", question.getId());
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tags", question.getTags());
        //获取所有标签
        List<TagDto> tagDtos = get();
        model.addAttribute("tagDtos", tagDtos);
        return "publish";
    }
}


/*    @GetMapping("/publish")
    public String publish(Model model) {
        //获取所有标签
        //TODO
//        List<TagDto> tagDtos = TagCache.get();
        TagCache tagCache = new TagCache();
        List<TagDto> tagDtos = tagCache.get();
        model.addAttribute("tagDtos", tagDtos);
        return "publish";
    }

    *//**
     * 发布问题
     *
     * @param id
     * @param title
     * @param description
     * @param tags
     * @param model
     * @param request
     * @return
     *//*
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "id") Long id,
                            @RequestParam(value = "title") String title,
                            @RequestParam(value = "description") String description,
                            @RequestParam(value = "tags") String tags,
                            Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tags", tags);

        //获取所有标签
        //TODO
//        List<TagDto> tagDtos = TagCache.get();
        TagCache tagCache = new TagCache();
        List<TagDto> tagDtos = tagCache.get();
        model.addAttribute("tagDtos", tagDtos);

        //错误信息
        if (user == null) {
            model.addAttribute("error", "用户未登陆");
            return "publish";
        }
        //判断是否填入信息
        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "描述信息不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(tags)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        Question question = new Question();
        question.setId(id);
        question.setTitle(title.trim());
        question.setDescription(description);
        question.setTags(tags);
        question.setCreator(user.getId());
        questionService.createOrUpdateQuestion(question, user);

        //发布成功，返回主页面
        return "redirect:/";
    }


    *//**
     * 修改问题
     *
     * @param id
     * @param model
     * @return
     *//*
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        //通过问题id查找问题，存入作用域
        QuestionDto question = questionService.findById(id);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !question.getCreator().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.IS_NOT_LEGAL);
        }
        model.addAttribute("id", question.getId());
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tags", question.getTags());
        //获取所有标签
        // todo
//        List<TagDto> tagDtos = TagCache.get();
        TagCache tagCache = new TagCache();
        List<TagDto> tagDtos = tagCache.get();
        model.addAttribute("tagDtos", tagDtos);
        return "publish";
    }*/
