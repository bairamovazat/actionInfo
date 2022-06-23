<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html>
<#include "../head.ftl">
<body>
<#include "../header.ftl">
<div class="container-fluid">
    <div class="row">
        <div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xs-12">
            <div>
                <h2>
                    <b>Пользователи</b>
                </h2>
            </div>
            <div id="info">

            </div>
            <table class="table">
                <thead>
                <tr>
                    <th scope="row">ID пользователя</th>
                    <th scope="col">Логин</th>
                    <th scope="col">Почта</th>
                    <th scope="col">Роль</th>
                    <th scope="col">Статус</th>
                    <th scope="col" colspan="3">Изменить статус</th>
                </tr>
                </thead>
                <tbody id="steamIdRequestTableBody">

                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="acceptUser" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Подтвердить пользователя?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                <button id="acceptUserButton" type="button" class="btn btn-primary" data-dismiss="modal">
                    Подтвердить
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="banUser" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-danger">Забанить пользователя?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                <button id="banUserButton" type="button" class="btn btn-danger" data-dismiss="modal">
                    Забанить
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="newUser" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Изменить статус пользователя на новый ??</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                <button id="newUserButton" type="button" class="btn btn-primary" data-dismiss="modal">
                    Подтвердить
                </button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="editUserRole" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Изменить роли пользоватея</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <div class="form-group form-check">
                    <input class="form-check-input" type="checkbox" value="" id="roleUser">
                    <label class="form-check-label" for="roleAdmin">
                        User
                    </label>
                </div>
                <div class="form-group form-check">
                    <input class="form-check-input" type="checkbox" value="" id="roleCreator">
                    <label class="form-check-label" for="roleCreator">
                        Creator
                    </label>
                </div>
                <div class="form-group form-check">
                    <input class="form-check-input" type="checkbox" value="" id="roleAdmin">
                    <label class="form-check-label" for="roleAdmin">
                        Admin
                    </label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn  btn-secondary" data-dismiss="modal">Закрыть</button>
                <button id="editUserRoleButton" type="button" class="btn btn-primary" data-dismiss="modal">
                    Подтвердить
                </button>
            </div>
        </div>
    </div>
</div>

<script>


    var globalUserList = [];

    var globalUser = {};

    function loadSteamIdRequestList() {
        $.ajax({
            url: 'user-confirm',
            contentType: "application/json",
            error: function () {
                addError("Ошибка получения списка пользователей");
            },
            success: function (data) {
                globalUserList = data;
                updateSteamIdRequestList();
            },
            type: 'GET'
        });
    }

    function updateSteamIdRequestList() {
        $("#steamIdRequestTableBody").empty();
        let userList = globalUserList;
        <#noparse>
        for (let user of userList) {
            let tr = document.createElement("tr");
            let rowData = `<th scope="row">${user.id}</th>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>
                <button class="btn btn-primary edit-user-role"
                    data-id="${user.id}"
                    data-toggle="modal" data-target="#editUserRole">
                    Изменить
                </button>
                ${user.role?.toString()}
            </td>
            <td>${user.state}</td>

            <td>
                <button class="btn btn-primary new-steam-id-request"
                    data-id="${user.id}" ${user.state === "CREATED" ? "disabled" : ""}
                    data-toggle="modal" data-target="#newUser">
                Новый
                </button>
            </td>
            <td>
                <button class="btn btn-primary confirm-steam-id-request"
                    data-id="${user.id}" ${user.state === "CONFIRMED" ? "disabled" : ""}
                    data-toggle="modal" data-target="#acceptUser">
                Подтвердить
                </button>
            </td>
            <td>
                <button class="btn btn-primary ban-steam-id-request"
                    data-id="${user.id}" ${user.state === "BANNED" ? "disabled" : ""}
                    data-toggle="modal" data-target="#banUser">
                Забанить
                </button>
            </td>`;
            tr.innerHTML = rowData;
            $("#steamIdRequestTableBody").append(tr);
        }
        </#noparse>
    }

    $(document).on("click", ".new-steam-id-request", function () {
        let requestId = $(this).data('id');
        globalUser = globalUserList.find(e => e.id === requestId);
    });

    $(document).on("click", ".confirm-steam-id-request", function () {
        let requestId = $(this).data('id');
        globalUser = globalUserList.find(e => e.id === requestId);
    });

    $(document).on("click", ".ban-steam-id-request", function () {
        let requestId = $(this).data('id');
        globalUser = globalUserList.find(e => e.id === requestId);
    });

    $(document).on("click", ".edit-user-role", function () {
        let requestId = $(this).data('id');
        globalUser = globalUserList.find(e => e.id === requestId);
        $("#roleUser")[0].checked = (globalUser.role.indexOf("USER") !== -1);
        $("#roleCreator")[0].checked = (globalUser.role.indexOf("CREATOR") !== -1);
        $("#roleAdmin")[0].checked = (globalUser.role.indexOf("ADMIN") !== -1);
    });

    $(document).ready(function () {
        loadSteamIdRequestList();
    });

    $("#editUserRoleButton").click(() => {
        let user = globalUser;

        let role = [];

        if ($("#roleUser")[0].checked) {
            role.push("USER");
        }
        if ($("#roleCreator")[0].checked) {
            role.push("CREATOR");
        }
        if ($("#roleAdmin")[0].checked) {
            role.push("ADMIN");
        }

        user.role = role;

        $.ajax({
            url: 'user-role/' + user.id,
            contentType: "application/json",
            data: JSON.stringify(user),
            type: 'PUT'
        })
            .then((data) => {
                loadSteamIdRequestList();
                addSuccess("Роли обновлены");
            })
            .catch((data) => {
                addError(data.responseJSON.message);
            })
    });

    $("#acceptUserButton").click(() => {
        changeStatus("CONFIRMED")
    });

    $("#banUserButton").click(() => {
        changeStatus("BANNED")
    });

    $("#newUserButton").click(() => {
        changeStatus("CREATED")
    });

    function changeStatus(status) {
        let user = globalUser;
        updateUserState(user.id, {
            id: user.id,
            state: status,
        })
            .then((data) => {
                loadSteamIdRequestList();
                addSuccess("Успешно принят");
            })
            .catch((data) => {
                addError(data.responseJSON.message);
            })
    }

    function updateUserState(steamIdRequestId, steamIdRequestData) {
        return $.ajax({
            url: 'user-confirm/' + steamIdRequestId,
            contentType: "application/json",
            data: JSON.stringify(steamIdRequestData),
            type: 'PUT'
        });
    }

    function removeSteamIdRequest(steamIdRequest) {
        globalUserList.splice(globalUserList.indexOf(steamIdRequest), 1);
    }
</script>

<#include "../footer.ftl">
</body>
</html>
