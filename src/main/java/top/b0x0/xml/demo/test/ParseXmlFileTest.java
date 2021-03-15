package top.b0x0.xml.demo.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.core.io.ClassPathResource;
import top.b0x0.xml.demo.domain.User;

import java.io.IOException;

/**
 * @author musui
 */
public class ParseXmlFileTest {

    public static void main(String[] args) throws IOException {
        XmlMapper xmlMapper = XmlMapper.builder()
                //  忽略实体类没有对应属性    如果为 true 会抛出异常
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 忽略null
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                // 属性使用 驼峰首字母小写
                .propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
                .build();

        ClassPathResource testXml = new ClassPathResource("./test.xml");
        // Java  User(username=felord.cn, age=18, created=<2020-10-22>)
        User user = xmlMapper.readValue(testXml.getFile(), User.class);
        System.out.println("xml file to Java Object " + user);

        // <User><username>felord.cn</username><age>18</age><created>&lt;2020-10-22></created></User>
        String s = xmlMapper.writeValueAsString(user);
        System.out.println("Java Object to xml string " + s);

        // Java User(username=felord.cn, age=18, created=<2020-10-22>)
        User u = xmlMapper.readValue(s, User.class);
        System.out.println("xml string to Java Object" + u);
    }


}
