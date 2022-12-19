<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Авторизация</h1>
    <hr>
    <center><form action="/login " method="post" class="logform">
        <div class="form-group">
            <label for="email">Электронная почта</label>
            <input style="width: 30%" type="email" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="email" name="username">
            <div id="email" class="invalid-feedback">
                Неверныя электронная почта
            </div>
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input style="width: 30%" type="password" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="password" name="password">
            <div id="password" class="invalid-feedback">
                Неверный пароль
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button  type="submit" style="width: 30%" class="btn btn-dark">Войти</button>
    </form></center>
    <hr>
    <center><h2> <a class="link" href="/registration">Зарегистрироваться!</a></h2></center>
</@t.template>