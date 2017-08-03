<#import "layouts/admin.ftl" as layout />
<#import "spring.ftl" as spring />
<@layout.admin>
    <h1>Login</h1>

    <form method="POST" action="/management/auth">
        <@spring.formInput path="user.username" />

        <@spring.formPasswordInput path="user.password" />


        <button class="btn btn-primary">Log in</button>
    </form>

</@layout.admin>