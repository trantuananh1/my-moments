"User":{
<#list value as item>
    "${item.id?j_string}":{
        "href":"/user/${item.id?j_string}",
        "version":"${item.version?c}",
        "data":{
            "id":"${item.id?j_string}",
            "username":"${item.username?j_string}",
            "email":"${item.email?j_string}",
            "lastIP":"<#if item.lastIP??>${item.lastIP?j_string}</#if>",
            "enabled":"<#if item.enabled??>${item.enabled?c}</#if>",
            "profile":{
                "full_name":"<#if item.profile.fullName??>${item.profile.fullName?j_string}</#if>",
                "city":"<#if item.profile.city??>${item.profile.city?j_string}</#if>",
                "country":"<#if item.profile.country??>${item.profile.country?j_string}</#if>",
                "biography":"<#if item.profile.biography??>${item.profile.biography?j_string}</#if>",
                "gender":"<#if item.profile.gender??>${item.profile.gender?j_string}</#if>",
                "date_of_birth":"<#if item.profile.dateOfBirth??>${item.profile.dateOfBirth?j_string}</#if>",
                "avatar_url":"<#if item.profile.avatarUrl??>${item.profile.avatarUrl?j_string}</#if>",
                "cover_url":"<#if item.profile.coverUrl??>${item.profile.coverUrl?j_string}</#if>"
            }
        }
    }<#sep>,</#sep>
</#list>
}