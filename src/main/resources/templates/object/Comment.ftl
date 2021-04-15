"Comment":{
<#list value as item>
    "${item.id?j_string}":{
        "href":"/comment/${item.id?j_string}",
        "version":"${item.version?c}",
        "data":{
            "content":"<#if item.content??>${item.content?j_string}<#else></#if>"
        }
    }<#sep>,</#sep>
</#list>
}