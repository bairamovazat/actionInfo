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
                    <th scope="col">Почта</th>
                    <th scope="col">orcid</th>
                    <th scope="col">url</th>
                    <th scope="col">username</th>
                    <th scope="col">данные</th>
                </tr>
                </thead>
                <tbody id="steamIdRequestTableBody">

                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="showUserData" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Статистика пользователя</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label id="visitSites">Количество посещённых страниц: </label>
                    <br>
                    <label id="articleTotal">Количество поданных статей: </label>
                    <br>
                    <label id="articleSuccess">Количество принятых статей: </label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<script>


    var globalUserList = [];

    var globalUser = {};

    function loadSteamIdRequestList() {
        $.ajax({
            url: '<@spring.url "/"/>api/agrest/action-info-user',
            contentType: "application/json",
            error: function () {
                addError("Ошибка получения списка пользователей");
            },
            success: function (data) {
                globalUserList = data["data"];
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
            <td>${user.email}</td>
            <td>${user.orcid}</td>
            <td>${user.url}</td>
            <td>${user.username}</td>
            <td>
                <button class="btn btn-primary show-user-data"
                    data-id="${user.id}"
                    data-toggle="modal" data-target="#showUserData">
                    Данные
                </button>
            </td>
            `;
            tr.innerHTML = rowData;
            $("#steamIdRequestTableBody").append(tr);
        }
        </#noparse>
    }


    $(document).on("click", ".show-user-data", function () {
        let requestId = $(this).data('id');
        globalUser = globalUserList.find(e => e.id === requestId);

        $.ajax({
            url: '<@spring.url "/"/>api/agrest/action-info?include=context&include=user&exp=user=' + globalUser.id,
            contentType: "application/json",
            type: 'GET'
        })
            .then((data) => {
                updateUserData(data["data"]);
                addSuccess("Данные обновлены");
            })
            .catch((data) => {
                addError(data.responseJSON.message);
            })

    });

    function getUserData(user) {
        $.ajax({
            url: '<@spring.url "/"/>api/agrest/action-info',
            contentType: "application/json",
            error: function () {
                addError("Ошибка получения списка пользователей");
            },
            success: function (data) {
                globalUserList = data["data"];
                updateSteamIdRequestList();
            },
            type: 'GET'
        });
    }

    $(document).ready(function () {
        loadSteamIdRequestList();
    });

    function updateUserData(userData) {
        let pageLoadCount = 0;
        let submissionCount = 0;
        let successSubmissionCount = 0;

        userData.forEach(row => {
            if (row.type === "PAGE_LOAD") {
                pageLoadCount++;
            } else if (row.type === "SUBMISSION") {
                if (row.action == "submission.status.scheduled" || row.action == "submission.status.published") {
                    successSubmissionCount++;
                }
                submissionCount++;
            }
        });

        $("#visitSites")[0].innerText = "Количество посещённых страниц: " + pageLoadCount;
        $("#articleTotal")[0].innerText = "Количество поданных статей: " + submissionCount;
        $("#articleSuccess")[0].innerText = "Количество принятых статей: " + successSubmissionCount;
    }

    function removeSteamIdRequest(steamIdRequest) {
        globalUserList.splice(globalUserList.indexOf(steamIdRequest), 1);
    }
</script>

<#include "../footer.ftl">
</body>
</html>
