"Attachment":{
<#list value as item>
    "${item.id?j_string}":{
        "href":"/attachments/${item.id?j_string}",
        "version":"${item.version?c}",
        "data":{
            "userId":"<#if item.userId??>${item.userId?j_string}</#if>",
            "url":"<#if item.url??>${item.url?j_string}</#if>",
            "name":"<#if item.name??>${item.name?j_string}</#if>",
            "mime":"<#if item.caption??>${item.mime?j_string}</#if>",
            "size":"<#if item.size??>${item.size?c}</#if>",
        }
    }<#sep>,</#sep>
</#list>
}