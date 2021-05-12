"${key}":{
<#list value as assocOutput>
    "${assocOutput.objectId?j_string}":{
        "maxScore":<#if assocOutput.maxScore??>${assocOutput.maxScore?c}</#if>,
        "minScore":<#if assocOutput.minScore??>${assocOutput.minScore?c}</#if>,
        "total":<#if assocOutput.total??>${assocOutput.total?c}</#if>,
        "itemIds":[
        <#list assocOutput.itemIds as objectId>
            "${objectId?j_string}"<#sep>,</#sep>
        </#list>
        ]
    }<#sep>,</#sep>
</#list>
}