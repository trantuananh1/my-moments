"${key}:{
<#list value as assocOutput>
    "${assocOutput.objectId?j_string}:{
        "min_score":<#if assocOutput.minScore??>${assocOutput.minScore?c}</#if>,
        "max_score":<#if assocOutput.maxScore??>${assocOutput.maxScore?c}</#if>,
        "total":<#if assocOutput.total??>${assocOutput.total?c}</#if>,
        "itemIds":[
        <#list assocOutput.itemIds as objectId>
            "${objectId?j_string}"<#sep>,</#sep>
        </#list>
        ]
    }<#sep>,</#sep>
</#list>
}