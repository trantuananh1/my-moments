"Post":{
<#list value as item>
    "${item.id?j_string}":{
        "href":"/post/${item.id?j_string}",
        "version":"${item.version?c}",
        "data":{
            "caption":"<#if item.caption??>${item.caption?j_string}</#if>",
            "latitude":"<#if item.latitude??>${item.latitude?c}</#if>",
            "longtitude":"<#if item.longtitude??>${item.longtitude?c}</#if>"
        }
    }<#sep>,</#sep>
</#list>
}