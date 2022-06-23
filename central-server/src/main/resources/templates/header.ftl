<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <a class="navbar-brand" href="#">KPFU</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsingNavbarLg">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="navbar-collapse collapse" id="collapsingNavbarLg">
        <ul class="navbar-nav">
            <#if model.user.isPresent()>
                <li class="nav-item">
                    <a class="nav-link" href="<@spring.url "/"/>profile">Личный кабинет</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="<@spring.url "/"/>token">Токен</a>
                </li>


                <#if model.user.get().hasRole("ADMIN")>
                    <li class="nav-item">
                        <a class="nav-link" href="<@spring.url "/"/>admin/users">Пользователи</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="<@spring.url "/"/>admin/rest-client">Rest</a>
                    </li>
                </#if>


                <li class="nav-item">
                    <a class="nav-link" href="<@spring.url "/"/>logout">Выход</a>
                </li>
            </#if>


             <#if !model.user.isPresent()>
                <li class="nav-item">
                    <a class="nav-link" href="<@spring.url "/"/>login">Авторизация</a>
                </li>
             </#if>
        </ul>
    </div>
</nav>