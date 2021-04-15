package com.hunganh.mymoments.util;

import com.hunganh.mymoments.constant.ConfigConstant;
import com.hunganh.mymoments.constant.StringPool;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Tran Tuan Anh
 * @Created: Wed, 31/03/2021 11:47 PM
 **/

public class TemplateUtil {
    public static String getResultByMap(Map<String, Object> map) {
        String result = StringPool.BlANK;

        Configuration config = new Configuration(Configuration.VERSION_2_3_31);

        try {
            // generate result from template
            config.setDirectoryForTemplateLoading(new File(ConfigConstant.TEMPLATE_DIRECTORY));
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
                Template template = config.getTemplate(ConfigUtil.getTemplate(entry.getKey()));
                StringWriter writer = new StringWriter();
                Map<String, Object> tmpMap = new HashMap<>();
                tmpMap.put("key", entry.getKey());
                tmpMap.put("value", entry.getValue());
                template.process(tmpMap, writer);

                result += writer.toString() + ",\n";
            }
            result = result.substring(0, result.length() - 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String generateJson(Map<String, Object> map) {
        String content = getResultByMap(map);
        return "{\n" +
                content.replaceAll("(?m)^", "\t")
                + "}";
    }

    public static void main(String[] args) {
        //object
//        User user = new User();
//        user.setId("fsfsdfwef");
//        user.setUsername("anhtrt");
//        user.setVersion(2);
//        User user3 = new User();
//        user3.setId("41234daa");
//        user3.setUsername("bnao");
//        user3.setVersion(3);
//        List<User> users = Arrays.asList(user, user3);
//        Profile profile = new Profile();
//        profile.setUserId(user.getId());
//        profile.setEmail("tta9799@gmail.com");
//        profile.setGender("male");
//        List<Profile> profiles = Arrays.asList(profile);
//        //assoc
//        List<AssocOutput> assocOutputs = Arrays.asList(
//                new AssocOutput("43423fsdfa", 4523423423l, 4242342l, 43, Arrays.asList("4234234", "423d"))
//        );
//        Map<String, Object> map = new HashMap<>();
//        map.put("User", users);
//        map.put("Profile", profiles);
//        map.put("HasPost", assocOutputs);
//        System.out.println(generateJson(map));
    }
}
