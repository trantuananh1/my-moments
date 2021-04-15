"Profile":{
<#list value as item>
    "${item.userId?j_string}":{
        "data":{
            "email":"<#if item.email??>${item.email?j_string}</#if>",
            "full_name":"<#if item.fullName??>${item.fullName?j_string}</#if>",
            "city":"<#if item.city??>${item.city?j_string}</#if>",
            "country":"<#if item.country??>${item.country?j_string}</#if>",
            "biography":"<#if item.biography??>${item.biography?j_string}</#if>",
            "gender":"<#if item.gender??>${item.gender?j_string}</#if>",
            "date_of_birth":"<#if item.dateOfBirth??>${item.dateOfBirth?j_string}</#if>",
            "avatar_url":"<#if item.avatarUrl??>${item.avatarUrl?j_string}</#if>",
            "cover_url":"<#if item.coverUrl??>${item.coverUrl?j_string}</#if>"
        }
    }<#sep>,</#sep>
</#list>
}