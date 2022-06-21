<#ftl encoding='UTF-8'>

<!DOCTYPE html>
<html>
<#include "head.ftl">
<body>

<#include "header.ftl">

<div class="container-fluid">
    <div class="row">
        <div class="col-md-1 col-lg-2 col-xs-3">

        </div>
        <div class="col-12 col-sm-12 col-md-10 col-lg-8 col-xs-6">
            <form action="token" method='POST'>
                <div class="form-group">
                    <#if error??>
                        <div class="alert alert-danger" role="alert">${error}</div>
                    </#if>

                    <#if info??>
                        <div class="alert alert-danger" role="success">${info}</div>
                    </#if>
                </div>
                <div class="form-group">
                    <label for="inputName">Введите название токена</label>
                    <input name="name" class="form-control" id="inputName" placeholder="Название"/>
                </div>

                <div class="form-group">
                    <input type="submit" value="Создать" class="form-control" id="inputSubmit"/>
                </div>
            </form>
        </div>
        <div class="col-md-1 col-lg-2 col-xs-3">

        </div>
    </div>

    <div class="row">
        <div class="col-md-1 col-lg-2 col-xs-3">

        </div>
        <div class="col-12 col-sm-12 col-md-10 col-lg-8 col-xs-6">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Название токена</th>
                    <th scope="col">Удалить</th>
                </tr>
                </thead>
                <tbody id="tokenListBody">
                <#list model.tokens as token>
                    <tr id="token-${token.getId()}">
                        <th scope="row">${token.getId()}</th>
                        <td>${token.getName()}</td>
                        <td>
                            <button class="btn btn-danger delete-token-modal"
                                    data-id="${token.getId()}" data-name="${token.getName()}"
                                    data-toggle="modal" data-target="#deleteTokenModal">
                                Удалить
                            </button>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="col-md-1 col-lg-2 col-xs-3">

        </div>
    </div>

    <div class="modal fade" id="deleteTokenModal" tabindex="-1" role="dialog" aria-labelledby="deleteTokenTitle"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addTokenTitle">Удаление токена "<span id="tokenNameSpan"></span>"</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div id="deleteInfo">

                    </div>
                    <div class="form-group" id="selectTokenGroup">
                        <label for="deleteTokenName">Введите токена для подтверждения</label>
                        <input type="text" class="form-control" id="deleteTokenName">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                    <button id="deleteTokenButton" type="button" class="btn btn-danger" data-dismiss="modal">Удалить
                    </button>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="<@spring.url "/"/>js/token.js"></script>

<#include "footer.ftl">
</body>
</html>
