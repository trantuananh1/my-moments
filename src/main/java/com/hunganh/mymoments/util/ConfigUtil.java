package com.hunganh.mymoments.util;

import com.hunganh.mymoments.constant.ConfigConstant;
import com.hunganh.mymoments.constant.StringPool;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * @Author: Tran Tuan Anh
 * @Created: Wed, 31/03/2021 11:46 PM
 **/

public class ConfigUtil {
    public static String getTemplate(String key) {
        try {
            File fXmlFile = new File(ConfigConstant.TEMPLATE_CONFIG_FILE);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Config");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    if (element.getAttribute("data").equals(key)) {
                        return element.getAttribute("template");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return StringPool.BlANK;
    }
}
