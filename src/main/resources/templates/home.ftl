<#import "layouts/general.ftl" as layout />
<@layout.general>
    <h1>Home</h1>

    <#list posts.results as post>
        <div class="post">
            <h2>${post.author}</h2>
            <p>${post.content}</p>
            <ul>
                <#list post.tags as tag>
                    <li>${tag.value}</li>
                </#list>
            </ul>
        </div>
    </#list>

</@layout.general>
