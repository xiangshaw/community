package plus.axz.community.cache;

/*@Component
public class TagCache {

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
}*/

/*
public class TagCache {

    public static List<TagDto> get() {
        List<TagDto> tagDtos = new ArrayList<>();

        TagDto tool = new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("intellij-idea", "git", "github", "vscode", "vim", "sublime", "eclipse", "xcode", "maven", "ide", "svn", "android-studio", "atom", "emacs", "textmate", "hg"));
        tagDtos.add(tool);

        TagDto program = new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java", "c/c++", "python", "golang", "c#", "php", "javascript", "scala", "html", "html5", "css", "node·js", "objective-c", "typescript", "shell", "swift", "sass", "ruby", "bash", "less", "asp·net", "lua", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDtos.add(program);

        TagDto framework = new TagDto();
        framework.setCategoryName("开发框架");
        framework.setTags(Arrays.asList("spring", "springmvc", "springboot", "springcloud", "mybatis", "bootstrap", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts", "laravel"));
        tagDtos.add(framework);

        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存", "tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDtos.add(server);

        TagDto db = new TagDto();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql", "memcached", "elasticsearch", "sqlserver", "postgresql", "sqlite"));
        tagDtos.add(db);

        TagDto other = new TagDto();
        other.setCategoryName("其它");
        other.setTags(Arrays.asList("找bug", "测试", "冒泡", "交友", "生活", "电影", "音乐", "读书", "美食", "游戏", "科技", "数码", "理财"));
        tagDtos.add(other);
        return tagDtos;
    }
}
*/
